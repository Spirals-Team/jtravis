package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public class Owner extends Entity {
    @Expose
    @SerializedName("@type")
    private OwnerType type;

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

    public OwnerType getType() {
        return type;
    }

    public void setType(OwnerType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return id == owner.id &&
                githubId == owner.githubId &&
                isSyncing == owner.isSyncing &&
                type == owner.type &&
                Objects.equals(login, owner.login) &&
                Objects.equals(name, owner.name) &&
                Objects.equals(avatarUrl, owner.avatarUrl) &&
                Objects.equals(syncedAt, owner.syncedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type, id, login, name, githubId, avatarUrl, isSyncing, syncedAt);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "type=" + type +
                ", id=" + id +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", githubId=" + githubId +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isSyncing=" + isSyncing +
                ", syncedAt=" + syncedAt +
                '}';
    }
}
