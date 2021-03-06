package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import fr.inria.jtravis.RequestAPI;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * Business object to deal with job in Travis CI API
 * A job can get lazily its log information
 *
 * @author Simon Urli
 */
public final class Job extends EntityUnary {
    @Expose
    private boolean allowFailure;

    @Expose
    private String number;

    @Expose
    private StateType state;

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
    private Owner owner;

    @Expose
    private Date createdAt;

    @Expose
    private Date updatedAt;

    @Expose
    private Config config;

    private transient Log log;
    private transient BuildTool buildTool;

    // GETTER

    public boolean isAllowFailure() {
        return allowFailure;
    }

    public String getNumber() {
        return number;
    }

    public StateType getState() {
        return state;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public Build getBuild() {
        return build;
    }

    public String getQueue() {
        return queue;
    }

    public Repository getRepository() {
        return repository;
    }

    public Commit getCommit() {
        return commit;
    }

    public Owner getOwner() {
        return owner;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Config getConfig() {
        return config;
    }

    // SETTER

    protected void setAllowFailure(boolean allowFailure) {
        this.allowFailure = allowFailure;
    }

    protected void setNumber(String number) {
        this.number = number;
    }

    protected void setState(StateType state) {
        this.state = state;
    }

    protected void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

    protected void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    protected void setBuild(Build build) {
        this.build = build;
    }

    protected void setQueue(String queue) {
        this.queue = queue;
    }

    protected void setRepository(Repository repository) {
        this.repository = repository;
    }

    protected void setCommit(Commit commit) {
        this.commit = commit;
    }

    protected void setOwner(Owner owner) {
        this.owner = owner;
    }

    protected void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    protected void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    protected void setConfig(Config config) {
        this.config = config;
    }

    @RequestAPI
    public boolean fetchLog() {
        if (this.getJtravis() != null) {
            Optional<Log> log = this.getJtravis().log().from(this);
            if (log.isPresent()) {
                this.log = log.get();
                return true;
            }
        }
        return false;
    }

    @RequestAPI
    public Optional<Log> getLog() {
        if (this.log == null) {
            if (!this.fetchLog()) {
                return Optional.empty();
            }
        }
        return Optional.of(this.log);
    }

    @RequestAPI
    public Optional<BuildTool> getBuildTool() {
        if (this.buildTool == null) {
            if (this.getLog().isPresent()) {
                this.buildTool = this.getLog().get().getBuildTool();
            } else {
                return Optional.empty();
            }
        }

        return Optional.of(buildTool);
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

    /**
     * This method retrieve the language information from the configuration. It might need to refresh the entity.
     * @return The string corresponding to the language or an empty string
     */
    @RequestAPI
    public String getLanguage() {
        if (this.getJtravis() != null) {
            if (this.getRepresentation() == RepresentationType.MINIMAL) {
                this.getJtravis().refresh(this);
            }
            return this.getConfig().getLanguage();
        }

        return "";
    }

    /**
     * We override the getUri method to always include job.config.
     */
    @Override
    public String getUri() {
        return super.getUri() + "?include=job.config";
    }

    @Override
    protected void dispatchJTravisToChildren() {
        if (this.repository != null) {
            this.repository.setJtravis(this.getJtravis());
        }

        if (this.build != null) {
            this.build.setJtravis(this.getJtravis());
        }

        if (this.commit != null) {
            this.commit.setJtravis(this.getJtravis());
        }

        if (this.owner != null) {
            this.owner.setJtravis(this.getJtravis());
        }

        if (this.log != null) {
            this.log.setJtravis(this.getJtravis());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Job job = (Job) o;
        return allowFailure == job.allowFailure &&
                Objects.equals(number, job.number) &&
                state == job.state &&
                Objects.equals(startedAt, job.startedAt) &&
                Objects.equals(finishedAt, job.finishedAt) &&
                Objects.equals(build, job.build) &&
                Objects.equals(queue, job.queue) &&
                Objects.equals(repository, job.repository) &&
                Objects.equals(commit, job.commit) &&
                Objects.equals(owner, job.owner) &&
                Objects.equals(createdAt, job.createdAt) &&
                Objects.equals(updatedAt, job.updatedAt) &&
                Objects.equals(log, job.log) &&
                Objects.equals(config, job.config) &&
                buildTool == job.buildTool;
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), allowFailure, number, state, startedAt, finishedAt, build, queue, repository, commit, owner, createdAt, updatedAt, log, config, buildTool);
    }

    @Override
    public String toString() {
        return "Job{" +
                "allowFailure=" + allowFailure +
                ", number='" + number + '\'' +
                ", state=" + state +
                ", startedAt=" + startedAt +
                ", finishedAt=" + finishedAt +
                ", build=" + build +
                ", queue='" + queue + '\'' +
                ", repository=" + repository +
                ", commit=" + commit +
                ", owner=" + owner +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", log=" + log +
                ", config=" + config +
                ", buildTool=" + buildTool +
                '}';
    }
}
