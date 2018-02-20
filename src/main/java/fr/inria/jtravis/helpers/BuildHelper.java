package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.Builds;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.Repository;
import okhttp3.OkHttpClient;

import java.util.Optional;

/**
 * The helper to deal with Build objects
 *
 * @author Simon Urli
 */
public class BuildHelper extends EntityHelper {

    public BuildHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }

    public Optional<Builds> fromRepository(Repository repository) {
        return getEntityFromUri(Builds.class, TravisConstants.REPO_ENDPOINT, String.valueOf(repository.getId()), TravisConstants.BUILDS_ENDPOINT);
    }

    public Optional<Builds> fromRepository(Repository repository, int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("The limit should be > 0. Current value: "+limit);
        }
        return getEntityFromUri(Builds.class, TravisConstants.REPO_ENDPOINT, String.valueOf(repository.getId()), TravisConstants.BUILDS_ENDPOINT, "?limit=", String.valueOf(limit));
    }

    public Optional<Builds> next(Builds builds) {
        return this.getNextCollection(builds);
    }

    public Optional<Build> fromId(int id) {
        return getEntityFromUri(Build.class, TravisConstants.BUILD_ENDPOINT, String.valueOf(id));
    }

    public Optional<Build> lastBuildFromMasterBranch(Repository repository) {
        Optional<Builds> builds = getEntityFromUri(Builds.class,
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repository.getId()),
                TravisConstants.BUILDS_ENDPOINT,
                "?branch.name=",
                repository.getDefaultBranch().getName(),
                "&limit=1",
                "&sorted_by=",
                new BuildsSorting().byFinishedAtDesc().build());

        if (builds.isPresent()) {
            if (builds.get().getBuilds().size() > 0) {
                return Optional.of(builds.get().getBuilds().get(0));
            }
        }
        return Optional.empty();
    }

