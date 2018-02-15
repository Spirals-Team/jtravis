package fr.inria.jtravis.helpers;

import com.google.gson.JsonElement;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.Commit;
import okhttp3.OkHttpClient;

/**
 * The helper to deal with Commit objects
 *
 * @author Simon Urli
 */
public class CommitHelper extends EntityHelper {

    public CommitHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public static Commit getCommitFromJsonElement(JsonElement element) {
        return createGson().fromJson(element, Commit.class);
    }
}
