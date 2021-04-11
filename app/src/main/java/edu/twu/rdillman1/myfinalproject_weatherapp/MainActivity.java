package edu.twu.rdillman1.myfinalproject_weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
public TextView tv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        tv_name = findViewById(R.id.welcomeID);
        tv_name.setText(user.getDisplayName());
        Spinner s =(Spinner) findViewById(R.id.settings);
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
                        Intent loginIntent = new Intent(MainActivity.this,Login_Activity.class);
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
                    default:
                       //do nothing
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }
}