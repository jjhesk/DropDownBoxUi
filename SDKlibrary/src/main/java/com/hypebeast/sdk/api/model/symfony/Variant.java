package com.hypebeast.sdk.api.model.symfony;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by hesk on 2/18/15.
 */
public class Variant {
    @SerializedName("id")
    private long id;
    @SerializedName("master")
    private boolean master;
    @SerializedName("options")
    private ArrayList<VarientContent> options;
    @SerializedName("is_in_stock")
    private boolean is_in_stock;
    @SerializedName("is_last_one")
    private boolean is_last_one;

    public enum Type {
        OUT_OF_STOCK,
        LAST_ONE,
        NORMAL
    }

    //below this line will be the internal use valuables not directly parsed from the json
    //these are post process and handled by the program and they are not from the json
    private String variant_type_presentation;
    private String option_name;
    private String stock_config;
    private int stock_status = 0;
    private int stock_delta = 0;
    public Type stockStatus;
    public VariantItem.VType variant_type;
    public static int OUT_OF_STOCK = -1, LAST_ONE = 1, NORMAL = 0;

    public Variant() {

    }

    public long getId() {
        return id;
    }

    /**
     * some pre-processing from the data
     *
     * @return the object Variant
     */
    public Variant init() {
        variant_type_presentation = options.get(0).option.presentation;
        variant_type = options.get(0).option.name;
        option_name = options.get(0).value;

        if (is_in_stock) {
            stock_config = "";
            stock_status = NORMAL;
            stockStatus = Type.NORMAL;
            if (is_last_one) {
                stock_config = " (Last One Left)";
                stock_status = LAST_ONE;
                stockStatus = Type.LAST_ONE;
            }
        } else {
            stock_config = " (Out of Stock)";
            stock_status = OUT_OF_STOCK;
            stockStatus = Type.OUT_OF_STOCK;
        }

        return this;
    }


    public String getType() {
        return variant_type_presentation;
    }

    public VariantItem.VType getTypeConstant() {
        return variant_type;
    }

    public String getMetaValueFromType() {
        return option_name;
    }

    public String stock_logic_configuration() {
        return stock_config;
    }

    public String displayLastItem() {
        return is_in_stock ? "Last Item" : "";
    }

    public boolean instock() {
        return stock_status == LAST_ONE || stock_status == NORMAL;
    }

    public boolean isLastOne() {
        return stock_status == LAST_ONE;
    }

}
