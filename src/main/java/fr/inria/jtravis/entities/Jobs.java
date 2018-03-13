package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Objects;

public final class Jobs extends EntityCollection {
    @Expose
    private List<Job> jobs;

    public List<Job> getJobs() {
        return jobs;
    }

    protected void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Jobs jobs1 = (Jobs) o;
        return Objects.equals(jobs, jobs1.jobs);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), jobs);
    }

    @Override
    public String toString() {
        return "Jobs{" +
                "jobs=" + jobs +
                '}';
    }
}
