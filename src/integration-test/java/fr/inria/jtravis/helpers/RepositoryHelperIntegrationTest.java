package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertNull;

public class RepositoryHelperIntegrationTest extends AbstractTest {

    @Test
    public void testGetUnknowRepositoryReturnNull() {
        assertNull(getJTravis().repository().fromSlug("surli/unknown"));
    }
}
