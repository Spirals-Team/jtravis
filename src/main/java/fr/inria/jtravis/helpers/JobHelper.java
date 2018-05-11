package fr.inria.jtravis.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.v2.JobV2;

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
    public JobHelper(JTravis jTravis) {
        super(jTravis);
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

    public Optional<JobV2> fromIdV2(long id) {
        String url = this.getConfig().getTravisEndpoint() + "/" + TravisConstants.JOBS_ENDPOINT + "/" + id;
        try {
            String response = this.get(url, true);
            JsonObject jsonObject = getJsonFromStringContent(response).getAsJsonObject("job");
            JobV2 jobV2 = createGson().fromJson(jsonObject, JobV2.class);
            return Optional.of(jobV2);
        } catch (IOException e) {
            this.getLogger().error("Error while getting job from V2 API", e);
        }
        return Optional.empty();
    }
}
