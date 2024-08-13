package com.app.feepay.LoginClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.feepay.MainActivity;
import com.app.feepay.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    TextView TXTsignup;
    Button btnLogin;
    EditText LoginPhoneNumber,editLoginPassword;
    ProgressBar LoginProgressBar;

    private static String CHILD_NAME,CHILD_CLASS,CHILD_MEDIUM,SCHOOL_NAME,CHILD_FEE,CHILD_ADDRESS,CHILD_EMAIL;

    // Defining a Functions For Validation

    private boolean ValidPhoneNumber() {
        String value = LoginPhoneNumber.getEditableText().toString().trim();
        if (value.isEmpty()) {
            LoginPhoneNumber.setError("Phone Number Cannot be empty");
            return false;
        } else if (value.length() != 10) {
            LoginPhoneNumber.setError("Enter 10 digit Phone Number ");
            return false;
        } else {
            LoginPhoneNumber.setError(null);
//            editRegisterChildName.setErrorEnabled(false);
            return true;
        }
    }


    private boolean ValidPassword() {
        String value = editLoginPassword.getEditableText().toString().trim();

        if (value.isEmpty()) {
            editLoginPassword.setError("Password Cannot be empty");
            return false;
        } else {
            editLoginPassword.setError(null);
//            editRegisterChildName.setErrorEnabled(false);
            return true;
        }
    }


    public void LoginUser(){
        //Validating Login Info
        if (ValidPhoneNumber() & ValidPassword()) {
            isUser();
        }
        else{
            btnLogin.setVisibility(View.VISIBLE);
            LoginProgressBar.setVisibility(View.INVISIBLE);
            return;
        }
    }

    private void isUser() {
        String UserEnteredNumber = LoginPhoneNumber.getEditableText().toString().trim();
        String UserEnteredPass = editLoginPassword.getEditableText().toString().trim();

        DatabaseReference Reference = FirebaseDatabase.getInstance("https://feepay-cf15d-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("Users");
        Query CheckUser = Reference.orderByChild("phone").equalTo(UserEnteredNumber);

        CheckUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                //For Removing Error Msg if User Re-enters The Details
                LoginPhoneNumber.setError(null);


                if (snapshot.exists()){

                    //For Removing Error Msg if User Re-enters The Details
                    editLoginPassword.setError(null);

                    String PassFromDB = snapshot.child(UserEnteredNumber).child("password").getValue(String.class);
                    if (PassFromDB.equals(UserEnteredPass)){

                        CHILD_NAME = snapshot.child(UserEnteredNumber).child("childName").getValue(String.class);
                        SCHOOL_NAME = snapshot.child(UserEnteredNumber).child("schoolName").getValue(String.class);
                        CHILD_FEE = snapshot.child(UserEnteredNumber).child("feeAmount").getValue(String.class);
                        CHILD_CLASS = snapshot.child(UserEnteredNumber).child("childClass").getValue(String.class);
                        CHILD_MEDIUM = snapshot.child(UserEnteredNumber).child("medium").getValue(String.class);
                        CHILD_ADDRESS = snapshot.child(UserEnteredNumber).child("address").getValue(String.class);
                        CHILD_EMAIL = snapshot.child(UserEnteredNumber).child("address").getValue(String.class);
                        CHILD_ADDRESS = snapshot.child(UserEnteredNumber).child("address").getValue(String.class);


                        SharedPreferences pref = getSharedPreferences("Login", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        editor.putString("UserPhone",UserEnteredNumber);
                        editor.putString("childName",CHILD_NAME);
                        editor.putString("schoolName",SCHOOL_NAME);
                        editor.putString("feeAmount",CHILD_FEE);
                        editor.putString("childClass",CHILD_CLASS);
                        editor.putString("childMedium",CHILD_MEDIUM);
                        editor.putString("childAddress",CHILD_ADDRESS);
                        editor.putString("childEmail",CHILD_EMAIL);
                        editor.apply();

                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        editLoginPassword.setError("Wrong Password");
                        editLoginPassword.requestFocus();

                        btnLogin.setVisibility(View.VISIBLE);
                        LoginProgressBar.setVisibility(View.INVISIBLE);
                    }

                } else {
                    LoginPhoneNumber.setError("No Such User Exists");
                    LoginPhoneNumber.requestFocus();

                    btnLogin.setVisibility(View.VISIBLE);
                    LoginProgressBar.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                btnLogin.setVisibility(View.VISIBLE);
                LoginProgressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        Declaring Hooks For the Variables
        TXTsignup = findViewById(R.id.TXTsignup);
        btnLogin = findViewById(R.id.btnLogin);
        LoginPhoneNumber = findViewById(R.id.LoginPhoneNumber);
        editLoginPassword = findViewById(R.id.editLoginPassword);
        LoginProgressBar = findViewById(R.id.LoginProgressBar);



        //for changing Activity (Login --> Register)
        TXTsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, RegisterPhone.class));
                finish();
            }
        });

        //for changing Activity (Login --> MainActivity)
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (LoginUser()) {
//                    startActivity(new Intent(Login.this, MainActivity.class));
//                    finish();
//                }

                btnLogin.setVisibility(View.INVISIBLE);
                LoginProgressBar.setVisibility(View.VISIBLE);

                LoginUser();

            }
        });

    }
}