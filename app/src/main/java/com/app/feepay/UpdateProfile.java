package com.app.feepay;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateProfile extends AppCompatActivity {

    EditText FullName,SchoolName,childClass,Address;
    Button btnUpdate;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    String _childName,_schoolName, _Fee, _ChildClass, _address,_PhoneNumber;

    boolean DataUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

//        Defining Hooks
        FullName = findViewById(R.id.FullName);
        SchoolName = findViewById(R.id.SchoolName);
        childClass = findViewById(R.id.childClass);
        Address = findViewById(R.id.Address);

        btnUpdate = findViewById(R.id.btnUpdate);






        SharedPreferences pref = getSharedPreferences("Login", UpdateProfile.MODE_PRIVATE);
        _childName = pref.getString("childName", null);
        _schoolName = pref.getString("schoolName", null);
        _Fee = pref.getString("feeAmount", null);
        _ChildClass = pref.getString("childClass", null);
        String _ChildMedium = pref.getString("childMedium", null);
        _address = pref.getString("childAddress", null);
        _PhoneNumber = pref.getString("UserPhone",null);

//        setting Data to fields from Shared Preference
        FullName.setText(_childName);
        SchoolName.setText(_schoolName);
        childClass.setText(_ChildClass);
        Address.setText(_address);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseDatabase = FirebaseDatabase.getInstance("https://feepay-cf15d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                databaseReference = firebaseDatabase.getReference().child("Users").child(_PhoneNumber);

//                if(isNameChanged() && isClassChanged() && isFeeChanged() && isAddressChanged() && isSchoolNameChanged()){}
                isNameChanged();
                isClassChanged();
                isAddressChanged();
                isSchoolNameChanged();
                if (DataUpdated == true) {
                    Toast.makeText(UpdateProfile.this, "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                    DataUpdated = false;
                }else{
                    Toast.makeText(UpdateProfile.this, "Same data Can't be Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }

    private boolean isNameChanged() {
        if(!FullName.getText().toString().trim().equals(_childName)){

            databaseReference.child("childName").setValue(FullName.getText().toString().trim());
            SharedPreferences pref = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("childName",FullName.getText().toString().trim());
            editor.apply();

            DataUpdated = true;
            return true;

        }else{
            return false;
        }
    }

    private boolean isAddressChanged() {
        if(!Address.getText().toString().trim().equals(_address)){

            databaseReference.child("address").setValue(Address.getText().toString().trim());
            SharedPreferences pref = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("childAddress",Address.getText().toString().trim());
            editor.apply();

            DataUpdated = true;
            return true;

        }else{
            return false;
        }
    }



    private boolean isClassChanged() {
        if(!childClass.getText().toString().trim().equals(_ChildClass)){

            databaseReference.child("childClass").setValue(childClass.getText().toString().trim());
            SharedPreferences pref = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("childClass",childClass.getText().toString().trim());
            editor.apply();

            DataUpdated = true;
            return true;

        }else{
            return false;
        }
    }

    private boolean isSchoolNameChanged() {
        if(!SchoolName.getText().toString().trim().equals(_schoolName)){

            databaseReference.child("schoolName").setValue(SchoolName.getText().toString().trim());
            SharedPreferences pref = getSharedPreferences("Login", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("schoolName",SchoolName.getText().toString().trim());
            editor.apply();

            DataUpdated = true;
            return true;

        }else{
            return false;
        }
    }
}