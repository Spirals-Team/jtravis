package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.entities.Build;
import fr.inria.jtravis.entities.BuildStub;
import fr.inria.jtravis.entities.BuildTool;
import fr.inria.jtravis.entities.StateType;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
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
}
