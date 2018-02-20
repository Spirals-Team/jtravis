package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestRepository extends AbstractTest {

    public static final String PATH_REPO_STANDARD = "./src/test/resources/response/repository/repo_answer_standard.json";
    private static final String PATH_REPO_MINIMAL = "./src/test/resources/response/repository/repo_answer_minimal.json";

    public static Repository getStandardExpectedRepo() {
        Repository expectedRepository = new Repository();
        expectedRepository.setRepresentation(RepresentationType.STANDARD);
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
        defaultBranch.setRepresentation(RepresentationType.MINIMAL);
        defaultBranch.setName("master");
        expectedRepository.setDefaultBranch(defaultBranch);

        return expectedRepository;
    }

    @Test
    public void testRetrieveRepositoryFromJsonResponse() {
        JsonObject repoObject = this.getJsonObjectFromFilePath(PATH_REPO_STANDARD);
        Repository result = EntityHelper.createGson().fromJson(repoObject, Repository.class);

        assertNotNull(result);

        assertEquals(getStandardExpectedRepo(), result);
    }

    @Test
    public void testRefreshRepository() throws InterruptedException {
        JsonObject repoObject = this.getJsonObjectFromFilePath(PATH_REPO_MINIMAL);
        Repository minimalRepo = EntityHelper.createGson().fromJson(repoObject, Repository.class);

        assertNotNull(minimalRepo);
        Repository expectedMinimalRepo = new Repository();
        expectedMinimalRepo.setRepresentation(RepresentationType.MINIMAL);
        expectedMinimalRepo.setUri("/repo/2800492");
        expectedMinimalRepo.setId(2800492);
        expectedMinimalRepo.setName("spoon");
        expectedMinimalRepo.setSlug("INRIA/spoon");

        assertEquals(expectedMinimalRepo, minimalRepo);

        String buildContent = this.getFileContent(PATH_REPO_STANDARD);

        enqueueContentMockServer(buildContent);


        assertTrue(getJTravis().refresh(minimalRepo));
        assertEquals(getStandardExpectedRepo(), minimalRepo);

        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals("/fake"+expectedMinimalRepo.getUri(), request1.getPath());

        assertFalse(getJTravis().refresh(minimalRepo));
    }
}
