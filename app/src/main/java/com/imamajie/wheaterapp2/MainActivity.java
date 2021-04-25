package com.imamajie.wheaterapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    EditText etCity, etCountry;
    TextView tvResult, textView;
    Button btnloc;
    FusedLocationProviderClient fusedLocationProviderClient;

    private final String url = "https://api.openweathermap.org/data/2.5/";
    private final String appid = "d940c4e17f65b1374292e212fb63c2f8";
    DecimalFormat df = new DecimalFormat(  "#.##");

    //https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCity = findViewById(R.id.etCity);
        etCountry = findViewById(R.id.etCountry);
        tvResult = findViewById(R.id.tvResult);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        btnloc = findViewById(R.id.btnGetLoc);
        textView = findViewById(R.id.location_text);
        btnloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        fusedLocationProviderClient.getLastLocation()
                                .addOnSuccessListener(new OnSuccessListener<Location>() {
                                    @Override
                                    public void onSuccess(Location location) {
                                        String tempUrl = "";
                                        if (location != null) {
                                            Double lat = location.getLatitude();
                                            Double lon = location.getLongitude();

                                            tempUrl = url + "onecall?lat=" + lat + "&lon=" + lon + "&cnt=7&appid=" + appid;
                                            //textView.setText(lat +" , "+lon);
                                            Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl,
                                                    new Response.Listener<String>(){
                                                @Override
                                                public void onResponse(String response) {
                                                Log.d( "response", response);
                                                    String output = "";

                                                    try {
                                                        JSONObject jsonResponse = new JSONObject(response);
                                                        String timezone = jsonResponse.getString("timezone");
                                                        JSONArray jsonArray = jsonResponse.getJSONArray("daily");
//                                                        JSONObject jsonObjectWeather = jsonResponse.getJSONObject("daily");
//                                                        JSONArray jsonWeather = jsonArray.getJSONArray(14);
                                                        tvResult.setTextColor(Color.rgb(68,134,199));
                                                        output += "Current weather of " + lat + " (" + lon + ")"
                                                                + "\n Time Zone : "+ timezone + "\n";

                                                        for (int i = 0; i < jsonArray.length(); i++){
                                                            JSONObject jsonObjectDaily = jsonArray.getJSONObject(i);
                                                            JSONArray jsonObjectWeather = jsonObjectDaily.getJSONArray("weather");
                                                            JSONObject jsonObjectdescription = jsonObjectWeather.getJSONObject(0);
                                                            String description = jsonObjectdescription.getString("description");
                                                            Long dt = jsonObjectDaily.getLong("dt");
                                                            long dv = Long.valueOf(dt)*1000;// its need to be in milisecond
                                                            Date df = new java.util.Date(dv);
                                                            String vv = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);
                                                            Double clouds = jsonObjectDaily.getDouble("clouds");


                                                                   output += "\n Date : " + vv
                                                                           + "\n Weather : " + description
                                                                            + "\n Cloudiness : " + clouds + "% \n";
                                                            tvResult.setText(output);
                                                        }
//                                                        JSONObject jsonObjectDaily = jsonArray.getJSONObject(0);
//                                                        Double clouds = jsonObjectDaily.getDouble("clouds");
//                                                        JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
//                                                        double temp = jsonObjectMain.getDouble("temp") - 273.15;
//                                                        double feelsLike = jsonObjectMain.getDouble("temp") - 273.25;
//                                                        float pressure = jsonObjectMain.getInt("pressure");
//                                                        int humidity = jsonObjectMain.getInt("humidity");
//                                                        JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
//                                                        String wind = jsonObjectWind.getString("speed");
//                                                        JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
//                                                        String clouds = jsonObjectClouds.getString("all");
//                                                        JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");

//                                                        tvResult.setTextColor(Color.rgb(68,134,199));
//                                                        output += "Current weather of " + lat + " (" + lon + ")"
//                                                                + "\n Temp: " + df.format(temp) + " C"
//                                                                + "\n Feels Like: " + df.format(feelsLike) + " C"
//                                                                + "\n Humidity: " + humidity + "%"
//                                                                + "\n Description: " + description
//                                                                + "\n Wind Speed: " + wind + "m/s (meter per second)"
//                                                                + "\n Cloudiness: " + clouds + "%";
//                                                                + "\n Pressure: " + pressure + " hPa";
//                                                        tvResult.setText(output);

                                                    }catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                            requestQueue.add(stringRequest);

                                        }
                                    }
                                });

                    } else {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    }
                }
            }});
    }



    public void getWeatherDetails(View View) {
        String tempUrl = "";
        String city = etCity.getText().toString().trim();
        String country = etCountry.getText().toString().trim();
        if (city.equals("")){
            tvResult.setText("City field can not be empty!");
        }else{
            if(!country.equals("")){
                tempUrl = url + "weather" + "?q=" + city + "," + country + "&appid=" + appid;
            }else{
                tempUrl = url + "weather" + "?q=" + city + "&appid=" + appid;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                    Log.d( "response", response);
                        String output = "";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp") - 273.15;
                            double feelsLike = jsonObjectMain.getDouble("temp") - 273.25;
                            float pressure = jsonObjectMain.getInt("pressure");
                            int humidity = jsonObjectMain.getInt("humidity");
                            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                            String clouds = jsonObjectClouds.getString("all");
                            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                            String countryName = jsonObjectSys.getString("country");
                            String cityName = jsonResponse.getString("name");
                            tvResult.setTextColor(Color.rgb(68,134,199));
                            output += "Current weather of " + cityName + " (" + countryName + ")"
                                    + "\n Temp: " + df.format(temp) + " C"
                                    + "\n Feels Like: " + df.format(feelsLike) + " C"
                                    + "\n Humidity: " + humidity + "%"
                                    + "\n Description: " + description
                                    + "\n Wind Speed: " + wind + "m/s (meter per second)"
                                    + "\n Cloudiness: " + clouds + "%"
                                    + "\n Pressure: " + pressure + " hPa";
                            tvResult.setText(output);

                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    public void getWeatherByLocation(View view){

    }
}