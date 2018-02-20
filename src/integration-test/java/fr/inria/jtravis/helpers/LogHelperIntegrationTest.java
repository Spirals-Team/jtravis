package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.JobStub;
import fr.inria.jtravis.entities.Log;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class LogHelperIntegrationTest extends AbstractTest {
    private static String logBegin = "travis_fold:start:worker_info";

    private static String passingLogEnd = "Done. Your build exited with 0.";
    private static String failingLogEnd = "Done. Your build exited with 1.";
    private static String erroringLogEnd = "Your build has been stopped.";

    @Test
    public void testGetLogFromPassingJob() {
        Job job = JobStub.passingJob();
        int length = 1499916;

        Optional<Log> obtainedLogOpt = getJTravis().log().from(job);
        assertTrue(obtainedLogOpt.isPresent());
        Log obtainedLog = obtainedLogOpt.get();
        assertEquals(135819715, obtainedLog.getId());
        assertNotNull(obtainedLog.getContent());
        assertEquals(length, obtainedLog.getContent().length());
        assertTrue(obtainedLog.getContent().trim().startsWith(logBegin));
        assertTrue(obtainedLog.getContent().trim().endsWith(passingLogEnd));
    }

    @Test
    public void testGetFailingLogFromId() {
        Job job = JobStub.failingJob();

        int length = 163775;

        Optional<Log> obtainedLogOpt = getJTravis().log().from(job);
        assertTrue(obtainedLogOpt.isPresent());
        Log obtainedLog = obtainedLogOpt.get();
        assertEquals(155697016, obtainedLog.getId());
        assertNotNull(obtainedLog.getContent());
        assertEquals(length, obtainedLog.getContent().length());
        assertTrue(obtainedLog.getContent().trim().startsWith(logBegin));
        assertTrue(obtainedLog.getContent().trim().endsWith(failingLogEnd));
    }

    @Test
    public void testGetErroringLogFromId() {
        Job job = JobStub.erroringJob();

        int length = 91188;

        Optional<Log> obtainedLogOpt = getJTravis().log().from(job);
        assertTrue(obtainedLogOpt.isPresent());
        Log obtainedLog = obtainedLogOpt.get();
        assertEquals(154426055, obtainedLog.getId());
        assertNotNull(obtainedLog.getContent());
        assertEquals(length, obtainedLog.getContent().length());
        assertTrue(obtainedLog.getContent().trim().startsWith(logBegin));
        assertTrue(obtainedLog.getContent().trim().endsWith(erroringLogEnd));
    }
}
