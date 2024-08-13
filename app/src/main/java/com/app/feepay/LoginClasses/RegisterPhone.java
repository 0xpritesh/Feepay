package com.app.feepay.LoginClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.feepay.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class RegisterPhone extends AppCompatActivity {

    //    Declaring Variables
    Button btnSendOTP;
    ImageView backArrowImg;
    EditText editRegisterPhone, editRegisterConfirmPassword, editRegisterPassword;
    ProgressBar RegisterProgressBar;
    TextView TXTsignin;

    // Defining a Functions For Validation

    private boolean ValidPhoneNumber() {
        String value = editRegisterPhone.getEditableText().toString().trim();
        if (value.isEmpty()) {
            editRegisterPhone.setError("Phone Number Cannot be empty");
            return false;
        } else if (value.length() != 10) {
            editRegisterPhone.setError("Enter 10 digit Phone Number ");
            return false;
        } else {
            editRegisterPhone.setError(null);
//            editRegisterChildName.setErrorEnabled(false);
            return true;
        }
    }


    private boolean ValidPassword() {
        String value = editRegisterPassword.getEditableText().toString().trim();
        String passPattern = "^" +
                "(?=.*[0-9])" +    //represents a digit must occur at least once.
                //  "(?=.*[a-z])" +  //represents a lower case alphabet must occur at least once.
                //  "(?=.*[A-Z])" +  //represents an upper case alphabet that must occur at least
                "(?=.*[A-Za-z])" + //represents any letters can be Used.
                //"(?=.*[@#$%^&-+=()])" + //represents a special character that must occur at least once.
                "(?=\\S+$)" +      // white spaces don’t allowed in the entire string.
                ".{8,20}" +       // represents at least 8 characters and at most 20 characters.
                "$";
        if (value.isEmpty()) {
            editRegisterPassword.setError("Child Name Cannot be empty");
            return false;
        } else if (!value.matches(passPattern)) {
            editRegisterPassword.setError("Password must contains 8 characters and has atleast one number and no Spaces");
            return false;
        } else {
            editRegisterPassword.setError(null);
//            editRegisterChildName.setErrorEnabled(false);
            return true;
        }
    }


    private boolean ConfirmPassword() {
        String value = editRegisterConfirmPassword.getEditableText().toString().trim();
        String value1 = editRegisterPassword.getEditableText().toString().trim();
        if (value.isEmpty()) {
            editRegisterConfirmPassword.setError("Child Name Cannot be empty");
            return false;
        } else if (!value.equals(value1)) {
            editRegisterConfirmPassword.setError("Password Does not Matched!");
            return false;
        } else {
            editRegisterConfirmPassword.setError(null);
//            editRegisterChildName.setErrorEnabled(false);
            return true;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_phone);

//        finding Ids of Fields
        btnSendOTP = findViewById(R.id.btnSendOTP);
        backArrowImg = findViewById(R.id.backArrowImg);
        editRegisterPhone = findViewById(R.id.editRegisterPhone);
        editRegisterPassword = findViewById(R.id.editRegisterPassword);
        editRegisterConfirmPassword = findViewById(R.id.editRegisterConfirmPassword);
        RegisterProgressBar = findViewById(R.id.RegisterProgressBar);
        TXTsignin = findViewById(R.id.TXTsignin);




        //for changing Activity (Register --> Login)
        TXTsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterPhone.this, Login.class));
                finish();
            }
        });


        //for changing Activity (RegisterPhone --> VerifyOTP)
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidPhoneNumber() & ValidPassword() & ConfirmPassword()) {

                    RegisterProgressBar.setVisibility(View.VISIBLE);
                    btnSendOTP.setVisibility(View.INVISIBLE);






                    // Verificaion Process for sending Otp
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91" + editRegisterPhone.getText().toString().trim(),
                            60,
                            TimeUnit.SECONDS,
                            RegisterPhone.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    RegisterProgressBar.setVisibility(View.VISIBLE);
                                    btnSendOTP.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    RegisterProgressBar.setVisibility(View.INVISIBLE);
                                    btnSendOTP.setVisibility(View.VISIBLE);
                                    Toast.makeText(RegisterPhone.this, "Registration Failed! Please Check Your Connection : " + e, Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onCodeSent(@NonNull String FireBaseBackendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                                    RegisterProgressBar.setVisibility(View.VISIBLE);
                                    btnSendOTP.setVisibility(View.INVISIBLE);

                                    Intent intent = new Intent(RegisterPhone.this, VerifyOTP.class);
                                    intent.putExtra("Phone Number", editRegisterPhone.getText().toString().trim());
                                    intent.putExtra("User Password", editRegisterPassword.getText().toString().trim());
                                    intent.putExtra("FireBaseBackendOTP", FireBaseBackendOTP);

                                    startActivity(intent);
                                    finish();
                                }
                            }
                    );

                }
            }
        });


        //for changing Activity (Register2 --> Register1)
        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterPhone.this, Login.class));
                finish();
            }
        });
    }
}