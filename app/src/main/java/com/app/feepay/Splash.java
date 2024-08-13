package com.app.feepay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.app.feepay.LoginClasses.Login;


public class Splash extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                SharedPreferences pref = getSharedPreferences("Login",MODE_PRIVATE);

                String check = pref.getString("UserPhone", null);



                if (check != null){


                    Intent mainIntent = new Intent(Splash.this, MainActivity.class);
                    startActivity(mainIntent);
                    Splash.this.finish();
                }else{
                    Intent mainIntent = new Intent(Splash.this, Login.class);
                    startActivity(mainIntent);
                    Splash.this.finish();
                }

                /* Create an Intent that will start the Menu-Activity. */



            }

        }, SPLASH_DISPLAY_LENGTH);
    }
}