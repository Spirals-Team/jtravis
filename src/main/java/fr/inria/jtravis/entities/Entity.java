package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.inria.jtravis.helpers.BuildHelper;
import fr.inria.jtravis.helpers.GenericHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
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

    protected void setUri(String uri) {
        this.uri = uri;
    }

    public RepresentationType getRepresentation() {
        return representation;
    }

    protected void setRepresentation(RepresentationType representation) {
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

    protected Logger getLogger() {
        return LoggerFactory.getLogger(this.getClass());
    }

    public boolean refresh() {
        if (this.getRepresentation() == RepresentationType.MINIMAL && this.getUri() != null) {
            Object instance1 = GenericHelper.getEntityFromUri(this.getClass(), this.getUri());

            if (instance1 != null) {
                for (Field field : this.getClass().getDeclaredFields()) {
                    Expose[] annotations = field.getAnnotationsByType(Expose.class);
                    if (annotations != null && annotations.length >= 1) {
                        try {
                            field.setAccessible(true);
                            Object value = field.get(instance1);
                            field.set(this, value);
                            field.setAccessible(false);
                        } catch (IllegalAccessException e) {
                            this.getLogger().error("Error while setting field: "+field.getName(), e);
                            return false;
                        }
                    }
                }
                this.setRepresentation(RepresentationType.STANDARD);
                return true;
            }
        }
        return false;
    }
}
