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
public final class Repository extends EntityUnary {
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

    @Expose
    private Owner owner;

    @Expose
    private Branch defaultBranch;

    // GETTER

    public String getName() {
        return name;
    }

    public String getSlug() {
        return slug;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isPrivateProperty() {
        return privateProperty;
    }

    public String getDescription() {
        return description;
    }

    public String getGithubLanguage() {
        return githubLanguage;
    }

    public boolean isStarred() {
        return starred;
    }

    public Owner getOwner() {
        return owner;
    }

    public Branch getDefaultBranch() {
        return defaultBranch;
    }

    // SETTER

    protected void setName(String name) {
        this.name = name;
    }

    protected void setSlug(String slug) {
        this.slug = slug;
    }

    protected void setActive(boolean active) {
        this.active = active;
    }

    protected void setPrivateProperty(boolean privateProperty) {
        this.privateProperty = privateProperty;
    }

    protected void setDescription(String description) {
        this.description = description;
    }

    protected void setGithubLanguage(String githubLanguage) {
        this.githubLanguage = githubLanguage;
    }

    protected void setStarred(boolean starred) {
        this.starred = starred;
    }

    protected void setOwner(Owner owner) {
        this.owner = owner;
    }

    protected void setDefaultBranch(Branch defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Repository that = (Repository) o;
        return active == that.active &&
                privateProperty == that.privateProperty &&
                starred == that.starred &&
                Objects.equals(name, that.name) &&
                Objects.equals(slug, that.slug) &&
                Objects.equals(description, that.description) &&
                Objects.equals(githubLanguage, that.githubLanguage) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(defaultBranch, that.defaultBranch);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, slug, active, privateProperty, description, githubLanguage, starred, owner, defaultBranch);
    }

    @Override
    public String toString() {
        return "Repository{" +
                "name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                ", active=" + active +
                ", privateProperty=" + privateProperty +
                ", description='" + description + '\'' +
                ", githubLanguage='" + githubLanguage + '\'' +
                ", starred=" + starred +
                ", owner=" + owner +
                ", defaultBranch=" + defaultBranch +
                '}';
    }
}
