package fr.inria.jtravis.helpers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.inria.jtravis.entities.Commit;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by urli on 22/12/2016.
 */
public class CommitHelperTest {
//
//    private class Helper extends AbstractHelper {
//        public Helper() {
//            super();
//        }
//
//        public JsonElement getJsonCommitElement(int buildId) throws IOException {
//            String response = this.get(getEndpoint()+BuildHelper.BUILD_ENDPOINT+buildId);
//            JsonParser parser = new JsonParser();
//            JsonObject allAnswer = parser.parse(response).getAsJsonObject();
//            return allAnswer.getAsJsonObject("commit");
//        }
//    }
//
//    @Test
//    public void buildElementContainCommitInformation() throws IOException {
//        int buildId = 185719843;
//        Commit expectedCommit = new Commit();
//        expectedCommit.setId(53036982);
//        expectedCommit.setSha("d283ce5727f47c854470e64ac25144de5d8e6c05");
//        expectedCommit.setBranch("master");
//        expectedCommit.setMessage("test: add test for method parameter templating (#1064)");
//        expectedCommit.setCommittedAt(TestUtils.getDate(2016, 12, 21, 9, 48, 50));
//        expectedCommit.setAuthorName("Martin Monperrus");
//        expectedCommit.setAuthorEmail("monperrus@users.noreply.github.com");
//        expectedCommit.setCommitterName("Simon Urli");
//        expectedCommit.setCommitterEmail("simon.urli@gmail.com");
//        expectedCommit.setCompareUrl("https://github.com/INRIA/spoon/compare/3c5ab0fe7a89...d283ce5727f4");
//
//        Helper helper = new Helper();
//        Commit obtainedCommit = CommitHelper.getCommitFromJsonElement(helper.getJsonCommitElement(buildId));
//        assertEquals(expectedCommit, obtainedCommit);
//    }
}
