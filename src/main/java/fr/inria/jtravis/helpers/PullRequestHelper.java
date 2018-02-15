package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.PullRequest;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.Repository;
import okhttp3.OkHttpClient;
import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by urli on 04/01/2017.
 */
public class PullRequestHelper extends EntityHelper {

    private RepositoryHelper repositoryHelper;

    public PullRequestHelper(TravisConfig config, OkHttpClient client, RepositoryHelper repositoryHelper) {
        super(config, client);
        this.repositoryHelper = repositoryHelper;
    }


    public PullRequest fromBuild(Build build) {
        try {
            if (build.isPullRequest()) {
                GitHub github = this.getGithub();
                GHRateLimit rateLimit = github.getRateLimit();
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                this.getLogger().debug("GitHub ratelimit: Limit: " + rateLimit.limit + " Remaining: " + rateLimit.remaining + " Reset hour: " + dateFormat.format(rateLimit.reset));

                if (rateLimit.remaining > 2) {
                    GHRepository ghRepo = github.getRepository(build.getRepository().getSlug());
                    GHPullRequest pullRequest = ghRepo.getPullRequest(build.getPullRequestNumber());
                    PullRequest prInformation = new PullRequest();

                    GHRepository headRepo = pullRequest.getHead().getRepository();

                    if (headRepo == null) {
                        this.getLogger().warn("The head repository is null: maybe it has been deleted from GitHub");
                        return null;
                    }

                    GHCommit base, head;
                    try {
                        GHCommit commitMerge = ghRepo.getCommit(build.getCommit().getSha());
                        base = commitMerge.getParents().get(0);
                        head = commitMerge.getParents().get(1);
                    } catch (FileNotFoundException e) {
                        this.getLogger().error("The merge commit was deleted from Github: it means the previous commit information can't be get.");
                        return null;
                    }

                    return prInformation.setOtherRepo(headRepo).setBase(base).setHead(head).setBaseRef(pullRequest.getBase()).setHeadRef(pullRequest.getHead());
                } else {
                    this.getLogger().warn("You reach your rate limit for github, you have to wait " + rateLimit.reset + " to get datas. PRInformation will be null for build "+build.getId());
                }
            } else {
                this.getLogger().info("Getting PRInformation return null for build id "+build.getId()+" as it does not come from a PR.");
            }
        } catch (IOException e) {
            this.getLogger().warn("Error when getting PRInformation for build id "+build.getId()+" : "+e.getMessage());
        }
        return null;
    }
}
