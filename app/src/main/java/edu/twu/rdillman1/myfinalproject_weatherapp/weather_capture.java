package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class weather_capture extends AppCompatActivity {
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_capture);
        Bundle bundle = getIntent().getExtras();
        String city = bundle.getString("cityLocation");
        Button btn_saved;
        Button btn_back;
        btn_saved = findViewById(R.id.save);
        btn_back = findViewById(R.id.back);
        image = findViewById(R.id.captureimage);

        //capture permission
        if (ContextCompat.checkSelfPermission(weather_capture.this, Manifest.permission.CAMERA)
        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(weather_capture.this,new String[]{
                    Manifest.permission.CAMERA
            },100);
        }
        btn_saved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    Intent capture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(capture, 100);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backIntent = new Intent(weather_capture.this,MainActivity.class);
                backIntent.putExtra("cityLocation",city);
                startActivity(backIntent);
                weather_capture.this.finish();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( requestCode ==100){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image.setImageBitmap(bitmap);
        }
    }
}