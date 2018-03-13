package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestBuild extends AbstractTest {
    private static final String BUILD_STANDARD_PATH = "./src/test/resources/response/build/build_answer_standard.json";
    private static final String BUILD_MINIMAL_PATH = "./src/test/resources/response/build/build_answer_minimal.json";
    private static final String BUILD_STANDARD_NOPR_PATH = "./src/test/resources/response/build/build_answer_standard_nopr.json";
    private static final String BUILD_WITH_TAG_PATH = "./src/test/resources/response/build/build_with_tag.json";

    private Build getExpectedBuild() {
        Build expectedBuild = new Build();
        expectedBuild.setUri("/build/86601346");
        expectedBuild.setRepresentation(RepresentationType.STANDARD);
        expectedBuild.setId(86601346);
        expectedBuild.setNumber("28677");
        expectedBuild.setState(StateType.PASSED);
        expectedBuild.setDuration(5241);
        expectedBuild.setEventType(EventType.PULL_REQUEST);
        expectedBuild.setPreviousState(StateType.PASSED);
        expectedBuild.setPullRequestTitle("Add SQL parameter sanitization to `.joins`");
        expectedBuild.setPullRequestNumber(21847);
        expectedBuild.setStartedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 54, 1, 0));
        expectedBuild.setFinishedAt(getDateFor(2015, Calendar.OCTOBER, 21, 12, 13, 12, 0));
        expectedBuild.setUpdatedAt(getDateFor(2017, Calendar.APRIL, 17, 11, 53, 42, 240));

        Repository repository = new Repository();
        repository.setUri("/repo/891");
        repository.setRepresentation(RepresentationType.MINIMAL);
        repository.setId(891);
        repository.setName("rails");
        repository.setSlug("rails/rails");
        expectedBuild.setRepository(repository);

        Branch branch = new Branch();
        branch.setUri("/repo/891/branch/master");
        branch.setRepresentation(RepresentationType.MINIMAL);
        branch.setName("master");
        expectedBuild.setBranch(branch);

        Commit commit = new Commit();
        commit.setRepresentation(RepresentationType.MINIMAL);
        commit.setId(24633751);
        commit.setSha("c5019a4229539ea68c3e5df137219e6000ba16da");
        commit.setRef("refs/pull/21847/merge");
        commit.setMessage("move build_join to private methods");
        commit.setCompareUrl("https://github.com/rails/rails/pull/21847");
        commit.setCommittedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 53, 32, 0));
        expectedBuild.setCommit(commit);

        List<Job> jobs = new ArrayList<>();
        Job job = new Job();
        job.setUri("/job/86601347");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601347);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601348");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601348);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601349");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601349);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601350");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601350);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601351");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601351);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601352");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601352);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601353");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601353);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601354");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601354);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601355");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601355);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601356");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601356);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601357");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601357);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601358");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601358);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601359");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601359);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601360");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601360);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601361");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601361);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601362");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601362);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601363");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601363);
        jobs.add(job);

        job = new Job();
        job.setUri("/job/86601364");
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setId(86601364);
        jobs.add(job);

        expectedBuild.setJobs(jobs);

        Owner createdBy = new Owner();
        createdBy.setType(OwnerType.USER);
        createdBy.setUri("/user/610866");
        createdBy.setRepresentation(RepresentationType.MINIMAL);
        createdBy.setId(610866);
        createdBy.setLogin("esjee");
        expectedBuild.setCreatedBy(createdBy);

        return expectedBuild;
    }

    @Test
    public void testRetrieveBuildFromJsonAnswer() {
        JsonObject buildObject = this.getJsonObjectFromFilePath(BUILD_STANDARD_PATH);
        Build result = EntityHelper.createGson().fromJson(buildObject, Build.class);

        assertNotNull(result);

        assertEquals(getExpectedBuild(), result);
    }

    @Test
    public void testIsPullRequestGiveTheRightAnswer() {
        JsonObject buildObject = this.getJsonObjectFromFilePath(BUILD_STANDARD_PATH);
        Build result = EntityHelper.createGson().fromJson(buildObject, Build.class);

        assertNotNull(result);
        assertTrue(result.isPullRequest());

        buildObject = this.getJsonObjectFromFilePath(BUILD_STANDARD_NOPR_PATH);
        result = EntityHelper.createGson().fromJson(buildObject, Build.class);
        assertNotNull(result);
        assertFalse(result.isPullRequest());
    }

    @Test
    public void testRefreshBuildAfterGettingInFromJob() throws IOException {
        JsonObject buildObject = this.getJsonObjectFromFilePath(BUILD_MINIMAL_PATH);

        assertNotNull(buildObject);
        Build minimalBuild = EntityHelper.createGson().fromJson(buildObject, Build.class);

        Build minimalExpectedBuild = new Build();
        minimalExpectedBuild.setUri("/build/86601346");
        minimalExpectedBuild.setRepresentation(RepresentationType.MINIMAL);
        minimalExpectedBuild.setId(86601346);
        minimalExpectedBuild.setNumber("28677");
        minimalExpectedBuild.setState(StateType.PASSED);
        minimalExpectedBuild.setDuration(5241);
        minimalExpectedBuild.setEventType(EventType.PULL_REQUEST);
        minimalExpectedBuild.setPreviousState(StateType.PASSED);
        minimalExpectedBuild.setPullRequestTitle("Add SQL parameter sanitization to `.joins`");
        minimalExpectedBuild.setPullRequestNumber(21847);
        minimalExpectedBuild.setStartedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 54, 1, 0));
        minimalExpectedBuild.setFinishedAt(getDateFor(2015, Calendar.OCTOBER, 21, 12, 13, 12, 0));
        assertEquals(minimalExpectedBuild, minimalBuild);

        String buildContent = this.getFileContent(BUILD_STANDARD_PATH);

        this.enqueueContentMockServer(buildContent);


        assertTrue(getJTravis().refresh(minimalBuild));
        assertEquals(this.getExpectedBuild(), minimalBuild);
        assertFalse(getJTravis().refresh(minimalBuild));
    }

    @Test
    public void testBuildWithTagAreCorrectlyParsed() throws IOException {
        JsonObject buildObject = this.getJsonObjectFromFilePath(BUILD_WITH_TAG_PATH);
        assertNotNull(buildObject);
        Build buildWithTag = EntityHelper.createGson().fromJson(buildObject, Build.class);
        assertNotNull(buildWithTag);

        assertEquals(319252906, buildWithTag.getId());
        Tag expectedTagField = new Tag();
        expectedTagField.setName("spoon-core-6.1.0");
        expectedTagField.setRepositoryId(2800492);
        expectedTagField.setRepresentation(RepresentationType.MINIMAL);

        assertEquals(expectedTagField, buildWithTag.getTag());
    }
}
