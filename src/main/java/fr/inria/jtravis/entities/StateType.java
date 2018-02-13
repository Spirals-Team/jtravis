package fr.inria.jtravis.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Business object to deal with status of the build or of the job in Travis CI API
 *
 * @author Simon Urli
 */
public enum StateType {
    @SerializedName("failed")
    FAILED,

    @SerializedName("passed")
    PASSED,

    @SerializedName("created")
    CREATED,

    @SerializedName("started")
    STARTED,

    @SerializedName("errored")
    ERRORED,

    @SerializedName("canceled")
    CANCELED,

    @SerializedName("received")
    RECEIVED,

    @SerializedName("queued")
    QUEUED,

    @SerializedName("finished")
    FINISHED // used for jobs
}
