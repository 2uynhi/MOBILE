package com.lehuuquynhnhi.catalog.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lehuuquynhnhi.catalog.domain.CatalogCategory;
import com.lehuuquynhnhi.k234111e_mobile.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryCatalogAdapter extends RecyclerView.Adapter<CategoryCatalogAdapter.CategoryViewHolder> {
    public interface OnCategoryClickListener {
        void onCategoryClick(CatalogCategory category);
    }

    private final List<CatalogCategory> categories = new ArrayList<>();
    private final OnCategoryClickListener listener;

    public CategoryCatalogAdapter(OnCategoryClickListener listener) {
        this.listener = listener;
    }

    public void submitList(List<CatalogCategory> newCategories) {
        categories.clear();
        categories.addAll(newCategories);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_catalog_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        CatalogCategory category = categories.get(position);
        holder.txtCategoryName.setText(category.getName());
        holder.txtCategoryId.setText(category.getId());
        holder.itemView.setOnClickListener(view -> listener.onCategoryClick(category));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        final TextView txtCategoryName;
        final TextView txtCategoryId;

        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCategoryName = itemView.findViewById(R.id.txtCategoryName);
            txtCategoryId = itemView.findViewById(R.id.txtCategoryId);
        }
    }
}
