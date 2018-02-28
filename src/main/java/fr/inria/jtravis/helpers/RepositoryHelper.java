package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Repository;
import okhttp3.OkHttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

/**
 * The helper to deal with repository objects.
 *
 * @author Simon Urli
 */
public class RepositoryHelper extends EntityHelper {
    public RepositoryHelper(JTravis jTravis, TravisConfig config, OkHttpClient client) {
        super(jTravis, config, client);
    }

    public Optional<Repository> fromSlug(String slug) {
        Optional<String> optionalS = this.getEncodedSlug(slug);
        if (optionalS.isPresent()) {
            return getEntityFromUri(Repository.class, Arrays.asList(
                    TravisConstants.REPO_ENDPOINT,
                    optionalS.get()), null);
        } else {
            return Optional.empty();
        }
    }

    public Optional<Repository> fromId(int id) {
        return getEntityFromUri(Repository.class, Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(id)), null);
    }

    public Optional<Repository> fromId(String id) {
        return getEntityFromUri(Repository.class, Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                id), null);
    }
}
