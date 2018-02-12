package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String state;

    @Expose
    private int duration;

    @Expose
    private String eventType;

    @Expose
    private String previousState;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
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

    public BuildStatus getBuildStatus() {
        if (this.getState() != null) {
            return BuildStatus.valueOf(this.getState().toUpperCase());
        } else {
            return null;
        }
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
}
