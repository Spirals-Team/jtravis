package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRepository extends AbstractTest {

    @Test
    public void testRetrieveRepositoryFromJsonResponse() {
        String filePath = "./src/test/resources/response/repo_answer.json";
        JsonObject repoObject = this.getJsonObjectFromFilePath(filePath);
        Repository result = AbstractHelper.createGson().fromJson(repoObject, Repository.class);

        assertNotNull(result);

        Repository expectedRepository = new Repository();
        expectedRepository.setId(2800492);
        expectedRepository.setName("spoon");
        expectedRepository.setSlug("INRIA/spoon");
        expectedRepository.setDescription("Spoon is a library to analyze, transform, rewrite, transpile Java source code (incl Java 9 modules). It parses source files to build a well-designed AST with powerful analysis and transformation API. Made at Inria with :heart:, :beers: and :sparkles:.");
        expectedRepository.setGithubLanguage(null);
        expectedRepository.setActive(true);
        expectedRepository.setPrivateProperty(false);
        expectedRepository.setStarred(false);
        expectedRepository.setUri("/repo/2800492");

        Owner owner = new Owner();
        owner.setType(OwnerType.ORGANIZATION);
        owner.setId(83206);
        owner.setLogin("INRIA");
        owner.setUri("/org/83206");
        expectedRepository.setOwner(owner);

        Branch defaultBranch = new Branch();
        defaultBranch.setUri("/repo/2800492/branch/master");
        defaultBranch.setName("master");
        expectedRepository.setDefaultBranch(defaultBranch);

        assertEquals(expectedRepository, result);
    }
}
