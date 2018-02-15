package fr.inria.jtravis.helpers;

import fr.inria.jtravis.TravisConfig;
import fr.inria.jtravis.entities.PRInformation;
import fr.inria.jtravis.entities.Build;
import okhttp3.OkHttpClient;

/**
 * Created by urli on 04/01/2017.
 */
public class PRInformationHelper extends EntityHelper {

    private static PRInformationHelper instance;

    public PRInformationHelper(TravisConfig config, OkHttpClient client) {
        super(config, client);
    }


    public static PRInformation getPRInformationFromBuild(Build build) {
//        try {
//            if (build.isPullRequest()) {
//                GitHub github = build().getGithub();
//                GHRateLimit rateLimit = github.getRateLimit();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
//                build().getLogger().debug("GitHub ratelimit: Limit: " + rateLimit.limit + " Remaining: " + rateLimit.remaining + " Reset hour: " + dateFormat.format(rateLimit.reset));
//
//                if (rateLimit.remaining > 2) {
//                    GHRepository ghRepo = github.getRepository(build.getRepository().getSlug());
//                    GHPullRequest pullRequest = ghRepo.getPullRequest(build.getPullRequestNumber());
//                    PRInformation prInformation = new PRInformation();
//
//                    GHRepository headRepo = pullRequest.getHead().getRepository();
//
//                    if (headRepo == null) {
//                        build().getLogger().warn("The head repository is null: maybe it has been deleted from GitHub");
//                        return null;
//                    }
//
//                    GHCommit base, head;
//                    try {
//                        GHCommit commitMerge = ghRepo.getCommit(build.getCommit().getSha());
//                        base = commitMerge.getParents().get(0);
//                        head = commitMerge.getParents().get(1);
//                    } catch (FileNotFoundException e) {
//                        build().getLogger().error("The merge commit was deleted from Github: it means the previous commit information can't be get.");
//                        return null;
//                    }
//
//
//                    Repository repoPR = new Repository();
//                    repoPR.setId(headRepo.getId());
//                    repoPR.setDescription(headRepo.getDescription());
//                    repoPR.setActive(true);
//                    repoPR.setSlug(headRepo.getFullName());
//
//                    prInformation.setOtherRepo(repoPR);
//
//                    Commit commitHead = new Commit();
//                    commitHead.setSha(head.getSHA1());
//                    commitHead.setBranch(pullRequest.getHead().getRef());
//                    commitHead.setCompareUrl(head.getHtmlUrl().toString());
//
//                    GHCommit.ShortInfo infoCommit = head.getCommitShortInfo();
//
//                    commitHead.setMessage(infoCommit.getMessage());
//                    commitHead.setCommitterEmail(infoCommit.getAuthor().getEmail());
//                    commitHead.setCommitterName(infoCommit.getAuthor().getName());
//                    commitHead.setCommittedAt(infoCommit.getCommitDate());
//                    prInformation.setHead(commitHead);
//
//                    Commit commitBase = new Commit();
//                    commitBase.setSha(base.getSHA1());
//                    commitBase.setBranch(pullRequest.getBase().getRef());
//                    commitBase.setCompareUrl(base.getHtmlUrl().toString());
//
//                    infoCommit = base.getCommitShortInfo();
//
//                    commitBase.setMessage(infoCommit.getMessage());
//                    commitBase.setCommitterEmail(infoCommit.getAuthor().getEmail());
//                    commitBase.setCommitterName(infoCommit.getAuthor().getName());
//                    commitBase.setCommittedAt(infoCommit.getCommitDate());
//                    prInformation.setBase(commitBase);
//
//                    return prInformation;
//                } else {
//                    build().getLogger().warn("You reach your rate limit for github, you have to wait " + rateLimit.reset + " to get datas. PRInformation will be null for build "+build.getId());
//                }
//            } else {
//                build().getLogger().info("Getting PRInformation return null for build id "+build.getId()+" as it does not come from a PR.");
//            }
//        } catch (IOException e) {
//            build().getLogger().warn("Error when getting PRInformation for build id "+build.getId()+" : "+e.getMessage());
//        }
        return null;
    }
}
