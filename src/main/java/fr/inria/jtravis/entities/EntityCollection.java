package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import fr.inria.jtravis.helpers.GenericHelper;

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

    public abstract boolean fillWithNextValues();

    <T extends EntityCollection> T getNextCollectionAndUpdatePagination() {
        Pagination currentPagination = this.getPagination();
        if (currentPagination != null && currentPagination.getNext() != null) {
            T result = (T) GenericHelper.getEntityFromUri(this.getClass(), this.getPagination().getNext().getUri());

            Pagination nextPagination = result.getPagination();
            currentPagination.setNext(nextPagination.getNext());
            currentPagination.setLast(nextPagination.getLast());
            currentPagination.setOffset(nextPagination.getOffset());
            boolean isFirst = currentPagination.isFirst() || nextPagination.isFirst();
            boolean isLast = currentPagination.isLast() || nextPagination.isLast();

            currentPagination.setFirst(isFirst);
            currentPagination.setLast(isLast);
            return result;
        }

        return null;
    }
}
