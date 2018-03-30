package fr.inria.jtravis;

import fr.inria.jtravis.entities.Entity;
import fr.inria.jtravis.entities.EntityCollection;
import fr.inria.jtravis.helpers.BuildHelper;
import fr.inria.jtravis.helpers.EntityHelper;
import fr.inria.jtravis.helpers.JobHelper;
import fr.inria.jtravis.helpers.LogHelper;
import fr.inria.jtravis.helpers.OwnerHelper;
import fr.inria.jtravis.helpers.PullRequestHelper;
import fr.inria.jtravis.helpers.RepositoryHelper;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.Set;

public class JTravis {
    private static final Logger LOGGER = LoggerFactory.getLogger(JTravis.class);
    private static final JTravis instance = new JTravis();
    private static final SimpleDateFormat hourSimpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    private TravisConfig travisConfig;
    private OkHttpClient client;
    private GitHub github;

    private RepositoryHelper repositoryHelper;
    private EntityHelper entityHelper;
    private JobHelper jobHelper;
    private PullRequestHelper pullRequestHelper;
    private BuildHelper buildHelper;
    private LogHelper logHelper;
    private OwnerHelper ownerHelper;

    public static class Builder {
        private String travisToken;
        private String endpoint;
        private String githubToken;

        Builder() {
            this.endpoint = TravisConstants.TRAVIS_API_ENDPOINT;
            this.setTravisToken(System.getenv(TravisConstants.TRAVIS_TOKEN_ENV_PROPERTY));
            this.setGithubToken(System.getenv(TravisConstants.GITHUB_TOKEN_ENV_PROPERTY));
        }

        public Builder setTravisToken(String travisToken) {
            if (travisToken != null) {
                this.travisToken = travisToken;
            } else {
                this.travisToken = "";
            }
            return this;
        }

        public Builder setEndpoint(String endpoint) {
            if (endpoint != null) {
                this.endpoint = endpoint;
            } else {
                this.endpoint = "";
            }
            return this;
        }

        public Builder setGithubToken(String githubToken) {
            if (githubToken != null) {
                this.githubToken = githubToken;
            } else {
                this.githubToken = "";
            }
            return this;
        }

        public JTravis build() {
            TravisConfig travisConfig = new TravisConfig(this.endpoint, this.travisToken, this.githubToken);
            instance.reset(travisConfig);
            return instance;
        }
    }

    private JTravis() {
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("rwxrwxrwx");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        try {
            File cacheDirectory = Files.createTempDirectory("okhttpcache", attr).toFile();
            Cache cache = new Cache(cacheDirectory, TravisConstants.DEFAULT_OKHTTP_CACHE_SIZE);
            this.client = new OkHttpClient.Builder().cache(cache).build();
        } catch (IOException e) {
            LOGGER.error("Error while doing the setup for OKhttp cache", e);
            this.client = new OkHttpClient();
        }
    }

    private void reset(TravisConfig travisConfig) {
        this.travisConfig = travisConfig;
        this.client = new OkHttpClient();
    }

    public GitHub getGithub() throws IOException {
        if (this.github == null) {
            if (!this.travisConfig.getGithubToken().isEmpty()) {
                try {
                    LOGGER.debug("Get GH OAuth (10 first characters): "+this.travisConfig.getGithubToken().substring(0,10));
                    this.github = GitHubBuilder.fromEnvironment().withOAuthToken(this.travisConfig.getGithubToken()).build();
                } catch (IOException e) {
                    LOGGER.warn("Error while using credentials: try to use anonymous access to github.", e);
                    this.github = GitHub.connectAnonymously();
                    LOGGER.warn("No github information has been given to connect (set GITHUB_OAUTH), you will have a very low ratelimit for github requests.");
                }
            } else {
                this.github = GitHub.connectAnonymously();
                LOGGER.warn("No github information has been given to connect (set GITHUB_OAUTH), you will have a very low ratelimit for github requests.");
            }
        }
        GHRateLimit rateLimit = this.github.getRateLimit();

        LOGGER.info("GitHub ratelimit: Limit: " + rateLimit.limit + " Remaining: " + rateLimit.remaining + " Reset hour: " + hourSimpleDateFormat.format(rateLimit.reset));
        return this.github;
    }

    public OkHttpClient getHttpClient() {

        return this.client;
    }

    public TravisConfig getTravisConfig() {
        return this.travisConfig;
    }

    private EntityHelper entityHelper() {
        if (this.entityHelper == null) {
            this.entityHelper = new EntityHelper(this);
        }

        return this.entityHelper;
    }

    public RepositoryHelper repository() {
        if (repositoryHelper == null) {
            this.repositoryHelper = new RepositoryHelper(this);
        }

        return this.repositoryHelper;
    }

    public JobHelper job() {
        if (this.jobHelper == null) {
            this.jobHelper = new JobHelper(this);
        }

        return this.jobHelper;
    }

    public PullRequestHelper pullRequest() {
        if (this.pullRequestHelper == null) {
            this.pullRequestHelper = new PullRequestHelper(this);
        }
        return pullRequestHelper;
    }

    public BuildHelper build() {
        if (this.buildHelper == null) {
            this.buildHelper = new BuildHelper(this);
        }
        return buildHelper;
    }

    public LogHelper log() {
        if (this.logHelper == null) {
            this.logHelper = new LogHelper(this);
        }
        return this.logHelper;
    }

    public OwnerHelper owner() {
        if (this.ownerHelper == null) {
            this.ownerHelper = new OwnerHelper(this);
        }
        return this.ownerHelper;
    }

    public static Builder builder() {
        return new Builder();
    }

    public boolean refresh(Entity entity) {
        return this.entityHelper().refresh(entity);
    }

    public <T extends EntityCollection> Optional<T> getNextCollection(T entityCollection) {
        return entityHelper().getNextCollection(entityCollection);
    }
}
