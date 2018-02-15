package fr.inria.jtravis;

import fr.inria.jtravis.entities.Entity;
import fr.inria.jtravis.entities.EntityCollection;
import fr.inria.jtravis.helpers.EntityHelper;
import fr.inria.jtravis.helpers.JobHelper;
import fr.inria.jtravis.helpers.RepositoryHelper;
import okhttp3.OkHttpClient;

public class JTravis {
    public static final String TRAVIS_API_ENDPOINT="https://api.travis-ci.org";
    private static final String TOKEN_ENV_PROPERTY = "TRAVIS_TOKEN";

    private TravisConfig travisConfig;
    private OkHttpClient client;
    private RepositoryHelper repositoryHelper;
    private EntityHelper entityHelper;
    private JobHelper jobHelper;


    private JTravis(TravisConfig travisConfig) {
        this.client = new OkHttpClient();
        this.travisConfig = travisConfig;
    }

    public RepositoryHelper repository() {
        if (repositoryHelper == null) {
            this.repositoryHelper = new RepositoryHelper(this.travisConfig, this.client);
        }

        return this.repositoryHelper;
    }

    public JobHelper job() {
        if (this.jobHelper == null) {
            this.jobHelper = new JobHelper(this.travisConfig, this.client);
        }

        return this.jobHelper;
    }

    public static Builder builder() {
        return new Builder();
    }

    private EntityHelper entityHelper() {
        if (this.entityHelper == null) {
            this.entityHelper = new EntityHelper(this.travisConfig, this.client);
        }

        return this.entityHelper;
    }

    public boolean refresh(Entity entity) {
        return this.entityHelper().refresh(entity);
    }

    public <T extends EntityCollection> T getNextCollection(T entityCollection) {
        return entityHelper().getNextCollection(entityCollection);
    }

    public static class Builder {
        private String token;
        private String endpoint;

        public Builder() {
            this.endpoint = TRAVIS_API_ENDPOINT;
            this.token = System.getenv(TOKEN_ENV_PROPERTY);
            if (this.token == null) {
                this.token = "";
            }
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }

        public JTravis build() {
            TravisConfig travisConfig = new TravisConfig(this.endpoint, this.token);
            return new JTravis(travisConfig);
        }
    }
}
