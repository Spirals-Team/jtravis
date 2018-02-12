package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

/**
 * Business object to deal with repository in Travis CI API
 * A repository can lazily get its last build.
 *
 * @author Simon Urli
 */
public class Repository extends Entity {
    @Expose
    private int id;

    @Expose
    private String name;

    @Expose
    private String slug;

    @Expose
    private boolean active;

    @Expose
    @SerializedName("private")
    private boolean privateProperty;

    @Expose
    private String description;

    @Expose
    private String githubLanguage;

    @Expose
    private boolean starred;

    private Build currentBuild;

    private Build lastStartedBuild;


    private Build lastBuild;
    private Build lastBuildOnMaster;
    private Date lastAccess;

    /**
     * Get the last build of the current repository. If onMaster is specified it will only look for builds created by push on master or cron.
     *
     * @param onMaster True if the last build should be search only on the master branch
     * @return The last build
     */
    public Build getLastBuild(boolean onMaster) {

        return null;
        // first case: we should get the last build on master
        /*if (onMaster) {

            // if we already get the last build and it's not a PR, then it's the last build on master
            if (this.lastBuildOnMaster == null && this.lastBuild != null && !this.lastBuild.isPullRequest()) {
                this.lastBuildOnMaster = this.lastBuild;

            // else we have to request it
            } else if (this.lastBuildOnMaster == null) {
                this.lastBuildOnMaster = BuildHelper.getLastBuildFromMaster(this);
            }
            return this.lastBuildOnMaster;

        // second case: we should get the last build, no matter if it's on master or not
        } else {

            // if we already get the last build on master and it has the same ID as the last build, then there're the same
            if (this.lastBuild == null && this.lastBuildOnMaster != null && this.getLastBuildId() > 0 && this.lastBuildOnMaster.getId() == this.getLastBuildId()) {
                this.lastBuild = this.lastBuildOnMaster;

            // else we have to request it
            } else if (this.lastBuild == null && this.getLastBuildId() > 0) {
                this.lastBuild = BuildHelper.getBuildFromId(this.getLastBuildId(), this);
            }
            return this.lastBuild;
        }*/
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isPrivateProperty() {
        return privateProperty;
    }

    public void setPrivateProperty(boolean privateProperty) {
        this.privateProperty = privateProperty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGithubLanguage() {
        return githubLanguage;
    }

    public void setGithubLanguage(String githubLanguage) {
        this.githubLanguage = githubLanguage;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }

    public Build getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(Build lastBuild) {
        this.lastBuild = lastBuild;
    }

    public Build getLastBuildOnMaster() {
        return lastBuildOnMaster;
    }

    public void setLastBuildOnMaster(Build lastBuildOnMaster) {
        this.lastBuildOnMaster = lastBuildOnMaster;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void updateLastAccess() {
        this.lastAccess = new Date();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Repository that = (Repository) o;
        return id == that.id &&
                active == that.active &&
                privateProperty == that.privateProperty &&
                starred == that.starred &&
                Objects.equals(name, that.name) &&
                Objects.equals(slug, that.slug) &&
                Objects.equals(description, that.description) &&
                Objects.equals(githubLanguage, that.githubLanguage) &&
                Objects.equals(lastBuild, that.lastBuild) &&
                Objects.equals(lastBuildOnMaster, that.lastBuildOnMaster) &&
                Objects.equals(lastAccess, that.lastAccess);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, slug, active, privateProperty, description, githubLanguage, starred, lastBuild, lastBuildOnMaster, lastAccess);
    }
}
