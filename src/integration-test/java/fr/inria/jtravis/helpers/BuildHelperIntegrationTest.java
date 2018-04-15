package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.IntegrationTest;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.BuildStub;
import fr.inria.jtravis.entities.BuildTool;
import fr.inria.jtravis.entities.Builds;
import fr.inria.jtravis.entities.EventType;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.Repository;
import fr.inria.jtravis.entities.StateType;
import fr.inria.jtravis.parsers.TestUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.kohsuke.github.GitUser;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BuildHelperIntegrationTest extends AbstractTest {
    @Test
    @Category(IntegrationTest.class)
    public void testGetBuildFromIdReturnTheRightBuild() {
       int buildId = 185719843;
        Optional<Build> obtainedBuildOpt = getJTravis().build().fromId(buildId);

        assertTrue(obtainedBuildOpt.isPresent());
        Build obtainedBuild = obtainedBuildOpt.get();
        assertEquals(BuildStub.expectedBuildWithoutPR(), obtainedBuild);
    }

    @Test
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
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
    @Category(IntegrationTest.class)
    public void testGetTheLastBuildNumberOfADate() {
        Date date = getDateFor(2017, Calendar.MARCH, 16, 22, 59, 59, 0);
        String slug = "Spirals-Team/repairnator";

        int buildId = 212115810;
        String expectedBuildNumber = "226";
        Optional<Build> optionalBuild = getJTravis().build().forDate(slug, date, 1, ChronoUnit.DAYS);
        assertTrue(optionalBuild.isPresent());
        assertEquals(expectedBuildNumber, optionalBuild.get().getNumber());
        assertEquals(buildId, optionalBuild.get().getId());
    }

    // this test is the same as the previous one but with the old repo slug
    @Test
    @Category(IntegrationTest.class)
    public void testGetTheLastBuildNumberOfADateFromRenamedRepository() {
        Date date = getDateFor(2017, Calendar.MARCH, 16, 22, 59, 59, 0);
        String oldSlug = "Spirals-Team/librepair";
        String newSlug = "Spirals-Team/repairnator";

        int buildId = 212115810;
        String expectedBuildNumber = "226";

        Optional<Build> optionalBuild = getJTravis().build().forDate(oldSlug, date, 1, ChronoUnit.DAYS);

        assertTrue(optionalBuild.isPresent());
        Build build = optionalBuild.get();
        assertEquals(expectedBuildNumber, build.getNumber());
        assertEquals(buildId, build.getId());
        assertEquals(newSlug, build.getRepository().getSlug());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetTheLastBuildNumberOfADate2() {
        Date date = getDateFor(2017, Calendar.MARCH, 14, 22, 59, 59, 0);
        String slug = "Spirals-Team/repairnator";

        int buildId = 211461651;
        String expectedBuildNumber = "208";

        Optional<Build> optionalBuild = getJTravis().build().forDate(slug, date, 1, ChronoUnit.DAYS);
        assertTrue(optionalBuild.isPresent());
        assertEquals(expectedBuildNumber, optionalBuild.get().getNumber());
        assertEquals(buildId, optionalBuild.get().getId());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetBuildsFromRepositoryInTimeInterval() {
        String slug = "Spirals-Team/repairnator";
        Date initialDate = getDateFor(2017, Calendar.MARCH, 13, 23, 0, 0, 0);

        Optional<List<Build>> optionalBuild = getJTravis().build().allForDate(slug, initialDate, 3, ChronoUnit.DAYS);

        assertTrue(optionalBuild.isPresent());

        List<Build> buildList = optionalBuild.get();

        assertEquals(60, buildList.size());
        assertEquals("215", buildList.get(0).getNumber());
        assertEquals("156", buildList.get(buildList.size()-1).getNumber());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetBuildsFromRepositoryInTimeIntervalAPI2() {
        String slug = "Spirals-Team/repairnator";
        Date beginDate = getDateFor(2017, Calendar.MARCH, 13, 23, 0, 0, 0);
        Date endDate = getDateFor(2017, Calendar.MARCH, 16, 23, 0, 0, 0);

        Optional<List<Build>> optionalBuild = getJTravis().build().betweenDates(slug, beginDate, endDate);

        assertTrue(optionalBuild.isPresent());

        List<Build> buildList = optionalBuild.get();

        assertEquals(60, buildList.size());
        assertEquals("215", buildList.get(0).getNumber());
        assertEquals("156", buildList.get(buildList.size()-1).getNumber());

        Build firstBuild = buildList.get(0);
        String language = firstBuild.getLanguage();
        assertNotNull(language);
        assertFalse(language.isEmpty());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetBuildsFromRepositoryInTimeIntervalAlsoTakePR() {
        String slug = "INRIA/spoon";
        Date initialDate = getDateFor(2017, Calendar.JUNE, 26, 20, 0, 0, 0);
        Optional<List<Build>> optionalBuild = getJTravis().build().allForDate(slug, initialDate, 1, ChronoUnit.HOURS);

        assertTrue(optionalBuild.isPresent());

        List<Build> buidList = optionalBuild.get();

        assertEquals(3, buidList.size());
        assertEquals("3424", buidList.get(2).getNumber());
        for (Build build : buidList) {
            assertTrue(build.isPullRequest());
        }

        assertEquals(1428, buidList.get(2).getPullRequestNumber());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetLastBuildFromMasterIgnoreNumberNullBuilds() {
        String slug = "Graylog2/graylog2-server";
        Optional<Build> buildOptional = getJTravis().build().last(slug);

        assertTrue(buildOptional.isPresent());
        assertNotNull(buildOptional.get().getNumber());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetLastBuildFromRenamedRepository() {
        String oldSlug = "alibaba/dubbo";
        String newSlug = "apache/incubator-dubbo";

        Optional<Build> buildOptional = getJTravis().build().last(oldSlug);

        assertTrue(buildOptional.isPresent());
        Build build = buildOptional.get();
        assertNotNull(build.getNumber());
        assertEquals(newSlug, build.getRepository().getSlug());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetFutureBuildOfBranch() {
        int buildId = 220970612;
        Optional<Build> optionalBuild = getJTravis().build().fromId(buildId);
        assertTrue(optionalBuild.isPresent());
        Build b = optionalBuild.get();
        assertEquals("21405", b.getNumber());

        Optional<Build> nextBuild = getJTravis().build().getAfter(b, true);

        assertTrue(nextBuild.isPresent());
        assertEquals("21423", nextBuild.get().getNumber());
    }

    // This test is currently taking more than a minute to execute
    // we MUST use some improvement in our algo
    @Ignore
    @Test
    @Category(IntegrationTest.class)
    public void testGetFutureBuildOfBranch2() {
        int buildId = 219250521;

        Optional<Build> optionalBuild = getJTravis().build().fromId(buildId);
        assertTrue(optionalBuild.isPresent());
        Build b = optionalBuild.get();
        assertEquals("21005", b.getNumber());

        Optional<Build> nextBuild = getJTravis().build().getAfter(b, true);

        assertTrue(nextBuild.isPresent());
        assertEquals("21420", nextBuild.get().getNumber());
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetBuildsFromSlugWithLimitDate() {
        String slug = "surli/failingProject";
        //Retrieve builds done after this date
        Date limitDate = getDateFor(2017, Calendar.SEPTEMBER, 14, 0, 0, 1, 0);

        Optional<List<Build>> optionalBuilds = getJTravis().build().afterDate(slug, limitDate);
        assertTrue(optionalBuilds.isPresent());
        List<String> obtainedIds = new ArrayList<String>();

        //Unexpected build's numbers
        List<String> unexpectedIds = Arrays.asList("273","274","275");

        for (Build b: optionalBuilds.get()) {
            obtainedIds.add(b.getNumber());
            //Check that the retrieved build number is not among the unexpected
            assertFalse(unexpectedIds.contains(b.getNumber()));
        }

        //Check that retrieved build's numbers are all in the expected build's numbers list
        assertThat(obtainedIds, CoreMatchers.hasItems("373","374","375"));
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetBuildsFromRepositoryWithLimitDate() {
        String slug = "google/jsonnet";
        //Retrieve builds done after this date
        Date limitDate = getDateFor(2017, Calendar.SEPTEMBER, 14, 0, 0, 1, 0);

        Optional<List<Build>> optionalBuilds = getJTravis().build().afterDate(slug, limitDate);
        assertTrue(optionalBuilds.isPresent());
        List<String> obtainedIds = new ArrayList<String>();

        //Unexpected build's numbers
        List<String> unexpectedIds = Arrays.asList("273","274","275");

        for (Build b: optionalBuilds.get()) {
            obtainedIds.add(b.getNumber());
            //Check that the retrieved build number is not among the unexpected
            assertFalse(unexpectedIds.contains(b.getNumber()));
        }

        //Check that retrieved build's numbers are all in the expected build's numbers list
        assertThat(obtainedIds, CoreMatchers.hasItems("685","684","679"));
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetShortInfo() {
        int stdandardBuildId = 359429242;
        Optional<Build> optionalBuild = this.getJTravis().build().fromId(stdandardBuildId);
        assertTrue(optionalBuild.isPresent());

        Build build = optionalBuild.get();

        String expectedEmail = "simon.urli@inria.fr";
        Optional<GitUser> optionalAuthor = build.getAuthor();
        Optional<GitUser> optionalCommitter = build.getCommitter();

        assertTrue(optionalAuthor.isPresent());
        assertTrue(optionalCommitter.isPresent());

        String authorEmail = optionalAuthor.get().getEmail();
        String committerEmail = optionalCommitter.get().getEmail();

        assertEquals(expectedEmail, authorEmail);
        assertEquals(expectedEmail, committerEmail);
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetShortInfoWithPR() {
        int prBuildId = 358367158;
        Optional<Build> optionalBuild = this.getJTravis().build().fromId(prBuildId);
        assertTrue(optionalBuild.isPresent());

        Build build = optionalBuild.get();

        Optional<GitUser> optionalAuthor = build.getAuthor();
        Optional<GitUser> optionalCommitter = build.getCommitter();

        assertTrue(optionalAuthor.isPresent());
        assertTrue(optionalCommitter.isPresent());

        String authorEmail = optionalAuthor.get().getEmail();
        String committerEmail = optionalCommitter.get().getEmail();

        assertEquals("simon.urli@gmail.com", authorEmail);
        assertEquals("noreply@github.com", committerEmail); // automatic merge
    }

    @Test
    @Category(IntegrationTest.class)
    public void testGetShortInfoWithMergeCommit() {
        int prBuildId = 355763901;
        Optional<Build> optionalBuild = this.getJTravis().build().fromId(prBuildId);
        assertTrue(optionalBuild.isPresent());

        Build build = optionalBuild.get();

        Optional<GitUser> optionalAuthor = build.getAuthor();
        Optional<GitUser> optionalCommitter = build.getCommitter();

        assertTrue(optionalAuthor.isPresent());
        assertTrue(optionalCommitter.isPresent());

        String authorEmail = optionalAuthor.get().getEmail();
        String committerEmail = optionalCommitter.get().getEmail();

        assertEquals("pvojtechovsky@users.noreply.github.com", authorEmail);
        assertEquals("simon.urli@gmail.com", committerEmail); // automatic merge
    }


    // this test might change in the future. It should be improved.
    @Ignore
    @Test
    @Category(IntegrationTest.class)
    public void testGetLastBuildFromMasterWithStateType() {
        String repo = "surli/failingProject";
        Optional<Repository> repositoryOptional = this.getJTravis().repository().fromSlug(repo);
        assertTrue(repositoryOptional.isPresent());

        Repository repository = repositoryOptional.get();
        Optional<Build> optionalBuild = this.getJTravis().build().lastBuildFromMasterWithState(repository, StateType.ERRORED);
        assertTrue(optionalBuild.isPresent());
        assertEquals("682", optionalBuild.get().getNumber());
    }

    // This test can actually take up than 3 min 30
    // TODO IT MUST BE IMPROVED
    @Ignore
    @Test
    @Category(IntegrationTest.class)
    public void testDifferentBuildIdsShouldNotGiveSameInFuture() {
        int build1 = 210419994;
        int build2 = 207796355;

        Optional<Build> b1Opt = getJTravis().build().fromId(build1);
        Optional<Build> b2Opt = getJTravis().build().fromId(build2);
        assertTrue(b1Opt.isPresent());
        assertTrue(b2Opt.isPresent());

        Build b1 = b1Opt.get();
        Build b2 = b2Opt.get();

        assertTrue(b1.getRepository().equals(b2.getRepository()));

        Optional<Build> nextB1Opt = getJTravis().build().getAfter(b1, true);
        Optional<Build> nextB2Opt = getJTravis().build().getAfter(b2, true);
        assertTrue(nextB1Opt.isPresent());
        assertTrue(nextB2Opt.isPresent());

        assertFalse(nextB1Opt.get().equals(nextB2Opt.get()));
    }

    @Test
    public void testGetPassingBuildAfterGivenBuild() {
        int buildId = 352395977;
        Optional<Build> firstBuild = getJTravis().build().fromId(buildId);
        assertTrue(firstBuild.isPresent());

        Build build1 = firstBuild.get();
        Optional<Build> optBuildPassing = getJTravis().build().getAfter(build1, true, StateType.PASSED);
        assertFalse(optBuildPassing.isPresent());
    }

    @Test
    public void testJTravisPropagation() {
        // jtravis should be propagated to entities
        int buildId = 364156914; // inria/spoon with 5 jobs

        Optional<Build> buildOptional = this.getJTravis().build().fromId(buildId);
        assertTrue(buildOptional.isPresent());
        Build build = buildOptional.get();

        assertSame(this.getJTravis(), build.getJtravis());
        assertSame(this.getJTravis(), build.getRepository().getJtravis());
        assertSame(this.getJTravis(), build.getBranch().getJtravis());
        assertSame(this.getJTravis(), build.getCreatedBy().getJtravis());
        int counter = 0;

        for (Job job : build.getJobs()) {
            assertSame(this.getJTravis(), job.getJtravis());
            counter++;
        }

        assertEquals(5, counter);

    }

}
