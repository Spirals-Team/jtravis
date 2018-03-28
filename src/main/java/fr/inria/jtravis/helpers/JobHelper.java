package fr.inria.jtravis.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.JobV2;
import okhttp3.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        return getEntityFromUri(Job.class, Arrays.asList(
                TravisConstants.JOB_ENDPOINT,
                String.valueOf(id)), null);
    }

    public Optional<Job> fromId(String id) {
        return getEntityFromUri(Job.class, Arrays.asList(
                TravisConstants.JOB_ENDPOINT,
                id), null);
    }

    public Optional<List<JobV2>> allFromV2() {
        String url = this.getConfig().getTravisEndpoint() + "/" + TravisConstants.JOBS_ENDPOINT;
        try {
            String response = this.get(url, true);
            JsonObject jsonObj = getJsonFromStringContent(response);
            JsonArray jsonArray = jsonObj.getAsJsonArray("jobs");
            List<JobV2> result = new ArrayList<>();

            for (JsonElement jsonElement : jsonArray) {
                result.add(createGson().fromJson(jsonElement, JobV2.class));
            }

            return Optional.of(result);
        } catch (IOException|JsonSyntaxException e) {
            this.getLogger().error("Error while getting jobs from V2 API", e);
        }

        return Optional.empty();
    }
}
