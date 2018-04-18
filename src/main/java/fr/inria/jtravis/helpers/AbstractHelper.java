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
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

public abstract class AbstractHelper {
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

    private Request.Builder requestBuilder(String url, boolean useV2) {
        Request.Builder builder = new Request.Builder()
                .header("User-Agent",USER_AGENT);

        if (useV2) {
            builder.header("Accept", TravisConstants.TRAVIS_API_V2_ACCEPT_APP);
        } else {
            builder.header("Travis-API-Version", "3");
        }

        if (this.getConfig().getTravisToken() != null && !this.getConfig().getTravisToken().isEmpty()) {
            builder.header("Authorization", this.getConfig().getTravisToken());
        }

        return builder.url(url);
    }

    private void checkResponse(Response response) throws IOException {
        if (response.code() != 200) {
            throw new IOException("The response answer to "+response.request().url().toString()+" is not 200: "+response.code()+" "+response.message());
        }
    }

    protected String rawGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = this.getjTravis().getHttpClient().newCall(request);
        long dateBegin = new Date().getTime();
        this.getLogger().debug("Execute raw get request to the following URL: "+url);
        Response response = call.execute();
        long dateEnd = new Date().getTime();
        this.getLogger().debug("Raw get request to :"+url+" done after "+(dateEnd-dateBegin)+" ms");
        checkResponse(response);
        ResponseBody responseBody = response.body();
        String result = responseBody.string();
        response.close();
        return result;
    }

    protected String get(String url) throws IOException {
        return this.get(url, false);
    }

    protected String get(String url, boolean useV2) throws IOException {
        Request request = this.requestBuilder(url, useV2).get().build();
        Call call = this.getjTravis().getHttpClient().newCall(request);
        long dateBegin = new Date().getTime();
        Response response = call.execute();
        long dateEnd = new Date().getTime();
        this.getLogger().debug("Get request to :"+url+" done after "+(dateEnd-dateBegin)+" ms");
        checkResponse(response);
        ResponseBody responseBody = response.body();
        String result = responseBody.string();
        response.close();
        return result;
    }

    public static JsonObject getJsonFromStringContent(String content) {
        JsonParser parser = new JsonParser();
        return parser.parse(content).getAsJsonObject();
    }

    public static Gson createGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    protected String buildUrl(List<String> pathComponent, Properties queryParameters) {

        StringBuilder stringBuilder = new StringBuilder(StringUtils.join(pathComponent, "/"));

        if (queryParameters != null && !queryParameters.isEmpty()) {
            stringBuilder.append("?");
            int size = queryParameters.size();
            int i = 0;
            for (Map.Entry<Object, Object> queryParameter : queryParameters.entrySet()) {
                stringBuilder.append(queryParameter.getKey().toString().toLowerCase());
                stringBuilder.append("=");
                stringBuilder.append(queryParameter.getValue().toString().toLowerCase());
                if (++i < size) {
                    stringBuilder.append("&");
                }
            }
        }

        String result = stringBuilder.toString();
        if (!result.startsWith("/")) {
            result = "/"+result;
        }

        return this.getConfig().getTravisEndpoint()+result;
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
