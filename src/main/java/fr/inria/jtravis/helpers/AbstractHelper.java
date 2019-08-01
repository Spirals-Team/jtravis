package fr.inria.jtravis.helpers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.inria.jtravis.Http404Exception;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import static fr.inria.jtravis.helpers.AbstractHelper.API_VERSION.v2;
import static fr.inria.jtravis.helpers.AbstractHelper.API_VERSION.v3;

public abstract class AbstractHelper {
    enum API_VERSION {v2, v3}

    private static final String USER_AGENT = "MyClient/1.0.0";
    private JTravis jTravis;

    AbstractHelper(JTravis jTravis) {
        this.jTravis = jTravis;
    }

    public JTravis getjTravis() {
        return jTravis;
    }

    public TravisConfig getConfig() {
        return this.getjTravis().getTravisConfig();
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    /** @param url starts with slash (no protocol yet, no server name) */
    private Request requestBuilder(String url, API_VERSION version) {
        Request.Builder builder = new Request.Builder()
                .header("User-Agent",USER_AGENT);

        if (version == v3 && !url.startsWith("/v3")) {
            url = "/v3" + url;
        } else if (version == v2) {
            builder.header("Accept", TravisConstants.TRAVIS_API_V2_ACCEPT_APP);
        }

        // now we can add the endpoint
        url = this.getConfig().getTravisEndpoint() + url;

        if (this.getConfig().getTravisToken() != null && !this.getConfig().getTravisToken().isEmpty()) {
            builder.header("Authorization", this.getConfig().getTravisToken());
        }

        return builder.url(HttpUrl.parse(url)).get().build();
    }

    private void checkResponse(Response response) throws IOException {
        if (response.code() != 200) {
            if (response.code() == 404) {
                throw new Http404Exception(response.message());
            }
            throw new IOException("The response answer to "+response.request().url().toString()+" is not 200: "+response.code()+" "+response.message());
        }
    }

    protected String get(String url) throws IOException {
        return this.get(url, v3, TravisConstants.DEFAULT_NUMBER_OF_RETRY);
    }

    protected String get(String url, API_VERSION version, int numberOfRetry) throws IOException {
        Request request = this.requestBuilder(url, version);
        try {
            Call call = this.getjTravis().getHttpClient().newCall(request);
            long dateBegin = new Date().getTime();
            Response response = call.execute(); // might throw IOException (e.g. in case of timeout)
            long dateEnd = new Date().getTime();
            this.getLogger().debug("Get request to :" + url + " done after " + (dateEnd - dateBegin) + " ms");
            checkResponse(response); // might throw IOException and Http404Exception
            ResponseBody responseBody = response.body();
            String result = responseBody.string();
            response.close();
            return result;
        // if it's 404 it's unecessary to redo the request
        // so let throw it and handle it elsewhere
        } catch (Http404Exception e) {
            throw e;

        // in case of any other IOException,
        // it might be useful to try it again
        } catch (IOException e) {
            if (numberOfRetry <= 0) {
                throw e;
            } else {
                this.getLogger().debug("Error while executing the request " +url+ " ("+e.getMessage()+"). Let's wait and try it again.");
                // sleeping for some time before retrying (in case of 429 or 500)
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    throw new RuntimeException(e1);
                }
                return this.get(url, version, numberOfRetry-1);
            }
        }
    }

    public static JsonObject getJsonFromStringContent(String content) {
        JsonParser parser = new JsonParser();
        return parser.parse(content).getAsJsonObject();
    }

    public static Gson createGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    /**
     * Build a valid URL for Travis CI API based on the endpoint registered in the configuration
     * and the given components of the path and query parameters.
     * @param pathComponent base component of the path, generally an entity and optionnaly its id (like /repos/12)
     * @param queryParameters filters for the request, those parameters are passed after to filter the request (like /repos/12/builds?state=failed).
     *                        Please note that the value of the parameters is case sensitive.
     * @return A proper URL for Travis CI
     */
    protected String buildUrl(List<String> pathComponent, Properties queryParameters) {

        StringBuilder stringBuilder = new StringBuilder(StringUtils.join(pathComponent, "/"));

        if (queryParameters != null && !queryParameters.isEmpty()) {
            stringBuilder.append("?");
            int size = queryParameters.size();
            int i = 0;
            for (Map.Entry<Object, Object> queryParameter : queryParameters.entrySet()) {
                stringBuilder.append(queryParameter.getKey().toString().toLowerCase());
                stringBuilder.append("=");
                stringBuilder.append(queryParameter.getValue().toString());
                if (++i < size) {
                    stringBuilder.append("&");
                }
            }
        }

        String result = stringBuilder.toString();
        if (!result.startsWith("/")) {
            result = "/"+result;
        }

        return result;
    }

    public Optional<String> getEncodedSlug(String slug) {
        String encodedSlug;
        try {
            encodedSlug = URLEncoder.encode(slug, "UTF-8");

            boolean checkSlugExists = this.checkSlugExists(encodedSlug);
            if (checkSlugExists) {
                return Optional.of(encodedSlug);
            } else {
                Optional<String> rightSlug = this.searchCurrentRepoNameWithGHAPI(slug);
                if (rightSlug.isPresent()) {
                    getLogger().warn("The slug name has changed! "+slug+" is now "+rightSlug.get());
                    return this.getEncodedSlug(rightSlug.get());
                }
            }
        } catch (UnsupportedEncodingException e) {
            getLogger().error("Error while encoding given repository slug: "+slug, e);
        } catch (IOException e) {
            getLogger().error("Error while checking existence of the slug on TravisCI: "+slug, e);
        }
        return Optional.empty();
    }

    /**
     * Check that a slug name is recognized by TravisCI
     * @param encodedSlug A slug name encoded for URL
     * @return true if it is recognized by the API
     */
    private boolean checkSlugExists(String encodedSlug) throws IOException {
        List<String> pathComponent = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                encodedSlug);
        String url = this.buildUrl(pathComponent, null);
        try {
            this.get(url);
            return true;
        } catch (Http404Exception e) {
            return false;
        }
    }

    private Optional<String> searchCurrentRepoNameWithGHAPI(String slug) {
        GitHub gitHub;
        try {
            gitHub = this.getjTravis().getGithub();
            GHRepository repository = gitHub.getRepository(slug);
            if (repository != null) {
                return Optional.of(repository.getFullName());
            }
        } catch (IOException e) {
            getLogger().error("Error while trying to retrieve the repository from slug "+slug, e);
        }
        return Optional.empty();
    }

}
