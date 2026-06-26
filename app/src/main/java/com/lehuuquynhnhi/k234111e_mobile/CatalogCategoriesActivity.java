package com.lehuuquynhnhi.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.lehuuquynhnhi.catalog.data.CatalogRepository;
import com.lehuuquynhnhi.catalog.data.FirebaseRealtimeCatalogRepository;
import com.lehuuquynhnhi.catalog.domain.CatalogCategory;
import com.lehuuquynhnhi.catalog.ui.CategoryCatalogAdapter;
import com.lehuuquynhnhi.catalog.ui.ResponsiveGrid;

import java.util.List;

public class CatalogCategoriesActivity extends AppCompatActivity {
    public static final String EXTRA_CATEGORY_ID = "CATEGORY_ID";
    public static final String EXTRA_CATEGORY_NAME = "CATEGORY_NAME";

    private CategoryCatalogAdapter adapter;
    private ProgressBar progressCategories;
    private TextView txtCategoryEmpty;
    private CatalogRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_categories);

        addViews();
        loadCategories();
    }

    private void addViews() {
        RecyclerView rvCategories = findViewById(R.id.rvCategories);
        progressCategories = findViewById(R.id.progressCategories);
        txtCategoryEmpty = findViewById(R.id.txtCategoryEmpty);
        MaterialButton btnViewAllProducts = findViewById(R.id.btnViewAllProducts);

        adapter = new CategoryCatalogAdapter(this::openProductsByCategory);
        rvCategories.setLayoutManager(new GridLayoutManager(this, ResponsiveGrid.spanCount(this, 220)));
        rvCategories.setAdapter(adapter);

        btnViewAllProducts.setOnClickListener(view -> startActivity(new Intent(this, ProductCatalogActivity.class)));
    }

    private void loadCategories() {
        setLoading(true);
        try {
            repository = new FirebaseRealtimeCatalogRepository(this);
        } catch (Exception exception) {
            setLoading(false);
            txtCategoryEmpty.setVisibility(View.VISIBLE);
            txtCategoryEmpty.setText(exception.getMessage());
            return;
        }
        repository.loadCategories(new CatalogRepository.ResultCallback<List<CatalogCategory>>() {
            @Override
            public void onSuccess(List<CatalogCategory> data) {
                setLoading(false);
                adapter.submitList(data);
                txtCategoryEmpty.setVisibility(data.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onError(Exception exception) {
                setLoading(false);
                txtCategoryEmpty.setVisibility(View.VISIBLE);
                txtCategoryEmpty.setText(exception.getMessage());
                Toast.makeText(CatalogCategoriesActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openProductsByCategory(CatalogCategory category) {
        Intent intent = new Intent(this, ProductCatalogActivity.class);
        intent.putExtra(EXTRA_CATEGORY_ID, category.getId());
        intent.putExtra(EXTRA_CATEGORY_NAME, category.getName());
        startActivity(intent);
    }

    private void setLoading(boolean isLoading) {
        progressCategories.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        txtCategoryEmpty.setVisibility(View.GONE);
    }
}
