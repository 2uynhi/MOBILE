package com.lehuuquynhnhi.catalog.data;

import android.content.Context;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.lehuuquynhnhi.catalog.domain.CatalogCategory;
import com.lehuuquynhnhi.catalog.domain.CatalogProduct;

import java.util.ArrayList;
import java.util.List;

public class FirebaseCatalogRepository implements CatalogRepository {
    private static final String COLLECTION_CATEGORIES = "categories";
    private static final String COLLECTION_PRODUCTS = "products";

    private final FirebaseFirestore firestore;

    public FirebaseCatalogRepository(Context context) {
        firestore = FirebaseFirestore.getInstance(FirebaseCatalogConfig.getApp(context));
    }

    @Override
    public void loadCategories(ResultCallback<List<CatalogCategory>> callback) {
        firestore.collection(COLLECTION_CATEGORIES)
                .orderBy("categoryName", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<CatalogCategory> categories = new ArrayList<>();
                    for (DocumentSnapshot document : snapshot.getDocuments()) {
                        categories.add(mapCategory(document));
                    }
                    callback.onSuccess(categories);
                })
                .addOnFailureListener(callback::onError);
    }

    @Override
    public void loadProducts(ResultCallback<List<CatalogProduct>> callback) {
        firestore.collection(COLLECTION_PRODUCTS)
                .orderBy("productName", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<CatalogProduct> products = new ArrayList<>();
                    for (DocumentSnapshot document : snapshot.getDocuments()) {
                        products.add(mapProduct(document));
                    }
                    callback.onSuccess(products);
                })
                .addOnFailureListener(callback::onError);
    }

    private CatalogCategory mapCategory(DocumentSnapshot document) {
        String id = firstString(document, "categoryId", "id");
        if (id.isEmpty()) {
            id = document.getId();
        }
        String name = firstString(document, "categoryName", "name", "title");
        if (name.isEmpty()) {
            name = id;
        }
        return new CatalogCategory(id, name);
    }

    private CatalogProduct mapProduct(DocumentSnapshot document) {
        String id = firstString(document, "productId", "id");
        if (id.isEmpty()) {
            id = document.getId();
        }
        String name = firstString(document, "productName", "name", "title");
        String categoryId = firstString(document, "categoryId", "category_id");
        String categoryName = firstString(document, "categoryName", "category");
        String imageUrl = firstString(document, "imageUrl", "photoUrl", "thumbnailUrl");
        String imagePath = firstString(document, "imagePath", "storagePath", "imageStoragePath", "photoPath");
        double price = firstDouble(document, "price", "unitPrice");
        int quantity = (int) firstLong(document, "quantity", "stock", "stockQuantity");
        return new CatalogProduct(id, name, categoryId, categoryName, imageUrl, imagePath, price, quantity);
    }

    private String firstString(DocumentSnapshot document, String... fields) {
        for (String field : fields) {
            Object value = document.get(field);
            if (value != null) {
                String text = String.valueOf(value).trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }
        return "";
    }

    private double firstDouble(DocumentSnapshot document, String... fields) {
        for (String field : fields) {
            Object value = document.get(field);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            if (value != null) {
                try {
                    return Double.parseDouble(String.valueOf(value));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return 0;
    }

    private long firstLong(DocumentSnapshot document, String... fields) {
        for (String field : fields) {
            Object value = document.get(field);
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            if (value != null) {
                try {
                    return Long.parseLong(String.valueOf(value));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return 0;
    }
}
