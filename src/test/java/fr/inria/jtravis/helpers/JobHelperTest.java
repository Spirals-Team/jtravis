package fr.inria.jtravis.helpers;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.JTravis;
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
        MockWebServer server = new MockWebServer();
        String buildContent = this.getFileContent(TestJob.JOB_STANDARD_PATH);

        server.enqueue(new MockResponse().setBody(buildContent));

        server.start();
        HttpUrl baseUrl = server.url("fake");
        JTravis.getInstance().setTravisEndpoint(baseUrl.toString());
        Job job = JTravis.getInstance().job().fromId(id);

        assertEquals(TestJob.standardExpectedJob(), job);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/fake"+JobHelper.JOB_ENDPOINT+id, request1.getPath());
    }

    @Category(UnitTest.class)
    @Test
    public void testFromIdIntegerMocked() throws IOException, InterruptedException {
        int id = 340663038;
        MockWebServer server = new MockWebServer();
        String buildContent = this.getFileContent(TestJob.JOB_STANDARD_PATH);

        server.enqueue(new MockResponse().setBody(buildContent));

        server.start();
        HttpUrl baseUrl = server.url("fake");
        JTravis.getInstance().setTravisEndpoint(baseUrl.toString());
        Job job = JTravis.getInstance().job().fromId(id);

        assertEquals(TestJob.standardExpectedJob(), job);
        RecordedRequest request1 = server.takeRequest();
        assertEquals("/fake"+JobHelper.JOB_ENDPOINT+id, request1.getPath());
    }
}
