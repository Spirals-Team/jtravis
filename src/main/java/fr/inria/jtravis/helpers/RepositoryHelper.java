package fr.inria.jtravis.helpers;

import fr.inria.jtravis.entities.Repository;

/**
 * The helper to deal with repository objects.
 *
 * @author Simon Urli
 */
public class RepositoryHelper extends GenericHelper {
    protected static final String REPO_ENDPOINT = "/repo/";

    private static RepositoryHelper instance;

    private RepositoryHelper() {
        super();
    }

    public static RepositoryHelper getInstance() {
        if (instance == null) {
            instance = new RepositoryHelper();
        }
        return instance;
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
