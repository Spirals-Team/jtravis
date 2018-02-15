package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.Job;
import okhttp3.OkHttpClient;

/**
 * The helper to deal with job objects
 *
 * @author Simon Urli
 */
public class JobHelper extends EntityHelper {
    protected static final String JOB_ENDPOINT = "/job/";

    public JobHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public Job fromId(int id) {
        return getEntityFromUri(Job.class,JOB_ENDPOINT+id);
    }

    public Job fromId(String id) {
        return getEntityFromUri(Job.class,JOB_ENDPOINT+id);
    }
}
