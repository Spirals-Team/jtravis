package fr.inria.jtravis.auth;

public class TokenReader {
    private static String TOKEN_ENV_PROPERTY = "TRAVIS_TOKEN";

    public static String fromEnvironment() {
        return System.getenv(TOKEN_ENV_PROPERTY);
    }
}
