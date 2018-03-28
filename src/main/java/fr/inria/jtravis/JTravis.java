package fr.inria.jtravis;

import fr.inria.jtravis.entities.Entity;
import fr.inria.jtravis.entities.EntityCollection;
import fr.inria.jtravis.helpers.BuildHelper;
import fr.inria.jtravis.helpers.EntityHelper;
import fr.inria.jtravis.helpers.JobHelper;
import fr.inria.jtravis.helpers.LogHelper;
import fr.inria.jtravis.helpers.PullRequestHelper;
import fr.inria.jtravis.helpers.RepositoryHelper;
import okhttp3.OkHttpClient;

import java.util.Optional;

public class JTravis {
    private TravisConfig travisConfig;
    private OkHttpClient client;

    private RepositoryHelper repositoryHelper;
    private EntityHelper entityHelper;
    private JobHelper jobHelper;
    private PullRequestHelper pullRequestHelper;
    private BuildHelper buildHelper;
    private LogHelper logHelper;

    public static class Builder {
        private String travisToken;
        private String endpoint;
        private String githubToken;

        public Builder() {
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
            return new JTravis(travisConfig);
        }
    }

    private JTravis(TravisConfig travisConfig) {
        this.client = new OkHttpClient();
        this.travisConfig = travisConfig;
    }

    private EntityHelper entityHelper() {
        if (this.entityHelper == null) {
            this.entityHelper = new EntityHelper(this, this.travisConfig, this.client);
        }

        return this.entityHelper;
    }

    public RepositoryHelper repository() {
        if (repositoryHelper == null) {
            this.repositoryHelper = new RepositoryHelper(this, this.travisConfig, this.client);
        }

        return this.repositoryHelper;
    }

    public JobHelper job() {
        if (this.jobHelper == null) {
            this.jobHelper = new JobHelper(this, this.travisConfig, this.client);
        }

        return this.jobHelper;
    }

    public PullRequestHelper pullRequest() {
        if (this.pullRequestHelper == null) {
            this.pullRequestHelper = new PullRequestHelper(this, this.travisConfig, this.client);
        }
        return pullRequestHelper;
    }

    public BuildHelper build() {
        if (this.buildHelper == null) {
            this.buildHelper = new BuildHelper(this, this.travisConfig, this.client);
        }
        return buildHelper;
    }

    public LogHelper log() {
        if (this.logHelper == null) {
            this.logHelper = new LogHelper(this, this.travisConfig, this.client);
        }
        return this.logHelper;
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
