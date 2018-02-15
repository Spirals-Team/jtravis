package fr.inria.jtravis;

import com.google.gson.JsonObject;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import fr.inria.jtravis.helpers.EntityHelper;
import org.apache.commons.lang.StringUtils;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AbstractTest {

    private MockWebServer server;

    @After
    public void tearDown() {
        if (server != null) {
            try {
                server.shutdown();
            } catch (IOException e) {
            }
        }
    }

    public MockWebServer getMockServer() {
        if (this.server == null) {
            this.server = new MockWebServer();
        }
        return this.server;
    }

    public void enqueueContentMockServer(String content) {
        getMockServer().enqueue(new MockResponse().setBody(content));
    }

    public JTravis getJTravis() {
        try {
            if (server != null) {
                server.start();
                HttpUrl baseUrl = server.url("fake");
                return JTravis.builder().setEndpoint(baseUrl.toString()).build();
            } else {
                return JTravis.builder().build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected String getFileContent(String filePath) {
        try {
            return StringUtils.join(Files.readAllLines(new File(filePath).toPath()), "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected JsonObject getJsonObjectFromFilePath(String filePath) {
        String content = this.getFileContent(filePath);
        if (content == null) {
            return null;
        } else {
            return EntityHelper.getJsonFromStringContent(content);
        }
    }

    protected static Date getDateFor(int year, int month, int day, int hourOfDay, int minutes, int seconds, int milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(milliseconds);
        calendar.set(year, month, day, hourOfDay, minutes, seconds);

        return calendar.getTime();
    }
}
