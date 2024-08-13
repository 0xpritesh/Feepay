package com.app.feepay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.feepay.JavaClasses.RecyclerViewAdapter;
import com.app.feepay.JavaClasses.TransactionData;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PaymentHistory extends AppCompatActivity {

    ImageButton DownloadHistoryImgbtn;
    RecyclerView HistoryRecyclerView;

    ArrayList<TransactionData> TransactionList;

    FirebaseDatabase firebase = FirebaseDatabase.getInstance("https://feepay-cf15d-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference databaseReference;
    RecyclerViewAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        DownloadHistoryImgbtn = findViewById(R.id.DownloadHistoryImgbtn);
        HistoryRecyclerView = findViewById(R.id.HistoryRecyclerView);

        SharedPreferences pref = getSharedPreferences("Login", Context.MODE_PRIVATE);

        databaseReference = firebase.getReference("Transactions").child(pref.getString("UserPhone", null));

        TransactionList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        HistoryRecyclerView.setLayoutManager(layoutManager);


        FirebaseRecyclerOptions<TransactionData> options =
                new FirebaseRecyclerOptions.Builder<TransactionData>()
                        .setQuery(databaseReference, TransactionData.class)
                        .build();


        adapter = new RecyclerViewAdapter(options,this);
        HistoryRecyclerView.setAdapter(adapter);


        adapter.notifyDataSetChanged();

//        adapter = new RecyclerViewAdapter(this, TransactionList);
//        HistoryRecyclerView.setAdapter(adapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot i : snapshot.getChildren()){

                    try {
                        String Id,datetime,amount;
                        Id = i.child("TransactionId").getValue(String.class);
                        datetime = i.child("DateTime").getValue(String.class);
                        amount = i.child("Amount").getValue(String.class);

//                        Toast.makeText(PaymentHistory.this, Id + datetime + amount, Toast.LENGTH_SHORT).show();

                    }catch (Exception e){
                        Toast.makeText(PaymentHistory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        DownloadHistoryImgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(PaymentHistory.this, MainActivity.class));
        finish();

    }



    // it starts fetching data on activity start
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    // it Stops fetching data on activity start
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }





}