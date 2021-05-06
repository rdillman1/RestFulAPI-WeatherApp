package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class weather_by_date extends AppCompatActivity {
    WeatherDataService weatherDataService = new WeatherDataService(weather_by_date.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_by_date);
        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
        TextView et_changeLocation;
        TextView et_changeDate;
        TextView tv_temp,tv_max,tv_min,tv_wind,tv_humid;
        Button btn_saved;
        Button btn_back;

        et_changeLocation = findViewById(R.id.et_city);
        et_changeDate = findViewById(R.id.et_date);
        btn_saved = findViewById(R.id.save);
        btn_back = findViewById(R.id.back);
        ImageView image;

        tv_temp = findViewById(R.id.tv_temp);
        tv_max = findViewById(R.id.tv_high);
        tv_min = findViewById(R.id.tv_low);
        tv_wind = findViewById(R.id.et_wind);
        tv_humid = findViewById(R.id.tv_humid);
        image = findViewById(R.id.dateImage);


        et_changeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(),"Format must me YYYY/MM/DD",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(weather_by_date.this,MainActivity.class);
                backIntent.putExtra("cityLocation",city);
                startActivity(backIntent);
                weather_by_date.this.finish();
            }
        });
        btn_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataService.getCityID(et_changeLocation.getText().toString(), new WeatherDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getApplicationContext(), "City ID something failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(String theCityID) {
                        weatherDataService.getByDate(theCityID,et_changeDate.getText().toString(), new WeatherDataService.ForcastResponse() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(getApplicationContext(),"soemthing went wrong",Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onResponse(WeatherModel weatherModel) {
                              //  Toast.makeText(getApplicationContext(),weatherModel.toString(),Toast.LENGTH_LONG).show();
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
                                tv_temp.setText(currentTemp);
                                tv_min.setText(currentMin);
                                tv_max.setText(currentMax);
                                tv_humid.setText(currentHumid);
                            }
                        });
                    }
                });
                    }
        });
    }
}