package com.hypebeast.sdk.api.realm;

import io.realm.RealmObject;

/**
 * Created by hesk on 6/10/15.
 */
public class QLRealmString extends RealmObject {
    private String val;

    public String getVal() {
        return val;
    }

    public void setVal(String value) {
        this.val = value;
    }
}
