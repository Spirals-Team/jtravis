package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import fr.inria.jtravis.parsers.LogParser;

import java.util.Objects;

/**
 * Business object to deal with log in Travis CI API
 *
 * @see <a href="https://docs.travis-ci.com/api#logs">https://docs.travis-ci.com/api#logs</a>
 * @author Simon Urli
 */
public final class Log extends EntityUnary {

    @Expose
    private String content;


    private TestsInformation testsInformation;
    private BuildTool buildTool;

    public String getContent() {
        return content;
    }

    protected void setContent(String content) {
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Log log = (Log) o;
        return Objects.equals(content, log.content) &&
                Objects.equals(testsInformation, log.testsInformation) &&
                buildTool == log.buildTool;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), content, testsInformation, buildTool);
    }

    public TestsInformation getTestsInformation() {
        if (testsInformation == null) {
            LogParser logParser = new LogParser(getContent());

            this.testsInformation = logParser.getTestsInformation();
        }

        return this.testsInformation;
    }

    public BuildTool getBuildTool() {
        if (this.buildTool == null) {
            LogParser logParser = new LogParser(getContent());
            this.buildTool = logParser.getBuildTool();
        }

        return buildTool;
    }
}
