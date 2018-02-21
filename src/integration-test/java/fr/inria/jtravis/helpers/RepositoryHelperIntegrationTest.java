package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.entities.Repository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RepositoryHelperIntegrationTest extends AbstractTest {

    @Test
    public void testGetUnknowRepositoryReturnNull() {
        assertFalse(getJTravis().repository().fromSlug("surli/unknown").isPresent());
    }

    @Test
    public void testGetRailsRepositoryReturnRightData() {
        Optional<Repository> repositoryOptional = getJTravis().repository().fromSlug("rails/rails");

        assertTrue(repositoryOptional.isPresent());

        Repository repository = repositoryOptional.get();
        assertEquals("rails/rails", repository.getSlug());
        assertEquals(891, repository.getId());
    }
}
