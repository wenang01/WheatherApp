package com.imamajie.wheaterapp2.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WeatherDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "weatheroffline.db";
    private static final int DATABASE_VERSION = 1;

    public WeatherDataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){

        String sql_weather_by_city = "create table weather_by_city(id Integer primary key, " +
                                                                    "city_name text null, " +
                                                                    "country_name text null, " +
                                                                    "tempr real null, " +
                                                                    "fellslike real null, " +
                                                                    "humidity real null," +
                                                                    "description text null, " +
                                                                    "wind real null, " +
                                                                    "clouds real null, " +
                                                                    "presure real null)";
        Log.d("Data", "OnCreate: "+sql_weather_by_city);
        db.execSQL(sql_weather_by_city);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2){

    }
}
