package com.happytimes.alisha.letsshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alishaalam on 7/8/16.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "products"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class StoreProducts {

    @JsonProperty("id")
    private String id;
    @JsonProperty("products")
    List<Product> productsList;

    public static final List<Product> PRODUCT_LIST = new ArrayList<>();
    public static final Map<String, Product> PRODUCT_MAP = new LinkedHashMap<>();

    public List<Product> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Product> products) {
        this.productsList = products;
    }

/*    private static void addItem(Product item) {
        productsList.add(item);
        PRODUCT_MAP.put(item.productId, item);
    }*/



}
