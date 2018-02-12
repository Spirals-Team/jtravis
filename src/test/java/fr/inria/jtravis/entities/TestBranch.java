package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBranch {

    @Test
    public void testRetrieveBranchFromJsonAnswer() throws IOException {
        String filePath = "./src/test/resources/response/branch_answer.json";
        String fileContent = StringUtils.join(Files.readAllLines(new File(filePath).toPath()), "\n");

        JsonParser parser = new JsonParser();
        JsonObject branchObject = parser.parse(fileContent).getAsJsonObject();
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

        Calendar startedAt = Calendar.getInstance();
        startedAt.setTimeInMillis(0);
        startedAt.set(2018, Calendar.FEBRUARY, 12, 14, 9, 31);

        lastBuild.setStartedAt(startedAt.getTime());

        Calendar finishedAt = Calendar.getInstance();
        finishedAt.setTimeInMillis(0);
        finishedAt.set(2018, Calendar.FEBRUARY, 12, 14, 53, 31);

        expectedBranch.setLastBuild(lastBuild);

        assertEquals(expectedBranch, result);
    }
}
