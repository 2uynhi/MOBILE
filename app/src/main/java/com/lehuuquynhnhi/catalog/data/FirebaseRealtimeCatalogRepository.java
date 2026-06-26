package com.lehuuquynhnhi.catalog.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.lehuuquynhnhi.catalog.domain.CatalogCategory;
import com.lehuuquynhnhi.catalog.domain.CatalogProduct;
import com.lehuuquynhnhi.k234111e_mobile.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FirebaseRealtimeCatalogRepository implements CatalogRepository {
    private static final String NODE_CATEGORIES = "categories";
    private static final String NODE_PRODUCTS = "products";

    private final String databaseUrl;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public FirebaseRealtimeCatalogRepository(Context context) {
        databaseUrl = normalizeUrl(context.getString(R.string.firebase_database_url));
    }

    @Override
    public void loadCategories(ResultCallback<List<CatalogCategory>> callback) {
        executorService.execute(() -> {
            try {
                String response = getJson(NODE_CATEGORIES);
                List<CatalogCategory> categories = parseCategories(response);
                Collections.sort(categories, (left, right) -> left.getName().compareToIgnoreCase(right.getName()));
                mainHandler.post(() -> callback.onSuccess(categories));
            } catch (Exception exception) {
                mainHandler.post(() -> callback.onError(exception));
            }
        });
    }

    @Override
    public void loadProducts(ResultCallback<List<CatalogProduct>> callback) {
        executorService.execute(() -> {
            try {
                String response = getJson(NODE_PRODUCTS);
                List<CatalogProduct> products = parseProducts(response);
                Collections.sort(products, (left, right) -> left.getName().compareToIgnoreCase(right.getName()));
                mainHandler.post(() -> callback.onSuccess(products));
            } catch (Exception exception) {
                mainHandler.post(() -> callback.onError(exception));
            }
        });
    }

    private String getJson(String node) throws IOException {
        URL url = new URL(databaseUrl + "/" + node + ".json");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(15000);
        connection.setReadTimeout(15000);

        int responseCode = connection.getResponseCode();
        String responseBody = readStream(responseCode >= 200 && responseCode < 300
                ? connection.getInputStream()
                : connection.getErrorStream());
        connection.disconnect();

        if (responseCode < 200 || responseCode >= 300) {
            throw new IOException("Realtime Database HTTP " + responseCode + ": " + responseBody);
        }
        return responseBody;
    }

    private List<CatalogCategory> parseCategories(String response) throws JSONException {
        List<CatalogCategory> categories = new ArrayList<>();
        if (response == null || response.trim().isEmpty() || response.trim().equals("null")) {
            return categories;
        }

        Object root = parseRoot(response);
        if (root instanceof JSONArray) {
            JSONArray array = (JSONArray) root;
            for (int index = 0; index < array.length(); index++) {
                Object value = array.opt(index);
                if (value instanceof JSONObject) {
                    categories.add(mapCategory(String.valueOf(index), (JSONObject) value));
                }
            }
        } else if (root instanceof JSONObject) {
            JSONObject object = (JSONObject) root;
            JSONArray keys = object.names();
            if (keys == null) {
                return categories;
            }
            for (int index = 0; index < keys.length(); index++) {
                String key = keys.getString(index);
                Object value = object.opt(key);
                if (value instanceof JSONObject) {
                    categories.add(mapCategory(key, (JSONObject) value));
                }
            }
        }
        return categories;
    }

    private List<CatalogProduct> parseProducts(String response) throws JSONException {
        List<CatalogProduct> products = new ArrayList<>();
        if (response == null || response.trim().isEmpty() || response.trim().equals("null")) {
            return products;
        }

        Object root = parseRoot(response);
        if (root instanceof JSONArray) {
            JSONArray array = (JSONArray) root;
            for (int index = 0; index < array.length(); index++) {
                Object value = array.opt(index);
                if (value instanceof JSONObject) {
                    products.add(mapProduct(String.valueOf(index), (JSONObject) value));
                }
            }
        } else if (root instanceof JSONObject) {
            JSONObject object = (JSONObject) root;
            JSONArray keys = object.names();
            if (keys == null) {
                return products;
            }
            for (int index = 0; index < keys.length(); index++) {
                String key = keys.getString(index);
                Object value = object.opt(key);
                if (value instanceof JSONObject) {
                    products.add(mapProduct(key, (JSONObject) value));
                }
            }
        }
        return products;
    }

    private Object parseRoot(String response) throws JSONException {
        String trimmed = response.trim();
        return trimmed.startsWith("[") ? new JSONArray(trimmed) : new JSONObject(trimmed);
    }

    private CatalogCategory mapCategory(String key, JSONObject object) {
        String id = firstString(object, "categoryId", "id");
        if (id.isEmpty()) {
            id = key;
        }
        String name = firstString(object, "categoryName", "name", "title");
        if (name.isEmpty()) {
            name = id;
        }
        return new CatalogCategory(id, name);
    }

    private CatalogProduct mapProduct(String key, JSONObject object) {
        String id = firstString(object, "productId", "id");
        if (id.isEmpty()) {
            id = key;
        }
        String name = firstString(object, "productName", "name", "title");
        String categoryId = firstString(object, "categoryId", "category_id");
        String categoryName = firstString(object, "categoryName", "category");
        String imageUrl = firstString(object, "imageUrl", "photoUrl", "thumbnailUrl", "image");
        String imagePath = firstString(object, "imagePath", "storagePath", "imageStoragePath", "photoPath");
        double price = firstDouble(object, "price", "unitPrice");
        int quantity = (int) firstLong(object, "quantity", "stock", "stockQuantity");
        return new CatalogProduct(id, name, categoryId, categoryName, imageUrl, imagePath, price, quantity);
    }

    private String firstString(JSONObject object, String... fields) {
        for (String field : fields) {
            Object value = object.opt(field);
            if (value != null && value != JSONObject.NULL) {
                String text = String.valueOf(value).trim();
                if (!text.isEmpty()) {
                    return text;
                }
            }
        }
        return "";
    }

    private double firstDouble(JSONObject object, String... fields) {
        for (String field : fields) {
            Object value = object.opt(field);
            if (value instanceof Number) {
                return ((Number) value).doubleValue();
            }
            if (value != null && value != JSONObject.NULL) {
                try {
                    return Double.parseDouble(String.valueOf(value));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return 0;
    }

    private long firstLong(JSONObject object, String... fields) {
        for (String field : fields) {
            Object value = object.opt(field);
            if (value instanceof Number) {
                return ((Number) value).longValue();
            }
            if (value != null && value != JSONObject.NULL) {
                try {
                    return Long.parseLong(String.valueOf(value));
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return 0;
    }

    private String readStream(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
        }
        return builder.toString();
    }

    private String normalizeUrl(String url) {
        String value = url == null ? "" : url.trim();
        while (value.endsWith("/")) {
            value = value.substring(0, value.length() - 1);
        }
        return value;
    }
}
