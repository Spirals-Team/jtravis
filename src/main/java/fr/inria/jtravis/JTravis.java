package fr.inria.jtravis;

import fr.inria.jtravis.entities.Entity;
import fr.inria.jtravis.entities.EntityCollection;
import fr.inria.jtravis.helpers.EntityHelper;
import fr.inria.jtravis.helpers.JobHelper;
import fr.inria.jtravis.helpers.RepositoryHelper;
import okhttp3.OkHttpClient;

public class JTravis {
    private TravisConfig travisConfig;
    private OkHttpClient client;
    private RepositoryHelper repositoryHelper;
    private EntityHelper entityHelper;
    private JobHelper jobHelper;

    public static class Builder {
        private String token;
        private String endpoint;

        public Builder() {
            this.endpoint = TravisConstants.TRAVIS_API_ENDPOINT;
            this.token = System.getenv(TravisConstants.TOKEN_ENV_PROPERTY);
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

    private JTravis(TravisConfig travisConfig) {
        this.client = new OkHttpClient();
        this.travisConfig = travisConfig;
    }

    private EntityHelper entityHelper() {
        if (this.entityHelper == null) {
            this.entityHelper = new EntityHelper(this.travisConfig, this.client);
        }

        return this.entityHelper;
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

    public boolean refresh(Entity entity) {
        return this.entityHelper().refresh(entity);
    }

    public <T extends EntityCollection> T getNextCollection(T entityCollection) {
        return entityHelper().getNextCollection(entityCollection);
    }
}
