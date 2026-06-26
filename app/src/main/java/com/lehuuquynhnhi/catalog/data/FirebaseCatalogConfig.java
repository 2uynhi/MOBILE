package com.lehuuquynhnhi.catalog.data;

import android.content.Context;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.lehuuquynhnhi.k234111e_mobile.R;

import java.util.List;

public final class FirebaseCatalogConfig {
    private static final String APP_NAME = "catalog_app";

    private FirebaseCatalogConfig() {
    }

    public static FirebaseApp getApp(Context context) {
        List<FirebaseApp> apps = FirebaseApp.getApps(context);
        if (!apps.isEmpty()) {
            return apps.get(0);
        }

        String appId = context.getString(R.string.firebase_google_app_id);
        String apiKey = context.getString(R.string.firebase_api_key);
        String projectId = context.getString(R.string.firebase_project_id);
        String bucket = context.getString(R.string.firebase_storage_bucket);

        if (isPlaceholder(appId) || isPlaceholder(apiKey)) {
            throw new IllegalStateException("Missing Firebase config. Add google_app_id and api_key in strings.xml or add google-services.json.");
        }

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setApplicationId(appId)
                .setApiKey(apiKey)
                .setProjectId(projectId)
                .setStorageBucket(bucket)
                .build();
        return FirebaseApp.initializeApp(context.getApplicationContext(), options, APP_NAME);
    }

    private static boolean isPlaceholder(String value) {
        return value == null || value.trim().isEmpty() || value.startsWith("YOUR_");
    }
}
