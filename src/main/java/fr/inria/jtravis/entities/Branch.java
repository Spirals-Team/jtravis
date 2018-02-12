package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Branch extends Entity {
    @Expose
    private String name;

    @Expose
    private Repository repository;

    @Expose
    private boolean defaultBranch;

    @Expose
    private boolean existsOnGithub;

    @Expose
    private Build lastBuild;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public boolean isDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(boolean defaultBranch) {
        this.defaultBranch = defaultBranch;
    }

    public boolean isExistsOnGithub() {
        return existsOnGithub;
    }

    public void setExistsOnGithub(boolean existsOnGithub) {
        this.existsOnGithub = existsOnGithub;
    }

    public Build getLastBuild() {
        return lastBuild;
    }

    public void setLastBuild(Build lastBuild) {
        this.lastBuild = lastBuild;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Branch branch = (Branch) o;
        return defaultBranch == branch.defaultBranch &&
                existsOnGithub == branch.existsOnGithub &&
                Objects.equals(name, branch.name) &&
                Objects.equals(repository, branch.repository) &&
                Objects.equals(lastBuild, branch.lastBuild);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), name, repository, defaultBranch, existsOnGithub, lastBuild);
    }
}
