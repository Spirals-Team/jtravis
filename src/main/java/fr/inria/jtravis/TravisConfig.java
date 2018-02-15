package fr.inria.jtravis;

public class TravisConfig {
    private final String travisEndpoint;
    private final String travisToken;

    protected TravisConfig(String travisEndpoint, String travisToken) {
        this.travisEndpoint = travisEndpoint;
        this.travisToken = travisToken;
    }

    public String getTravisEndpoint() {
        return travisEndpoint;
    }

    public String getTravisToken() {
        return travisToken;
    }
}
