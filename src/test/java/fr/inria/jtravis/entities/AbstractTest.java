package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import fr.inria.jtravis.helpers.GenericHelper;
import org.apache.commons.lang.StringUtils;
import org.junit.After;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class AbstractTest {

    MockWebServer server;

    @After
    public void tearDown() {
        if (server != null) {
            try {
                server.shutdown();
            } catch (IOException e) {
            }
        }
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
            return GenericHelper.getJsonFromStringContent(content);
        }
    }

    protected Date getDateFor(int year, int month, int day, int hourOfDay, int minutes, int seconds, int milliseconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar.setTimeInMillis(milliseconds);
        calendar.set(year, month, day, hourOfDay, minutes, seconds);

        return calendar.getTime();
    }
}
