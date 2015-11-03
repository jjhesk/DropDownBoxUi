package com.hypebeast.sdk.api.resources.hbstore;

import com.hypebeast.sdk.api.exception.ApiException;
import com.hypebeast.sdk.api.model.hypebeaststore.ResponseMobileOverhead;
import com.hypebeast.sdk.api.model.symfony.Config;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by hesk on 10/9/15.
 */
public interface Overhead {
    @GET("/mobile-api/v1/config.json?platform=android")
    void mobile_config(final Callback<ResponseMobileOverhead> cb) throws ApiException;
}
