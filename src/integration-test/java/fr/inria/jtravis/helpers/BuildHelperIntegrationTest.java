package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.BuildStub;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BuildHelperIntegrationTest extends AbstractTest {


    @Test
    public void testGetBuildFromIdWithRepoShouldReturnTheRightBuild() {
       int buildId = 185719843;
        Optional<Build> obtainedBuildOpt = getJTravis().build().fromId(buildId);

        assertTrue(obtainedBuildOpt.isPresent());
        Build obtainedBuild = obtainedBuildOpt.get();
        assertEquals(BuildStub.expectedBuildWithoutPR(), obtainedBuild);
    }
}
