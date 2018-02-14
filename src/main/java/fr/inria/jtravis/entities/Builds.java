package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Objects;

public final class Builds extends EntityCollection {
    @Expose
    private List<Build> builds;

    public List<Build> getBuilds() {
        return builds;
    }

    protected void setBuilds(List<Build> builds) {
        this.builds = builds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Builds builds1 = (Builds) o;
        return Objects.equals(builds, builds1.builds);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), builds);
    }

    @Override
    public String toString() {
        return "Builds{" +
                "builds=" + builds +
                '}';
    }
}
