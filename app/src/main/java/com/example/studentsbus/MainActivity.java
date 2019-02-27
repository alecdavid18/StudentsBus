package com.example.studentsbus;

import android.content.Intent;

import android.os.SystemClock;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login=(Button)findViewById(R.id.LoginButton);
        final TextView forgotpassword=(TextView)findViewById(R.id.ForgotPasswordTextView);
        final Intent changepassword=new Intent(MainActivity.this,ChangePasswordActivity.class);
        final Intent i= new Intent(MainActivity.this,Logedin.class);

        final EditText username=(EditText)findViewById(R.id.UsernameEditText);
        final EditText password=(EditText)findViewById(R.id.PasswordEditText);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();





        //login button working
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String TypedUsername=username.getText().toString();
                final String TypedPassword=password.getText().toString();


                myRef.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(TypedUsername.equals(myRef.child("users").child(TypedUsername).getKey())) {

                            String passcode = dataSnapshot.child(TypedUsername).child("password").getValue().toString();

                            if (TypedPassword.equals(passcode)) {
                                startActivity(i);
                            } else {
                                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });



        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(changepassword);
            }
        });






    }
}
