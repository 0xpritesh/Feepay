package com.app.feepay.LoginClasses;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.feepay.R;
import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class VerifyOTP extends AppCompatActivity {

    ImageView backArrowImg;
    Button btnVerifyOTP;
    PinView pinView;
    TextView setPhoneNumberTXT, ResendOtpTXT;
    ProgressBar VerifyProgressBar;
    private String GetOTPBackend;



    //    Defining Function For PinView Validation
    private Boolean ValidateUserOTP() {
        String value = pinView.getText().toString().trim();
        if (value.length() != 6) {
            Toast.makeText(VerifyOTP.this, "Enter Correct 6 Digit OTP", Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

//        Declaring Hooks For All Fields
        backArrowImg = findViewById(R.id.backArrowImg);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        pinView = findViewById(R.id.pinView);
        setPhoneNumberTXT = findViewById(R.id.setPhoneNumberTXT);
        VerifyProgressBar = findViewById(R.id.VerifyProgressBar);
        ResendOtpTXT = findViewById(R.id.ResendOtpTXT);


//        Setting Phone Number in TextView
        setPhoneNumberTXT.setText(
                String.format("+91-%s", getIntent().getStringExtra("Phone Number"))
        );

//        Getting Backend OTP in this Activity Using getIntent()
        GetOTPBackend = getIntent().getStringExtra("FireBaseBackendOTP");


        //Cancle Image On Click Function
        backArrowImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VerifyOTP.this, RegisterPhone.class));
                finish();
            }
        });


        // ResendOtpTXT OnClick Listner Function Implimentation
        ResendOtpTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // coping all logic code of Generating OTP From FireBase from RegisterUserData Activity

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + setPhoneNumberTXT.getText().toString().trim(),
                        60,
                        TimeUnit.SECONDS,
                        VerifyOTP.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {

                                Toast.makeText(VerifyOTP.this, "Failed To Resend OTP, Check Your Connection", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String NewFireBaseBackendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                GetOTPBackend = NewFireBaseBackendOTP;
                                Toast.makeText(VerifyOTP.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }
        });



        //VerifyOTP Button On Click Listener

        btnVerifyOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidateUserOTP()) {

                    if (GetOTPBackend != null) {
                        VerifyProgressBar.setVisibility(View.VISIBLE);
                        btnVerifyOTP.setVisibility(View.INVISIBLE);

                        PhoneAuthCredential PhoneAuthCredential = PhoneAuthProvider.getCredential(
                                GetOTPBackend, pinView.getText().toString()
                        );

                        FirebaseAuth.getInstance().signInWithCredential(PhoneAuthCredential).addOnCompleteListener(
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        VerifyProgressBar.setVisibility(View.GONE);
                                        btnVerifyOTP.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()) {
                                            Intent intent = new Intent(VerifyOTP.this, RegisterUserData.class);
                                            intent.putExtra("phoneNo",getIntent().getStringExtra("Phone Number"));
                                            intent.putExtra("Psd",getIntent().getStringExtra("User Password"));
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(VerifyOTP.this, "Some Error Occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                        );

                    } else {
                        Toast.makeText(VerifyOTP.this, "Please Check The Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


    }
}