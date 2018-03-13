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
import static org.junit.Assert.assertTrue;

public class TestBuilds extends AbstractTest {

    @Test
    public void testRetrieveBuildsFromJsonAnswer() {
        String filePath = "./src/test/resources/response/builds/builds_answer.json";
        JsonObject buildsJson = this.getJsonObjectFromFilePath(filePath);

        assertNotNull(buildsJson);
        Builds result = EntityHelper.createGson().fromJson(buildsJson, Builds.class);
        assertNotNull(result);

        Builds expectedBuilds = new Builds();
        expectedBuilds.setUri("/builds?limit=2&offset=2");
        expectedBuilds.setRepresentation(RepresentationType.STANDARD);

        Pagination pagination = new Pagination();
        pagination.setLimit(2);
        pagination.setOffset(2);
        pagination.setCount(2843);

        PaginationEntity next = new PaginationEntity();
        next.setUri("/builds?limit=2&offset=4");
        next.setLimit(2);
        next.setOffset(4);
        pagination.setNext(next);

        PaginationEntity prev = new PaginationEntity();
        prev.setUri("/builds?limit=2&offset=0");
        prev.setOffset(0);
        prev.setLimit(2);
        pagination.setPrev(prev);
        pagination.setFirst(prev);

        PaginationEntity last = new PaginationEntity();
        last.setUri("/builds?limit=2&offset=2842");
        last.setOffset(2842);
        last.setLimit(2);
        pagination.setLast(last);
        expectedBuilds.setPagination(pagination);

        List<Build> buildList = new ArrayList<>();
        Build firstBuild = new Build();
        firstBuild.setUri("/build/340863044");
        firstBuild.setRepresentation(RepresentationType.STANDARD);
        firstBuild.setId(340863044);
        firstBuild.setNumber("25");
        firstBuild.setState(StateType.ERRORED);
        firstBuild.setDuration(124);
        firstBuild.setEventType(EventType.PUSH);
        firstBuild.setPreviousState(StateType.ERRORED);
        firstBuild.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 13, 9, 25, 8, 0));
        firstBuild.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 13, 9, 27, 12, 0));

        Repository firstRepo = new Repository();
        firstRepo.setUri("/repo/17328693");
        firstRepo.setRepresentation(RepresentationType.MINIMAL);
        firstRepo.setId(17328693);
        firstRepo.setName("jtravis");
        firstRepo.setSlug("Spirals-Team/jtravis");
        firstBuild.setRepository(firstRepo);

        Branch firstBranch = new Branch();
        firstBranch.setUri("/repo/17328693/branch/travisV3");
        firstBranch.setRepresentation(RepresentationType.MINIMAL);
        firstBranch.setName("travisV3");
        firstBuild.setBranch(firstBranch);

        Commit firstCommit = new Commit();
        firstCommit.setRepresentation(RepresentationType.MINIMAL);
        firstCommit.setId(101354863);
        firstCommit.setSha("199b38150c969a5f00fe74af40eca20f8005a9c5");
        firstCommit.setRef("refs/heads/travisV3");
        firstCommit.setMessage("Improve entities with new documentation");
        firstCommit.setCompareUrl("https://github.com/Spirals-Team/jtravis/compare/a3d5714b53ff...199b38150c96");
        firstCommit.setCommittedAt(this.getDateFor(2018, Calendar.FEBRUARY, 13, 9, 21, 55, 0));
        firstBuild.setCommit(firstCommit);

        List<Job> jobList = new ArrayList<>();
        Job firstJob = new Job();
        firstJob.setUri("/job/340863045");
        firstJob.setRepresentation(RepresentationType.MINIMAL);
        firstJob.setId(340863045);
        jobList.add(firstJob);
        firstBuild.setJobs(jobList);

        Owner createdBy = new Owner();
        createdBy.setType(OwnerType.USER);
        createdBy.setUri("/user/95813");
        createdBy.setRepresentation(RepresentationType.MINIMAL);
        createdBy.setId(95813);
        createdBy.setLogin("surli");
        firstBuild.setCreatedBy(createdBy);
        firstBuild.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 13, 9, 27, 12, 379));
        buildList.add(firstBuild);

        Build secondBuild = new Build();
        secondBuild.setUri("/build/340617742");
        secondBuild.setRepresentation(RepresentationType.STANDARD);
        secondBuild.setId(340617742);
        secondBuild.setNumber("648");
        secondBuild.setState(StateType.FAILED);
        secondBuild.setDuration(43);
        secondBuild.setEventType(EventType.CRON);
        secondBuild.setPreviousState(StateType.FAILED);
        secondBuild.setStartedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 18, 31, 30, 0));
        secondBuild.setFinishedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 18, 32, 13, 0));

        Repository secondRepo = new Repository();
        secondRepo.setUri("/repo/11594784");
        secondRepo.setRepresentation(RepresentationType.MINIMAL);
        secondRepo.setId(11594784);
        secondRepo.setName("failingProject");
        secondRepo.setSlug("surli/failingProject");
        secondBuild.setRepository(secondRepo);

        Branch secondBranch = new Branch();
        secondBranch.setUri("/repo/11594784/branch/erroring-branch");
        secondBranch.setRepresentation(RepresentationType.MINIMAL);
        secondBranch.setName("erroring-branch");
        secondBuild.setBranch(secondBranch);

        Commit secondCommit = new Commit();
        secondCommit.setRepresentation(RepresentationType.MINIMAL);
        secondCommit.setId(101281499);
        secondCommit.setSha("349626e0c9138dbea032dc9cec92794e97401d48");
        secondCommit.setMessage("Remove tests in fail");
        secondCommit.setCompareUrl("https://github.com/surli/failingProject/compare/28e6430ececcb3b511292ca4f85b66fd5b606b3a...349626e0c9138dbea032dc9cec92794e97401d48");
        secondCommit.setCommittedAt(this.getDateFor(2017, Calendar.MARCH, 2, 15, 35, 36, 0));
        secondBuild.setCommit(secondCommit);

        List<Job> jobList1 = new ArrayList<>();
        Job job = new Job();
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setUri("/job/340617745");
        job.setId(340617745);
        jobList1.add(job);
        secondBuild.setJobs(jobList1);
        secondBuild.setCreatedBy(createdBy);
        secondBuild.setUpdatedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 18, 32, 13, 797));
        buildList.add(secondBuild);

        expectedBuilds.setBuilds(buildList);

        assertEquals(expectedBuilds, result);
        assertNotNull(result.getWarnings());
        assertTrue(result.getWarnings().isEmpty());
    }

    @Test
    public void testRetrieveBuildsWithWarning() {
        String filePath = "./src/test/resources/response/builds/builds_answer_with_warnings.json";
        JsonObject buildsJson = this.getJsonObjectFromFilePath(filePath);

        assertNotNull(buildsJson);
        Builds result = EntityHelper.createGson().fromJson(buildsJson, Builds.class);
        assertNotNull(result);

        List<Warning> warningList = result.getWarnings();
        assertEquals(1, warningList.size());

        Warning warning = new Warning();
        warning.setMessage("query parameter sorted_by not safelisted, ignored");
        warning.setWarningType("ignored_parameter");
        warning.setParameter("sorted_by");
        assertEquals(warning, warningList.get(0));

        assertEquals(25, result.getBuilds().size());
    }
}
