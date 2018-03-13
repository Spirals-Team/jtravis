package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.inria.jtravis.JTravis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Entity {
    private transient JTravis jtravis;

    @Expose
    @SerializedName("@warnings")
    private List<Warning> warnings;

    @Expose
    @SerializedName("@href")
    private String uri;

    @Expose
    @SerializedName("@representation")
    private RepresentationType representation;

    public String getUri() {
        return uri;
    }

    protected void setUri(String uri) {
        this.uri = uri;
    }

    public RepresentationType getRepresentation() {
        return representation;
    }

    protected void setRepresentation(RepresentationType representation) {
        this.representation = representation;
    }

    protected JTravis getJtravis() {
        return jtravis;
    }

    protected void setJtravis(JTravis jtravis) {
        this.jtravis = jtravis;
    }

    public List<Warning> getWarnings() {
        if (warnings == null) {
            return Collections.emptyList();
        }
        return warnings;
    }

    protected void setWarnings(List<Warning> warnings) {
        this.warnings = warnings;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Entity entity = (Entity) o;
        return Objects.equals(warnings, entity.warnings) &&
                Objects.equals(uri, entity.uri) &&
                representation == entity.representation;
    }

    @Override
    public int hashCode() {

        return Objects.hash(warnings, uri, representation);
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
