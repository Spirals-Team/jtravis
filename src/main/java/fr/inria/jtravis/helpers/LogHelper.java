package fr.inria.jtravis.helpers;

import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.Log;
import fr.inria.jtravis.entities.StateType;

import java.io.IOException;

/**
 * The helper to deal with log objects
 *
 * @author Simon Urli
 */
public class LogHelper extends AbstractHelper {
    public static final String LOG_ENDPOINT = "logs/";
    public static final String LOG_JOB_ENDPOINT = "/log";

    private static LogHelper instance;

    private LogHelper() {
        super();
    }

    protected static LogHelper getInstance() {
        if (instance == null) {
            instance = new LogHelper();
        }
        return instance;
    }

    public static Log getLogFromJob(Job job) {
        /*if (job.getId() != 0) {
            if (job.getState() == StateType.FAILED || job.getState() == StateType.PASSED || job.getState() == StateType.ERRORED) {
                String logJobUrl = getInstance().getEndpoint()+JobHelper.JOB_ENDPOINT+job.getId()+LOG_JOB_ENDPOINT;
                try {
                    String body = getInstance().rawGet(logJobUrl);
                    return new Log(job.getId(), body);
                } catch (IOException e) {
                    getInstance().getLogger().warn("Error when getting log from job id "+job.getId()+" ",e);
                }
            } else {
                getInstance().getLogger().warn("Error when getting log from job id "+job.getId()+" : build status is neither failed or passed: "+job.getState().name());
            }
        }*/
        return null;
    }
}
