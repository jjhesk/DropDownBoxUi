package com.hypebeast.sdk.api.resources.hbstore;

import com.hypebeast.sdk.api.exception.ApiException;
import com.hypebeast.sdk.api.model.hypebeaststore.ResponseBrandList;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by hesk on 29/10/15.
 */
public interface Authentication {

    @FormUrlEncoded
    @POST("/login_check")
    void checkLoginStatus(final @Field("_username") String user,
                          final @Field("_password") String pass,
                          final Callback<String> raw
    ) throws ApiException;

    @FormUrlEncoded
    @POST("/register")
    void register(final @Field("_username") String user,
                  final @Field("_password") String pass,
                  final Callback<String> raw
    ) throws ApiException;


}
