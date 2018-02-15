package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Repository;
import okhttp3.OkHttpClient;

/**
 * The helper to deal with repository objects.
 *
 * @author Simon Urli
 */
public class RepositoryHelper extends EntityHelper {
    public RepositoryHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public Repository fromSlug(String slug) {
        return getEntityFromUri(Repository.class, TravisConstants.REPO_ENDPOINT, slug);
    }

    public Repository fromId(int id) {
        return getEntityFromUri(Repository.class,TravisConstants.REPO_ENDPOINT, String.valueOf(id));
    }

    public Repository fromId(String id) {
        return getEntityFromUri(Repository.class,TravisConstants.REPO_ENDPOINT, id);
    }
}
