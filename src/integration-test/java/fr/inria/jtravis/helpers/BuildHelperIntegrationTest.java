package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.BuildStub;
import fr.inria.jtravis.entities.BuildTool;
import fr.inria.jtravis.entities.Builds;
import fr.inria.jtravis.entities.EventType;
import fr.inria.jtravis.entities.StateType;
import fr.inria.jtravis.parsers.TestUtils;
import org.junit.Test;

import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BuildHelperIntegrationTest extends AbstractTest {
    @Test
    public void testGetBuildFromIdReturnTheRightBuild() {
       int buildId = 185719843;
        Optional<Build> obtainedBuildOpt = getJTravis().build().fromId(buildId);

        assertTrue(obtainedBuildOpt.isPresent());
        Build obtainedBuild = obtainedBuildOpt.get();
        assertEquals(BuildStub.expectedBuildWithoutPR(), obtainedBuild);
    }

    @Test
    public void testBuildOrderDescIsWorkingAsExpected() {
        int repositoryId = 2800492; // spoon

        List<String> pathParameter = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repositoryId),
                TravisConstants.BUILDS_ENDPOINT);


        Properties properties = new Properties();
        properties.put("state", StateType.FAILED);
        properties.put("sort_by", new BuildsSorting().byFinishedAtDesc().build());

        properties.put("branch.name", "master");
        properties.put("event_type", EventType.PULL_REQUEST);

        Optional<Builds> buildsOptional = getJTravis().build().getEntityFromUri(Builds.class, pathParameter, properties);
        assertTrue(buildsOptional.isPresent());

        List<Build> builds = buildsOptional.get().getBuilds();
        assertFalse(builds.isEmpty());

        for (int i = 0; i < builds.size() - 2; i++) {
            Build build1 = builds.get(i);
            Build build2 = builds.get(i+1);

            assertTrue("Wrong comparison for first call with b1.id = "+build1.getId()+" and b2.id = "+build2.getId(), build1.getFinishedAt().toInstant().isAfter(build2.getFinishedAt().toInstant()));
        }

        buildsOptional = getJTravis().getNextCollection(buildsOptional.get());
        assertTrue(buildsOptional.isPresent());
        builds = buildsOptional.get().getBuilds();
        assertFalse(builds.isEmpty());

        for (int i = 0; i < builds.size() - 2; i++) {
            Build build1 = builds.get(i);
            Build build2 = builds.get(i+1);

            assertTrue("Wrong comparison for second call with b1.id = "+build1.getId()+" and b2.id = "+build2.getId(), build1.getFinishedAt().toInstant().isAfter(build2.getFinishedAt().toInstant()));
        }
    }

    // WARNING: This test MIGHT be flaky
    @Test
    public void testBuildOrderIsWorkingAsExpected() {
        int repositoryId = 2800492; // spoon

        List<String> pathParameter = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repositoryId),
                TravisConstants.BUILDS_ENDPOINT);


        Properties properties = new Properties();
        properties.put("state", StateType.FAILED);
        properties.put("sort_by", new BuildsSorting().byFinishedAt().build());

        properties.put("branch.name", "master");
        properties.put("event_type", EventType.PULL_REQUEST);

        Optional<Builds> buildsOptional = getJTravis().build().getEntityFromUri(Builds.class, pathParameter, properties);
        assertTrue(buildsOptional.isPresent());

        List<Build> builds = buildsOptional.get().getBuilds();
        assertFalse(builds.isEmpty());

        for (int i = 0; i < builds.size() - 2; i++) {
            Build build1 = builds.get(i);
            Build build2 = builds.get(i + 1);

            assertTrue("Wrong comparison for first call with b1.id = " + build1.getId() + " and b2.id = " + build2.getId(), build1.getFinishedAt().toInstant().isBefore(build2.getFinishedAt().toInstant()));
        }
    }

    @Test
    public void testGetBuildToolFromBuildRecognizeTool() {
        int buildId = 185719843;
        Optional<Build> obtainedBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(obtainedBuildOpt.isPresent());
        Build obtainedBuild = obtainedBuildOpt.get();

        Optional<BuildTool> buildToolOpt = obtainedBuild.getBuildTool();
        assertTrue(buildToolOpt.isPresent());
        assertEquals(BuildTool.MAVEN, buildToolOpt.get());
    }

    @Test
    public void testGetLastFailingBuildBeforeGivenBuild() {
        int buildId = 197104485;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 197067445;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getBefore(passingBuild, true, StateType.FAILED);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetNextPassingBuildAfterGivenFailingBuild() {
        int buildId = 197067445;
        Optional<Build> failingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(failingBuildOpt.isPresent());
        Build failingBuild = failingBuildOpt.get();

        int expectedBuildId = 197104485;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getAfter(failingBuild, false, StateType.PASSED);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetLastErroredBuildBeforeGivenBuild() {
        int buildId = 197233494;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 193970329;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getBefore(passingBuild, true, StateType.ERRORED);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetNextPassingBuildAfterGivenErroredBuildOnSameBranch() {
        int buildId = 193970329;
        Optional<Build> erroredBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(erroredBuildOpt.isPresent());
        Build erroredBuild = erroredBuildOpt.get();

        int expectedBuildId = 193992095;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getAfter(erroredBuild, true, StateType.PASSED);

        assertTrue(obtainedBuildOpt.isPresent());
        Build obtainedBuild = obtainedBuildOpt.get();
        assertEquals(expectedBuildId, obtainedBuild.getId());
    }

    @Test
    public void testGetLastBuildJustBeforeGivenBuild() {
        int buildId = 191511078;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 191412122;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getBefore(passingBuild, true);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetNextBuildJustAfterGivenBuild() {
        int buildId = 191412122;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 191511078;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getAfter(passingBuild, true);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetLastBuildJustBeforeGivenBuildOnMaster() {
        int buildId = 207455891;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 207113449;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getBefore(passingBuild, true);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetNextBuildJustAfterGivenBuildOnMaster() {
        int buildId = 207113449;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 207455891;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getAfter(passingBuild, true);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetLastBuildJustBeforeGivenBuildOnTheSameBranch() {
        int buildId = 208181440;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 208116073;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getBefore(passingBuild, true);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetNextBuildJustAfterGivenBuildOnTheSameBranch() {
        int buildId = 208116073;
        Optional<Build> passingBuildOpt = getJTravis().build().fromId(buildId);
        assertTrue(passingBuildOpt.isPresent());
        Build passingBuild = passingBuildOpt.get();

        int expectedBuildId = 208181440;
        Optional<Build> obtainedBuildOpt = getJTravis().build().getAfter(passingBuild, true);

        assertTrue(obtainedBuildOpt.isPresent());
        assertEquals(expectedBuildId, obtainedBuildOpt.get().getId());
    }

    @Test
    public void testGetTheLastBuildNumberOfADate() {
        Date date = getDateFor(2017, 3, 16, 22, 59, 59, 0);

        String slug = "Spirals-Team/repairnator";

        int expectedBuildNumber = 215;

        Optional<Build> optionalBuild = getJTravis().build().forDate(slug, date, 1, ChronoUnit.DAYS);

        assertTrue(optionalBuild.isPresent());

        assertEquals(expectedBuildNumber, optionalBuild.get().getNumber());
    }

}
