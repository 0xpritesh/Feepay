package com.app.feepay.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.app.feepay.PaymentHistory;
import com.app.feepay.R;


public class PaymentFragment extends Fragment {

    AppCompatButton btnMakePayment,btnPaymentHistory;
    ProgressBar PaymentProgressBar,PaymentHistoryProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_payment, container, false);

        btnMakePayment = root.findViewById(R.id.btnMakePayment);
        PaymentProgressBar = root.findViewById(R.id.PaymentProgressBar);
        btnPaymentHistory = root.findViewById(R.id.btnPaymentHistory);
        PaymentHistoryProgressBar = root.findViewById(R.id.PaymentHistoryProgressBar);


        // Make Payment Button Implementation Code
        btnMakePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnMakePayment.setVisibility(View.INVISIBLE);
                PaymentProgressBar.setVisibility(View.VISIBLE);


            }
        });

        PaymentProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentProgressBar.setVisibility(View.INVISIBLE);
                btnMakePayment.setVisibility(View.VISIBLE);
            }
        });

        // Payment History Button Implementation Code
        btnPaymentHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPaymentHistory.setVisibility(View.INVISIBLE);
                PaymentHistoryProgressBar.setVisibility(View.VISIBLE);

                Intent i = new Intent(getActivity(),PaymentHistory.class);
                startActivity(i);

            }
        });

        PaymentHistoryProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentHistoryProgressBar.setVisibility(View.INVISIBLE);
                btnPaymentHistory.setVisibility(View.VISIBLE);
            }
        });




        return root;
    }

}