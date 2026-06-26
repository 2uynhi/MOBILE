package com.lehuuquynhnhi.catalog.domain;

public class CatalogProduct {
    private final String id;
    private final String name;
    private final String categoryId;
    private final String categoryName;
    private final String imageUrl;
    private final String imagePath;
    private final double price;
    private final int quantity;

    public CatalogProduct(String id, String name, String categoryId, String categoryName,
                          String imageUrl, String imagePath, double price, int quantity) {
        this.id = id == null ? "" : id;
        this.name = name == null ? "" : name;
        this.categoryId = categoryId == null ? "" : categoryId;
        this.categoryName = categoryName == null ? "" : categoryName;
        this.imageUrl = imageUrl == null ? "" : imageUrl;
        this.imagePath = imagePath == null ? "" : imagePath;
        this.price = price;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}
