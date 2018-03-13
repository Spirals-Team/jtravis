package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestLog extends AbstractTest {

    @Test
    public void testRetrieveLogFromJsonAnswer() {
        String filePath = "./src/test/resources/response/log_answer.json";
        JsonObject logJson = this.getJsonObjectFromFilePath(filePath);

        assertNotNull(logJson);
        Log result = EntityHelper.createGson().fromJson(logJson, Log.class);
        assertNotNull(result);

        Log expectedLog = new Log();
        expectedLog.setUri("/job/340617745/log");
        expectedLog.setRepresentation(RepresentationType.STANDARD);
        expectedLog.setId(248551122);
        expectedLog.setContent("travis_fold:start:worker_info\r\u001b[0K\u001b[33;1mWorker information\u001b[0m\nhostname: 88255186-1dd4-4654-8979-a9de8297acc1@1.i-05992b1-production-2-worker-org-ec2.travisci.net\n[FULL LOG CUT]");

        assertEquals(expectedLog, result);
    }
}
