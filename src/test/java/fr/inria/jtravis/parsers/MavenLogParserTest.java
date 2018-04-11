package fr.inria.jtravis.parsers;

import fr.inria.jtravis.entities.TestsInformation;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by urli on 03/01/2017.
 */
public class MavenLogParserTest {

    @Test
    public void testParsingJavaEE7LogReturnsRightInfoTest() throws IOException {
        String path = "./src/test/resources/maven-logs/javaee7log.txt";

        String fileContent = TestUtils.readFile(path);
        LogParser parser = new LogParser(fileContent);
        TestsInformation infoTest = parser.getTestsInformation();

        assertEquals(0, infoTest.getFailing());
        assertEquals(2, infoTest.getErrored());
        assertEquals(1, infoTest.getSkipping());
        assertEquals(9, infoTest.getRunning());
        assertEquals(6, infoTest.getPassing());

    }

    @Test
    public void testParsingSpoonLogReturnsRightInfoTest() throws IOException {
        String path = "./src/test/resources/maven-logs/spoon_build_log.txt";

        String fileContent = TestUtils.readFile(path);
        LogParser parser = new LogParser(fileContent);
        TestsInformation infoTest = parser.getTestsInformation();

        assertEquals(2, infoTest.getFailing());
        assertEquals(0, infoTest.getErrored());
        assertEquals(2, infoTest.getSkipping());
        assertEquals(1114, infoTest.getRunning());
        assertEquals(1110, infoTest.getPassing());
    }

    @Test
    public void testParsingLog3ReturnsRightInfoTest() throws IOException {
        String path = "./src/test/resources/maven-logs/log3.txt";

        String fileContent = TestUtils.readFile(path);
        LogParser parser = new LogParser(fileContent);
        TestsInformation infoTest = parser.getTestsInformation();

        assertEquals(1, infoTest.getFailing());
        assertEquals(0, infoTest.getErrored());
        assertEquals(12, infoTest.getSkipping());
        assertEquals(1392, infoTest.getRunning());
        assertEquals(1379, infoTest.getPassing());
    }

    @Test
    public void testParsingLogLibrepairReturnsRightInfoTest() throws IOException {
        String path = "./src/test/resources/maven-logs/librepair.txt";

        String fileContent = TestUtils.readFile(path);
        LogParser parser = new LogParser(fileContent);
        TestsInformation infoTest = parser.getTestsInformation();

        assertEquals(0, infoTest.getFailing());
        assertEquals(0, infoTest.getErrored());
        assertEquals(2, infoTest.getSkipping());
        assertEquals(96, infoTest.getRunning());
        assertEquals(94, infoTest.getPassing());
    }

    @Test
    public void testDetailedParsingLogLibrepairReturnsRightInfoTest() throws IOException {
        String path = "./src/test/resources/maven-logs/librepair.txt";

        String fileContent = TestUtils.readFile(path);
        LogParser parser = new LogParser(fileContent);
        List<TestsInformation> infoTest = parser.getDetailedTestsInformation();


        assertEquals(52, infoTest.get(0).getRunning());
        assertEquals(0, infoTest.get(0).getFailing());
        assertEquals(0, infoTest.get(0).getSkipping());
        assertEquals(0, infoTest.get(0).getErrored());
        assertEquals(52, infoTest.get(0).getPassing());

        assertEquals(5, infoTest.get(1).getRunning());
        assertEquals(0,  infoTest.get(1).getFailing());
        assertEquals(2,  infoTest.get(1).getSkipping());
        assertEquals(0,  infoTest.get(1).getErrored());
        assertEquals(3, infoTest.get(1).getPassing());

        assertEquals(39, infoTest.get(2).getRunning());
        assertEquals(0,  infoTest.get(2).getFailing());
        assertEquals(0,  infoTest.get(2).getSkipping());
        assertEquals(0,  infoTest.get(2).getErrored());
        assertEquals(39, infoTest.get(2).getPassing());

    }

