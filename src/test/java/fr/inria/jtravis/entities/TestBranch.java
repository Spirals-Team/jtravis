package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.junit.Test;

import java.util.Calendar;
import java.util.TimeZone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBranch extends AbstractTest {

    @Test
    public void testRetrieveBranchFromJsonAnswer() {
        String filePath = "./src/test/resources/response/branch_answer.json";
        JsonObject branchObject = this.getJsonObjectFromFilePath(filePath);
        Branch result = AbstractHelper.createGson().fromJson(branchObject, Branch.class);

        assertNotNull(result);

        Branch expectedBranch = new Branch();
        expectedBranch.setUri("/repo/891/branch/master");
        expectedBranch.setName("master");
        expectedBranch.setDefaultBranch(true);
        expectedBranch.setExistsOnGithub(true);

        Repository repository = new Repository();
        repository.setUri("/repo/891");
        repository.setId(891);
        repository.setName("rails");
        repository.setSlug("rails/rails");

        expectedBranch.setRepository(repository);

        Build lastBuild = new Build();
        lastBuild.setUri("/build/340499707");
        lastBuild.setId(340499707);
        lastBuild.setNumber("50024");
        lastBuild.setState("failed");
        lastBuild.setDuration(69519);
        lastBuild.setEventType("push");
        lastBuild.setPreviousState("errored");
        lastBuild.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 14, 9, 31, 0));
        lastBuild.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 14, 53, 31, 0));

        expectedBranch.setLastBuild(lastBuild);

        assertEquals(expectedBranch, result);
    }
}