//    private static boolean isAcceptedBuild(Build build, int prNumber, BuildStatus status, String previousBranch) {
//        if (prNumber != -1 && build.getPullRequestNumber() != prNumber) {
//            return false;
//        }
//        if (previousBranch != null && !previousBranch.equals("") && !previousBranch.equals(build.getCommit().getBranch())) {
//            return false;
//        }
//        if (status != null && build.getBuildStatus() != status) {
//            return false;
//        }
//        return true;
//    }
//
//    private static String getResourceUrl(String slug, String eventType, int after_number) {
//        String resourceUrl = build().getEndpoint()+RepositoryHelper.REPO_ENDPOINT+slug+"/"+BUILD_NAME;
//
//        if (eventType != null) {
//            resourceUrl += "?event_type=" + eventType;
//        }
//        if (after_number > 0) {
//            if (eventType != null) {
//                resourceUrl += "&after_number=" + after_number;
//            } else {
//                resourceUrl += "?after_number="+after_number;
//            }
//        }
//
//        return resourceUrl;
//    }
//
//    private static JsonArray sortBuildJsonArray(JsonArray buidArray) {
//        List<JsonObject> jsonValues = new ArrayList<JsonObject>();
//
//        for (JsonElement element : buidArray) {
//            jsonValues.add((JsonObject) element);
//        }
//
//        Collections.sort( jsonValues, new Comparator<JsonObject>() {
//            @Override
//            public int compare(JsonObject a, JsonObject b) {
//                return (a.get("number").getAsInt()-b.get("number").getAsInt());
//            }
//        });
//
//        JsonArray result = new JsonArray();
//
//        for (JsonObject values : jsonValues) {
//            result.add(values);
//        }
//
//        return result;
//    }
//
//    private static JsonArray getBuildsAndCommits(String resourceUrl, Map<Integer, Commit> commits, boolean sortBuilds) {
//        try {
//            String response = build().get(resourceUrl);
//            JsonParser parser = new JsonParser();
//            JsonObject allAnswer = parser.parse(response).getAsJsonObject();
//
//            JsonArray buildArray = allAnswer.getAsJsonArray("builds");
//
//            if (sortBuilds) {
//                buildArray = sortBuildJsonArray(buildArray);
//            }
//
//            JsonArray commitsArray = allAnswer.getAsJsonArray("commits");
//
//            for (JsonElement commitJson : commitsArray) {
//                Commit commit = createGson().fromJson(commitJson, Commit.class);
//                commits.put(commit.getId(), commit);
//            }
//
//            return buildArray;
//        } catch (IOException e) {
//            build().getLogger().warn("Error when trying to get builds and commits from "+resourceUrl+" : "+e.getMessage());
//            if (e.getMessage().equals("timeout")) {
//                build().getLogger().warn("Timeout reached. Try to sleep 2 seconds and execute again the request.");
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                return getBuildsAndCommits(resourceUrl, commits, sortBuilds);
//            }
//        }
//        return new JsonArray();
//    }
//
//
//
//    /**
//     * This is a recursive method allowing to get build from a slug.
//     *
//     * @param slug The slug where to get the builds
//     * @param limitDate If given, the date limit to get builds: all builds *before* this date are considered
//     * @param after_number Used for pagination: multiple requests may have to be made to reach the final date
//     * @param original_after_number is used to set the value of after_number back to the original value before the first request to an event type be made, as after_number changes when more than one request is made for a same event type (pagination)
//     * @param eventTypes Travis support multiple event types for builds like Push, PR or CRON. Those are retrieved individually
//     * @param limitNumber Allow to finish early based on the number of builds selected. if 0 passed use only the date to stop searching
//     * @param status Allow to only select builds of that status, if null it takes all status
//     * @param prNumber Allow to only consider builds of that PR, if -1 given it takes all builds
//     * @param onlyAfterNumber Consider only builds after the specified after_number: should be use in conjunction with a specified after_number.
//     * @param delayTimeout A time in minute to pass on this function: a timeout will be triggered after this delay
//     */
//    private static List<Build> getBuildsFromSlug(String slug, Date limitDate, int after_number, int original_after_number, List<String> eventTypes, int limitNumber, BuildStatus status, int prNumber, boolean onlyAfterNumber, String previousBranch, int delayTimeout) {
//        List<Build> result = new ArrayList<>();
//        long dateEnd = -1;
//
//        if (delayTimeout != -1) {
//            dateEnd = new Date().toInstant().plus(delayTimeout, ChronoUnit.MINUTES).toEpochMilli();
//        }
//
//
//        while (!((eventTypes.isEmpty()) || (after_number <= 0 && onlyAfterNumber))) {
//            String resourceUrl = getResourceUrl(slug, eventTypes.get(0), after_number);
//
//            boolean dateReached = false;
//
//            Map<Integer, Commit> commits = new HashMap<Integer, Commit>();
//
//            JsonArray buildArray = getBuildsAndCommits(resourceUrl, commits, false);
//
//            int lastBuildNumber = Integer.MAX_VALUE;
//
//            for (JsonElement buildJson : buildArray) {
//                Build build = createGson().fromJson(buildJson, Build.class);
//
//                if ((limitDate == null) || (build.getFinishedAt() == null) || (build.getFinishedAt().after(limitDate))) {
//                    int commitId = build.getCommitId();
//
//                    if (commits.containsKey(commitId)) {
//                        build.setCommit(commits.get(commitId));
//                    }
//
//                    if (build.getNumber() != null) {
//                        int buildNumber = Integer.parseInt(build.getNumber());
//                        if (lastBuildNumber > buildNumber) {
//                            lastBuildNumber = buildNumber;
//                        }
//
//                        // we only accept build with a build number
//                        if (isAcceptedBuild(build, prNumber, status, previousBranch)) {
//                            result.add(build);
//                        }
//                    }
//
//                    // if we reach the limitNumber we can break the loop, and consider the date is reached
//                    // we don't return now because we may get a more interesting build (closest to the original one) with another event type
//                    if (limitNumber != 0 && result.size() >= limitNumber) {
//                        dateReached = true;
//                        break;
//                    }
//                } else {
//                    dateReached = true;
//                    break;
//                }
//            }
//
//            if (buildArray.size() == 0) {
//                dateReached = true;
//            }
//
//            if (!dateReached) {
//                after_number = lastBuildNumber;
//            } else {
//                eventTypes.remove(0);
//                if (!onlyAfterNumber) {
//                    after_number = 0;
//                } else {
//                    after_number = original_after_number;
//                }
//            }
//
//            if (dateEnd != -1) {
//                if (new Date().getTime() > dateEnd) {
//                    build().getLogger().warn("The delay of "+delayTimeout+" minutes has been exceeded while inspecting the repo: "+slug);
//                    return result;
//                }
//            }
//        }
//
//
//        return result;
//    }
//
//    /**
//     * Compute the last build number for a given slug and event type. Allow to determine a stop condition when iterating recursively towards the present (in future of a build)
//     * @param slug
//     * @param eventType
//     * @return
//     */
//    private static int computeStopCondition(String slug, String eventType) {
//        String resourceUrl = getResourceUrl(slug, eventType, -1);
//        Map<Integer, Commit> commits = new HashMap<Integer,Commit>();
//        JsonArray buildArray = getBuildsAndCommits(resourceUrl, commits, true);
//
//        if (buildArray.size() == 0) {
//            return -1;
//        }
//
//        int counter = 1;
//        String buildNumber = null;
//
//        while (buildNumber == null && counter <= buildArray.size()) {
//            JsonElement lastBuild = buildArray.get(buildArray.size()-counter);
//            Build build = createGson().fromJson(lastBuild, Build.class);
//            buildNumber = build.getNumber();
//            counter++;
//        }
//
//        if (buildNumber != null) {
//            return Integer.parseInt(buildNumber);
//        } else {
//            return 0;
//        }
//    }
//
//    private static final int CONSTANT_NB_OCC_FOR_FUTURE = 20;
//
//    /**
//     * Search recursively for a build AFTER a given build, so in its future. Take the same parameters as #getBuildsFromSlug
//     * @param slug
//     * @param resultByEventTypes
//     * @param after_number
//     * @param originalAfterNumber
//     * @param eventTypes
//     * @param status
//     * @param prNumber
//     * @param previousBranch
//     * @param limitNumber
//     * @param stop_condition_in_future
//     */
//    private static void getBuildsFromSlugRecursivelyInFuture(String slug, Map<String, List<Build>> resultByEventTypes, int after_number, int originalAfterNumber, List<String> eventTypes, BuildStatus status, int prNumber, String previousBranch, int limitNumber, int stop_condition_in_future) {
//        if (eventTypes.isEmpty()) {
//            return;
//        }
//
//        String currentEventType = eventTypes.get(0);
//
//        if (stop_condition_in_future == -1) {
//            stop_condition_in_future = computeStopCondition(slug, currentEventType);
//        }
//
//        boolean dateReached;
//        int lastBuildNumber = after_number;
//
//        if (stop_condition_in_future >= after_number || stop_condition_in_future == 0) {
//            String resourceUrl = getResourceUrl(slug, currentEventType, after_number+CONSTANT_NB_OCC_FOR_FUTURE);
//            dateReached = false;
//
//            Map<Integer, Commit> commits = new HashMap<Integer,Commit>();
//
//            JsonArray buildArray = getBuildsAndCommits(resourceUrl, commits, true);
//
//            for (JsonElement buildJson : buildArray) {
//                Build build = createGson().fromJson(buildJson, Build.class);
//                int commitId = build.getCommitId();
//
//                if (commits.containsKey(commitId)) {
//                    build.setCommit(commits.get(commitId));
//                }
//
//                if (build.getNumber() != null) {
//                    int buildNumber = Integer.parseInt(build.getNumber());
//                    if (buildNumber > after_number) {
//                        if (isAcceptedBuild(build, prNumber, status, previousBranch)) {
//                            if (!resultByEventTypes.containsKey(currentEventType)) {
//                                resultByEventTypes.put(currentEventType, new ArrayList<Build>());
//                            }
//                            List<Build> result = resultByEventTypes.get(currentEventType);
//                            result.add(build);
//                        }
//                        if (buildNumber > lastBuildNumber) {
//                            lastBuildNumber = buildNumber;
//                        }
//                    }
//
//
//                }
//
//                if (limitNumber != 0) {
//                    List<Build> result = resultByEventTypes.get(currentEventType);
//                    if (result != null && result.size() >= limitNumber) {
//                        dateReached = true;
//                        break;
//                    }
//                }
//            }
//
//            if (lastBuildNumber == after_number) {
//                lastBuildNumber += 20;
//            }
//
//            if (stop_condition_in_future == 0) {
//                if (lastBuildNumber == after_number) {
//                    dateReached = true;
//                }
//            } else {
//                if (lastBuildNumber >= stop_condition_in_future) {
//                    dateReached = true;
//                }
//            }
//
//
//            if (buildArray.size() == 0) {
//                dateReached = true;
//            }
//        } else {
//            dateReached = true;
//        }
//
//        if (!dateReached) {
//            getBuildsFromSlugRecursivelyInFuture(slug, resultByEventTypes, lastBuildNumber, originalAfterNumber, eventTypes, status, prNumber, previousBranch, limitNumber, stop_condition_in_future);
//        } else {
//            eventTypes.remove(0);
//            getBuildsFromSlugRecursivelyInFuture(slug, resultByEventTypes, originalAfterNumber, originalAfterNumber, eventTypes, status, prNumber, previousBranch, limitNumber, -1);
//        }
//    }
//
//    /**
//     * This is a recursive method allowing to get the number of the last build before a given date from a slug.
//     *
//     * @param slug The slug where to get the build
//     * @param date The date to get the last build
//     * @param after_number Used for pagination: multiple requests may have to be made to reach the interesting build
//     * @param onlyAfterNumber Consider only builds after the specified after_number: should be use in conjunction with a specified after_number
//     * @return The build number of the last build before a given date from a slug.
//     */
//    public static int getTheLastBuildNumberOfADate(String slug, Date date, int after_number, boolean onlyAfterNumber) {
//        if ((date == null) || (after_number <= 0 && onlyAfterNumber)) {
//            return -1;
//        }
//
//        String resourceUrl = getResourceUrl(slug, null, after_number);
//
//        boolean dateReached = false;
//
//        Map<Integer, Commit> commits = new HashMap<Integer,Commit>();
//
//        JsonArray buildArray = getBuildsAndCommits(resourceUrl, commits, false);
//
//        int lastBuildNumber = Integer.MAX_VALUE;
//
//        for (JsonElement buildJson : buildArray) {
//            Build build = createGson().fromJson(buildJson, Build.class);
//
//            if (build.getNumber() != null) {
//                int buildNumber = Integer.parseInt(build.getNumber());
//                if (lastBuildNumber > buildNumber) {
//                    lastBuildNumber = buildNumber;
//                }
//            }
//
//            if (build.getFinishedAt() != null && !build.getFinishedAt().after(date)) {
//                dateReached = true;
//                break;
//            }
//        }
//
//        if (!dateReached && !(buildArray.size() == 0)) {
//            return getTheLastBuildNumberOfADate(slug, date, lastBuildNumber, onlyAfterNumber);
//        } else {
//            return lastBuildNumber;
//        }
//    }
//
//    public static List<Build> getBuildsFromSlugWithLimitDate(String slug, Date limitDate) {
//        List<Build> result = getBuildsFromSlug(slug, limitDate, 0, 0, getEventTypes(), 0, null, -1, false, null, -1);
//        return result;
//    }
//
//    public static List<Build> getBuildsFromSlug(String slug) {
//        return getBuildsFromSlugWithLimitDate(slug, null);
//    }
//
//    public static List<Build> getBuildsFromRepositoryWithLimitDate(Repository repository, Date limitDate) {
//        List<Build> result = getBuildsFromSlug(repository.getSlug(), limitDate, 0, 0, getEventTypes(), 0, null, -1, false, null, -1);
//
//        for (Build b : result) {
//            b.setRepository(repository);
//        }
//        return result;
//    }
//
//
//    public static Build getLastBuildOfSameBranchOfStatusBeforeBuild(Build build, BuildStatus status) {
//        return BuildHelper.getLastBuildOfSameBranchOfStatusBeforeBuild(build, status, false);
//    }
//
//    /**
//     * Return the last build before the given build which respect the given status and which is from the same PR if it's a PR build. If given status is null, it will return the last build before.
//     * If no build is found, it returns null.
//     * @param build The build to look before
//     * @param status The status we are looking for
//     * @param skipDateLimit if false, this will go only 2 years in the past. If true this limit is skipped and it looks as far as needed.
//     * @return The searched build or null if nothing has been found
//     */
//    public static Build getLastBuildOfSameBranchOfStatusBeforeBuild(Build build, BuildStatus status, boolean skipDateLimit) {
//        String slug = build.getRepository().getSlug();
//
//        Date limitDate = null;
//        if (!skipDateLimit) {
//            Calendar calendar = Calendar.build();
//            calendar.add(Calendar.YEAR, -2);
//            limitDate = calendar.getTime();
//        }
//
//        int after_number = 0;
//        try {
//            after_number = Integer.parseInt(build.getNumber());
//        } catch (NumberFormatException e) {
//            build().getLogger().error("Error while parsing build number for build id: "+build.getId(),e);
//            return null;
//        }
//
//        int limitNumber = 1;
//        List<String> eventTypes = new ArrayList<String>();
//        int prNumber;
//
//        if (build.isPullRequest()) {
//            eventTypes.add("pull_request");
//            prNumber = build.getPullRequestNumber();
//        } else {
//            eventTypes.add("cron");
//            eventTypes.add("push");
//            prNumber = -1;
//        }
//
//        List<Build> results = getBuildsFromSlug(slug, limitDate, after_number, after_number, eventTypes, limitNumber, status, prNumber, true, build.getCommit().getBranch(), -1);
//
//        if (results.size() > 0) {
//            if (results.size() > 1) {
//                Collections.sort(results);
//            }
//            return results.get(results.size()-1);
//        } else {
//            return null;
//        }
//    }
//
//    public static Build getNextBuildOfSameBranchOfStatusAfterBuild(Build build, BuildStatus status) {
//        String slug = build.getRepository().getSlug();
//        List<Build> results = new ArrayList<Build>();
//
//
//        int after_number = 0;
//        try {
//            after_number = Integer.parseInt(build.getNumber());
//        } catch (NumberFormatException e) {
//            build().getLogger().error("Error while parsing build number for build id: "+build.getId(),e);
//            return null;
//        }
//
//        List<String> eventTypes = new ArrayList<String>();
//        int prNumber;
//
//        if (build.isPullRequest()) {
//            eventTypes.add("pull_request");
//            prNumber = build.getPullRequestNumber();
//        } else {
//            eventTypes.add("cron");
//            eventTypes.add("push");
//            prNumber = -1;
//        }
//
//        int limitNumber = 1;
//
//        Map<String, List<Build>> resultsByEventTypes = new HashMap<>();
//        getBuildsFromSlugRecursivelyInFuture(slug, resultsByEventTypes, after_number, after_number, eventTypes, status, prNumber, build.getCommit().getBranch(), limitNumber, -1);
//
//        for (List<Build> tempResults : resultsByEventTypes.values()) {
//            results.addAll(tempResults);
//        }
//
//        if (results.size() > 0) {
//            if (results.size() > 1) {
//                Collections.sort(results);
//            }
//            return results.get(0);
//        } else {
//            return null;
//        }
//    }
//
//    public static Build getLastSuccessfulBuildFromMaster(Repository repository, boolean withCron) {
//        return getLastSuccessfulBuildFromMaster(repository, withCron, -1);
//    }
//
//    public static Build getLastSuccessfulBuildFromMaster(Repository repository, boolean withCron, int delayTimeout) {
//        String slug = repository.getSlug();
//
//        Calendar calendar = Calendar.build();
//        calendar.add(Calendar.YEAR, -10);
//        Date limitDate = calendar.getTime();
//
//        int limitNumber = 1;
//        List<String> eventTypes = new ArrayList<String>();
//        if (withCron) {
//            eventTypes.add("cron");
//        }
//        eventTypes.add("push");
//        int prNumber = -1;
//
//        List<Build> results = getBuildsFromSlug(slug, limitDate, 0, 0, eventTypes, limitNumber, BuildStatus.PASSED, prNumber, false, null, delayTimeout);
//
//        if (results.size() > 0) {
//            return results.get(0);
//        } else {
//            return null;
//        }
//    }
//
//    public static Build getLastBuildFromMaster(Repository repository) {
//        String slug = repository.getSlug();
//
//        Calendar calendar = Calendar.build();
//        calendar.add(Calendar.YEAR, -10);
//        Date limitDate = calendar.getTime();
//
//        int limitNumber = 1;
//        List<String> eventTypes = new ArrayList<String>();
//        eventTypes.add("cron");
//        eventTypes.add("push");
//        int prNumber = -1;
//
//        List<Build> results = getBuildsFromSlug(slug, limitDate, 0, 0, eventTypes, limitNumber, null, prNumber, false, null, -1);
//
//
//        if (results.size() > 0) {
//            return results.get(0);
//        } else {
//            return null;
//        }
//    }
//
//    public static List<Build> getBuildsFromRepositoryInTimeInterval(Repository repository, Date initialDate, Date finalDate) {
//        int lastBuildNumber = getTheLastBuildNumberOfADate(repository.getSlug(), finalDate, 0, false);
//        if (lastBuildNumber != -1) {
//
//            int after_number = lastBuildNumber + 1;
//
//            List<Build> results = getBuildsFromSlug(repository.getSlug(), initialDate, after_number, after_number, getEventTypes(), 0, null, -1, true, null, -1);
//
//            for (Build b : results) {
//                b.setRepository(repository);
//            }
//            if (results.size() > 1) {
//                Collections.sort(results);
//            }
//            return results;
//        }
//        return null;
//    }
}
