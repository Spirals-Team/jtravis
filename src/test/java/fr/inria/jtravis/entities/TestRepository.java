package fr.inria.jtravis.entities;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.inria.jtravis.helpers.AbstractHelper;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestRepository {

    @Test
    public void testRetrieveRepositoryFromJsonResponse() throws IOException {
        String filePath = "./src/test/resources/response/spoon-get-repo.json";
        String fileContent = StringUtils.join(Files.readAllLines(new File(filePath).toPath()), "\n");

        JsonParser parser = new JsonParser();
        JsonObject repoObject = parser.parse(fileContent).getAsJsonObject();
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

        assertEquals(expectedRepository, result);
    }
}
