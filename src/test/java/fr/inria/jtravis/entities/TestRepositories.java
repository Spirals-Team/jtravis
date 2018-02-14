package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRepositories extends AbstractTest {
    @Test
    public void testRetrieveRepositoriesFromJsonAnswer() {
        String filePath = "./src/test/resources/response/repositories_answer.json";
        JsonObject repoJson = this.getJsonObjectFromFilePath(filePath);
        assertNotNull(repoJson);

        Repositories result = AbstractHelper.createGson().fromJson(repoJson, Repositories.class);
        assertNotNull(result);

        Repositories expectedRepositories = new Repositories();
        expectedRepositories.setUri("/owner/surli/repos?limit=2");
        expectedRepositories.setRepresentation(RepresentationType.STANDARD);

        Pagination pagination = new Pagination();
        pagination.setLimit(2);
        pagination.setCount(35);
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

        PaginationEntity last = new PaginationEntity();
        last.setUri("/owner/surli/repos?limit=2&offset=34");
        last.setOffset(34);
        last.setLimit(2);
        pagination.setLast(last);

        expectedRepositories.setPagination(pagination);

        List<Repository> repositoryList = new ArrayList<>();
        Repository firstRepo = new Repository();
        firstRepo.setUri("/repo/11594784");
        firstRepo.setRepresentation(RepresentationType.STANDARD);
        firstRepo.setId(11594784);
        firstRepo.setName("failingProject");
        firstRepo.setSlug("surli/failingProject");
        firstRepo.setActive(true);

        Owner firstRepoOwner = new Owner();
        firstRepoOwner.setType(OwnerType.USER);
        firstRepoOwner.setId(95813);
        firstRepoOwner.setLogin("surli");
        firstRepoOwner.setUri("/user/95813");
        firstRepo.setOwner(firstRepoOwner);

        Branch firstRepoDefaultBranch = new Branch();
        firstRepoDefaultBranch.setUri("/repo/11594784/branch/master");
        firstRepoDefaultBranch.setRepresentation(RepresentationType.MINIMAL);
        firstRepoDefaultBranch.setName("master");
        firstRepo.setDefaultBranch(firstRepoDefaultBranch);
        repositoryList.add(firstRepo);

        Repository secondRepo = new Repository();
        secondRepo.setUri("/repo/17103452");
        secondRepo.setRepresentation(RepresentationType.STANDARD);
        secondRepo.setId(17103452);
        secondRepo.setName("astor");
        secondRepo.setSlug("surli/astor");
        secondRepo.setDescription("automatic program repair for Java with generate-and-validate techniques - jGenProg (GenProg for Java) - jKali - jMutRepair - DeepRepair - Cardumen");
        secondRepo.setOwner(firstRepoOwner);

        Branch secondRepoDefaultBranch = new Branch();
        secondRepoDefaultBranch.setUri("/repo/17103452/branch/master");
        secondRepoDefaultBranch.setRepresentation(RepresentationType.MINIMAL);
        secondRepoDefaultBranch.setName("master");
        secondRepo.setDefaultBranch(secondRepoDefaultBranch);

        repositoryList.add(secondRepo);

        expectedRepositories.setRepositories(repositoryList);

        assertEquals(expectedRepositories, result);
    }
}
