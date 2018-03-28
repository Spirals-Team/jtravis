package fr.inria.jtravis;

public class TravisConfig {
    private final String travisEndpoint;
    private final String travisToken;
    private final String githubToken;

    protected TravisConfig(String travisEndpoint, String travisToken, String githubToken) {
        this.travisEndpoint = travisEndpoint;
        this.travisToken = travisToken;
        this.githubToken = githubToken;
    }

    public String getTravisEndpoint() {
        return travisEndpoint;
    }

    public String getTravisToken() {
        return travisToken;
    }

    public String getGithubToken() {
        return githubToken;
    }
}
