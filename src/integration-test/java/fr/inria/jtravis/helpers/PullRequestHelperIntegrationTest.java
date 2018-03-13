package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.PullRequest;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by urli on 04/01/2017.
 */
public class PullRequestHelperIntegrationTest extends AbstractTest {
    @Test
    public void testGetBuildWithPRWhenMergeCommitDeleted() {
        int id = 187029370;
        Optional<Build> originBuild = getJTravis().build().fromId(id);
        assertTrue(originBuild.isPresent());
        Optional<PullRequest> obtainedPRInfo = getJTravis().pullRequest().fromBuild(originBuild.get());
        assertFalse(obtainedPRInfo.isPresent());
    }

    @Test
    public void testGetCommitInformationFromPR() {
        int id = 186814810;
        Optional<Build> originBuild = getJTravis().build().fromId(id);
        assertTrue(originBuild.isPresent());
        Optional<PullRequest> obtainedPRInfoOpt = getJTravis().pullRequest().fromBuild(originBuild.get());
        assertTrue(obtainedPRInfoOpt.isPresent());

        PullRequest prInformation = obtainedPRInfoOpt.get();
        assertEquals("pvojtechovsky/spoon", prInformation.getOtherRepo().getFullName());
        assertEquals("master", prInformation.getBaseRef().getRef());
        assertEquals("lazyQueries", prInformation.getHeadRef().getRef());
        assertEquals("7a55cd2c526a7bbb914dacbe6ba2ddc621f23870", prInformation.getBase().getSHA1());
        assertEquals("1ae580bc0f1bbeb140db074bb84c23080620f156", prInformation.getHead().getSHA1());
    }
}
