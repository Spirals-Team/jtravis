package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestBuild extends AbstractTest {

    @Test
    public void testRetrieveBuildFromJsonAnswer() {
        String filePath = "./src/test/resources/response/build_answer.json";
        JsonObject buildObject = this.getJsonObjectFromFilePath(filePath);
        Build result = AbstractHelper.createGson().fromJson(buildObject, Build.class);

        assertNotNull(result);

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
        expectedBuild.setStartedAt(this.getDateFor(2015, Calendar.OCTOBER, 21, 11, 54, 1, 0));
        expectedBuild.setFinishedAt(this.getDateFor(2015, Calendar.OCTOBER, 21, 12, 13, 12, 0));
        expectedBuild.setUpdatedAt(this.getDateFor(2017, Calendar.APRIL, 17, 11, 53, 42, 240));

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
        commit.setCommittedAt(this.getDateFor(2015, Calendar.OCTOBER, 21, 11, 53, 32, 0));
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

        assertEquals(expectedBuild, result);
    }
}
