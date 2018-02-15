package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import fr.inria.jtravis.AbstractTest;
import fr.inria.jtravis.JTravis;
import fr.inria.jtravis.helpers.EntityHelper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestRepositories extends AbstractTest {
    private static final String PATH_REPOSITORIES_LIMIT_2_OFFSET_0 = "./src/test/resources/response/repositories/repositories_answer_l2_o0.json";
    private static final String PATH_REPOSITORIES_LIMIT_2_OFFSET_2 = "./src/test/resources/response/repositories/repositories_answer_l2_o2.json";

    private Owner expectedRepoOwner() {
        Owner firstRepoOwner = new Owner();
        firstRepoOwner.setType(OwnerType.USER);
        firstRepoOwner.setId(95813);
        firstRepoOwner.setLogin("surli");
        firstRepoOwner.setUri("/user/95813");
        return firstRepoOwner;
    }

    private Repository expectedFirstRepository() {
        Repository firstRepo = new Repository();
        firstRepo.setUri("/repo/11594784");
        firstRepo.setRepresentation(RepresentationType.STANDARD);
        firstRepo.setId(11594784);
        firstRepo.setName("failingProject");
        firstRepo.setSlug("surli/failingProject");
        firstRepo.setActive(true);
        firstRepo.setOwner(this.expectedRepoOwner());

        Branch firstRepoDefaultBranch = new Branch();
        firstRepoDefaultBranch.setUri("/repo/11594784/branch/master");
        firstRepoDefaultBranch.setRepresentation(RepresentationType.MINIMAL);
        firstRepoDefaultBranch.setName("master");
        firstRepo.setDefaultBranch(firstRepoDefaultBranch);
        return firstRepo;
    }

    private Repository expectedSecondRepository() {
        Repository secondRepo = new Repository();
        secondRepo.setUri("/repo/17103452");
        secondRepo.setRepresentation(RepresentationType.STANDARD);
        secondRepo.setId(17103452);
        secondRepo.setName("astor");
        secondRepo.setSlug("surli/astor");
        secondRepo.setDescription("automatic program repair for Java with generate-and-validate techniques - jGenProg (GenProg for Java) - jKali - jMutRepair - DeepRepair - Cardumen");
        secondRepo.setOwner(this.expectedRepoOwner());

        Branch secondRepoDefaultBranch = new Branch();
        secondRepoDefaultBranch.setUri("/repo/17103452/branch/master");
        secondRepoDefaultBranch.setRepresentation(RepresentationType.MINIMAL);
        secondRepoDefaultBranch.setName("master");
        secondRepo.setDefaultBranch(secondRepoDefaultBranch);
        return secondRepo;
    }

    private Repository expectedThidRepository() {
        Repository thirdRepo = new Repository();
        thirdRepo.setRepresentation(RepresentationType.STANDARD);
        thirdRepo.setUri("/repo/16432953");
        thirdRepo.setId(16432953);
        thirdRepo.setName("intellij-bug-resources");
        thirdRepo.setSlug("surli/intellij-bug-resources");
        thirdRepo.setOwner(this.expectedRepoOwner());

        Branch defaultBranch = new Branch();
        defaultBranch.setUri("/repo/16432953/branch/master");
        defaultBranch.setRepresentation(RepresentationType.MINIMAL);
        defaultBranch.setName("master");
        thirdRepo.setDefaultBranch(defaultBranch);
        return thirdRepo;
    }

    private Repository expectedFourthRepository() {
        Repository fourthRepo = new Repository();
        fourthRepo.setUri("/repo/16306191");
        fourthRepo.setRepresentation(RepresentationType.STANDARD);
        fourthRepo.setId(16306191);
        fourthRepo.setName("gumtree-spoon-ast-diff");
        fourthRepo.setSlug("surli/gumtree-spoon-ast-diff");
        fourthRepo.setDescription("Computes the AST difference (aka edit script) between two Spoon Java abstract syntax trees");
        fourthRepo.setOwner(this.expectedRepoOwner());

        Branch defaultBranch = new Branch();
        defaultBranch.setRepresentation(RepresentationType.MINIMAL);
        defaultBranch.setUri("/repo/16306191/branch/master");
        defaultBranch.setName("master");
        fourthRepo.setDefaultBranch(defaultBranch);
        return fourthRepo;
    }

    @Test
    public void testRetrieveRepositoriesFromJsonAnswer() {
        JsonObject repoJson = this.getJsonObjectFromFilePath(PATH_REPOSITORIES_LIMIT_2_OFFSET_0);
        assertNotNull(repoJson);

        Repositories result = EntityHelper.createGson().fromJson(repoJson, Repositories.class);
        assertNotNull(result);

        Repositories expectedRepositories = new Repositories();
        expectedRepositories.setUri("/owner/surli/repos?limit=2");
        expectedRepositories.setRepresentation(RepresentationType.STANDARD);

        Pagination pagination = new Pagination();
        pagination.setLimit(2);
        pagination.setCount(4);
        pagination.setFirst(true);

        PaginationEntity next = new PaginationEntity();
        next.setUri("/owner/surli/repos?limit=2&offset=2");
        next.setOffset(2);
        next.setLimit(2);
        pagination.setNext(next);

        PaginationEntity first = new PaginationEntity();
        first.setUri("/owner/surli/repos?limit=2");
        first.setLimit(2);
        pagination.setFirst(first);
        pagination.setLast(next);

        expectedRepositories.setPagination(pagination);

        List<Repository> repositoryList = new ArrayList<>();

        repositoryList.add(this.expectedFirstRepository());
        repositoryList.add(this.expectedSecondRepository());
        expectedRepositories.setRepositories(repositoryList);
        assertEquals(expectedRepositories, result);
    }
}
