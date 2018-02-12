package fr.inria.jtravis.pojos;

import fr.inria.jtravis.entities.PaginationOffset;

import java.util.Objects;

public class PaginationPojo {
    private int limit;
    private int offset;
    private int count;
    private boolean is_first;
    private boolean is_last;
    private PaginationOffset next;
    private PaginationOffset prev;
    private PaginationOffset first;
    private PaginationOffset last;

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

    public boolean isIs_first() {
        return is_first;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

    public boolean isIs_last() {
        return is_last;
    }

    public void setIs_last(boolean is_last) {
        this.is_last = is_last;
    }

    public PaginationOffset getNext() {
        return next;
    }

    public void setNext(PaginationOffset next) {
        this.next = next;
    }

    public PaginationOffset getPrev() {
        return prev;
    }

    public void setPrev(PaginationOffset prev) {
        this.prev = prev;
    }

    public PaginationOffset getFirst() {
        return first;
    }

    public void setFirst(PaginationOffset first) {
        this.first = first;
    }

    public PaginationOffset getLast() {
        return last;
    }

    public void setLast(PaginationOffset last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaginationPojo that = (PaginationPojo) o;
        return limit == that.limit &&
                offset == that.offset &&
                count == that.count &&
                is_first == that.is_first &&
                is_last == that.is_last &&
                Objects.equals(next, that.next) &&
                Objects.equals(prev, that.prev) &&
                Objects.equals(first, that.first) &&
                Objects.equals(last, that.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limit, offset, count, is_first, is_last, next, prev, first, last);
    }

    @Override
    public String toString() {
        return "PaginationPojo{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", count=" + count +
                ", is_first=" + is_first +
                ", is_last=" + is_last +
                ", next=" + next +
                ", prev=" + prev +
                ", first=" + first +
                ", last=" + last +
                '}';
    }
}
