package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;
import okhttp3.OkHttpClient;

/**
 * The helper to deal with job objects
 *
 * @author Simon Urli
 */
public class JobHelper extends EntityHelper {
    public JobHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public Job fromId(int id) {
        return getEntityFromUri(Job.class, TravisConstants.JOB_ENDPOINT, String.valueOf(id));
    }

    public Job fromId(String id) {
        return getEntityFromUri(Job.class,TravisConstants.JOB_ENDPOINT, id);
    }
}
