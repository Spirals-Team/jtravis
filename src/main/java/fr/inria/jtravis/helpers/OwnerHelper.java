package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Owner;
import okhttp3.OkHttpClient;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHUser;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class OwnerHelper extends EntityHelper {

    public OwnerHelper(JTravis jTravis, TravisConfig config, OkHttpClient client) {
        super(jTravis, config, client);
    }

    public Optional<Owner> fromLogin(String login) {
        return this.getEntityFromUri(Owner.class, Arrays.asList(TravisConstants.OWNER_ENDPOINT, login), null);
    }
}