    @Test
    public void testParseLine() {
        String line = "[\u001B[1;31mERROR\u001B[m] \u001B[1;31mTests run: 1406, Failures: 4, Errors: 11, Skipped: 4\u001B[m";
        MavenLogParser mavenLogParser = new MavenLogParser();
        boolean result = mavenLogParser.parseTestLine(line);
        assertTrue(result);
        assertEquals(1406, mavenLogParser.globalResults.getRunning());
        assertEquals(4, mavenLogParser.globalResults.getFailing());
        assertEquals(11, mavenLogParser.globalResults.getErrored());
        assertEquals(4, mavenLogParser.globalResults.getSkipping());
    }

    @Test
    public void testDontParseLine() {
        String line = "Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 173.81 sec - in fr.inria.spirals.repairnator.process.inspectors.TestProjectInspector";
        MavenLogParser mavenLogParser = new MavenLogParser();
        boolean result = mavenLogParser.parseTestLine(line);
        assertFalse(result);
    }

    @Test
    public void testParseLines() {
        String s1 = "Tests run: 12, Failures: 0, Errors: 0, Skipped: 0";
        String s2 = "Tests run: 35, Failures: 0, Errors: 0, Skipped: 0";
        String s3 = "Tests run: 52, Failures: 0, Errors: 0, Skipped: 0";
        String s4 = "Tests run: 5, Failures: 0, Errors: 0, Skipped: 2";
        String s5 = "Tests run: 39, Failures: 0, Errors: 0, Skipped: 0";

        MavenLogParser mavenLogParser1 = new MavenLogParser();
        boolean result1 = mavenLogParser1.parseTestLine(s1);
        assertTrue(result1);
        assertEquals(12, mavenLogParser1.globalResults.getRunning());
        assertEquals(0, mavenLogParser1.globalResults.getFailing());
        assertEquals(0, mavenLogParser1.globalResults.getErrored());
        assertEquals(0, mavenLogParser1.globalResults.getSkipping());

        MavenLogParser mavenLogParser2 = new MavenLogParser();
        boolean result2 = mavenLogParser2.parseTestLine(s2);
        assertTrue(result2);
        assertEquals(35, mavenLogParser2.globalResults.getRunning());
        assertEquals(0, mavenLogParser2.globalResults.getFailing());
        assertEquals(0, mavenLogParser2.globalResults.getErrored());
        assertEquals(0, mavenLogParser2.globalResults.getSkipping());

        MavenLogParser mavenLogParser3 = new MavenLogParser();
        boolean result3 = mavenLogParser3.parseTestLine(s3);
        assertTrue(result3);
        assertEquals(52, mavenLogParser3.globalResults.getRunning());
        assertEquals(0, mavenLogParser3.globalResults.getFailing());
        assertEquals(0, mavenLogParser3.globalResults.getErrored());
        assertEquals(0, mavenLogParser3.globalResults.getSkipping());

        MavenLogParser mavenLogParser4 = new MavenLogParser();
        boolean result4 = mavenLogParser4.parseTestLine(s4);
        assertTrue(result4);
        assertEquals(5, mavenLogParser4.globalResults.getRunning());
        assertEquals(0, mavenLogParser4.globalResults.getFailing());
        assertEquals(0, mavenLogParser4.globalResults.getErrored());
        assertEquals(2, mavenLogParser4.globalResults.getSkipping());

        MavenLogParser mavenLogParser5 = new MavenLogParser();
        boolean result5 = mavenLogParser5.parseTestLine(s5);
        assertTrue(result5);
        assertEquals(39, mavenLogParser5.globalResults.getRunning());
        assertEquals(0, mavenLogParser5.globalResults.getFailing());
        assertEquals(0, mavenLogParser5.globalResults.getErrored());
        assertEquals(0, mavenLogParser5.globalResults.getSkipping());
    }
}
