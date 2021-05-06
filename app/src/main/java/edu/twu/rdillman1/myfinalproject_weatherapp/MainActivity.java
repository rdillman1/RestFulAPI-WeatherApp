package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    //initialize weather service
    WeatherDataService weatherDataService = new WeatherDataService(MainActivity.this);
    // Add code here to register the listener with the Location Manager to receive location updates
    LocationManager locationManager;
    // private static final int TAKE_PHOTO_PERMISSION = 1;
    LocationListener locationListener;
    Double currentLat;
    Double currentLon;
    TextView tv_name;
    TextView tv_location;
    TextView tv_temp;
    TextView tv_state;
    TextView tv_max;
    TextView tv_min;
    TextView tv_wind;
    TextView tv_humid;
    public String cityID;
   ImageView image;
    int res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
// Get city from Splash Screen
        //Get data from google auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        // Set ID for Location and name
        tv_name = findViewById(R.id.welcomeID);
        tv_location = findViewById(R.id.tv_locationName);
        tv_temp = findViewById(R.id.tempF);
        tv_max = findViewById(R.id.tempHigh);
        tv_min = findViewById(R.id.tempLow);
        tv_state = findViewById(R.id.weatherState);
        tv_wind = findViewById(R.id.wind);
        tv_humid = findViewById(R.id.humid);
        image = findViewById(R.id.imageView4);
        //set text
        tv_name.setText(user.getDisplayName());
        Spinner s = (Spinner) findViewById(R.id.settings);
        tv_location.setText(bundle.getString("cityLocation"));


//Toast.makeText(getApplicationContext(),city,Toast.LENGTH_LONG).show();
        weatherDataService.getCityID(tv_location.getText().toString(), new WeatherDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), "City ID something failed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(String theCityID) {
        weatherDataService.getForcast(theCityID, new WeatherDataService.ForcastResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(),"soemthing went wrong",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(WeatherModel weatherModel) {
                String ist = weatherModel.toStringImage();
                switch (ist){
                    case "c":
                        image.setImageResource(R.drawable.sunny);
                        break;
                    case "lc":
                        image.setImageResource(R.drawable.cloudy);
                        break;
                    case "hc":
                        image.setImageResource(R.drawable.heavycloud);
                        break;
                    case "s":
                        image.setImageResource(R.drawable.showers);
                        break;
                    case "lr":
                        image.setImageResource(R.drawable.rain);
                        break;
                    case "hr":
                        image.setImageResource(R.drawable.heavyrain);
                        break;
                    case "t":
                        image.setImageResource(R.drawable.thunderstorm);
                        break;
                    case "h":
                        image.setImageResource(R.drawable.hail);
                        break;
                    case "sl":
                        image.setImageResource(R.drawable.snow);
                        break;
                    case "sn":
                        image.setImageResource(R.drawable.snow);
                        break;
                }
                String state = weatherModel.toStringState();
                double tempTEMP = weatherModel.toStringtemp() * 1.8;
                tempTEMP = tempTEMP + 32;
                long tempF = Math.round(tempTEMP*10)/10;
                double tempTEMPmax = weatherModel.toStringHigh() * 1.8;
                tempTEMPmax = tempTEMPmax +32;
                long tempMax = Math.round(tempTEMPmax*10)/10;
                String currentMax = String.valueOf(tempMax);
                double tempTEMPmin = weatherModel.toStringLow() * 1.8;
                tempTEMPmin = tempTEMPmin +32;
                long tempMin = Math.round(tempTEMPmin*10)/10;
                String currentMin = String.valueOf(tempMin);
                String currentTemp = String.valueOf(tempF);
                Float windSpeed = weatherModel.toStringWind();
                String currentWind = String.valueOf(windSpeed);
                int humidity = weatherModel.toStringHumid();
                String currentHumid = String.valueOf(humidity);
                tv_wind.setText(currentWind);
                tv_state.setText(state);
                tv_temp.setText(currentTemp);
                tv_min.setText(currentMin);
                tv_max.setText(currentMax);
                tv_humid.setText(currentHumid);
            }
        });
            }
        });
//spinner class selections
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                switch (selected) {
                    case "Settings":
                        //do nothing
                        break;
                    //LOGOUT CASE
                    case "Logout":
                        mAuth.signOut();
                        Intent loginIntent = new Intent(MainActivity.this, Spash_Activity.class);
                        MainActivity.this.startActivity(loginIntent);
                        MainActivity.this.finish();
                        break;
                    //GRAB CITY ID CASE
                    case "Get City ID":
                        weatherDataService.getCityID(tv_location.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(), "City ID something failed", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(String theCityID) {

                                Toast.makeText(getApplicationContext(), "City ID for " + city + " is " + theCityID, Toast.LENGTH_LONG).show();
                            }
                        });
                        s.setSelection(0);
                        break;
                    //CHANGE LOCATION CASE
                    case "Change Location":
                        s.setSelection(0);
                        Intent change = new Intent(MainActivity.this,change_Location.class);
                        change.putExtra("cityLocation",city);
                        MainActivity.this.startActivity(change);
                        MainActivity.this.finish();
                        break;
                    case "Weather by Date":
                        s.setSelection(0);
                        Intent byDate = new Intent(MainActivity.this,weather_by_date.class);
                        byDate.putExtra("cityLocation",city);
                        MainActivity.this.startActivity(byDate);
                        MainActivity.this.finish();
                        break;
                    case "Refresh":
                        finish();
                        startActivity(getIntent());
                        s.setSelection(0);
                        Toast.makeText(getApplicationContext(), "Page Refreshed", Toast.LENGTH_LONG).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}