package com.example.delish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MealDatabaseHelper extends SQLiteOpenHelper {
    //Table Name
    public static final String MEAL_TABLE = "MEALS";

    //Table columns
    public static final String ID = "ID";
    public static final String DAY = "DAY";
    public static final String MONTH = "MONTH";
    public static final String YEAR = "YEAR";
    public static final String MEAL = "MEAL";   //formatted as "food1,amount;food2,amount;food3,amount;"

    //Database Information
    static final String MEAL_DB_NAME = "MEALS.DB";

    //database version
    static final int MEAL_DB_VERSION = 1;

    private static MealDatabaseHelper instance = null;
    private FoodDatabaseHelper foodDatabaseHelper;

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMealTable = "CREATE TABLE " + MEAL_TABLE + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DAY + " INTEGER, " + MONTH + " INTEGER, " + YEAR + " INTEGER, " + MEAL + " TEXT NOT NULL)";
        db.execSQL(createMealTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MEAL_TABLE);
        onCreate(db);
    }

    public static synchronized MealDatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new MealDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private MealDatabaseHelper(Context context) {
        super(context, MEAL_DB_NAME, null, MEAL_DB_VERSION);
        foodDatabaseHelper = FoodDatabaseHelper.getInstance(context);
    }

    public void insert(String meal, int day, int month, int year) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(DAY, day);
        contentValue.put(MONTH, month);
        contentValue.put(YEAR, year);
        contentValue.put(MEAL, meal);
        database.insert(MEAL_TABLE, null, contentValue);
        database.close();
    }

    public Map<List<Food>, Integer> getFromDate(int day, int month, int year) {
        Map<List<Food>, Integer> meals = new HashMap<List<Food>, Integer>();
        String query = "SELECT * FROM " + MEAL_TABLE + " WHERE " + DAY + " = " + day + " AND " + MONTH + " = " + month + " AND " + YEAR + " = " + year;
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        for(int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            String[] mealString = cursor.getString(4).split(";");
            List<Food> meal = new ArrayList<Food>();
            int mealCalories = 0;
            for(String foodString : mealString) {
                String name = foodString.split(",")[0];
                int amount = Integer.parseInt(foodString.split(",")[1]);
                Food food = foodDatabaseHelper.getFromName(name);
                food.setAmount(amount);
                meal.add(food);
                mealCalories += food.getTotalCalories();
            }
            meals.put(meal, mealCalories);
        }
        return meals;
    }

    public void delete(int day, int month, int year) {
        String query = "DELETE FROM " + MEAL_TABLE + " WHERE " + DAY + " = " + day + " AND " + MONTH + " = " + month + " AND " + YEAR + " = " + year;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.close();
        database.close();
    }
}