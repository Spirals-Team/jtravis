package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public final class Pagination {
    @Expose
    private int limit;

    @Expose
    private int offset;

    @Expose
    private int count;

    @Expose
    private boolean isFirst;

    @Expose
    private boolean isLast;

    @Expose
    private PaginationEntity next;

    @Expose
    private PaginationEntity prev;

    @Expose
    private PaginationEntity first;

    @Expose
    private PaginationEntity last;

    // GETTERS

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public boolean isLast() {
        return isLast;
    }

    public PaginationEntity getNext() {
        return next;
    }

    public PaginationEntity getPrev() {
        return prev;
    }

    public PaginationEntity getFirst() {
        return first;
    }

    public PaginationEntity getLast() {
        return last;
    }

    // SETTERS

    protected void setLimit(int limit) {
        this.limit = limit;
    }

    protected void setOffset(int offset) {
        this.offset = offset;
    }

    protected void setCount(int count) {
        this.count = count;
    }

    protected void setFirst(boolean first) {
        isFirst = first;
    }

    protected void setLast(boolean last) {
        isLast = last;
    }

    protected void setNext(PaginationEntity next) {
        this.next = next;
    }

    protected void setPrev(PaginationEntity prev) {
        this.prev = prev;
    }

    protected void setFirst(PaginationEntity first) {
        this.first = first;
    }

    protected void setLast(PaginationEntity last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagination that = (Pagination) o;
        return limit == that.limit &&
                offset == that.offset &&
                count == that.count &&
                isFirst == that.isFirst &&
                isLast == that.isLast &&
                Objects.equals(next, that.next) &&
                Objects.equals(prev, that.prev) &&
                Objects.equals(first, that.first) &&
                Objects.equals(last, that.last);
    }

    @Override
    public int hashCode() {

        return Objects.hash(limit, offset, count, isFirst, isLast, next, prev, first, last);
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", count=" + count +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                ", next=" + next +
                ", prev=" + prev +
                ", first=" + first +
                ", last=" + last +
                '}';
    }
}
