package com.lehuuquynhnhi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lehuuquynhnhi.models.Product;

import java.util.ArrayList;

public class ProductDao {
    private DbHelper dbHelper;

    public ProductDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Phương thức nhập dữ liệu sản phẩm
    public long insertProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("productId", product.getProductId());
        values.put("productName", product.getProductName());
        values.put("price", product.getPrice());
        values.put("quantity", product.getQuantity());
        values.put("coupon", product.getCoupon());
        values.put("VAT", product.getVAT());
        values.put("categoryId", product.getCategoryId());

        long result = db.insert("Product", null, values);
        db.close();
        return result;
    }

    // Lấy danh sách sản phẩm
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Product", null);

        if (cursor.moveToFirst()) {
            do {
                Product p = new Product();
                p.setProductId(cursor.getString(0));
                p.setProductName(cursor.getString(1));
                p.setPrice(cursor.getDouble(2));
                p.setQuantity(cursor.getInt(3));
                p.setCoupon(cursor.getDouble(4));
                p.setVAT(cursor.getDouble(5));
                p.setCategoryId(cursor.getString(6));
                list.add(p);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
