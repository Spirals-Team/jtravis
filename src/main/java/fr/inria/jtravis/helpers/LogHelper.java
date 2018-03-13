package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.Log;
import okhttp3.OkHttpClient;

import java.util.Arrays;
import java.util.Optional;

/**
 * The helper to deal with log objects
 *
 * @author Simon Urli
 */
public class LogHelper extends EntityHelper {

    public LogHelper(JTravis jTravis, TravisConfig config, OkHttpClient client) {
        super(jTravis, config, client);
    }

    public Optional<Log> from(Job job) {
        return this.getEntityFromUri(Log.class, Arrays.asList(
                TravisConstants.JOB_ENDPOINT,
                String.valueOf(job.getId()),
                TravisConstants.LOG_ENDPOINT), null);
    }
}
