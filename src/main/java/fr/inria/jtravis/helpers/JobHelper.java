package fr.inria.jtravis.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
