package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hesk on 25/9/15.
 */
public class OrderItem {
    @SerializedName("id")
    public long _id;
    @SerializedName("quantity")
    public int quantity;
    @SerializedName("unit_price")
    public int unit_price;
    @SerializedName("adjustments_total")
    public int adjustment_total;
    @SerializedName("total")
    public int total;
    @SerializedName("immutable")
    public boolean immutable;
    @SerializedName("order")
    public OrderSub sub;
    @SerializedName("variant")
    public Variant var;
    @SerializedName("unit_wholesale_price")
    public int unit_wholesale_price;
    @SerializedName("quantity_ordered")
    public int quantity_ordered;
    @SerializedName("quantity_cancelled")
    public int quantity_cancelled;
    @SerializedName("quantity_backordered")
    public int quantity_backordered;
    @SerializedName("quantity_returned")
    public int quantity_returned;
}
