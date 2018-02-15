package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestJob extends AbstractTest {
    public static String JOB_STANDARD_PATH = "./src/test/resources/response/job/job_answer_rails.json";

    public static Job standardExpectedJob() {
        Job expectedJob = new Job();
        expectedJob.setUri("/job/86601347");
        expectedJob.setRepresentation(RepresentationType.STANDARD);
        expectedJob.setId(86601347);
        expectedJob.setNumber("28677.1");
        expectedJob.setState(StateType.PASSED);
        expectedJob.setStartedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 54, 1, 0));
        expectedJob.setFinishedAt(getDateFor(2015, Calendar.OCTOBER, 21, 12, 12, 27, 0));
        expectedJob.setQueue("builds.docker");
        expectedJob.setCreatedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 53, 49, 382));
        expectedJob.setUpdatedAt(getDateFor(2015, Calendar.OCTOBER, 21, 12, 12, 27, 213));

        Build build = new Build();
        build.setUri("/build/86601346");
        build.setRepresentation(RepresentationType.MINIMAL);
        build.setId(86601346);
        build.setNumber("28677");
        build.setState(StateType.PASSED);
        build.setDuration(5241);
        build.setEventType(EventType.PULL_REQUEST);
        build.setPreviousState(StateType.PASSED);
        build.setPullRequestTitle("Add SQL parameter sanitization to `.joins`");
        build.setPullRequestNumber(21847);
        build.setStartedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 54, 1, 0));
        build.setFinishedAt(getDateFor(2015, Calendar.OCTOBER, 21, 12, 13, 12, 0));
        expectedJob.setBuild(build);

        Repository repository = new Repository();
        repository.setUri("/repo/891");
        repository.setRepresentation(RepresentationType.MINIMAL);
        repository.setId(891);
        repository.setName("rails");
        repository.setSlug("rails/rails");
        expectedJob.setRepository(repository);

        Commit commit = new Commit();
        commit.setRepresentation(RepresentationType.MINIMAL);
        commit.setId(24633751);
        commit.setSha("c5019a4229539ea68c3e5df137219e6000ba16da");
        commit.setRef("refs/pull/21847/merge");
        commit.setMessage("move build_join to private methods");
        commit.setCompareUrl("https://github.com/rails/rails/pull/21847");
        commit.setCommittedAt(getDateFor(2015, Calendar.OCTOBER, 21, 11, 53, 32, 0));
        expectedJob.setCommit(commit);

        Owner owner = new Owner();
        owner.setRepresentation(RepresentationType.MINIMAL);
        owner.setType(OwnerType.ORGANIZATION);
        owner.setUri("/org/23");
        owner.setId(23);
        owner.setLogin("rails");
        expectedJob.setOwner(owner);

        return expectedJob;
    }

    @Test
    public void testRetrieveJobFromJsonAnswer() {
        JsonObject jobObject = this.getJsonObjectFromFilePath(JOB_STANDARD_PATH);

        assertNotNull(jobObject);
        Job result = EntityHelper.createGson().fromJson(jobObject, Job.class);
        assertNotNull(result);

        assertEquals(standardExpectedJob(), result);
    }
}
