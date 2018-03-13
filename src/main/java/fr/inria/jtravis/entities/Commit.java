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
public final class Commit extends EntityUnary {
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

    // GETTERS

    public String getSha() {
        return sha;
    }

    public String getRef() {
        return ref;
    }

    public String getMessage() {
        return message;
    }

    public String getCompareUrl() {
        return compareUrl;
    }

    public Date getCommittedAt() {
        return committedAt;
    }

    public Owner getCommitter() {
        return committer;
    }

    public Owner getAuthor() {
        return author;
    }

    // SETTERS

    protected void setSha(String sha) {
        this.sha = sha;
    }

    protected void setRef(String ref) {
        this.ref = ref;
    }

    protected void setMessage(String message) {
        this.message = message;
    }

    protected void setCompareUrl(String compareUrl) {
        this.compareUrl = compareUrl;
    }

    protected void setCommittedAt(Date committedAt) {
        this.committedAt = committedAt;
    }

    protected void setCommitter(Owner committer) {
        this.committer = committer;
    }

    protected void setAuthor(Owner author) {
        this.author = author;
    }

    /**
     * There is not dedicated endpoint to get commits in TravisCI API.
     * Then this method will ALWAYS return a null value.
     * @return null
     */
    @Override
    public String getUri() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Commit commit = (Commit) o;
        return Objects.equals(sha, commit.sha) &&
                Objects.equals(ref, commit.ref) &&
                Objects.equals(message, commit.message) &&
                Objects.equals(compareUrl, commit.compareUrl) &&
                Objects.equals(committedAt, commit.committedAt) &&
                Objects.equals(committer, commit.committer) &&
                Objects.equals(author, commit.author);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), sha, ref, message, compareUrl, committedAt, committer, author);
    }

    @Override
    public String toString() {
        return "Commit{" +
               "sha='" + sha + '\'' +
                ", ref='" + ref + '\'' +
                ", message='" + message + '\'' +
                ", compareUrl='" + compareUrl + '\'' +
                ", committedAt=" + committedAt +
                ", committer=" + committer +
                ", author=" + author +
                '}';
    }
}
