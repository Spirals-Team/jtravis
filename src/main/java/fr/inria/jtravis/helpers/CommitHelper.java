package fr.inria.jtravis.helpers;

import com.google.gson.JsonElement;
import fr.inria.jtravis.entities.Commit;

/**
 * The helper to deal with Commit objects
 *
 * @author Simon Urli
 */
public class CommitHelper extends GenericHelper {

    public static Commit getCommitFromJsonElement(JsonElement element) {
        return createGson().fromJson(element, Commit.class);
    }
}
