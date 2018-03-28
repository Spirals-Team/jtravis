package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Objects;

public final class JobV2 {

    @Expose
    private int id;

    @Expose
    private int repositoryId;

    @Expose
    private String number;

    @Expose
    private StateType state;

    public int getId() {
        return id;
    }

    public int getRepositoryId() {
        return repositoryId;
    }

    public String getNumber() {
        return number;
    }

    public StateType getState() {
        return state;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRepositoryId(int repositoryId) {
        this.repositoryId = repositoryId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final JobV2 jobV2 = (JobV2) o;
        return id == jobV2.id &&
                repositoryId == jobV2.repositoryId &&
                Objects.equals(number, jobV2.number) &&
                state == jobV2.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, repositoryId, number, state);
    }

    @Override
    public String toString() {
        return "JobV2{" +
                "id=" + id +
                ", repositoryId=" + repositoryId +
                ", number='" + number + '\'' +
                ", state=" + state +
                '}';
    }
}
