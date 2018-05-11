package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public abstract class EntityUnary extends Entity {
    @Expose
    private long id;

    public long getId() {
        return id;
    }

    protected void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final EntityUnary that = (EntityUnary) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id);
    }
}
