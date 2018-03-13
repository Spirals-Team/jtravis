package fr.inria.jtravis.entities;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHCommitPointer;
import org.kohsuke.github.GHRepository;

/**
 * Created by urli on 04/01/2017.
 */
public final class PullRequest {

    private GHCommitPointer headRef;
    private GHCommitPointer baseRef;
    private GHCommit head;
    private GHCommit base;
    private GHRepository otherRepo;

    public PullRequest() {}

    public GHCommitPointer getHeadRef() {
        return headRef;
    }

    public PullRequest setHeadRef(GHCommitPointer headRef) {
        this.headRef = headRef;
        return this;
    }

    public GHCommitPointer getBaseRef() {
        return baseRef;
    }

    public PullRequest setBaseRef(GHCommitPointer baseRef) {
        this.baseRef = baseRef;
        return this;
    }

    public GHCommit getHead() {
        return head;
    }

    public PullRequest setHead(GHCommit head) {
        this.head = head;
        return this;
    }

    public GHCommit getBase() {
        return base;
    }

    public PullRequest setBase(GHCommit base) {
        this.base = base;
        return this;
    }

    public GHRepository getOtherRepo() {
        return otherRepo;
    }

    public PullRequest setOtherRepo(GHRepository otherRepo) {
        this.otherRepo = otherRepo;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequest that = (PullRequest) o;

        if (head != null ? !head.equals(that.head) : that.head != null) return false;
        if (base != null ? !base.equals(that.base) : that.base != null) return false;
        return otherRepo != null ? otherRepo.equals(that.otherRepo) : that.otherRepo == null;
    }

    @Override
    public int hashCode() {
        int result = head != null ? head.hashCode() : 0;
        result = 31 * result + (base != null ? base.hashCode() : 0);
        result = 31 * result + (otherRepo != null ? otherRepo.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PRInformation{" +
                "head=" + head +
                ", base=" + base +
                ", otherRepo=" + otherRepo +
                '}';
    }
}
