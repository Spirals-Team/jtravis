package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.Log;
import okhttp3.OkHttpClient;

/**
 * The helper to deal with log objects
 *
 * @author Simon Urli
 */
public class LogHelper extends EntityHelper {
    public static final String LOG_ENDPOINT = "logs/";
    public static final String LOG_JOB_ENDPOINT = "/log";

    public LogHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }


    public static Log getLogFromJob(Job job) {
        /*if (job.getId() != 0) {
            if (job.getState() == StateType.FAILED || job.getState() == StateType.PASSED || job.getState() == StateType.ERRORED) {
                String logJobUrl = build().getEndpoint()+JobHelper.JOB_ENDPOINT+job.getId()+LOG_JOB_ENDPOINT;
                try {
                    String body = build().rawGet(logJobUrl);
                    return new Log(job.getId(), body);
                } catch (IOException e) {
                    build().getLogger().warn("Error when getting log from job id "+job.getId()+" ",e);
                }
            } else {
                build().getLogger().warn("Error when getting log from job id "+job.getId()+" : build status is neither failed or passed: "+job.getState().name());
            }
        }*/
        return null;
    }
}
