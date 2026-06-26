package com.lehuuquynhnhi.catalog.domain;

public class CatalogCategory {
    private final String id;
    private final String name;

    public CatalogCategory(String id, String name) {
        this.id = id == null ? "" : id;
        this.name = name == null ? "" : name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
