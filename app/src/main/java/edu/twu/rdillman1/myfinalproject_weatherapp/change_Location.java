package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class change_Location extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change__location);
        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
        TextView et_changeLocation;
        Button btn_saved;
        Button btn_back;
        TextView curCity;
        curCity = findViewById(R.id.currentCity);
        curCity.setText(bundle.getString("cityLocation"));

        et_changeLocation = findViewById(R.id.et_city);
         btn_saved = findViewById(R.id.save);
         btn_back = findViewById(R.id.back);
        btn_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(change_Location.this,MainActivity.class);
                String city = et_changeLocation.getText().toString();
                mainIntent.putExtra("cityLocation",city);
                change_Location.this.startActivity(mainIntent);
                change_Location.this.finish();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = curCity.getText().toString();
                Intent backIntent = new Intent(change_Location.this,MainActivity.class);
                backIntent.putExtra("cityLocation",city);
                startActivity(backIntent);
                change_Location.this.finish();

            }
        });
    }
}