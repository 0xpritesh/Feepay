package com.app.feepay.LoginClasses;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.feepay.JavaClasses.RegistrationUserDataHelperClass;
import com.app.feepay.MainActivity;
import com.app.feepay.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterUserData extends AppCompatActivity {


    Button btnSendOTP;
    EditText editRegisterChildName, editRegisterFeeAmount, editRegisterAddress, editRegisterEmail, editRegisterSchoolName;
    Spinner ClassSpinner, MediumSpinner;

    //Declaring ArrayList<> For both Spinners
    ArrayList<String> classList = new ArrayList<>();
    ArrayList<String> mediumList = new ArrayList<>();


    //Declaring Variables For DataBase
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    // Defining a Functions For Validation

    private boolean ValidChildName() {
        String value = editRegisterChildName.getEditableText().toString().trim();
        if (value.isEmpty()) {
            editRegisterChildName.setError("Child Name Cannot be empty");
            return false;
        } else {
            editRegisterChildName.setError(null);
            return true;
        }
    }


    private boolean ValidSchoolName() {
        String value = editRegisterSchoolName.getEditableText().toString().trim();
        if (value.isEmpty()) {
            editRegisterSchoolName.setError("School Name Cannot be empty");
            return false;
        } else {
            editRegisterSchoolName.setError(null);
            return true;
        }
    }


    private boolean ValidEmail() {
        String value = editRegisterEmail.getText().toString().trim();
//        String emailPattern = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$";
        if (value.isEmpty()) {
            editRegisterEmail.setError("Email Cannot be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            editRegisterEmail.setError("Enter a Valid Email Address");
            return false;
        } else {
            editRegisterEmail.setError(null);
            return true;
        }
    }


    private boolean ValidAddress() {
        String value = editRegisterAddress.getEditableText().toString().trim();
        if (value.isEmpty()) {
            editRegisterAddress.setError("Address Cannot be empty");
            return false;
        } else {
            editRegisterAddress.setError(null);
            return true;
        }
    }


    private boolean ValidFeeAmount() {
        String value = editRegisterFeeAmount.getEditableText().toString().trim();
        if (value.isEmpty()) {
            editRegisterFeeAmount.setError("Fee Cannot be empty");
            return false;
        } else {
            editRegisterFeeAmount.setError(null);
            return true;
        }
    }


    private boolean ValidClassSpinner() {
        String value = ClassSpinner.getSelectedItem().toString();
        if (value.equals("Select Class")) {
            Toast.makeText(RegisterUserData.this,"Please Select Class", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    private boolean ValidMediumSpinner() {
        String value = MediumSpinner.getSelectedItem().toString();
        if (value.equals("Select Medium")) {
            Toast.makeText(RegisterUserData.this,"Please Select Medium", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_userdata);

        //Declaring HOOKS of all Fields
        ClassSpinner = findViewById(R.id.Classspinner);
        MediumSpinner = findViewById(R.id.Mediumspinner);
        btnSendOTP = findViewById(R.id.btnRegister);
        editRegisterChildName = findViewById(R.id.editRegisterChildName);
        editRegisterSchoolName = findViewById(R.id.editRegisterSchoolName);
        editRegisterEmail = findViewById(R.id.editRegisterEmail);
        editRegisterAddress = findViewById(R.id.editRegisterAddress);
        editRegisterFeeAmount = findViewById(R.id.editRegisterFeeAmount);


        //for changing Activity (Register1 --> Register2)
        btnSendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ValidChildName() & ValidEmail() & ValidSchoolName() & ValidAddress() & ValidFeeAmount() & ValidClassSpinner() & ValidMediumSpinner()) {
                    try {


                        //Getting All Strings for Pushing it to Database
                        String ChildName = editRegisterChildName.getEditableText().toString().trim();
                        String SchoolName = editRegisterSchoolName.getEditableText().toString().trim();
                        String Email = editRegisterEmail.getText().toString().trim();
                        String Address = editRegisterAddress.getEditableText().toString().trim();
                        String FeeAmount = editRegisterFeeAmount.getEditableText().toString().trim();
                        String Class = ClassSpinner.getSelectedItem().toString();
                        String Medium = MediumSpinner.getSelectedItem().toString();
                        String PhoneNo = getIntent().getStringExtra("phoneNo");


                        firebaseDatabase = FirebaseDatabase.getInstance("https://feepay-cf15d-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        databaseReference = firebaseDatabase.getReference().child("Users");

                        //Instance For UserDataHelperClass and passing all Fields
                        //direct passing getExtra of Password
                        RegistrationUserDataHelperClass UserDataHandler = new RegistrationUserDataHelperClass(
                                ChildName,SchoolName,Email,Address,FeeAmount,Class,Medium ,PhoneNo,getIntent().getStringExtra("Psd")
                        );

                        //Setting Values in DataBase using databaseReference
                        databaseReference.child(PhoneNo).setValue(UserDataHandler)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {





                                startActivity(new Intent(RegisterUserData.this, MainActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(RegisterUserData.this,"Failed to Register User" , Toast.LENGTH_SHORT).show();
                                    }
                                });


                        //if all Registration is Successful then Change to MainActivity

                    } catch (Exception e) {
                        Toast.makeText(RegisterUserData.this,"Error occurred While Registering" , Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });


        // For classSpinner
        classList.add(0, "Select Class");
        classList.add("Jr.KG");
        classList.add("Sr.KG");
        classList.add("1");
        classList.add("2");
        classList.add("3");
        classList.add("4");
        classList.add("5");
        classList.add("6");
        classList.add("7");
        classList.add("8");
        classList.add("9");
        classList.add("10");
        classList.add("11");
        classList.add("12");

        ArrayAdapter<String> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classList);
        ClassSpinner.setAdapter(classAdapter);




        //For mediumSpinner
        mediumList.add(0, "Select Medium");
        mediumList.add("English");
        mediumList.add("Gujarati");

        ArrayAdapter<String> mediumAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mediumList);
        MediumSpinner.setAdapter(mediumAdapter);



    }
}
