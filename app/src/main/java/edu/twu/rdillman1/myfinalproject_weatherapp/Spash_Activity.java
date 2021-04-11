package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Spash_Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 8000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spash_);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(user != null){
                    Intent mainIntent = new Intent(Spash_Activity.this,MainActivity.class);
                    Spash_Activity.this.startActivity(mainIntent);
                    Spash_Activity.this.finish();
                }else{
                    Intent loginIntent = new Intent(Spash_Activity.this,Login_Activity.class);
                    Spash_Activity.this.startActivity(loginIntent);
                    Spash_Activity.this.finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}