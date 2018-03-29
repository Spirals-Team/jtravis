package fr.inria.jtravis.helpers;

import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.IntegrationTest;
import fr.inria.jtravis.entities.Owner;
import fr.inria.jtravis.entities.OwnerType;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OwnerHelperIntegrationTest extends AbstractTest {

    @Category(IntegrationTest.class)
    @Test
    public void testGetOwner() {
        String login = "surli";
        Optional<Owner> ownerOptional = this.getJTravis().owner().fromLogin(login);
        assertTrue(ownerOptional.isPresent());

        Owner owner = ownerOptional.get();

        assertEquals(OwnerType.USER, owner.getType());
        assertEquals("surli", owner.getLogin());
        assertEquals("Simon Urli", owner.getName());
        assertEquals(1478232, owner.getGithubId());
        assertEquals("https://avatars2.githubusercontent.com/u/1478232?v=4", owner.getAvatarUrl());
    }
}
