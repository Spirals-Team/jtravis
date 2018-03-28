package fr.inria.jtravis.helpers;

/**
 * Created by urli on 27/06/2017.
 */
public class GithubTokenHelper {
    private static GithubTokenHelper instance;

    private String githubOauth;

    private GithubTokenHelper() {}

    public static GithubTokenHelper getInstance() {
        if (instance == null) {
            instance = new GithubTokenHelper();
        }
        return instance;
    }

    public String getGithubOauth() {
        return (isAvailable()) ? githubOauth : null;
    }

    public void setGithubOauth(String githubOauth) {
        this.githubOauth = githubOauth;
    }

    public boolean isAvailable() {
        if (this.githubOauth != null) {
            return true;
        }

        if (System.getenv("GITHUB_OAUTH") != null && !System.getenv("GITHUB_OAUTH").equals("")) {
            this.setGithubOauth(System.getenv("GITHUB_OAUTH"));
            return true;
        }

        return false;
    }
}
