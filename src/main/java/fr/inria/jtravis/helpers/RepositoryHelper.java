package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.Repository;
import okhttp3.OkHttpClient;

/**
 * The helper to deal with repository objects.
 *
 * @author Simon Urli
 */
public class RepositoryHelper extends EntityHelper {
    protected static final String REPO_ENDPOINT = "/repo/";

    public RepositoryHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public Repository fromSlug(String slug) {
        return getEntityFromUri(Repository.class,REPO_ENDPOINT+slug);
    }

    public Repository fromId(int id) {
        return getEntityFromUri(Repository.class,REPO_ENDPOINT+id);
    }

    public Repository fromId(String id) {
        return getEntityFromUri(Repository.class,REPO_ENDPOINT+id);
    }
}
