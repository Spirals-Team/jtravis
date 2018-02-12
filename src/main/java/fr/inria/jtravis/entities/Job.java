package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import fr.inria.jtravis.helpers.LogHelper;

import java.util.Date;

/**
 * Business object to deal with job in Travis CI API
 * A job can get lazily its log information
 *
 * @author Simon Urli
 */
public class Job extends Entity {
    @Expose
    private int id;

    @Expose
    private boolean allowFailure;

    @Expose
    private String number;

    @Expose
    private String state;

    @Expose
    private Date startedAt;

    @Expose
    private Date finishedAt;

    @Expose
    private Build build;

    @Expose
    private String queue;

    @Expose
    private Repository repository;

    @Expose
    private Commit commit;

    @Expose
    private User owner;

    @Expose
    private Date createdAt;

    @Expose
    private Date updatedAt;

    private Log log;
    private BuildTool buildTool;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAllowFailure() {
        return allowFailure;
    }

    public void setAllowFailure(boolean allowFailure) {
        this.allowFailure = allowFailure;
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

    public Build getBuild() {
        return build;
    }

    public void setBuild(Build build) {
        this.build = build;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public BuildStatus getBuildStatus() {
        if (this.getState() != null) {
            return BuildStatus.valueOf(this.getState().toUpperCase());
        } else {
            return null;
        }
    }

    public Log getLog() {
        if (log == null) {
            this.log = LogHelper.getLogFromJob(this);
        }
        return this.log;
    }

    public BuildTool getBuildTool() {
        if (buildTool == null) {
            if (this.getLog() != null) {
                buildTool = this.getLog().getBuildTool();
            } else {
                buildTool = BuildTool.UNKNOWN;
            }
        }

        return buildTool;
    }

    public int getJobNumber() {
        if (this.getNumber() == null) {
            return -1;
        }

        String[] numbers = this.getNumber().split("\\.");
        if (numbers.length != 2) {
            return -1;
        }

        return Integer.parseInt(numbers[1]);
    }
}
