package com.lehuuquynhnhi.k234111e_mobile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lehuuquynhnhi.catalog.data.CatalogRepository;
import com.lehuuquynhnhi.catalog.data.FirebaseRealtimeCatalogRepository;
import com.lehuuquynhnhi.catalog.domain.CatalogCategory;
import com.lehuuquynhnhi.catalog.domain.CatalogProduct;
import com.lehuuquynhnhi.catalog.ui.ProductCatalogAdapter;
import com.lehuuquynhnhi.catalog.ui.ResponsiveGrid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProductCatalogActivity extends AppCompatActivity {
    private static final int SORT_NAME_ASC = 0;
    private static final int SORT_CATEGORY_ASC = 1;
    private static final int SORT_NAME_DESC = 2;

    private final List<CatalogCategory> categories = new ArrayList<>();
    private final List<CatalogProduct> allProducts = new ArrayList<>();
    private final Map<String, String> categoryNameById = new HashMap<>();

    private ProductCatalogAdapter adapter;
    private CatalogRepository repository;
    private ProgressBar progressProducts;
    private TextView txtProductEmpty;
    private TextView txtProductCatalogTitle;
    private EditText edtProductSearch;
    private Spinner spnCategoryFilter;
    private Spinner spnProductSort;
    private String selectedCategoryId = "";
    private int selectedSort = SORT_NAME_ASC;
    private boolean spinnersReady = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catalog);

        selectedCategoryId = getIntent().getStringExtra(CatalogCategoriesActivity.EXTRA_CATEGORY_ID);
        if (selectedCategoryId == null) {
            selectedCategoryId = "";
        }
        addViews();
        loadData();
    }

    private void addViews() {
        txtProductCatalogTitle = findViewById(R.id.txtProductCatalogTitle);
        edtProductSearch = findViewById(R.id.edtProductSearch);
        spnCategoryFilter = findViewById(R.id.spnCategoryFilter);
        spnProductSort = findViewById(R.id.spnProductSort);
        progressProducts = findViewById(R.id.progressProducts);
        txtProductEmpty = findViewById(R.id.txtProductEmpty);

        RecyclerView rvProducts = findViewById(R.id.rvProducts);
        int threshold = getResources().getInteger(R.integer.catalog_low_stock_threshold);
        adapter = new ProductCatalogAdapter(this, threshold);
        rvProducts.setLayoutManager(new GridLayoutManager(this, ResponsiveGrid.spanCount(this, 190)));
        rvProducts.setAdapter(adapter);

        edtProductSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                applyFilters();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spnCategoryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!spinnersReady) {
                    return;
                }
                selectedCategoryId = position == 0 ? "" : categories.get(position - 1).getId();
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spnProductSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSort = position;
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadData() {
        setLoading(true);
        try {
            repository = new FirebaseRealtimeCatalogRepository(this);
        } catch (Exception exception) {
            showError(exception);
            return;
        }
        repository.loadCategories(new CatalogRepository.ResultCallback<List<CatalogCategory>>() {
            @Override
            public void onSuccess(List<CatalogCategory> data) {
                categories.clear();
                categories.addAll(data);
                categoryNameById.clear();
                for (CatalogCategory category : categories) {
                    categoryNameById.put(category.getId(), category.getName());
                }
                setupSpinners();
                loadProducts();
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    private void loadProducts() {
        repository.loadProducts(new CatalogRepository.ResultCallback<List<CatalogProduct>>() {
            @Override
            public void onSuccess(List<CatalogProduct> data) {
                setLoading(false);
                allProducts.clear();
                for (CatalogProduct product : data) {
                    allProducts.add(withCategoryName(product));
                }
                applyFilters();
            }

            @Override
            public void onError(Exception exception) {
                showError(exception);
            }
        });
    }

    private CatalogProduct withCategoryName(CatalogProduct product) {
        if (!product.getCategoryName().isEmpty()) {
            return product;
        }
        String categoryName = categoryNameById.get(product.getCategoryId());
        return new CatalogProduct(product.getId(), product.getName(), product.getCategoryId(), categoryName,
                product.getImageUrl(), product.getImagePath(), product.getPrice(), product.getQuantity());
    }

    private void setupSpinners() {
        List<String> categoryLabels = new ArrayList<>();
        categoryLabels.add(getString(R.string.catalog_filter_all));
        int selectedIndex = 0;
        for (int index = 0; index < categories.size(); index++) {
            CatalogCategory category = categories.get(index);
            categoryLabels.add(category.getName());
            if (category.getId().equals(selectedCategoryId)) {
                selectedIndex = index + 1;
            }
        }
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryLabels);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoryFilter.setAdapter(categoryAdapter);
        spnCategoryFilter.setSelection(selectedIndex);

        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{
                        getString(R.string.catalog_sort_name),
                        getString(R.string.catalog_sort_category),
                        getString(R.string.catalog_sort_name_desc)
                });
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProductSort.setAdapter(sortAdapter);
        spnProductSort.setSelection(selectedSort);
        spinnersReady = true;

        String selectedCategoryName = getIntent().getStringExtra(CatalogCategoriesActivity.EXTRA_CATEGORY_NAME);
        if (selectedCategoryName != null && !selectedCategoryName.isEmpty()) {
            txtProductCatalogTitle.setText(selectedCategoryName);
        }
    }

    private void applyFilters() {
        String query = edtProductSearch.getText().toString().trim().toLowerCase(Locale.ROOT);
        List<CatalogProduct> filtered = new ArrayList<>();
        for (CatalogProduct product : allProducts) {
            boolean matchesName = query.isEmpty() || product.getName().toLowerCase(Locale.ROOT).contains(query);
            boolean matchesCategory = selectedCategoryId.isEmpty() || selectedCategoryId.equals(product.getCategoryId());
            if (matchesName && matchesCategory) {
                filtered.add(product);
            }
        }

        sortProducts(filtered);
        adapter.submitList(filtered);
        txtProductEmpty.setVisibility(filtered.isEmpty() && progressProducts.getVisibility() != View.VISIBLE ? View.VISIBLE : View.GONE);
    }

    private void sortProducts(List<CatalogProduct> products) {
        Comparator<CatalogProduct> byName = (left, right) -> left.getName().compareToIgnoreCase(right.getName());
        if (selectedSort == SORT_CATEGORY_ASC) {
            Collections.sort(products, (left, right) -> {
                int categoryCompare = left.getCategoryName().compareToIgnoreCase(right.getCategoryName());
                return categoryCompare == 0 ? byName.compare(left, right) : categoryCompare;
            });
        } else if (selectedSort == SORT_NAME_DESC) {
            Collections.sort(products, (left, right) -> right.getName().compareToIgnoreCase(left.getName()));
        } else {
            Collections.sort(products, byName);
        }
    }

    private void setLoading(boolean isLoading) {
        progressProducts.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        txtProductEmpty.setVisibility(View.GONE);
    }

    private void showError(Exception exception) {
        setLoading(false);
        txtProductEmpty.setVisibility(View.VISIBLE);
        txtProductEmpty.setText(exception.getMessage());
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
