package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.PullRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by urli on 04/01/2017.
 */
public class PullRequestHelperIntegrationTest extends AbstractTest {
    @Test
    public void testGetBuildWithPRWhenMergeCommitDeleted() {
        Build originBuild = getJTravis().build().fromId(187029370);
        PullRequest obtainedPRInfo = getJTravis().pullRequest().fromBuild(originBuild);

        assertNull(obtainedPRInfo);
    }

    @Test
    public void testGetCommitInformationFromPR() {
        Build originBuild = getJTravis().build().fromId(186814810);

        PullRequest prInformation = getJTravis().pullRequest().fromBuild(originBuild);

        assertNotNull(prInformation);
        assertEquals("pvojtechovsky/spoon", prInformation.getOtherRepo().getFullName());
        assertEquals("master", prInformation.getBaseRef().getRef());
        assertEquals("lazyQueries", prInformation.getHeadRef().getRef());
        assertEquals("7a55cd2c526a7bbb914dacbe6ba2ddc621f23870", prInformation.getBase().getSHA1());
        assertEquals("1ae580bc0f1bbeb140db074bb84c23080620f156", prInformation.getHead().getSHA1());
    }
}
