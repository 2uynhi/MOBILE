package com.lehuuquynhnhi.catalog.ui;

import android.content.Context;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.lehuuquynhnhi.catalog.data.FirebaseCatalogConfig;

import java.util.HashMap;
import java.util.Map;

public class FirebaseImageLoader {
    private final Context context;
    private FirebaseStorage storage;
    private final Map<String, String> downloadUrlCache = new HashMap<>();

    public FirebaseImageLoader(Context context) {
        this.context = context.getApplicationContext();
    }

    public void load(ImageView imageView, String imageUrl, String imagePath) {
        imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        if (imageUrl != null && imageUrl.startsWith("http")) {
            Glide.with(imageView).load(imageUrl).centerCrop().into(imageView);
            return;
        }

        String storageLocation = imagePath == null || imagePath.trim().isEmpty() ? imageUrl : imagePath;
        if (storageLocation == null || storageLocation.trim().isEmpty()) {
            return;
        }

        String cached = downloadUrlCache.get(storageLocation);
        if (cached != null) {
            Glide.with(imageView).load(cached).centerCrop().into(imageView);
            return;
        }

        FirebaseStorage firebaseStorage = getStorage();
        if (firebaseStorage == null) {
            imageView.setImageResource(android.R.drawable.ic_menu_report_image);
            return;
        }

        StorageReference reference = storageLocation.startsWith("gs://")
                ? firebaseStorage.getReferenceFromUrl(storageLocation)
                : firebaseStorage.getReference().child(storageLocation);
        reference.getDownloadUrl()
                .addOnSuccessListener(uri -> {
                    downloadUrlCache.put(storageLocation, uri.toString());
                    Glide.with(imageView).load(uri).centerCrop().into(imageView);
                })
                .addOnFailureListener(exception -> imageView.setImageResource(android.R.drawable.ic_menu_report_image));
    }

    private FirebaseStorage getStorage() {
        if (storage != null) {
            return storage;
        }
        try {
            storage = FirebaseStorage.getInstance(FirebaseCatalogConfig.getApp(context));
            return storage;
        } catch (Exception exception) {
            return null;
        }
    }
}
