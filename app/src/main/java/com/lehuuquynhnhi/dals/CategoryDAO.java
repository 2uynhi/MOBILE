package com.lehuuquynhnhi.dals;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lehuuquynhnhi.database.DbHelper;
import com.lehuuquynhnhi.models.Category;

import java.util.ArrayList;

public class CategoryDAO {
    private DbHelper dbHelper;

    public CategoryDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);
        
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            categories.add(new Category(id, name));
        }
        cursor.close();
        return categories;
    }
}
