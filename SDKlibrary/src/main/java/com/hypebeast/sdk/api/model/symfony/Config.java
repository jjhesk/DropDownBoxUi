package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hesk on 10/9/15.
 */
public class Config {

    @SerializedName("host")
    public String host;

    @SerializedName("navigation")
    public List<NavigationItem> navigation;

    @SerializedName("categories")
    public List<CategoryItem> categories;

}
