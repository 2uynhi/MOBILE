package com.lehuuquynhnhi.catalog.data;

import com.lehuuquynhnhi.catalog.domain.CatalogCategory;
import com.lehuuquynhnhi.catalog.domain.CatalogProduct;

import java.util.List;

public interface CatalogRepository {
    interface ResultCallback<T> {
        void onSuccess(T data);

        void onError(Exception exception);
    }

    void loadCategories(ResultCallback<List<CatalogCategory>> callback);

    void loadProducts(ResultCallback<List<CatalogProduct>> callback);
}
