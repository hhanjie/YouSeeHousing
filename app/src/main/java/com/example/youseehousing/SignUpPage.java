package com.example.youseehousing;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);
    }

    public void createAccountonClick(View view) {
        //Intent myIntent = new Intent(SignUpPage.this, MainHousingListing.class);
        Intent myIntent = new Intent(SignUpPage.this, AccountCreation.class);

        startActivity(myIntent);
    }
}
