package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestJobs extends AbstractTest {

    @Test
    public void testRetrieveJobsFromJsonAnswer() {
        String filePath = "./src/test/resources/response/jobs_answer.json";
        JsonObject jobsJson = this.getJsonObjectFromFilePath(filePath);
        assertNotNull(jobsJson);
        Jobs result = EntityHelper.createGson().fromJson(jobsJson, Jobs.class);
        assertNotNull(result);

        Jobs expectedJobs = new Jobs();
        expectedJobs.setUri("/build/340663037/jobs");
        expectedJobs.setRepresentation(RepresentationType.STANDARD);
        List<Job> jobList = new ArrayList<>();
        Job firstJob = new Job();
        firstJob.setUri("/job/340663038");
        firstJob.setRepresentation(RepresentationType.STANDARD);
        firstJob.setId(340663038);
        firstJob.setNumber("4744.1");
        firstJob.setState(StateType.FAILED);
        firstJob.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 20, 36, 0));
        firstJob.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 28, 9, 0));

        Build build = new Build();
        build.setUri("/build/340663037");
        build.setRepresentation(RepresentationType.MINIMAL);
        build.setId(340663037);
        build.setNumber("4744");
        build.setState(StateType.FAILED);
        build.setDuration(1735);
        build.setEventType(EventType.PULL_REQUEST);
        build.setPreviousState(StateType.PASSED);
        build.setPullRequestTitle("WiP: feature: Pattern = more abstract Template");
        build.setPullRequestNumber(1686);
        build.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 20, 29, 0));
        build.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 29, 02, 0));
        firstJob.setBuild(build);

        firstJob.setQueue("builds.gce");
        Repository repository = new Repository();
        repository.setUri("/repo/2800492");
        repository.setRepresentation(RepresentationType.MINIMAL);
        repository.setId(2800492);
        repository.setName("spoon");
        repository.setSlug("INRIA/spoon");
        firstJob.setRepository(repository);

        Commit commit = new Commit();
        commit.setRepresentation(RepresentationType.MINIMAL);
        commit.setId(101294919);
        commit.setSha("c581397a4b1e627451fbb0a736cfc7a6e4e7aea0");
        commit.setRef("refs/pull/1686/merge");
        commit.setMessage("match attribute of type map");
        commit.setCompareUrl("https://github.com/INRIA/spoon/pull/1686");
        commit.setCommittedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 19, 27, 0));
        firstJob.setCommit(commit);

        Owner owner = new Owner();
        owner.setRepresentation(RepresentationType.MINIMAL);
        owner.setType(OwnerType.ORGANIZATION);
        owner.setUri("/org/83206");
        owner.setId(83206);
        owner.setLogin("INRIA");
        firstJob.setOwner(owner);

        firstJob.setCreatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 19, 53, 212));
        firstJob.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 28, 9, 121));
        jobList.add(firstJob);

        Job secondJob = new Job();
        secondJob.setUri("/job/340663039");
        secondJob.setRepresentation(RepresentationType.STANDARD);
        secondJob.setId(340663039);
        secondJob.setNumber("4744.2");
        secondJob.setState(StateType.FAILED);
        secondJob.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 20, 29, 0));
        secondJob.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 28, 1, 0));
        secondJob.setBuild(build);
        secondJob.setQueue("builds.gce");
        secondJob.setRepository(repository);
        secondJob.setCommit(commit);
        secondJob.setOwner(owner);
        secondJob.setCreatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 19, 53, 319));
        secondJob.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 28, 2, 17));
        jobList.add(secondJob);

        Job thirdJob = new Job();
        thirdJob.setUri("/job/340663040");
        thirdJob.setRepresentation(RepresentationType.STANDARD);
        thirdJob.setId(340663040);
        thirdJob.setNumber("4744.3");
        thirdJob.setState(StateType.FAILED);
        thirdJob.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 20, 44, 0));
        thirdJob.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 29, 2, 0));
        thirdJob.setBuild(build);
        thirdJob.setQueue("builds.gce");
        thirdJob.setRepository(repository);
        thirdJob.setCommit(commit);
        thirdJob.setOwner(owner);
        thirdJob.setCreatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 19, 53, 637));
        thirdJob.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 29, 2, 916));
        jobList.add(thirdJob);

        Job fourthJob = new Job();
        fourthJob.setUri("/job/340663041");
        fourthJob.setRepresentation(RepresentationType.STANDARD);
        fourthJob.setId(340663041);
        fourthJob.setNumber("4744.4");
        fourthJob.setState(StateType.PASSED);
        fourthJob.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 20, 39, 0));
        fourthJob.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 23, 59, 0));
        fourthJob.setBuild(build);
        fourthJob.setQueue("builds.gce");
        fourthJob.setRepository(repository);
        fourthJob.setCommit(commit);
        fourthJob.setOwner(owner);
        fourthJob.setCreatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 19, 53, 667));
        fourthJob.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 23, 59, 358));
        jobList.add(fourthJob);

        Job fifthJob = new Job();
        fifthJob.setUri("/job/340663042");
        fifthJob.setRepresentation(RepresentationType.STANDARD);
        fifthJob.setId(340663042);
        fifthJob.setNumber("4744.5");
        fifthJob.setState(StateType.PASSED);
        fifthJob.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 20, 29, 0));
        fifthJob.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 22, 41, 0));
        fifthJob.setBuild(build);
        fifthJob.setQueue("builds.gce");
        fifthJob.setRepository(repository);
        fifthJob.setCommit(commit);
        fifthJob.setOwner(owner);
        fifthJob.setCreatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 19, 53, 987));
        fifthJob.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 20, 22, 41, 901));
        jobList.add(fifthJob);

        expectedJobs.setJobs(jobList);
        assertEquals(expectedJobs, result);
    }
}
