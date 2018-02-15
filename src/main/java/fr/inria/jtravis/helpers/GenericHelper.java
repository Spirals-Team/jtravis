package fr.inria.jtravis.helpers;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.auth.TokenReader;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;
import java.util.Date;

/**
 * This abstract helper is the base helper for all the others.
 * It defines constants for Travis CI API and methods to do requests and to parse them.
 *
 * @author Simon Urli
 */
public class GenericHelper {
    public static final String TRAVIS_API_ENDPOINT="https://api.travis-ci.org/";
    private static final String USER_AGENT = "MyClient/1.0.0";

    private static GenericHelper instance;
    private OkHttpClient client;
    private GitHub github;

    GenericHelper() {
        client = new OkHttpClient();
    }

    protected static GenericHelper getInstance() {
        if (instance == null) {
            instance = new GenericHelper();
        }
        return instance;
    }


    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    private Request.Builder requestBuilder(String url) {
        String token = "token " + TokenReader.fromEnvironment();
        return new Request.Builder()
                .header("User-Agent",USER_AGENT)
                .header("Travis-API-Version", "3")
                .header("Authorization", token)
                .url(JTravis.getInstance().getTravisEndpoint()+url);
    }

    private void checkResponse(Response response) throws IOException {
        if (response.code() != 200) {
            throw new IOException("The response answer to "+response.request().url().toString()+" is not 200: "+response.code()+" "+response.message());
        }
    }

    protected GitHub getGithub() throws IOException {
        if (this.github == null) {
            if (GithubTokenHelper.getInstance().isAvailable()) {
                try {
                    this.getLogger().debug("Get GH login: "+GithubTokenHelper.getInstance().getGithubLogin()+ "; OAuth (10 first characters): "+GithubTokenHelper.getInstance().getGithubOauth().substring(0,10));
                    this.github = GitHubBuilder.fromEnvironment().withOAuthToken(GithubTokenHelper.getInstance().getGithubOauth(), GithubTokenHelper.getInstance().getGithubLogin()).build();
                } catch (IOException e) {
                    this.getLogger().warn("Error while using credentials: try to use anonymous access to github.", e);
                    this.github = GitHub.connectAnonymously();
                    this.getLogger().warn("No github information has been given to connect (set GITHUB_OAUTH and GITHUB_LOGIN), you will have a very low ratelimit for github requests.");
                }

            } else {
                this.github = GitHub.connectAnonymously();
                this.getLogger().warn("No github information has been given to connect (set GITHUB_OAUTH and GITHUB_LOGIN), you will have a very low ratelimit for github requests.");
            }
        }
        return this.github;
    }

    protected String rawGet(String url) throws IOException {
        Request request = new Request.Builder().url(url).build();
        Call call = this.client.newCall(request);
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
        Request request = this.requestBuilder(url).get().build();
        Call call = this.client.newCall(request);
        long dateBegin = new Date().getTime();
        this.getLogger().debug("Execute get request to the following URL: "+url);
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

    public static <T extends Entity> T getEntityFromUri(Class<T> zeClass, String uri) {
        try {
            String jsonContent = getInstance().get(uri);
            JsonObject jsonObj = getJsonFromStringContent(jsonContent);
            if (jsonObj != null) {
                return createGson().fromJson(jsonObj, zeClass);
            }
        } catch (IOException e) {
            getInstance().getLogger().error("Error while getting JSON at URL: "+uri);
        }

        return null;
    }

}
