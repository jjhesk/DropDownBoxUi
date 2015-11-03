package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hesk on 29/9/15.
 */
public abstract class ErrorBaseAlternative {
    @SerializedName("error")
    public String _error;
}
