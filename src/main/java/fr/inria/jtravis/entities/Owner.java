package fr.inria.jtravis.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

public final class Owner extends EntityUnary {
    @Expose
    @SerializedName("@type")
    private OwnerType type;

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

    // GETTER

    public OwnerType getType() {
        return type;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getGithubId() {
        return githubId;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public boolean isSyncing() {
        return isSyncing;
    }

    public Date getSyncedAt() {
        return syncedAt;
    }

    // SETTER

    protected void setType(OwnerType type) {
        this.type = type;
    }

    protected void setLogin(String login) {
        this.login = login;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setGithubId(int githubId) {
        this.githubId = githubId;
    }

    protected void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    protected void setSyncing(boolean syncing) {
        isSyncing = syncing;
    }

    protected void setSyncedAt(Date syncedAt) {
        this.syncedAt = syncedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Owner owner = (Owner) o;
        return githubId == owner.githubId &&
                isSyncing == owner.isSyncing &&
                type == owner.type &&
                Objects.equals(login, owner.login) &&
                Objects.equals(name, owner.name) &&
                Objects.equals(avatarUrl, owner.avatarUrl) &&
                Objects.equals(syncedAt, owner.syncedAt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), type, login, name, githubId, avatarUrl, isSyncing, syncedAt);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "type=" + type +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", githubId=" + githubId +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", isSyncing=" + isSyncing +
                ", syncedAt=" + syncedAt +
                '}';
    }
}
