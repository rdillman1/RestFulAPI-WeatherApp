package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.Locale;

public class Spash_Activity extends AppCompatActivity implements LocationListener{
    private final int SPLASH_DISPLAY_LENGTH = 10000;
    private FirebaseAuth mAuth;
    LocationManager locationManager;
    // private static final int TAKE_PHOTO_PERMISSION = 1;
    LocationListener locationListener;
    Double currentLat;
    Double currentLon;
    public String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
//Check permissions
        if (Build.VERSION.SDK_INT >= 27 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
// Get location
        getLocation();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(user != null){
                    Intent mainIntent = new Intent(Spash_Activity.this,MainActivity.class);
                    mainIntent.putExtra("cityLocation",city);
                    Spash_Activity.this.startActivity(mainIntent);
                    Spash_Activity.this.finish();
                }else{
                    Intent loginIntent = new Intent(Spash_Activity.this,Login_Activity.class);
                    loginIntent.putExtra("cityLocation",city);
                    Spash_Activity.this.startActivity(loginIntent);
                    Spash_Activity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
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
            city = address;
            Toast.makeText(getApplicationContext()," "+ address,Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}