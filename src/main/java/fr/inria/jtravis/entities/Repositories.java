package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Objects;

public class Repositories extends EntityCollection {
    @Expose
    private List<Repository> repositories;

    public List<Repository> getRepositories() {
        return repositories;
    }

    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Repositories that = (Repositories) o;
        return Objects.equals(repositories, that.repositories);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), repositories);
    }

    @Override
    public String toString() {
        return "Repositories{" +
                "repositories=" + repositories +
                '}';
    }
}
