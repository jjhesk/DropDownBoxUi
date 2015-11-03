package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hesk on 12/10/15.
 */
public class NavigationItem {
    @SerializedName("name")
    public String name;
    @SerializedName("banners")
    public List<NaviImage> banners;
    @SerializedName("link")
    public String link;
}
