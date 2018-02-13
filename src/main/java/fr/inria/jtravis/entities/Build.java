package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Business object to deal with Build of Travis CI API.
 * This object can return its repository, commit, configuration and jobs.
 * It also has helpful methods to get its status and a complete log of its jobs.
 * If the jobs and/or repository are not retrieved when creating the object, there are lazily get on getters.
 *
 * @author Simon Urli
 */
public class Build extends Entity implements Comparable<Build> {
    private static final Logger LOGGER = LoggerFactory.getLogger(Build.class);

    @Expose
    private int id;

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
    private String tag;

    @Expose
    private Owner createdBy;

    private PRInformation prInformation;
    private String completeLog;
    private BuildTool buildTool;

    public Build() {
        super();
        this.jobs = new ArrayList<Job>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public StateType getPreviousState() {
        return previousState;
    }

    public void setPreviousState(StateType previousState) {
        this.previousState = previousState;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public String getPullRequestTitle() {
        return pullRequestTitle;
    }

    public void setPullRequestTitle(String pullRequestTitle) {
        this.pullRequestTitle = pullRequestTitle;
    }

    public int getPullRequestNumber() {
        return pullRequestNumber;
    }

    public void setPullRequestNumber(int pullRequestNumber) {
        this.pullRequestNumber = pullRequestNumber;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Owner getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Owner createdBy) {
        this.createdBy = createdBy;
    }

    public PRInformation getPrInformation() {
        return prInformation;
    }

    public void setPrInformation(PRInformation prInformation) {
        this.prInformation = prInformation;
    }

    public String getCompleteLog() {
        return completeLog;
    }

    public void setCompleteLog(String completeLog) {
        this.completeLog = completeLog;
    }

    public BuildTool getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(BuildTool buildTool) {
        this.buildTool = buildTool;
    }

    public void refreshStatus() {
        /*Build b = BuildHelper.getBuildFromId(this.getId(), null);
        if (b != null && (this.getState() == null || !this.getState().equals(b.getState()))) {
            this.jobs.clear();
            this.completeLog = null;
            this.buildTool = null;
            this.setState(b.getState());
            LOGGER.warn("New status for build id: "+this.getId()+" : "+this.getBuildStatus());

            this.setStartedAt(b.getStartedAt());
            this.setFinishedAt(b.getFinishedAt());
            this.setDuration(b.getDuration());
            this.setJobIds(b.getJobIds());
        }*/
    }

    /*public boolean addJob(Job job) {
        if (this.getJobIds().contains(job.getId()) && !jobs.contains(job)) {
            return this.jobs.add(job);
        }
        return false;
    }

    public List<Job> getJobs() {
        if (this.jobs.isEmpty() && !this.getJobIds().isEmpty()) {
            for (int jobId : this.getJobIds()) {
                Job job = JobHelper.getJobFromId(jobId);
                if (job != null) {
                    this.jobs.add(job);
                }
            }
        }
        return jobs;
    }*/

    public void clearJobs() {
        this.jobs.clear();
    }

    /*public String getCompleteLog() {
        if (!this.getJobs().isEmpty() && (this.completeLog == null || this.completeLog.equals(""))) {
            this.completeLog = "";
            for (Job job : this.getJobs()) {
                Log log = job.getLog();
                if (log != null) {
                    this.completeLog.concat(log.getBody());
                }
            }
        }
        return this.completeLog;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public PRInformation getPRInformation() {
        if (isPullRequest() && prInformation == null) {
            prInformation = PRInformationHelper.getPRInformationFromBuild(this);
        }
        return prInformation;
    }

    public BuildTool getBuildTool() {
        if (buildTool == null) {
            if (!this.getJobs().isEmpty()) {
                Job firstJob = this.getJobs().get(0);
                buildTool = firstJob.getBuildTool();
            } else {
                buildTool = BuildTool.UNKNOWN;
            }
        }

        return buildTool;
    }*/

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
        return id == build.id &&
                duration == build.duration &&
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
                Objects.equals(createdBy, build.createdBy) &&
                Objects.equals(prInformation, build.prInformation) &&
                Objects.equals(completeLog, build.completeLog) &&
                buildTool == build.buildTool;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, number, state, duration, eventType, previousState, pullRequestTitle, pullRequestNumber, startedAt, finishedAt, repository, branch, commit, jobs, updatedAt, tag, createdBy, prInformation, completeLog, buildTool);
    }

    @Override
    public String toString() {
        return "Build{" +
                "id=" + id +
                ", number='" + number + '\'' +
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
                ", jobs=" + jobs +
                ", updatedAt=" + updatedAt +
                ", tag='" + tag + '\'' +
                ", createdBy=" + createdBy +
                ", prInformation=" + prInformation +
                ", completeLog='" + completeLog + '\'' +
                ", buildTool=" + buildTool +
                '}';
    }
}
