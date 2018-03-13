package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public class Tag extends Entity {
    @Expose
    int repositoryId;

    @Expose
    String name;

    @Expose
    int lastBuildId;

    public int getRepositoryId() {
        return repositoryId;
    }

    public String getName() {
        return name;
    }

    public int getLastBuildId() {
        return lastBuildId;
    }

    protected Tag setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
        return this;
    }

    protected Tag setName(String name) {
        this.name = name;
        return this;
    }

    protected Tag setLastBuildId(int lastBuildId) {
        this.lastBuildId = lastBuildId;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Tag tag = (Tag) o;
        return repositoryId == tag.repositoryId &&
                lastBuildId == tag.lastBuildId &&
                Objects.equals(name, tag.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), repositoryId, name, lastBuildId);
    }
}
