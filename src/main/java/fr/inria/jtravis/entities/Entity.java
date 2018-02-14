package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public abstract class Entity {

    @Expose
    @SerializedName("@href")
    private String uri;

    @Expose
    @SerializedName("@representation")
    private RepresentationType representation;

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public RepresentationType getRepresentation() {
        return representation;
    }

    public void setRepresentation(RepresentationType representation) {
        this.representation = representation;
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
}
