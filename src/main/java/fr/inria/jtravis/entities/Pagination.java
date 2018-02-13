package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Pagination {
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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public PaginationEntity getNext() {
        return next;
    }

    public void setNext(PaginationEntity next) {
        this.next = next;
    }

    public PaginationEntity getPrev() {
        return prev;
    }

    public void setPrev(PaginationEntity prev) {
        this.prev = prev;
    }

    public PaginationEntity getFirst() {
        return first;
    }

    public void setFirst(PaginationEntity first) {
        this.first = first;
    }

    public PaginationEntity getLast() {
        return last;
    }

    public void setLast(PaginationEntity last) {
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
