package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class weather_genie extends AppCompatActivity {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private static final int SHAKE_THRESHOLD = 5;
private String[] responseArray = {"Seems Cloudy","A storm's a brewin'", "A bright chance", "No",
        "yes", "Good weather ahead", "Bad weather ahead", "wouldn't want to be outside", "Feels right" };
    private double aCurrentValue;
    TextView response;
    private double aPreviousValue;
    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            aCurrentValue = Math.sqrt((x*x+y*y+z*z));
            double changeInValue = Math.abs(aCurrentValue - aPreviousValue);
            aPreviousValue = aCurrentValue;

            if (changeInValue > SHAKE_THRESHOLD){
int rand = new Random().nextInt(responseArray.length);
response.setText(responseArray[rand]);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_genie);
        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
        Button btn_back;
        btn_back = findViewById(R.id.back);
        response = findViewById(R.id.response);


        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        Toast toast = Toast.makeText(this, "Shake to see your prediction", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        View view = toast.getView();

        toast.show();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(weather_genie.this,MainActivity.class);
                backIntent.putExtra("cityLocation",city);
                startActivity(backIntent);
                weather_genie.this.finish();

            }
        });
    }
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(sensorEventListener, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(sensorEventListener);
    }

}