package com.example.foodpantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {

    //variables for all input and buttons from register window
    EditText registerFullName, registerEmail,registerPassword,registerConformPassword;
    Button registerUserBtn,gotoLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //User authentication instance
        FirebaseAuth fAuth;

        //setting variables to the user input and button
        registerFullName=findViewById(R.id.registerFullName);
        registerEmail=findViewById(R.id.registerEmail);
        registerPassword=findViewById(R.id.registerPassword);
        registerConformPassword=findViewById(R.id.confPassword);
        registerUserBtn=findViewById(R.id.registerBtn);
        gotoLoginBtn=findViewById(R.id.gotoLoginBtn);

        //firebase initialization

        fAuth=FirebaseAuth.getInstance();

        gotoLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });

        //extracting and validating data from user only when they click on register button
        registerUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            //Converting and storing user inputs
            public void onClick(View v) {
                String FullName=registerFullName.getText().toString();
                String Email=registerEmail.getText().toString();
                String Password=registerPassword.getText().toString();
                String ConformPassword=registerConformPassword.getText().toString();

                //checking for empty inputs
                if(FullName.isEmpty()){
                    registerFullName.setError("Please Enter Full Name");
                    return;
                }
                if(Email.isEmpty()){
                    registerEmail.setError("Please Enter Email Address");
                    return;
                }
                if(Password.isEmpty()){
                    registerPassword.setError("Please Enter Password");
                    return;
                }
                if(ConformPassword.isEmpty()){
                    registerConformPassword.setError("Please Enter Conformation Password");
                    return;
                }

                //checking for matching password
                if(!Password.equals(ConformPassword)){
                    registerConformPassword.setError("Please Enter Matching Password");
                    return;
                }

                //
                Toast.makeText(Register.this, "Validating Your Information",Toast.LENGTH_SHORT).show();

                fAuth.createUserWithEmailAndPassword(Email,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //redirect user after registration
                        Toast.makeText(Register.this, "You have been Registered",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText( Register.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}