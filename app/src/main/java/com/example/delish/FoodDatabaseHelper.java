package com.example.delish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class FoodDatabaseHelper extends SQLiteOpenHelper {
    //Table Name
    public static final String FOOD_TABLE = "FOODS";

    //Table columns
    public static final String FOOD_NAME = "FOOD_NAME";
    public static final String FOOD_CALORIES = "FOOD_CALORIES";

    //Database Information
    private static final String FOOD_DB_NAME = "FOODS.DB";
    private static final int FOOD_DB_VERSION = 1;

    private static FoodDatabaseHelper instance = null;
    private static Context context = null;

    //this is called the first time a database is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create = "CREATE TABLE " + FOOD_TABLE + " (" + FOOD_NAME + " TEXT PRIMARY KEY, " + FOOD_CALORIES + " INTEGER)";
        db.execSQL(create);
    }

    //this is called if the database version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
        onCreate(db);
    }

    //makes FoodDatabaseHelper a singleton class (only one shared instance)
    public static synchronized FoodDatabaseHelper getInstance(Context c) {
        if (instance == null) {
            instance = new FoodDatabaseHelper(c.getApplicationContext());
            if(!instance.hasRows()) {
                instance.fillDatabase(c.getApplicationContext());
            }
        }
        return instance;
    }

    private FoodDatabaseHelper(Context c) {
        super(c, FOOD_DB_NAME, null, FOOD_DB_VERSION);
    }

    private boolean hasRows() {
        SQLiteDatabase database = getReadableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE;
        Cursor cursor = database.rawQuery(query, null);
        return cursor.moveToFirst();
    }

    private void fillDatabase(Context c) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document document = factory.newDocumentBuilder().parse(c.getResources().openRawResource(R.raw.food_inventory));
            Element rootElement = document.getDocumentElement();
            NodeList nodeList = rootElement.getElementsByTagName("food");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element node = (Element) nodeList.item(i);
                String name = node.getAttribute("name");
                if (!contains(name)) {
                    int calories = Integer.parseInt(node.getElementsByTagName("calories").item(0).getTextContent());
                    insert(name, calories);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void insert(String name, int calories) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(FOOD_NAME, name);
        contentValue.put(FOOD_CALORIES, calories);
        database.insert(FOOD_TABLE, null, contentValue);
        database.close();
    }

    public boolean contains(String name) {
        return getFromName(name) != null;
    }

    public Food getFromName(String name) {
        Food food = null;
        String query = "SELECT * FROM " + FOOD_TABLE + " WHERE " + FOOD_NAME + " = '" + name + "'";
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            food = new Food(name, cursor.getInt(1));
        }
        cursor.close();
        database.close();
        return food;
    }

    public void delete(String name) {
        String query = "DELETE FROM " + FOOD_TABLE + " WHERE " + FOOD_NAME + " = '" + name + "'";
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(query, null);
        cursor.close();
        database.close();
    }
}