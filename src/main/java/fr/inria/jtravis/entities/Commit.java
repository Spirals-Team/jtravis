package fr.inria.jtravis.entities;


import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.Objects;

/**
 * Business object to deal with a commit in Travis CI API
 * Please note that as of now commit are automatically retrieved when creating build objects, and can't be retrieved later.
 *
 * @author Simon Urli
 */
public class Commit extends Entity {
    @Expose
    private int id;

    @Expose
    private String sha;

    @Expose
    private String ref;

    @Expose
    private String message;

    @Expose
    private String compareUrl;

    @Expose
    private Date committedAt;

    @Expose
    private Owner committer;

    @Expose
    private Owner author;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public void setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
    }

    public Date getCommittedAt() {
        return committedAt;
    }

    public void setCommittedAt(Date committedAt) {
        this.committedAt = committedAt;
    }

    public Owner getCommitter() {
        return committer;
    }

    public void setCommitter(Owner committer) {
        this.committer = committer;
    }

    public Owner getAuthor() {
        return author;
    }

    public void setAuthor(Owner author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Commit commit = (Commit) o;
        return id == commit.id &&
                Objects.equals(sha, commit.sha) &&
                Objects.equals(ref, commit.ref) &&
                Objects.equals(message, commit.message) &&
                Objects.equals(compareUrl, commit.compareUrl) &&
                Objects.equals(committedAt, commit.committedAt) &&
                Objects.equals(committer, commit.committer) &&
                Objects.equals(author, commit.author);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, sha, ref, message, compareUrl, committedAt, committer, author);
    }
}
