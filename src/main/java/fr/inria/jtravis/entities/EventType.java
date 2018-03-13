package fr.inria.jtravis.entities;

import com.google.gson.annotations.SerializedName;

public enum EventType {

    @SerializedName("pull_request")
    PULL_REQUEST,

    @SerializedName("push")
    PUSH,

    @SerializedName("cron")
    CRON
}
