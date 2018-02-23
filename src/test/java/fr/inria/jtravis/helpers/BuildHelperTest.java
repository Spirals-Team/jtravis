package fr.inria.jtravis.helpers;


/**
 * Created by urli on 22/12/2016.
 */
public class BuildHelperTest {


//

//
//
//

//

//

//

//

//
//    @Test
//    public void testGetNextBuildJustAfterGivenBuild() {
//        int buildId = 191412122;
//        Build passingBuild = BuildHelper.getBuildFromId(buildId, null);
//
//
//        int expectedBuildId = 191511078;
//        Build obtainedBuild = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(passingBuild, null);
//
//        assertTrue(obtainedBuild != null);
//        assertEquals(expectedBuildId, obtainedBuild.getId());
//    }
//
//    @Test
//    public void testGetLastBuildWorksOnMaster() {
//        int buildId = 207455891;
//        Build currentBuild = BuildHelper.getBuildFromId(buildId, null);
//
//        int expectedBuildId = 207113449;
//        Build obtainedBuild = BuildHelper.getLastBuildOfSameBranchOfStatusBeforeBuild(currentBuild, null);
//
//        assertTrue(obtainedBuild != null);
//        assertEquals(expectedBuildId, obtainedBuild.getId());
//    }
//
//    @Test
//    public void testGetNextBuildWorksOnMaster() {
//        int buildId = 207113449;
//        Build currentBuild = BuildHelper.getBuildFromId(buildId, null);
//
//        int expectedBuildId = 207455891;
//        Build obtainedBuild = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(currentBuild, null);
//
//        assertTrue(obtainedBuild != null);
//        assertEquals(expectedBuildId, obtainedBuild.getId());
//    }
//
//    @Test
//    public void testGetLastBuildJustBeforeGivenBuildOnTheSameBranch() {
//        int buildId = 208181440;
//        Build currentBuild = BuildHelper.getBuildFromId(buildId, null);
//
//        int expectedBuildId = 208116073;
//        Build obtainedBuild = BuildHelper.getLastBuildOfSameBranchOfStatusBeforeBuild(currentBuild, null);
//
//        assertTrue(obtainedBuild != null);
//        assertEquals(expectedBuildId, obtainedBuild.getId());
//    }
//
//    @Test
//    public void testGetNextBuildJustAfterGivenBuildOnTheSameBranch() {
//        int buildId = 208116073;
//
//        Build currentBuild = BuildHelper.getBuildFromId(buildId, null);
//
//        int expectedBuildId = 208181440;
//        Build obtainedBuild = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(currentBuild, null);
//
//        assertTrue(obtainedBuild != null);
//        assertEquals(expectedBuildId, obtainedBuild.getId());
//    }
//
//    @Test
//    public void testGetTheLastBuildNumberOfADate() {
//        Date date = TestUtils.getDate(2017, 3, 16, 22, 59, 59);
//
//        String slug = "Spirals-Team/repairnator";
//
//        int expectedBuildNumber = 215;
//
//        int obtainedBuildNumber = BuildHelper.getTheLastBuildNumberOfADate(slug, date, 0,false);
//
//        assertEquals(expectedBuildNumber, obtainedBuildNumber);
//    }
//
//    @Test
//    public void testGetTheLastBuildNumberOfADate2() {
//        Date date = TestUtils.getDate(2017, 3, 14, 22, 59, 59);
//
//        String slug = "Spirals-Team/repairnator";
//
//        int expectedBuildNumber = 189;
//
//        int obtainedBuildNumber = BuildHelper.getTheLastBuildNumberOfADate(slug, date, 0,false);
//
//        assertEquals(expectedBuildNumber, obtainedBuildNumber);
//    }
//
//    @Test
//    public void testGetBuildsFromRepositoryInTimeInterval() {
//        Repository repo = new Repository();
//        repo.setSlug("Spirals-Team/repairnator");
//
//        Date initialDate = TestUtils.getDate(2017, 3, 13, 23, 0, 0);
//
//        Date finalDate = TestUtils.getDate(2017, 3, 16, 22, 59, 59);
//        List<Build> builds = BuildHelper.getBuildsFromRepositoryInTimeInterval(repo, initialDate, finalDate);
//
//        assertTrue(builds != null);
//
//        if (builds.size() > 1) {
//            Collections.sort(builds);
//        }
//
//        assertEquals(60, builds.size());
//        assertEquals(156, Integer.parseInt(builds.get(0).getNumber()));
//        assertEquals(215, Integer.parseInt(builds.get(builds.size()-1).getNumber()));
//    }
//
//    @Test
//    public void testGetBuildsFromRepositoryInTimeIntervalAlsoTakePR() {
//        Repository repo = new Repository();
//        repo.setSlug("INRIA/spoon");
//
//        Date initialDate = TestUtils.getDate(2017, 6, 26, 20, 0, 0);
//
//        Date finalDate = TestUtils.getDate(2017, 6, 26, 21, 0, 0);
//        List<Build> builds = BuildHelper.getBuildsFromRepositoryInTimeInterval(repo, initialDate, finalDate);
//
//        assertTrue(builds != null);
//
//        if (builds.size() > 1) {
//            Collections.sort(builds);
//        }
//
//        assertEquals(3, builds.size());
//        assertEquals(3424, Integer.parseInt(builds.get(0).getNumber()));
//        for (Build build : builds) {
//            assertTrue(build.isPullRequest());
//        }
//
//
//        assertEquals(1428, builds.get(0).getPullRequestNumber());
//    }
//
//    @Test
//    public void testGetLastBuildFromMasterIgnoreNumberNullBuilds() {
//        String slug = "Graylog2/graylog2-server";
//        Repository repo = RepositoryHelper.getRepositoryFromSlug(slug);
//
//        Build b = repo.getLastBuild(true);
//        assertTrue(b.getNumber() != null);
//    }
//
//    @Test
//    public void testGetFutureBuildOfBranch() {
//        int buildId = 220970612;
//
//        Build b = BuildHelper.getBuildFromId(buildId, null);
//        assertEquals("21405", b.getNumber());
//
//        Build obtained = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(b, null);
//
//        assertNotNull(obtained);
//        assertEquals("21423", obtained.getNumber());
//    }
//
//    @Test
//    public void testGetFutureBuildOfBranch2() {
//        int buildId = 219250521;
//
//        Build b = BuildHelper.getBuildFromId(buildId, null);
//        assertEquals("21005", b.getNumber());
//
//        Build obtained = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(b, null);
//
//        assertNotNull(obtained);
//        assertEquals("21420", obtained.getNumber());
//    }
//
//    @Test
//    public void testGetBuildsFromSlugWithLimitDate() {
//        String slug = "surli/failingProject";
//        //Retrieve builds done after this date
//        Date limitDate = TestUtils.getDate(2017, 9, 14, 0, 0, 1);
//
//        List<Build> builds = BuildHelper.getBuildsFromSlugWithLimitDate(slug, limitDate);
//        List<String> obtainedIds = new ArrayList<String>();
//        //Unexpected build's numbers
//        List<String> unexpectedIds = Arrays.asList("273","274","275");
//
//
//        for (Build b:builds)
//        {
//            obtainedIds.add(b.getNumber());
//            //Check that the retrieved build number is not among the unexpected
//            assertFalse(unexpectedIds.contains(b.getNumber()));
//        }
//        assertNotNull(builds);
//
//        //Check that retrieved build's numbers are all in the expected build's numbers list
//        assertThat(obtainedIds, CoreMatchers.hasItems("373","374","375"));
//    }
//
//    @Test
//    public void testGetBuildsFromSlug() {
//        String slug = "surli/failingProject";
//
//        List<Build> builds = BuildHelper.getBuildsFromSlug(slug);
//        List<String> obtainedIds = new ArrayList<String>();
//
//        for (Build b:builds)
//        {
//            obtainedIds.add(b.getNumber());
//        }
//        assertNotNull(builds);
//
//        //Check that retrieved build's numbers are all in the expected build's numbers list
//        assertThat(obtainedIds, CoreMatchers.hasItems("1","373","374","375"));
//    }
//
//    @Test
//    public void testGetBuildsFromRepositoryWithLimitDate() {
//        Repository repo = new Repository();
//        repo.setSlug("google/jsonnet");
//        //Retrieve builds done after this date
//        Date limitDate = TestUtils.getDate(2017, 9, 14, 0, 0, 1);
//
//        List<Build> builds = BuildHelper.getBuildsFromRepositoryWithLimitDate(repo, limitDate);
//        List<String> obtainedIds = new ArrayList<String>();
//        List<String> unexpectedIds = Arrays.asList("273","274","275");
//
//        for (Build b:builds)
//        {
//            obtainedIds.add(b.getNumber());
//            //Check that the retrieved build number is not among the unexpected
//            assertFalse(unexpectedIds.contains(b.getNumber()));
//        }
//        assertNotNull(builds);
//
//        //Check that retrieved build's numbers are all in the expected build's numbers list
//        assertThat(obtainedIds, CoreMatchers.hasItems("685","684","679"));
//    }
//
//    @Test
//    public void testGetBuildsFromRepository() {
//        Repository repo = new Repository();
//        //repo.setSlug("surli/failingProject");
//        repo.setSlug("google/jsonnet");
//
//        List<Build> builds = BuildHelper.getBuildsFromRepository(repo);
//        List<String> obtainedIds = new ArrayList<String>();
//
//        for (Build b:builds)
//        {
//            obtainedIds.add(b.getNumber());
//        }
//        assertNotNull(builds);
//
//        //Check that retrieved build's numbers are all in the expected build's numbers list
//        //assertThat(obtainedIds, CoreMatchers.hasItems("1","373","374","375"));
//        assertThat(obtainedIds, CoreMatchers.hasItems("1","602","613","615","685"));
//    }
//
//    @Test
//    public void testDifferentBuildIdsShouldNotGiveSameInFuture() {
//        int build1 = 210419994;
//        int build2 = 207796355;
//
//        Build b1 = BuildHelper.getBuildFromId(build1, null);
//        Build b2 = BuildHelper.getBuildFromId(build2, null);
//
//        assertTrue(b1.getRepository().equals(b2.getRepository()));
//
//        Build futureB1 = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(b1, null);
//        Build futureB2 = BuildHelper.getNextBuildOfSameBranchOfStatusAfterBuild(b2, null);
//
//        assertFalse(futureB1.equals(futureB2));
//    }
//
//    // this test might take around 1 minute
//    @Test
//    public void testTimeoutWhenGettingSuccessfulBuild() {
//        Date begin = new Date();
//        Repository repo = RepositoryHelper.getRepositoryFromSlug("everypolitician-scrapers/mexico-diputados-2015");
//        Build b = getLastSuccessfulBuildFromMaster(repo, false, 1);
//        Date end = new Date();
//
//        Date maxExpectedDate = new Date(begin.toInstant().plus(2, ChronoUnit.MINUTES).toEpochMilli());
//        assertTrue(end.getTime() < maxExpectedDate.getTime());
//    }

}
