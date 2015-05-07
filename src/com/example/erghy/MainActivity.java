package com.example.erghy;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity{
	 
    Button login;
    Button signup;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Er3'y"); 
        // Buttons
        login = (Button) findViewById(R.id.Login);
        signup = (Button) findViewById(R.id.signup1);
 
        // Login click event
        login.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching All Accounts Activity
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
 
            }
        });
 
        // Signup click event
        signup.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
                // Launching create new product activity
                Intent i = new Intent(getApplicationContext(), NewAccountActivity.class);
                startActivity(i);
 
            }
        });
    }
}