package fr.inria.jtravis.entities;

import fr.inria.jtravis.AbstractTest;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BuildStub extends AbstractTest {

    public static Build expectedBuildWithoutPR() {
        Build expectedBuild = new Build();
        expectedBuild.setUri("/build/185719843");
        expectedBuild.setRepresentation(RepresentationType.STANDARD);
        expectedBuild.setId(185719843);
        expectedBuild.setNumber("2373");
        expectedBuild.setState(StateType.PASSED);
        expectedBuild.setStartedAt(getDateFor(2016, Calendar.DECEMBER, 21, 9, 49, 46, 0));
        expectedBuild.setFinishedAt(getDateFor(2016, Calendar.DECEMBER, 21, 9, 56, 41, 0));
        expectedBuild.setDuration(415);
        expectedBuild.setEventType(EventType.PUSH);
        expectedBuild.setPreviousState(StateType.PASSED);

        Repository repository = new Repository();
        repository.setUri("/repo/2800492");
        repository.setRepresentation(RepresentationType.MINIMAL);
        repository.setId(2800492);
        repository.setName("spoon");
        repository.setSlug("INRIA/spoon");
        expectedBuild.setRepository(repository);

        Commit commit = new Commit();
        commit.setRepresentation(RepresentationType.MINIMAL);
        commit.setId(53036982);
        commit.setSha("d283ce5727f47c854470e64ac25144de5d8e6c05");
        commit.setMessage("test: add test for method parameter templating (#1064)");
        commit.setCompareUrl("https://github.com/INRIA/spoon/compare/3c5ab0fe7a89...d283ce5727f4");
        commit.setCommittedAt(getDateFor(2016, Calendar.DECEMBER,21,9,48,50, 0));
        commit.setRef("refs/heads/master");
        expectedBuild.setCommit(commit);

        Job job = new Job();
        job.setRepresentation(RepresentationType.MINIMAL);
        job.setUri("/job/185719844");
        job.setId(185719844);
        List<Job> jobs = new ArrayList<>();
        jobs.add(job);
        expectedBuild.setJobs(jobs);

        Owner owner = new Owner();
        owner.setRepresentation(RepresentationType.MINIMAL);
        owner.setUri("/user/95813");
        owner.setId(95813);
        owner.setLogin("surli");
        owner.setType(OwnerType.USER);
        expectedBuild.setCreatedBy(owner);

        Branch branch = new Branch();
        branch.setRepresentation(RepresentationType.MINIMAL);
        branch.setUri("/repo/2800492/branch/master");
        branch.setName("master");
        expectedBuild.setBranch(branch);

        expectedBuild.setUpdatedAt(getDateFor(2017, Calendar.APRIL, 21, 16, 27, 23, 494));

        return expectedBuild;
    }
}
