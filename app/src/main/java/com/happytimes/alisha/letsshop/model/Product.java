package com.happytimes.alisha.letsshop.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by alishaalam on 7/8/16.
 */

/*@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")*/
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "productId",
        "productName",
        "shortDescription",
        "longDescription",
        "price",
        "productImage",
        "reviewRating",
        "reviewCount",
        "inStock"
})
@JsonIgnoreProperties(ignoreUnknown=true)
public class Product {

    public String productId;
    public String productName;
    public String shortDescription;
    public String longDescription;
    public String price;
    public String productImage;
    public String reviewRating;
    public String reviewCount;
    public String inStock;


    public Product(String productId, String productName, String shortDescription, String longDescription, String price, String productImage, String reviewRating, String reviewCount, String inStock) {
        this.productId = productId;
        this.productName = productName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.price = price;
        this.productImage = productImage;
        this.reviewRating = reviewRating;
        this.reviewCount = reviewCount;
        this.inStock = inStock;
    }

    public Product() {
        this.productId = "testId";
        this.productName = "testName";
        this.productImage = "imageUrl";
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getReviewRating() {
        return reviewRating;
    }

    public void setReviewRating(String reviewRating) {
        this.reviewRating = reviewRating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getInStock() {
        return inStock;
    }

    public void setInStock(String inStock) {
        this.inStock = inStock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (!getProductId().equals(product.getProductId())) return false;
        return getProductName().equals(product.getProductName());

    }

    @Override
    public int hashCode() {
        int result = getProductId().hashCode();
        result = 31 * result + getProductName().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", longDescription='" + longDescription + '\'' +
                ", price='" + price + '\'' +
                ", productImage='" + productImage + '\'' +
                ", reviewRating='" + reviewRating + '\'' +
                ", reviewCount='" + reviewCount + '\'' +
                ", inStock='" + inStock + '\'' +
                '}';
    }
}
