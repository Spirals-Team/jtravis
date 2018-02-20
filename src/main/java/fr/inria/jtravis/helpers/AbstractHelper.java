package fr.inria.jtravis.helpers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

public abstract class AbstractHelper {
    private static final String USER_AGENT = "MyClient/1.0.0";

    private TravisConfig config;
    private OkHttpClient client;
    private GitHub github;

    AbstractHelper(TravisConfig config, OkHttpClient client) {
        this.config = config;
        this.client = client;
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    private Request.Builder requestBuilder(String url) {
        return new Request.Builder()
                .header("User-Agent",USER_AGENT)
                .header("Travis-API-Version", "3")
                .header("Authorization", this.config.getTravisToken())
                .url(url);
    }

    private void checkResponse(Response response) throws IOException {
        if (response.code() != 200) {
            throw new IOException("The response answer to "+response.request().url().toString()+" is not 200: "+response.code()+" "+response.message());
        }
    }

    protected GitHub getGithub() throws IOException {
        if (this.github == null) {
            if (!this.config.getGithubLogin().isEmpty() && !this.config.getGithubToken().isEmpty()) {
                try {
                    this.getLogger().debug("Get GH login: "+this.config.getGithubLogin()+ "; OAuth (10 first characters): "+this.config.getGithubToken().substring(0,10));
                    this.github = GitHubBuilder.fromEnvironment().withOAuthToken(this.config.getGithubToken(), this.config.getGithubLogin()).build();
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

    public String buildUrl(String... uriComponent) {

        String result;
        if (uriComponent.length > 1) {
            result = StringUtils.join(uriComponent, "/");
        } else {
            result = uriComponent[0];
        }

        if (!result.startsWith("/")) {
            result = "/"+result;
        }

        return this.config.getTravisEndpoint()+result;
    }
}
