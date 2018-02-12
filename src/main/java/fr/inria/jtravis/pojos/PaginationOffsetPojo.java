package fr.inria.jtravis.pojos;

import java.util.Objects;

public class PaginationOffsetPojo {
    private int limit;
    private int offset;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginationOffsetPojo that = (PaginationOffsetPojo) o;
        return limit == that.limit &&
                offset == that.offset;
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset);
    }

    @Override
    public String toString() {
        return "PaginationOffsetPojo{" +
                "limit=" + limit +
                ", offset=" + offset +
                '}';
    }
}
