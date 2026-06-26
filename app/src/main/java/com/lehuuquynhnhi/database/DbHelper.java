package com.lehuuquynhnhi.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "k234111Esales.sqlite";
    public static final int DB_VERSION = 1;

    public DbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Category
        db.execSQL("CREATE TABLE Category (" +
                "categoryId TEXT PRIMARY KEY, " +
                "categoryName TEXT)");

        // Tạo bảng Product
        db.execSQL("CREATE TABLE Product (" +
                "productId TEXT PRIMARY KEY, " +
                "productName TEXT, " +
                "price REAL, " +
                "quantity INTEGER, " +
                "coupon REAL, " +
                "VAT REAL, " +
                "categoryId TEXT, " +
                "FOREIGN KEY(categoryId) REFERENCES Category(categoryId))");

        // Tạo bảng Customer
        db.execSQL("CREATE TABLE Customer (" +
                "customerId TEXT PRIMARY KEY, " +
                "customerName TEXT, " +
                "phoneNumber TEXT, " +
                "email TEXT, " +
                "address TEXT)");

        // Tạo bảng Employee
        db.execSQL("CREATE TABLE Employee (" +
                "employeeId TEXT PRIMARY KEY, " +
                "employeeName TEXT, " +
                "phoneNumber TEXT, " +
                "birthplace TEXT)");

        // Tạo bảng Orders
        db.execSQL("CREATE TABLE Orders (" +
                "orderId TEXT PRIMARY KEY, " +
                "customerId TEXT, " +
                "employeeId TEXT, " +
                "orderDate TEXT, " +
                "orderStatus TEXT, " +
                "FOREIGN KEY(customerId) REFERENCES Customer(customerId), " +
                "FOREIGN KEY(employeeId) REFERENCES Employee(employeeId))");

        // Tạo bảng OrderDetail
        db.execSQL("CREATE TABLE OrderDetail (" +
                "detailId TEXT PRIMARY KEY, " +
                "orderId TEXT, " +
                "productId TEXT, " +
                "quantity INTEGER, " +
                "price REAL, " +
                "coupon REAL, " +
                "VAT REAL, " +
                "FOREIGN KEY(orderId) REFERENCES Orders(orderId), " +
                "FOREIGN KEY(productId) REFERENCES Product(productId))");

        seedData(db);
    }

    private void seedData(SQLiteDatabase db) {
        // Dữ liệu mẫu cho Category
        db.execSQL("INSERT INTO Category (categoryId, categoryName) VALUES ('C1', 'Điện thoại')");
        db.execSQL("INSERT INTO Category (categoryId, categoryName) VALUES ('C2', 'Laptop')");

        // Nhập dữ liệu sản phẩm theo yêu cầu: sản phẩm, giá, số lượng, coupon, VAT, categoryID
        db.execSQL("INSERT INTO Product (productId, productName, price, quantity, coupon, VAT, categoryId) " +
                "VALUES ('P1', 'iPhone 15 Pro', 30000000, 10, 500000, 0.1, 'C1')");
        db.execSQL("INSERT INTO Product (productId, productName, price, quantity, coupon, VAT, categoryId) " +
                "VALUES ('P2', 'Samsung S24 Ultra', 28000000, 15, 1000000, 0.1, 'C1')");
        db.execSQL("INSERT INTO Product (productId, productName, price, quantity, coupon, VAT, categoryId) " +
                "VALUES ('P3', 'MacBook M3', 45000000, 5, 2000000, 0.1, 'C2')");
        db.execSQL("INSERT INTO Product (productId, productName, price, quantity, coupon, VAT, categoryId) " +
                "VALUES ('P4', 'Dell XPS 13', 35000000, 8, 1500000, 0.1, 'C2')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS OrderDetail");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS Employee");
        db.execSQL("DROP TABLE IF EXISTS Customer");
        db.execSQL("DROP TABLE IF EXISTS Product");
        db.execSQL("DROP TABLE IF EXISTS Category");
        onCreate(db);
    }
}
