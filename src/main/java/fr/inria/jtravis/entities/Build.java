package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Business object to deal with Build of Travis CI API.
 * This object can return its repository, commit, configuration and jobs.
 * It also has helpful methods to get its status and a complete log of its jobs.
 * If the jobs and/or repository are not retrieved when creating the object, there are lazily get on getters.
 *
 * @author Simon Urli
 */
public final class Build extends EntityUnary implements Comparable<Build> {
    @Expose
    private String number;

    @Expose
    private StateType state;

    @Expose
    private int duration;

    @Expose
    private EventType eventType;

    @Expose
    private StateType previousState;

    @Expose
    private String pullRequestTitle;

    @Expose
    private int pullRequestNumber;

    @Expose
    private Date startedAt;

    @Expose
    private Date finishedAt;

    @Expose
    private Repository repository;

    @Expose
    private Branch branch;

    @Expose
    private Commit commit;

    @Expose
    private List<Job> jobs;

    @Expose
    private Date updatedAt;

    @Expose
    private Tag tag;

    @Expose
    private Owner createdBy;

    public Build() {
        super();
        this.jobs = new ArrayList<Job>();
    }

    // SETTERS

    protected void setNumber(String number) {
        this.number = number;
    }

    protected void setState(StateType state) {
        this.state = state;
    }

    protected void setDuration(int duration) {
        this.duration = duration;
    }

    protected void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    protected void setPreviousState(StateType previousState) {
        this.previousState = previousState;
    }

    protected void setPullRequestTitle(String pullRequestTitle) {
        this.pullRequestTitle = pullRequestTitle;
    }

    protected void setPullRequestNumber(int pullRequestNumber) {
        this.pullRequestNumber = pullRequestNumber;
    }

    protected void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    protected void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    protected void setRepository(Repository repository) {
        this.repository = repository;
    }

    protected void setBranch(Branch branch) {
        this.branch = branch;
    }

    protected void setCommit(Commit commit) {
        this.commit = commit;
    }

    protected void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    protected void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected void setTag(Tag tag) {
        this.tag = tag;
    }

    protected void setCreatedBy(Owner createdBy) {
        this.createdBy = createdBy;
    }

    // GETTERS

    public String getNumber() {
        return number;
    }

    public StateType getState() {
        return state;
    }

    public int getDuration() {
        return duration;
    }

    public EventType getEventType() {
        return eventType;
    }

    public StateType getPreviousState() {
        return previousState;
    }

    public String getPullRequestTitle() {
        return pullRequestTitle;
    }

    public int getPullRequestNumber() {
        return pullRequestNumber;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public Repository getRepository() {
        return repository;
    }

    public Branch getBranch() {
        return branch;
    }

    public Commit getCommit() {
        return commit;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Tag getTag() {
        return tag;
    }

    public Owner getCreatedBy() {
        return createdBy;
    }

    public boolean isPullRequest() {
        return this.getPullRequestNumber() > 0;
    }

    public Optional<BuildTool> getBuildTool() {
        if (!this.getJobs().isEmpty()) {
            Job firstJob = this.getJobs().get(0);
            if (this.getJtravis() != null) {
                firstJob.setJtravis(this.getJtravis());
            }
            return firstJob.getBuildTool();
        }

        return Optional.empty();
    }

    @Override
    public int compareTo(Build o) {
        return this.getId()-o.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Build build = (Build) o;
        return duration == build.duration &&
                pullRequestNumber == build.pullRequestNumber &&
                Objects.equals(number, build.number) &&
                Objects.equals(state, build.state) &&
                Objects.equals(eventType, build.eventType) &&
                Objects.equals(previousState, build.previousState) &&
                Objects.equals(pullRequestTitle, build.pullRequestTitle) &&
                Objects.equals(startedAt, build.startedAt) &&
                Objects.equals(finishedAt, build.finishedAt) &&
                Objects.equals(repository, build.repository) &&
                Objects.equals(branch, build.branch) &&
                Objects.equals(commit, build.commit) &&
                Objects.equals(jobs, build.jobs) &&
                Objects.equals(updatedAt, build.updatedAt) &&
                Objects.equals(tag, build.tag) &&
                Objects.equals(createdBy, build.createdBy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), number, state, duration, eventType, previousState, pullRequestTitle, pullRequestNumber, startedAt, finishedAt, repository, branch, commit, jobs, updatedAt, tag, createdBy);
    }

    @Override
    public String toString() {
        return "Build{" +
                "number='" + number + '\'' +
                ", state='" + state + '\'' +
                ", duration=" + duration +
                ", eventType='" + eventType + '\'' +
                ", previousState='" + previousState + '\'' +
                ", pullRequestTitle='" + pullRequestTitle + '\'' +
                ", pullRequestNumber=" + pullRequestNumber +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                ", repository=" + repository +
                ", branch=" + branch +
                ", commit=" + commit +
                ", jobs=" + StringUtils.join(jobs, ",") +
                ", updatedAt=" + updatedAt +
                ", tag='" + tag + '\'' +
                ", createdBy=" + createdBy +
                '}';
    }
}
