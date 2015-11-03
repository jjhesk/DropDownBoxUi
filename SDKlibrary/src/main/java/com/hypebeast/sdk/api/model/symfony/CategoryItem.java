package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hesk on 12/10/15.
 */
public class CategoryItem {
    @SerializedName("name")
    public String name;
    @SerializedName("link")
    public String link;
    @SerializedName("image")
    public String image;
}
