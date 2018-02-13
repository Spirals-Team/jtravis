package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertNotNull;

public class TestBuilds extends AbstractTest {

    @Test
    public void testRetrieveBuildsFromJsonAnswer() {
        String filePath = "./src/test/resources/response/builds_answer.json";
        JsonObject buildsJson = this.getJsonObjectFromFilePath(filePath);

        assertNotNull(buildsJson);
        Builds result = AbstractHelper.createGson().fromJson(buildsJson, Builds.class);
        assertNotNull(result);

        Builds expectedBuilds = new Builds();
        expectedBuilds.setUri("/builds?limit=2&offset=2");

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
        firstRepo.setId(17328693);
        firstRepo.setName("jtravis");
        firstRepo.setSlug("Spirals-Team/jtravis");
        firstBuild.setRepository(firstRepo);

        Branch firstBranch = new Branch();
        firstBranch.setUri("/repo/17328693/branch/travisV3");
        firstBranch.setName("travisV3");
        firstBuild.setBranch(firstBranch);

        Commit firstCommit = new Commit();
        firstCommit.setId(101354863);
        firstCommit.setSha("199b38150c969a5f00fe74af40eca20f8005a9c5");
        firstCommit.setRef("refs/heads/travisV3");
        firstCommit.setMessage("Improve entities with new documentation");
        firstCommit.setCompareUrl("https://github.com/Spirals-Team/jtravis/compare/a3d5714b53ff...199b38150c96");
        firstCommit.setCommittedAt(this.getDateFor(2018, Calendar.FEBRUARY, 13, 9, 21, 55, 0));
        firstBuild.setCommit(firstCommit);


    }
}
