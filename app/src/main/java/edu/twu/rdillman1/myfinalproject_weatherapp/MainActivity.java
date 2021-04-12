package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity  {
    private FirebaseAuth mAuth;

    // Add code here to register the listener with the Location Manager to receive location updates
    LocationManager locationManager;
   // private static final int TAKE_PHOTO_PERMISSION = 1;
    LocationListener locationListener;
    Double currentLat;
    Double currentLon;
    TextView tv_name;
    TextView tv_location;
    String cityID ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
// Get city from Splash Screen
        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
     //Get data from google auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        // Set ID for Location and name
        tv_name = findViewById(R.id.welcomeID);
        tv_location = findViewById(R.id.tv_locationName);
        //set text
        tv_name.setText(user.getDisplayName());
        Spinner s = (Spinner) findViewById(R.id.settings);
       tv_location.setText(bundle.getString("cityLocation"));

        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="https://www.metaweather.com/api/location/search/?query=";
//
//// Request a string response from the provided URL.
//        JsonArrayRequest jRequest = new JsonArrayRequest(Request.Method.GET, url+city,null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//
//                try {
//                    JSONObject cityInfo = response.getJSONObject(0);
//                    cityID = cityInfo.getString("woeid");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//  Toast.makeText(getApplicationContext(),cityID,Toast.LENGTH_LONG).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();
//
//            }
//        });
//        queue.add(jRequest);




       //spinner class selections
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                switch (selected){
                    case "Settings":
                        //do nothing
                        break;
                        //LOGOUT CASE
                    case "Logout":
                        mAuth.signOut();
                        Intent loginIntent = new Intent(MainActivity.this,Spash_Activity.class);
                        MainActivity.this.startActivity(loginIntent);
                        MainActivity.this.finish();
                        break;
                        //GRAB CITY ID CASE
                    case "Get City ID":
                        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                        String url ="https://www.metaweather.com/api/location/search/?query=";

// Request a string response from the provided URL.
                        JsonArrayRequest jRequest = new JsonArrayRequest(Request.Method.GET, url+city,null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {

                                try {
                                    JSONObject cityData = response.getJSONObject(0);
                                    cityID = cityData.getString("woeid");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getApplicationContext(),cityID,Toast.LENGTH_LONG).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_LONG).show();

                            }
                        });
                        queue.add(jRequest);
                        break;
                        //CHANGE LOCATION CASE
                    case "Change Location":
                        Toast.makeText(getApplicationContext(),"I am testing option 2",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}
