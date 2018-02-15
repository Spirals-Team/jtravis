package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.inria.jtravis.helpers.EntityHelper;

import java.util.Objects;

public abstract class EntityCollection extends Entity {
    @Expose
    @SerializedName("@pagination")
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    protected void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityCollection that = (EntityCollection) o;
        return Objects.equals(pagination, that.pagination);
    }

    @Override
    public int hashCode() {

        return Objects.hash(pagination);
    }
}
