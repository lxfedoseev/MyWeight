package com.example.alexfed.myweight;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "weightDB";
 
    // Weight table name
    private static final String TABLE_WEIGHTS = "wights";
 
    // Weight Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_DATE = "date";
    private static final String KEY_WEIGHT = "weight";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_WEIGHTS_TABLE = "CREATE TABLE " + TABLE_WEIGHTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_WEIGHT + " TEXT" + ")";
        db.execSQL(CREATE_WEIGHTS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHTS);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */
 
    // Adding new weight
    void addWeight(WeightEntry w) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, w.getDate()); 
        values.put(KEY_WEIGHT, w.getWeight()); 
 
        // Inserting Row
        db.insert(TABLE_WEIGHTS, null, values);
        db.close(); // Closing database connection
    }
 
    // Getting single weight entry
    WeightEntry getWeight(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_WEIGHTS, new String[] { KEY_ID,
                KEY_DATE, KEY_WEIGHT }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        WeightEntry w = new WeightEntry(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return weight
        return w;
    }
 
    // Getting All weights
    public List<WeightEntry> getAllWeight() {
        List<WeightEntry> weightList = new ArrayList<WeightEntry>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEIGHTS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
            	WeightEntry w = new WeightEntry();
                w.setID(Integer.parseInt(cursor.getString(0)));
                w.setDate(cursor.getString(1));
                w.setWeight(cursor.getString(2));
                // Adding weight to list
                weightList.add(w);
            } while (cursor.moveToNext());
        }
 
        // return weight list
        return weightList;
    }
 
    // Updating single weight entry
    public int updateWeight(WeightEntry w) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_DATE, w.getDate());
        values.put(KEY_WEIGHT, w.getWeight());
 
        // updating row
        return db.update(TABLE_WEIGHTS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(w.getID()) });
    }
 
    // Deleting single weight entry
    public void deleteWeight(WeightEntry w) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEIGHTS, KEY_ID + " = ?",
                new String[] { String.valueOf(w.getID()) });
        db.close();
    }
 
    // Getting weights Count
    public int getWeightsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_WEIGHTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int ret = cursor.getCount();
        cursor.close();
 
        // return count
        return ret;
    }
 
}



