package com.example.myweather;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class MainApplication extends Application {



    private static MainApplication instance;
    public static synchronized MainApplication getInstance(){
        return  instance;
    }
    private SQLiteDatabase db;

    public SQLiteDatabase getDb(){
        return db;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        db.close();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        DbWeatherHelper dbHelper = new DbWeatherHelper(getBaseContext());
        try {
            db = dbHelper.getWritableDatabase();
        }
        catch (SQLiteException ex){
            db = dbHelper.getReadableDatabase();
        }

//        db = getBaseContext().openOrCreateDatabase("CityTemp.db", MODE_PRIVATE, null);
//        db.execSQL("CREATE TABLE IF NOT EXISTS cityNameTemp (city TEXT, id INTEGER PRIMARY KEY, temperature REAL, iconId TEXT)");

    }



}
