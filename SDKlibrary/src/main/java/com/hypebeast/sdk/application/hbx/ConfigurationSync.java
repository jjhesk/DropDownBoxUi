package com.hypebeast.sdk.application.hbx;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.hypebeast.sdk.Constants;
import com.hypebeast.sdk.api.exception.ApiException;
import com.hypebeast.sdk.api.model.hypebeaststore.ResponseMobileOverhead;
import com.hypebeast.sdk.api.model.symfony.Config;
import com.hypebeast.sdk.api.resources.hbstore.Authentication;
import com.hypebeast.sdk.api.resources.hbstore.Brand;
import com.hypebeast.sdk.api.resources.hbstore.Overhead;
import com.hypebeast.sdk.api.resources.hbstore.Products;
import com.hypebeast.sdk.clients.HBStoreApiClient;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by hesk on 1/9/15.
 */
public class ConfigurationSync {
    public static ConfigurationSync instance;
    private final Application app;
    private Realm realm;
    public static final String PREFERENCE_FOUNDATION = "foundationfile";
    public static final String ACCOUNT_USER = "hbxuser";
    public static final String ACCOUNT_PASS = "hbxpass";
    public static final String PREFERENCE_FOUNDATION_REGISTRATION = "regtime";

    private Overhead mOverheadRequest;
    private ResponseMobileOverhead mFoundation;
    private HBStoreApiClient client;

    private ArrayList<sync> mListeners = new ArrayList<>();
    private Authentication request_login;
    private sync mListener;

    public static ConfigurationSync with(Application app, sync mListener) {
        if (instance == null) {
            instance = new ConfigurationSync(app, mListener);
            instance.init();
        } else {
            instance.addInterface(mListener);
            instance.init();
        }

        return instance;
    }

    public static ConfigurationSync getInstance() throws Exception {
        if (instance == null) {
            throw new Exception("please init a new instance. or go to the slash screen again");
        }
        return instance;
    }

    public ConfigurationSync(Application app, sync mListener) {
        this.app = app;
        this.realm = Realm.getInstance(app);
        client = HBStoreApiClient.getInstance();
        //client.setLanguageBase(HBEditorialClient.BASE_EN);
        mOverheadRequest = client.createOverHead();
        request_login = client.createAuthentication();
        addInterface(mListener);
    }


    public HBStoreApiClient getInstanceHBClient() {
        return client;
    }

    private void addInterface(sync listenerSync) {
        // mListeners.add(listenerSync);
        mListener = listenerSync;
    }

    public void clearListeners() {
        //mListeners.clear();
    }

    private void executeListeners() {
        if (mListener != null) mListener.syncDone(instance, mFoundation);
    }

    private boolean isLogin = false;

    public boolean isLoginStatusValid() {
        return isLogin;
    }

    private void syncCheckLogined() {
        try {

            String user = load(ACCOUNT_USER);
            String pass = load(ACCOUNT_PASS);
            if (user.equalsIgnoreCase("none") || pass.equalsIgnoreCase("none")) {
                executeListeners();
                return;
            } else {
                request_login.checkLoginStatus(user, pass, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        isLogin = true;
                        executeListeners();
                        Log.d("loginHBX", "login result : " + s);
                    }

                    @Override
                    public void failure(RetrofitError e) {
                        //if (mListener != null) mListener.error(e.getMessage());
                        Log.d("loginHBX", e.getMessage());
                    }
                });
            }


        } catch (ApiException e) {
            Log.d("loginHBX", e.getMessage());
            //  if (mListener != null) mListener.error(e.getMessage());
        }
    }

    private void syncWorkerThread() {
        try {
            mOverheadRequest.mobile_config(new Callback<ResponseMobileOverhead>() {
                @Override
                public void success(ResponseMobileOverhead foundation, Response response) {
                    mFoundation = foundation;
                    saveInfo(PREFERENCE_FOUNDATION, client.fromJsonToString(foundation));
                    Date date = new Date();
                    Timestamp timestamp = new Timestamp(date.getTime());
                    saveInfo(PREFERENCE_FOUNDATION_REGISTRATION, timestamp.toString());
                    //the second task is now started - check login status
                    syncCheckLogined();
                }

                @Override
                public void failure(RetrofitError error) {
                    if (mListener != null) mListener.error(error.getMessage());
                }
            });
        } catch (ApiException e) {
            if (mListener != null) mListener.error(e.getMessage());
        }
    }

    private void saveInfo(final String tag, final String data) {
        PreferenceManager.getDefaultSharedPreferences(app)
                .edit()
                .putString(tag, data)
                .commit();

    }

    private String load(final String tag) {
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(app);
        String data = sharedPreferences.getString(tag, "none");
        return data;
    }

    private void init() {
        String data = load(PREFERENCE_FOUNDATION);
        String time = load(PREFERENCE_FOUNDATION_REGISTRATION);
        if (!data.equalsIgnoreCase("none") && !time.equalsIgnoreCase("none")) {
            Timestamp past = Timestamp.valueOf(time);
            Date date = new Date();
            //   Calendar cal1 = Calendar.getInstance();
            Timestamp now = new Timestamp(date.getTime());
            long pastms = past.getTime();
            long nowms = now.getTime();
            if (nowms - pastms > Constants.ONE_DAY) {
                syncWorkerThread();
            } else {
                if (data.equalsIgnoreCase("")) {
                    syncWorkerThread();
                } else {
                    mFoundation = client.fromsavedConfiguration(data);
                    executeListeners();
                }
            }
        } else if (data.equalsIgnoreCase("none") || time.equalsIgnoreCase("none")) {
            syncWorkerThread();
        }
    }

    public ResponseMobileOverhead getFoundation() {
        return mFoundation;
    }


 /*   public configbank getByLanguage(String lang) {
        if (lang.equals("en")) {
            return mFoundation.english;
        } else if (lang.equals("zh_CN")) {
            return mFoundation.chinese_simplified;
        } else if (lang.equals("ja")) {
            return mFoundation.japanese;
        } else if (lang.equals("zh_TW")) {
            return mFoundation.chinese_traditional;
        } else
            return mFoundation.english;
    }*/
}
