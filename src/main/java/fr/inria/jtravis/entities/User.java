package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;

import java.util.Date;
import java.util.Objects;

public class User extends Entity {
    @Expose
    private int id;

    @Expose
    private String login;

    @Expose
    private String name;

    @Expose
    private int githubId;

    @Expose
    private String avatarUrl;

    @Expose
    private boolean isSyncing;

    @Expose
    private Date syncedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGithubId() {
        return githubId;
    }

    public void setGithubId(int githubId) {
        this.githubId = githubId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public boolean isSyncing() {
        return isSyncing;
    }

    public void setSyncing(boolean syncing) {
        isSyncing = syncing;
    }

    public Date getSyncedAt() {
        return syncedAt;
    }

    public void setSyncedAt(Date syncedAt) {
        this.syncedAt = syncedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User owner = (User) o;
        return id == owner.id &&
                githubId == owner.githubId &&
                isSyncing == owner.isSyncing &&
                Objects.equals(login, owner.login) &&
                Objects.equals(name, owner.name) &&
                Objects.equals(avatarUrl, owner.avatarUrl) &&
                Objects.equals(syncedAt, owner.syncedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), id, login, name, githubId, avatarUrl, isSyncing, syncedAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", githubId=" + githubId +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isSyncing=" + isSyncing +
                ", syncedAt=" + syncedAt +
                '}';
    }
}
