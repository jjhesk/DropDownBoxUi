/*
 * Copyright 2014 Phil Bayfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hypebeast.sdk.config;

import android.os.Bundle;


import com.hypebeast.sdk.api.exception.Check;

import static retrofit.RestAdapter.LogLevel;

/**
 * Configuration options
 */
public class clientConfig {

    /**
     * Api key
     */
    private String mApiKey;

    /**
     * Api secret
     */
    private String mApiSecret;

    /**
     * Access token
     */
    private String mAccessToken;

    /**
     * Referrer
     */
    private String mReferrer;

    /**
     * Retrofit log level
     */
    private LogLevel mLogLevel = LogLevel.NONE;
    private String mRedirectUri;

    /**
     * Empty constructor
     */
    public clientConfig() {

    }

    public void checkForAuth() {
        Check.checkNotNull(mApiKey, "A non null public key must be set!");
        Check.checkNotNull(mApiSecret, "A non null private key must be set!");
        //   Check.checkNotNull(appContext, "A context must be set!");
        Check.checkNotNull(mRedirectUri, "A redirect Uri must be set!");
    }

    /**
     * Set api key
     *
     * @param apiKey the API key obtained from the console
     */
    public clientConfig(String apiKey) {
        mApiKey = apiKey;
    }

    /**
     * Set api key and log level
     *
     * @param apiKey   the API key obtained from the console
     * @param logLevel level log
     */
    public clientConfig(String apiKey, LogLevel logLevel) {
        this(apiKey);
        mLogLevel = logLevel;
    }

    /**
     * Set api key and access token
     *
     * @param apiKey      the API key obtained from the console
     * @param accessToken the access token from the console
     */
    public clientConfig(String apiKey, String accessToken) {
        this(apiKey);
        mAccessToken = accessToken;
    }

    /**
     * Set api key, access token and log level
     *
     * @param apiKey      the API key obtained from the console
     * @param accessToken the access token from the console
     * @param logLevel    log level
     */
    public clientConfig(String apiKey, String accessToken, LogLevel logLevel) {
        this(apiKey, accessToken);
        mLogLevel = logLevel;
    }

    /**
     * Set api key, access token and referrer
     *
     * @param apiKey      the API key obtained from the console
     * @param accessToken the access token from the console
     * @param referrer    the parameter from retorfit as LogLevel
     */
    public clientConfig(String apiKey, String accessToken, String referrer) {
        this(apiKey, accessToken);
        mReferrer = referrer;
    }

    /**
     * Set api key, access token and referrer
     *
     * @param apiKey      the API key obtained from the console
     * @param accessToken the access token from the console
     * @param referrer    the parameter from retorfit as LogLevel
     * @param logLevel    log level
     */
    public clientConfig(String apiKey, String accessToken, String referrer, LogLevel logLevel) {
        this(apiKey, accessToken, referrer);
        mLogLevel = logLevel;
    }

    /**
     * Get api key
     *
     * @return the self object
     */
    public String getApiKey() {
        return mApiKey;
    }

    /**
     * Set api key
     *
     * @param apiKey the API key obtained from the console
     * @return the self object
     */
    public clientConfig setApiKey(String apiKey) {
        this.mApiKey = apiKey;
        return this;
    }

    /**
     * Get api secret
     *
     * @return the self object
     */
    public String getApiSecret() {
        return mApiSecret;
    }

    /**
     * Set api secret. Avoid using api secret as it's a security risk
     *
     * @param apiSecret api secret
     * @return the self object
     */
    public clientConfig setApiSecret(String apiSecret) {
        this.mApiSecret = apiSecret;
        return this;
    }

    /**
     * set redirect uri
     *
     * @param url the url of the return redirect
     * @return the acutual okject
     */
    public clientConfig setRedirectUri(String url) {
        this.mRedirectUri = url;
        return this;
    }

    /**
     * get the uri from the web redirect call back from the official server
     *
     * @return get the redirect uri from the after thing was working.
     */
    public String getRedirectURI() {
        return mRedirectUri;
    }

    /**
     * Get access token
     *
     * @return the self object
     */
    public String getAccessToken() {
        return mAccessToken;
    }

    /**
     * Set access token
     *
     * @param accessToken the access token from the console
     * @return the self object
     */
    public clientConfig setAccessToken(String accessToken) {
        this.mAccessToken = accessToken;
        return this;
    }

    /**
     * Get referrer
     *
     * @return the self object
     */
    public String getReferrer() {
        return mReferrer;
    }

    /**
     * Set referrer
     *
     * @param referrer the parameter from retorfit as LogLevel
     * @return the self object
     */
    public clientConfig setReferrer(String referrer) {
        this.mReferrer = referrer;
        return this;
    }

    /**
     * Get log level
     *
     * @return the self object
     */
    public LogLevel getLogLevel() {
        return mLogLevel;
    }

    /**
     * Set log level
     *
     * @param logLevel log level object
     * @return the self object
     */
    public clientConfig setLogLevel(LogLevel logLevel) {
        mLogLevel = logLevel;
        return this;
    }

    /**
     * retrieve the bundle data for the intent
     *
     * @return the Bundle object
     */
    public Bundle getLogInBundle() {
        Bundle b = new Bundle();
/*        b.putString(AuthorizeActivity.EXTRA_API_KEY, mApiKey);
        b.putString(AuthorizeActivity.EXTRA_SECRET, mApiSecret);
        b.putString(AuthorizeActivity.EXTRA_REDIRECT_URI, mRedirectUri);
        b.putStringArray(AuthorizeActivity.EXTRA_SCOPES, new String[]{
                "read",
                "write"
        });*/
        return b;
    }
}
