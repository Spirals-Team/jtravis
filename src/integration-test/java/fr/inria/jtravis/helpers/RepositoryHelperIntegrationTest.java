package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class RepositoryHelperIntegrationTest extends AbstractTest {

    @Test
    public void testGetUnknowRepositoryReturnNull() {
        assertFalse(getJTravis().repository().fromSlug("surli/unknown").isPresent());
    }
}
