package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Repository;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

/**
 * The helper to deal with repository objects.
 *
 * @author Simon Urli
 */
public class RepositoryHelper extends EntityHelper {
    public RepositoryHelper(JTravis jTravis) {
        super(jTravis);
    }

    public Optional<Repository> fromSlug(String slug) {
        Optional<Repository> repo = null;

        Optional<String> optionalS = this.getEncodedSlug(slug);
        if (optionalS.isPresent()) {
            repo = getEntityFromUri(Repository.class, Arrays.asList(
                    TravisConstants.REPO_ENDPOINT,
                    optionalS.get()), null);
            if (!repo.isPresent()) {
                String newSlug = searchCurrentRepoNameWithGHAPI(slug);
                if (newSlug != null && !newSlug.equals(slug)) {
                    repo = this.fromSlug(newSlug);
                }
            }
        }
        if (repo != null && repo.isPresent()) {
            return repo;
        } else {
            return Optional.empty();
        }
    }

    protected String searchCurrentRepoNameWithGHAPI(String slug) {
        JTravis jTravis = JTravis.builder().build();
        GitHub gitHub;
        try {
            gitHub = jTravis.getGithub();
            GHRepository repository = gitHub.getRepository(slug);
            return repository.getFullName();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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