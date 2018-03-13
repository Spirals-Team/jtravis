package fr.inria.jtravis.entities;

import com.google.gson.annotations.SerializedName;

public enum OwnerType {
    @SerializedName("organization")
    ORGANIZATION,

    @SerializedName("user")
    USER
}
