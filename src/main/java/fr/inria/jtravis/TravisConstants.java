package fr.inria.jtravis;

public class TravisConstants {
    public static final String TRAVIS_API_V2_ACCEPT_APP = "application/vnd.travis-ci.2+json";

    public static final String TRAVIS_API_ENDPOINT="https://api.travis-ci.org";
    public static final String TRAVIS_COMMERCIAL_API_ENDPOINT="https://api.travis-ci.com";

    public static final String TRAVIS_TOKEN_ENV_PROPERTY = "TRAVIS_TOKEN";
    public static final String GITHUB_TOKEN_ENV_PROPERTY = "GITHUB_OAUTH";

    public static final String BUILDS_ENDPOINT = "builds";
    public static final String BUILD_ENDPOINT = "build";
    public static final String REPO_ENDPOINT = "repo";
    public static final String REPOS_ENDPOINT = "repos";
    public static final String JOB_ENDPOINT = "job";
    public static final String JOBS_ENDPOINT = "jobs";
    public static final String LOG_ENDPOINT = "log";
}
