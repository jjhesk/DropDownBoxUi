package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hesk on 23/10/15.
 */
public class ShoppingCart {
    @SerializedName("cart")
    public OrderSub cart_data;
}
