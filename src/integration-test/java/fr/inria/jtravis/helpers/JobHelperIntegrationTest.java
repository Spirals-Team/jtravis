package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.IntegrationTest;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.StateType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by urli on 22/12/2016.
 */
public class JobHelperIntegrationTest extends AbstractTest {
    @Category(IntegrationTest.class)
    @Test
    public void testFromId() {
        int id = 340663038;
        Optional<Job> jobOptional = getJTravis().job().fromId(id);

        assertTrue(jobOptional.isPresent());
        Job job = jobOptional.get();

        assertEquals(id, job.getId());
        assertEquals(StateType.FAILED, job.getState());
        assertEquals(340663037, job.getBuild().getId());
        assertEquals(2800492, job.getRepository().getId());
        assertEquals(101294919, job.getCommit().getId());
        assertEquals(83206, job.getOwner().getId());
    }

    @Category(IntegrationTest.class)
    @Test
    public void testFromIdStr() {
        String id = "340663038";
        Optional<Job> jobOptional = getJTravis().job().fromId(id);

        assertTrue(jobOptional.isPresent());
        Job job = jobOptional.get();
        assertEquals(340663038, job.getId());
        assertEquals(StateType.FAILED, job.getState());
        assertEquals(340663037, job.getBuild().getId());
        assertEquals(2800492, job.getRepository().getId());
        assertEquals(101294919, job.getCommit().getId());
        assertEquals(83206, job.getOwner().getId());
    }
}
