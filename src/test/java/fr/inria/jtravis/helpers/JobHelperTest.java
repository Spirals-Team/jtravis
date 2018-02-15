package fr.inria.jtravis.helpers;

import com.squareup.okhttp.mockwebserver.RecordedRequest;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.UnitTest;
import fr.inria.jtravis.entities.Job;
import fr.inria.jtravis.entities.TestJob;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by urli on 22/12/2016.
 */
public class JobHelperTest extends AbstractTest {
    @Category(UnitTest.class)
    @Test
    public void testFromIdStrMocked() throws IOException, InterruptedException {
        String id = "340663038";
        String buildContent = this.getFileContent(TestJob.JOB_STANDARD_PATH);

        this.enqueueContentMockServer(buildContent);
        Job job = getJTravis().job().fromId(id);

        assertEquals(TestJob.standardExpectedJob(), job);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals("/fake"+JobHelper.JOB_ENDPOINT+id, request1.getPath());
    }

    @Category(UnitTest.class)
    @Test
    public void testFromIdIntegerMocked() throws IOException, InterruptedException {
        int id = 340663038;
        String buildContent = this.getFileContent(TestJob.JOB_STANDARD_PATH);

        this.enqueueContentMockServer(buildContent);

        Job job = getJTravis().job().fromId(id);

        assertEquals(TestJob.standardExpectedJob(), job);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals("/fake"+JobHelper.JOB_ENDPOINT+id, request1.getPath());
    }
}
