package com.lehuuquynhnhi.k234111e_mobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.lehuuquynhnhi.adapters.CategoryAdapter;
import com.lehuuquynhnhi.dals.CategoryDAO;
import com.lehuuquynhnhi.models.Category;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    ListView lvCategory;
    ArrayList<Category> categories;
    CategoryAdapter adapter;
    CategoryDAO categoryDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        categoryDAO = new CategoryDAO(this);
        addViews();
        addEvents();
    }

    private void addViews() {
        lvCategory = findViewById(R.id.lvCategory);
        categories = categoryDAO.getCategories();
        adapter = new CategoryAdapter(this, R.layout.category_custom_item, categories);
        lvCategory.setAdapter(adapter);
    }

    private void addEvents() {
        lvCategory.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                processDeleteCategory(position);
                return true;
            }
        });
    }

    private void processDeleteCategory(int position) {
        // Minimum logic to make it work: remove from list and notify adapter
        if (position >= 0 && position < categories.size()) {
            categories.remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemAdd) {
            Intent intent = new Intent(this, CategoryNewActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
