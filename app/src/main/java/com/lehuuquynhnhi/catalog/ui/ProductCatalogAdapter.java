package com.lehuuquynhnhi.catalog.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lehuuquynhnhi.catalog.domain.CatalogProduct;
import com.lehuuquynhnhi.catalog.domain.StockStatus;
import com.lehuuquynhnhi.k234111e_mobile.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductCatalogAdapter extends RecyclerView.Adapter<ProductCatalogAdapter.ProductViewHolder> {
    private final List<CatalogProduct> products = new ArrayList<>();
    private final FirebaseImageLoader imageLoader;
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    private final int lowStockThreshold;

    public ProductCatalogAdapter(Context context, int lowStockThreshold) {
        this.imageLoader = new FirebaseImageLoader(context);
        this.lowStockThreshold = lowStockThreshold;
    }

    public void submitList(List<CatalogProduct> newProducts) {
        products.clear();
        products.addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        CatalogProduct product = products.get(position);
        holder.txtProductName.setText(product.getName());
        holder.txtProductCategory.setText(product.getCategoryName().isEmpty() ? product.getCategoryId() : product.getCategoryName());
        holder.txtProductPrice.setText(currencyFormat.format(product.getPrice()));
        bindStock(context, holder.txtStockBadge, product);
        imageLoader.load(holder.imgProduct, product.getImageUrl(), product.getImagePath());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void bindStock(Context context, TextView badge, CatalogProduct product) {
        StockStatus status = stockStatus(product);
        if (status == StockStatus.OUT_OF_STOCK) {
            badge.setText(context.getString(R.string.catalog_stock_out));
            badge.setBackgroundResource(R.drawable.bg_stock_out);
        } else if (status == StockStatus.LOW_STOCK) {
            badge.setText(context.getString(R.string.catalog_stock_low) + " (" + product.getQuantity() + ")");
            badge.setBackgroundResource(R.drawable.bg_stock_low);
        } else {
            badge.setText(context.getString(R.string.catalog_stock_in) + " (" + product.getQuantity() + ")");
            badge.setBackgroundResource(R.drawable.bg_stock_in);
        }
    }

    private StockStatus stockStatus(CatalogProduct product) {
        if (product.getQuantity() <= 0) {
            return StockStatus.OUT_OF_STOCK;
        }
        if (product.getQuantity() < lowStockThreshold) {
            return StockStatus.LOW_STOCK;
        }
        return StockStatus.IN_STOCK;
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgProduct;
        final TextView txtProductName;
        final TextView txtProductCategory;
        final TextView txtProductPrice;
        final TextView txtStockBadge;

        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtProductName = itemView.findViewById(R.id.txtProductName);
            txtProductCategory = itemView.findViewById(R.id.txtProductCategory);
            txtProductPrice = itemView.findViewById(R.id.txtProductPrice);
            txtStockBadge = itemView.findViewById(R.id.txtStockBadge);
        }
    }
}
