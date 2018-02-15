package fr.inria.jtravis.helpers;

import fr.inria.jtravis.entities.Job;

/**
 * The helper to deal with job objects
 *
 * @author Simon Urli
 */
public class JobHelper extends GenericHelper {
    protected static final String JOB_ENDPOINT = "job/";

    private static JobHelper instance;

    private JobHelper() {
        super();
    }

    public static JobHelper getInstance() {
        if (instance == null) {
            instance = new JobHelper();
        }
        return instance;
    }

    public Job fromId(int id) {
        return getEntityFromUri(Job.class,JOB_ENDPOINT+id);
    }

    public Job fromId(String id) {
        return getEntityFromUri(Job.class,JOB_ENDPOINT+id);
    }
}
