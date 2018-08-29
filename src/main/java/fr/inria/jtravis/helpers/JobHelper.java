package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;

import java.util.Arrays;
import java.util.Optional;
import java.util.Properties;

/**
 * The helper to deal with job objects
 *
 * @author Simon Urli
 */
public class JobHelper extends EntityHelper {
    public JobHelper(JTravis jTravis) {
        super(jTravis);
    }

    public Optional<Job> fromId(int id) {
        Properties properties = new Properties();
        properties.put("include","job.config");

        return getEntityFromUri(Job.class, Arrays.asList(
                TravisConstants.JOB_ENDPOINT,
                String.valueOf(id)), properties);
    }

    public Optional<Job> fromId(String id) {
        Properties properties = new Properties();
        properties.put("include","job.config");

        return getEntityFromUri(Job.class, Arrays.asList(
                TravisConstants.JOB_ENDPOINT,
                id), properties);
    }
}
