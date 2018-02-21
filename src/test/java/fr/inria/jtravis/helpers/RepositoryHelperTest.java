package fr.inria.jtravis.helpers;

import com.squareup.okhttp.mockwebserver.RecordedRequest;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.TravisConstants;
import fr.inria.jtravis.entities.Repository;
import fr.inria.jtravis.entities.TestRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


/**
 * Created by urli on 21/12/2016.
 */
public class RepositoryHelperTest extends AbstractTest {

    @Test
    public void testFromSlugMocked() throws InterruptedException {
        String slug = "INRIA/spoon";
        String encodedSlug = "INRIA%2Fspoon";
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);

        Optional<Repository> repositoryOpt = getJTravis().repository().fromSlug(slug);
        assertTrue(repositoryOpt.isPresent());
        Repository repository = repositoryOpt.get();

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals(this.expectedUrl(TravisConstants.REPO_ENDPOINT, encodedSlug), request1.getPath());
    }

    @Test
    public void testFromIdStrMocked() throws InterruptedException {
        String id = "2800492";
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);
        Optional<Repository> repositoryOpt = getJTravis().repository().fromId(id);
        assertTrue(repositoryOpt.isPresent());
        Repository repository = repositoryOpt.get();

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals(this.expectedUrl(TravisConstants.REPO_ENDPOINT, id), request1.getPath());
    }

    @Test
    public void testFromIdIntMocked() throws InterruptedException {
        int id = 2800492;
        String buildContent = this.getFileContent(TestRepository.PATH_REPO_STANDARD);

        this.enqueueContentMockServer(buildContent);
        Optional<Repository> repositoryOpt = getJTravis().repository().fromId(id);
        assertTrue(repositoryOpt.isPresent());
        Repository repository = repositoryOpt.get();

        assertEquals(TestRepository.getStandardExpectedRepo(), repository);
        RecordedRequest request1 = getMockServer().takeRequest();
        assertEquals(this.expectedUrl(TravisConstants.REPO_ENDPOINT, String.valueOf(id)), request1.getPath());
    }
}
