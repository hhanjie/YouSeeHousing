package com.example.youseehousing.lib.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youseehousing.MainActivity;
import com.example.youseehousing.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class UserPreferences extends AppCompatActivity {

    private static final String TAG = "UserProfile";
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String Uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        //declare all the variables
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        FirebaseUser user = mAuth.getCurrentUser();
        Uid = user.getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Log.d(TAG, "User: " + user.getEmail() + " is signed in");
                } else{
                    Log.d(TAG,"unsuccessful sign in");
                }
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
                Log.d(TAG, "showData called");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Failed to read value.");
            }
        });
    }


    private void showData(DataSnapshot dataSnapshot) {
        for(DataSnapshot ds : dataSnapshot.getChildren()){
            Account uInfo = new Account();
           // Log.d(TAG,"Before set name: " + ds.child(Uid).getValue(Account.class).getName());
            uInfo.setName( ds.child(Uid).getValue(Account.class).getName()); //set the name
            uInfo.setEmail(ds.child(Uid).getValue(Account.class).getEmail()); //set the email
            uInfo.setFavorites(ds.child(Uid).getValue(Account.class).getFavorites()); // set the favorites
            Log.d(TAG, "name:" + ds.child(Uid).getValue());
            //display all the information
            Log.d(TAG, "showData: name: " + uInfo.getName());
            Log.d(TAG, "showData: email: " + uInfo.getEmail());
            Log.d(TAG, "showData: favorites: " + uInfo.getFavorites());

            final TextView name = (TextView) findViewById(R.id.name);
            name.setText(uInfo.getName());
            final TextView userEmail = (TextView) findViewById(R.id.email);
            userEmail.setText(uInfo.getEmail());

        }
    }

    public void doneUserPreferencesOnClick(View view) {
        Intent myIntent = new Intent(UserPreferences.this, MainActivity.class);
        startActivity(myIntent);
        Toast.makeText(getApplicationContext(), "Please log in",
                Toast.LENGTH_LONG).show();
    }
}
