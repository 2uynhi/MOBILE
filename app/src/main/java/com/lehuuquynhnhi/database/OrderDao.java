package com.lehuuquynhnhi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lehuuquynhnhi.models.Order;
import com.lehuuquynhnhi.models.OrderStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderDao {
    private DbHelper dbHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

    public OrderDao(Context context) {
        dbHelper = new DbHelper(context);
    }

    // Phương thức thêm mới một hóa đơn
    public long insertOrder(Order order) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("orderId", order.getOrderId());
        values.put("customerId", order.getCustomerId());
        values.put("employeeId", order.getEmployeeId());
        values.put("orderDate", sdf.format(order.getOrderDate()));
        values.put("orderStatus", order.getOrderStatus().toString());
        
        long result = db.insert("Orders", null, values);
        db.close();
        return result;
    }

    // Phương thức lấy tất cả hóa đơn
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Orders", null);

        if (cursor.moveToFirst()) {
            do {
                try {
                    String id = cursor.getString(0);
                    String cusId = cursor.getString(1);
                    String empId = cursor.getString(2);
                    Date date = sdf.parse(cursor.getString(3));
                    OrderStatus status = OrderStatus.valueOf(cursor.getString(4));

                    list.add(new Order(id, cusId, empId, date, status));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // Phương thức lọc hóa đơn theo ngày và trạng thái
    public ArrayList<Order> filterOrders(String status, String fromDate, String toDate) {
        ArrayList<Order> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        
        // Query SQLite sử dụng hàm date() để so sánh
        String query = "SELECT * FROM Orders WHERE date(orderDate) BETWEEN ? AND ?";
        ArrayList<String> args = new ArrayList<>();
        args.add(fromDate);
        args.add(toDate);

        if (!status.equals("ALL")) {
            query += " AND orderStatus = ?";
            args.add(status);
        }

        Cursor cursor = db.rawQuery(query, args.toArray(new String[0]));
        // ... (xử lý cursor tương tự getAllOrders)
        if (cursor.moveToFirst()) {
            do {
                try {
                    list.add(new Order(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        sdf.parse(cursor.getString(3)),
                        OrderStatus.valueOf(cursor.getString(4))
                    ));
                } catch (Exception e) { e.printStackTrace(); }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}
