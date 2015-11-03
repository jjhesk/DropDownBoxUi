package com.hypebeast.sdk.api.model.hypebeaststore;

import com.google.gson.annotations.SerializedName;
import com.hypebeast.sdk.api.model.Alternative;
import com.hypebeast.sdk.api.model.symfony.Config;

/**
 * Created by hesk on 12/10/15.
 */
public class ResponseMobileOverhead extends Alternative {
    @SerializedName("data")
    public Config data;
}
