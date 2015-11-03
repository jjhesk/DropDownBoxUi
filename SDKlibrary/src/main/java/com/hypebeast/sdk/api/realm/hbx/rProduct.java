package com.hypebeast.sdk.api.realm.hbx;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hesk on 2/10/15.
 */
public class rProduct extends RealmObject {
    @PrimaryKey
    private long product_id;
    private String name;
    private String description;
    private int price;
    private String created_at;
    private String updated_at;
    private String links;
    private String imageHead;
    private String brandname;

    public rProduct() {
    }

    public void setBrandname(String name) {
        brandname = name;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
