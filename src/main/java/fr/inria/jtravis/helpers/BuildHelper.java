package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.Builds;
import fr.inria.jtravis.entities.Repository;
import fr.inria.jtravis.entities.StateType;
import okhttp3.OkHttpClient;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

/**
 * The helper to deal with Build objects
 *
 * @author Simon Urli
 */
public class BuildHelper extends EntityHelper {

    public BuildHelper(JTravis jTravis, TravisConfig config, OkHttpClient client) {
        super(jTravis, config, client);
    }

    public Optional<Builds> fromRepository(Repository repository) {
        return getEntityFromUri(Builds.class, Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repository.getId()),
                TravisConstants.BUILDS_ENDPOINT), null);
    }

    public Optional<Builds> fromRepository(Repository repository, int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("The limit should be > 0. Current value: "+limit);
        }

        Properties properties = new Properties();
        properties.put("limit", limit);

        return getEntityFromUri(Builds.class, Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repository.getId()),
                TravisConstants.BUILDS_ENDPOINT),
                properties);
    }

    public Optional<Builds> next(Builds builds) {
        return this.getNextCollection(builds);
    }

    public Optional<Build> fromId(int id) {
        return getEntityFromUri(Build.class, Arrays.asList(TravisConstants.BUILD_ENDPOINT, String.valueOf(id)), null);
    }

    public Optional<Build> lastBuildFromMasterBranch(Repository repository) {
        Properties properties = new Properties();
        properties.put("branch.name", repository.getDefaultBranch().getName());
        properties.put("limit", 1);
        properties.put("sort_by", new BuildsSorting().byFinishedAtDesc().build());
        Optional<Builds> builds = getEntityFromUri(Builds.class, Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repository.getId()),
                TravisConstants.BUILDS_ENDPOINT),
                properties);

        if (builds.isPresent()) {
            if (builds.get().getBuilds().size() > 0) {
                return Optional.of(builds.get().getBuilds().get(0));
            }
        }
        return Optional.empty();
    }

    public Optional<Build> getBefore(Build originalBuild, boolean sameBranch) {
        return this.getBeforeOrAfter(true, originalBuild, sameBranch, Optional.empty());
    }

    public Optional<Build> getBefore(Build originalBuild, boolean sameBranch, StateType stateFilter) {
        return this.getBeforeOrAfter(true, originalBuild, sameBranch, Optional.of(stateFilter));
    }

    // TODO: In order to improve performance, maybe we can use offset in conjonction with build number.
    /**
     * This methods intends to retrieve a build just before or a just after a given build. The `finishedAt` field is used to assess the date.
     * The targeted build is on the same repository, possibly on the same branch. If the optional stateFilter is given, it must also be on the specified filter.
     * This method works by calling the pagination until a matching build is found or all occurences are passed.
     *
     * @param before If true, the matching build is finished BEFORE the originalBuild. Else it's after.
     * @param originalBuild The build before or after which the targeted build should be retrieved.
     * @param sameBranch If true, the retrieved build should be from the same branch, or the same pull request.
     * @param stateFilter If given, the retrieved build should have the given status.
     * @return The closest build before or after given build matching the given criteria.
     */
    private Optional<Build> getBeforeOrAfter(boolean before, Build originalBuild, boolean sameBranch, Optional<StateType> stateFilter) {
        Comparator<Build> buildComparator;
        if (before) {
            buildComparator = (o1, o2) -> {
                return Math.round(o2.getFinishedAt().getTime() - o1.getFinishedAt().getTime()); // > 0 means o1 finished before o2
            };
        } else {
            buildComparator = (o1, o2) -> {
                return Math.round(o1.getFinishedAt().getTime() - o2.getFinishedAt().getTime()); // > 0 means o1 finished after o2
            };
        }

        int repositoryId = originalBuild.getRepository().getId();

        List<String> pathParameter = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                String.valueOf(repositoryId),
                TravisConstants.BUILDS_ENDPOINT);


        Properties properties = new Properties();
        properties.put("limit", 100);
        stateFilter.ifPresent(stateType -> properties.put("state", stateType.name().toLowerCase()));

        if (before) {
            properties.put("sort_by", new BuildsSorting().byFinishedAtDesc().build());
        } else {
            properties.put("sort_by", new BuildsSorting().byFinishedAt().build()); // easier to request, but it might be less performant
        }

        if (sameBranch) {
            properties.put("branch.name", originalBuild.getBranch().getName());
            if (originalBuild.isPullRequest()) {
                properties.put("event_type", originalBuild.getEventType().name().toLowerCase());
            }
        }

        Optional<Builds> buildsOptional = getEntityFromUri(Builds.class, pathParameter, properties);

        boolean isFinished = false;
        while (buildsOptional.isPresent() && !isFinished) {
            Builds builds = buildsOptional.get();

            // lastPage -> we should interrupt the loop after it.
            isFinished = (builds.getPagination().isLast());

            List<Build> buildList = builds.getBuilds();
            Build lastBuild = buildList.get(buildList.size()-1);

            // we ensure that the build we target is in the current page
            if (buildComparator.compare(lastBuild, originalBuild) > 0) {
                for (Build build : buildList) {

                    // we do not want to get the originalBuild and if it does not respect the time criteria we don't want it either
                    if (build.getId() == originalBuild.getId() ||  buildComparator.compare(build, originalBuild) <= 0) {
                        continue;
                    }

                    if (sameBranch) {
                        if (originalBuild.isPullRequest()) {

                            // if we want the same branch as a pull request we have to check the PR number
                            if (!build.isPullRequest() || originalBuild.getPullRequestNumber() != build.getPullRequestNumber()) {
                                continue;
                            }
                        } else if (build.isPullRequest()) {
                            continue;
                        }
                        if (build.getBranch().equals(originalBuild.getBranch())) {
                            return Optional.of(build);
                        }
                    } else {
                        return Optional.of(build);
                    }
                }
            }

            if (!isFinished) {
                buildsOptional = this.next(builds);
            }
        }

        return Optional.empty();
    }

    public Optional<Build> getAfter(Build originalBuild, boolean sameBranch) {
        return getBeforeOrAfter(false, originalBuild, sameBranch, Optional.empty());
    }

    public Optional<Build> getAfter(Build originalBuild, boolean sameBranch, StateType stateFilter) {
        return getBeforeOrAfter(false, originalBuild, sameBranch, Optional.of(stateFilter));
    }

    private static boolean isBetween(Instant instant, Instant timeRangeBegin, Instant timeRangeEnd) {
        return (instant.isAfter(timeRangeBegin) && !instant.isBefore(timeRangeBegin) && instant.isBefore(timeRangeEnd) && !instant.isAfter(timeRangeEnd));
    }

    public Optional<Build> forDate(String slug, Date date, int durationRange, ChronoUnit timeUnit) {
        Date endDate = new Date(date.toInstant().plus(durationRange, timeUnit).toEpochMilli());
        Optional<List<Build>> results = this.allForDate(slug, date, endDate, true, false);
        return results.map(builds -> builds.get(0));
    }

    public Optional<List<Build>> allForDate(String slug, Date date, int durationRange, ChronoUnit timeUnit) {
        Date endDate = new Date(date.toInstant().plus(durationRange, timeUnit).toEpochMilli());
        return allForDate(slug, date, endDate, false, false);
    }

    public Optional<List<Build>> afterDate(String slug, Date date) {
        return allForDate(slug, date, new Date(), false, true);
    }

    private Optional<List<Build>> allForDate(String slug, Date date, Date endDate, boolean stopAfterFirstOne, boolean getRunning) {

        Optional<String> optionalS = this.getEncodedSlug(slug);

        if (!optionalS.isPresent()) {
            return Optional.empty();
        }

        List<String> pathParameter = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                optionalS.get(),
                TravisConstants.BUILDS_ENDPOINT);

        Instant limitDateBeginOfTheRange = date.toInstant();
        Instant limitDateEndOfTheRange = endDate.toInstant();

        Properties properties = new Properties();
        properties.put("limit", 100);
        properties.put("sort_by", new BuildsSorting().byFinishedAtDesc().build());

        List<Build> resultList = new ArrayList<>();
        boolean isFinished = false;

        Optional<Builds> optionalBuilds = this.getEntityFromUri(Builds.class, pathParameter, properties);
        if (!optionalBuilds.isPresent()) {
            return Optional.empty();
        } else {
            while (optionalBuilds.isPresent() && !isFinished) {
                Builds builds = optionalBuilds.get();
                List<Build> buildList = builds.getBuilds();
                Build firstBuild = buildList.get(0);
                Build lastBuild = buildList.get(buildList.size()-1);

                if (firstBuild.getFinishedAt() != null && firstBuild.getFinishedAt().toInstant().isBefore(limitDateBeginOfTheRange)) {
                    return Optional.empty();
                } else if (lastBuild.getFinishedAt().toInstant().isAfter(limitDateEndOfTheRange)) {
                    optionalBuilds = this.next(builds);
                } else {
                    for (Build build : buildList) {
                        if ((getRunning && build.getFinishedAt() == null) || (build.getFinishedAt() != null && isBetween(build.getFinishedAt().toInstant(), limitDateBeginOfTheRange, limitDateEndOfTheRange))) {
                            resultList.add(build);
                            if (stopAfterFirstOne) {
                                break;
                            }
                        }
                    }

                    if (lastBuild.getFinishedAt().toInstant().isBefore(limitDateBeginOfTheRange)) {
                        isFinished = true;
                    } else {
                        optionalBuilds = this.next(builds);
                    }
                }


            }
            if (resultList.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(resultList);
            }
        }
    }

    public Optional<Build> first(String slug) {
        Optional<String> optionalS = this.getEncodedSlug(slug);

        if (!optionalS.isPresent()) {
            return Optional.empty();
        }

        List<String> pathParameter = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                optionalS.get(),
                TravisConstants.BUILDS_ENDPOINT);

        Properties properties = new Properties();
        properties.put("sort_by", new BuildsSorting().byFinishedAtDesc().build());

        Optional<Builds> optionalBuilds = this.getEntityFromUri(Builds.class, pathParameter, properties);
        return optionalBuilds.map(builds -> builds.getBuilds().get(0));
    }

    public Optional<Build> last(String slug) {
        Optional<String> optionalS = this.getEncodedSlug(slug);

        if (!optionalS.isPresent()) {
            return Optional.empty();
        }

        List<String> pathParameter = Arrays.asList(
                TravisConstants.REPO_ENDPOINT,
                optionalS.get(),
                TravisConstants.BUILDS_ENDPOINT);

        Properties properties = new Properties();
        properties.put("sort_by", new BuildsSorting().byFinishedAt().build());

        Optional<Builds> optionalBuilds = this.getEntityFromUri(Builds.class, pathParameter, properties);
        return optionalBuilds.map(builds -> builds.getBuilds().get(0));
    }
}
