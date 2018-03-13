package fr.inria.jtravis;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class DateTest extends AbstractTest {
    @Test
    public void shouldReturnTheRightDateWhenCorrectlyFormatted() {
        String dateStr = "2016-12-21T09:48:50Z";
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(dateStr);

        Date expectedDate = getDateFor(2016, Calendar.DECEMBER, 21, 9, 48, 50, 0);
        Date obtainedDate = EntityHelper.createGson().fromJson(jsonElement, Date.class);
        assertEquals(expectedDate, obtainedDate);
    }

    @Test(expected = JsonSyntaxException.class)
    public void shouldReturnNullWhenWrongFormat() {
        String dateStr = "21/12/20016-09:48:50";
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(dateStr);

        Date obtainedDate = EntityHelper.createGson().fromJson(jsonElement, Date.class);
    }
}
