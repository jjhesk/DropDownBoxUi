package com.hypebeast.sdk.api.model.hypebeaststore;

import com.google.gson.annotations.SerializedName;
import com.hypebeast.sdk.api.model.symfony.ErrorBaseAlternative;
import com.hypebeast.sdk.api.model.symfony.OrderItem;

import java.util.List;

/**
 * Created by hesk on 25/9/15.
 */
public class ResponseListOrder extends ErrorBaseAlternative {
    @SerializedName("order_item")
    public List<OrderItem> orderItem;
}
