package fr.inria.jtravis.helpers;

import com.squareup.okhttp.mockwebserver.RecordedRequest;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Repository;
import fr.inria.jtravis.entities.TestRepository;
import org.junit.Test;

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
        assertEquals(this.expectedUrl(TravisConstants.REPO_ENDPOINT, slug), request1.getPath());
    }

    @Test
    public void testFromIdStrMocked() throws InterruptedException {
        String id = "2800492";
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);
        Repository repository = getJTravis().repository().fromId(id);

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals(this.expectedUrl(TravisConstants.REPO_ENDPOINT, id), request1.getPath());
    }

    @Test
    public void testFromIdIntMocked() throws InterruptedException {
        int id = 2800492;
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);
        Repository repository = getJTravis().repository().fromId(id);

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals(this.expectedUrl(TravisConstants.REPO_ENDPOINT, String.valueOf(id)), request1.getPath());
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
    public void testGetUnknownRepoThrowsException() {
        Repository unknownRepo = RepositoryHelper.getRepositoryFromSlug("surli/unknown");
        assertTrue(unknownRepo == null);
    }*/
}
