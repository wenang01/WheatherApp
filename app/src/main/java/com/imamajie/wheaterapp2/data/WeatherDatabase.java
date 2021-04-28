package com.imamajie.wheaterapp2.data;

import android.provider.BaseColumns;

public class WeatherDatabase {
    public static final class WeatherEntry implements BaseColumns{
        public static final String TABLE_NAME = "Weather";
        public static final String _ID = "id";
        public static final String COLUMN_COUNTRY_NAME = "Country_Name";
        public static final String COLUMN_TEMPERATURE = "Temperature";
        public static final String COLUMN_FEELSLIKE = "Feelslike";
        public static final String COLUMN_HUMIDITY = "Humidity";
        public static final String COLUMN_DESCRIPTION = "Description";
        public static final String COLUMN_WIND = "Wind";
        public static final String COLUMN_CLOUDS = "Clouds";
        public static final String COLUMN_PRESURE = "Presure";


    }
}
