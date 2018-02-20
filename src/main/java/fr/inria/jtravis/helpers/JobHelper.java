package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;
import okhttp3.OkHttpClient;

import java.util.Optional;

/**
 * The helper to deal with job objects
 *
 * @author Simon Urli
 */
public class JobHelper extends EntityHelper {
    public JobHelper(JTravis jTravis, TravisConfig config, OkHttpClient client) {
        super(jTravis, config, client);
    }

    public Optional<Job> fromId(int id) {
        return getEntityFromUri(Job.class, TravisConstants.JOB_ENDPOINT, String.valueOf(id));
    }

    public Optional<Job> fromId(String id) {
        return getEntityFromUri(Job.class,TravisConstants.JOB_ENDPOINT, id);
    }
}
