package com.hypebeast.sdk.application.hbx;

import com.hypebeast.sdk.api.model.hypebeaststore.ResponseMobileOverhead;

/**
 * Created by hesk on 1/9/15.
 */
public interface sync {
    void syncDone(ConfigurationSync me, ResponseMobileOverhead data);
    void error(String txt);
}
