package com.example.delish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FoodInventoryDatabaseHelper extends SQLiteOpenHelper {
    //Table Name
    public static final String FOOD_INVENTORY_TABLE_NAME = "FOOD_INVENTORY";

    //Table columns
    public static final String FOOD_NAME = "FOOD_NAME";
    public static final String FOOD_CALORIES = "FOOD_CALORIES";

    //Database Information
    static final String FOOD_INVENTORY_DB_NAME = "FOOD_INVENTORY.DB";

    //database version
    static final int FOOD_INVENTORY_VERSION = 1;

    private static FoodInventoryDatabaseHelper instance = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + FOOD_INVENTORY_TABLE_NAME + " (" + FOOD_NAME + " TEXT PRIMARY KEY, " + FOOD_CALORIES + " INTEGER DEFAULT 0)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_INVENTORY_TABLE_NAME);
        onCreate(db);
    }

    public static synchronized FoodInventoryDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new FoodInventoryDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private FoodInventoryDatabaseHelper(Context context) {
        super(context, FOOD_INVENTORY_DB_NAME, null, FOOD_INVENTORY_VERSION);
    }

    public void insert(String name, int calories) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(FOOD_NAME, name);
        contentValue.put(FOOD_CALORIES, calories);
        database.insert(FOOD_INVENTORY_TABLE_NAME, null, contentValue);
        database.close();
    }

    public boolean contains(String name) {
        return getFromName(name) != null;
    }

    public Food getFromName(String name) {
        String query = "SELECT * FROM " + FOOD_INVENTORY_TABLE_NAME + " WHERE " + FOOD_NAME + " = '" + name + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        Food food = null;
        if(cursor.moveToFirst()) {
            food = new Food(name, cursor.getInt(1));
        }
        database.close();
        return food;
    }

    public void delete(String name) {
        String query = "DELETE FROM " + FOOD_INVENTORY_TABLE_NAME + " WHERE " + FOOD_NAME + " = '" + name + "'";
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        database.close();
    }
}