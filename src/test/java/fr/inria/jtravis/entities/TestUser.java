package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUser extends AbstractTest {

    @Test
    public void testRetrieveUserFromJsonAnswer() {
        String filePath = "./src/test/resources/response/user_answer.json";
        JsonObject userObject = this.getJsonObjectFromFilePath(filePath);
        Owner result = EntityHelper.createGson().fromJson(userObject, Owner.class);

        assertNotNull(result);

        Owner expectedUser = new Owner();
        expectedUser.setRepresentation(RepresentationType.STANDARD);
        expectedUser.setType(OwnerType.USER);
        expectedUser.setId(95813);
        expectedUser.setUri("/user/95813");
        expectedUser.setLogin("surli");
        expectedUser.setName("Simon Urli");
        expectedUser.setGithubId(1478232);
        expectedUser.setAvatarUrl("https://avatars2.githubusercontent.com/u/1478232?v=4");
        expectedUser.setSyncedAt(this.getDateFor(2018, Calendar.FEBRUARY, 12, 3, 34, 17, 0));

        assertEquals(expectedUser, result);
    }
}
