package fr.inria.jtravis.helpers;

import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Owner;
import java.util.Arrays;
import java.util.Optional;

public class OwnerHelper extends EntityHelper {

    public OwnerHelper(JTravis jTravis) {
        super(jTravis);
    }

    public Optional<Owner> fromLogin(String login) {
        return this.getEntityFromUri(Owner.class, Arrays.asList(TravisConstants.OWNER_ENDPOINT, login), null);
    }
}
