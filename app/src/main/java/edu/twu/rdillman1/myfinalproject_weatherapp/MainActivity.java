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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
       // Toast.makeText(getApplicationContext()," "+city, Toast.LENGTH_LONG).show();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        tv_name = findViewById(R.id.welcomeID);
        tv_location = findViewById(R.id.tv_locationName);
        tv_name.setText(user.getDisplayName());
        Spinner s = (Spinner) findViewById(R.id.settings);
       tv_location.setText(bundle.getString("cityLocation"));
/*
//Check permissions
        if (Build.VERSION.SDK_INT >= 27 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
// Get location
    getLocation();*/
//setting dropdown menu
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected = parent.getItemAtPosition(position).toString();
                switch (selected){
                    case "Settings":
                        //do nothing
                        break;
                    case "Logout":
                        mAuth.signOut();
                        Intent loginIntent = new Intent(MainActivity.this,Spash_Activity.class);
                        MainActivity.this.startActivity(loginIntent);
                        MainActivity.this.finish();
                        break;
                    case "Option 1":
                        Intent testIntent = new Intent(MainActivity.this,Spash_Activity.class);
                        MainActivity.this.startActivity(testIntent);
                        MainActivity.this.finish();
                        break;
                    case "Option 2":
                        Toast.makeText(getApplicationContext(),"I am testing option 2",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







    }
/*
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,50,this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLon = location.getLongitude();
        currentLat = location.getLatitude();
        Toast.makeText(getApplicationContext()," "+ currentLat + currentLon,Toast.LENGTH_LONG).show();
   try{
       Geocoder geocoder = new Geocoder(this, Locale.getDefault());
       List<Address> addresses = geocoder.getFromLocation(currentLat, currentLon, 1);
       String address = addresses.get(0).getLocality();
       tv_location.setText(address);
       Toast.makeText(getApplicationContext()," "+ address,Toast.LENGTH_LONG).show();
   }catch (Exception e){
       e.printStackTrace();
   }
    }*/
}
