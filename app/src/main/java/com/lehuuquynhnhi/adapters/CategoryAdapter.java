package com.lehuuquynhnhi.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lehuuquynhnhi.k234111e_mobile.R;
import com.lehuuquynhnhi.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends ArrayAdapter<Category> {
    Activity context;
    int resource;
    ArrayList<Category> categories;

    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull ArrayList<Category> categories) {
        super(context, resource, categories);
        this.context = context;
        this.resource = resource;
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(resource, null);
        Category category = categories.get(position);
        
        TextView txtId = row.findViewById(R.id.txtCategoryId);
        TextView txtName = row.findViewById(R.id.txtCategoryName);
        
        if (category != null) {
            txtId.setText(category.getCategoryId());
            txtName.setText(category.getCategoryName());
        }
        
        return row;
    }
}
