package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.inria.jtravis.JTravis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public abstract class Entity {
    private transient JTravis jtravis;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return Objects.equals(uri, entity.uri) &&
                representation == entity.representation;
    }

    @Override
    public int hashCode() {

        return Objects.hash(uri, representation);
    }

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }
}
