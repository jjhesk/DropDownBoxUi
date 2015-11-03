package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hesk on 25/9/15.
 */
public class OrderSub {
    @SerializedName("total_quantity")
    public int total_quantity;
    @SerializedName("id")
    public long id;
    @SerializedName("total")
    public int total;
    @SerializedName("state")
    public String state;
    @SerializedName("expires_at")
    public String expires_at;
    @SerializedName("shipping_state")
    public String shipping_state_incart;
    @SerializedName("items")
    public List<OrderItem> items;

}
