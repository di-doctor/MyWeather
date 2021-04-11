package com.example.myweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbWeatherHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    public static final String TABLE_NAME = "cityNameTemp";
    public static final String CITY = "city";
    public static final String ID = "id";
    public static final String TEMPERATURE = "temperature";
    public static final String ICON = "iconId";


    public DbWeatherHelper(@Nullable Context context) {
        super(context, "weather.db", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + CITY + " TEXT, " + ID + " INTEGER PRIMARY KEY, " + TEMPERATURE + " REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int newVersion) {
        if (old < 2) {
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + ICON + " INTEGER");
        }
    }
}
