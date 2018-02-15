package fr.inria.jtravis.helpers;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.entities.Repository;
import fr.inria.jtravis.entities.TestRepository;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;


/**
 * Created by urli on 21/12/2016.
 */
public class RepositoryHelperTest extends AbstractTest {

    @Test
    public void testFromSlugMocked() throws InterruptedException {
        String slug = "INRIA/spoon";
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);

        Repository repository = getJTravis().repository().fromSlug(slug);

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals("/fake"+RepositoryHelper.REPO_ENDPOINT+slug, request1.getPath());
    }

    @Test
    public void testFromIdStrMocked() throws InterruptedException {
        String id = "2800492";
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);
        Repository repository = getJTravis().repository().fromId(id);

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals("/fake"+RepositoryHelper.REPO_ENDPOINT+id, request1.getPath());
    }

    @Test
    public void testFromIdIntMocked() throws InterruptedException {
        int id = 2800492;
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);
        Repository repository = getJTravis().repository().fromId(id);

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals("/fake"+RepositoryHelper.REPO_ENDPOINT+id, request1.getPath());
    }


    /*MockWebServer server;

    @After
    public void tearDown() {
        if (server != null) {
            try {
                server.shutdown();
            } catch (IOException e) {
            }
        }
        RepositoryHelper.build().setEndpoint(AbstractHelper.TRAVIS_API_ENDPOINT);
    }

    @Test
    public void testGetSpoonRepoFromSlugWorks() throws IOException {
        server = new MockWebServer();
        String mockAnswer = "{\"repo\":{\"id\":2800492,\"slug\":\"INRIA/spoon\",\"active\":true,\"description\":\"Spoon is a library to analyze, rewrite, transform, transpile Java source code. It parses source files to build a well-designed AST with powerful analysis and transformation API. It fully supports Java 8. Made at Inria with :heart:, :beers: and :sparkles:\",\"last_build_id\":190205141,\"last_build_number\":\"2433\",\"last_build_state\":\"started\",\"last_build_duration\":null,\"last_build_language\":null,\"last_build_started_at\":\"2017-01-09T10:28:08Z\",\"last_build_finished_at\":null,\"github_language\":null}}";
        server.enqueue(new MockResponse().setBody(mockAnswer));

        server.start();
        HttpUrl baseUrl = server.url("/repos/INRIA/spoon");

        RepositoryHelper.build().setEndpoint(baseUrl.toString());
        Repository spoonRepo = RepositoryHelper.getRepositoryFromSlug("INRIA/spoon");

        assertEquals("INRIA/spoon",spoonRepo.getSlug());
        assertEquals(2800492, spoonRepo.getId());
        //assertTrue(spoonRepo.getLastBuildId() > 0);
    }

    @Test
    public void testGetSpoonRepoFromIdWorks() {
        Repository spoonRepo = RepositoryHelper.getRepositoryFromId(2800492);

        assertEquals("INRIA/spoon",spoonRepo.getSlug());
        assertEquals(2800492, spoonRepo.getId());
        //assertTrue(spoonRepo.getLastBuildId() > 0);
    }

    @Test
    public void testGetUnknownRepoThrowsException() {
        Repository unknownRepo = RepositoryHelper.getRepositoryFromSlug("surli/unknown");
        assertTrue(unknownRepo == null);
    }*/
}
