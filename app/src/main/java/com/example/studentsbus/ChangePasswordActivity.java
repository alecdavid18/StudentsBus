package com.example.studentsbus;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        final Intent back= new Intent(ChangePasswordActivity.this,MainActivity.class);
        final Button submit=(Button)findViewById(R.id.SubmitButton);
        final  Button secquest=(Button)findViewById(R.id.SecurityButton);
        final EditText username=(EditText)findViewById(R.id.UsernameEditText);
        final  EditText password=(EditText)findViewById(R.id.PasswordEditText);
        final  EditText repassword=(EditText)findViewById(R.id.ReEnterPassEditText);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference();
        final TextView secques=(TextView)findViewById(R.id.SecurityQuestionTextView);
        final EditText answer=(EditText)findViewById(R.id.AnswerEditText);

        secquest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username.getText().toString()).exists()) {


                            secques.setText(dataSnapshot.child(username.getText().toString()).child("securityquestion").getValue().toString());


                        }
                        else
                        {
                            Toast.makeText(ChangePasswordActivity.this, "You have not subscribed to bus facility", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(username.getText().toString()).exists())
                        {
                            if(password.getText().toString().equals(repassword.getText().toString()))
                            {

                                if(answer.getText().toString().equals(dataSnapshot.child(username.getText().toString()).child("security answer").getValue()))
                                {
                                    myRef.child("users").child(username.getText().toString()).child("password").setValue(password.getText().toString());
                                    startActivity(back);
                                }
                                else{
                                    Toast.makeText(ChangePasswordActivity.this, "Wrong Answer", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(ChangePasswordActivity.this, "Answers are case sensitive", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else
                            {
                                Toast.makeText(ChangePasswordActivity.this, "Password Does Not Match", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });

    }
}
