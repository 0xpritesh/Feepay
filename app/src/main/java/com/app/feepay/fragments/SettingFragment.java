package com.app.feepay.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.app.feepay.LoginClasses.Login;
import com.app.feepay.R;
import com.app.feepay.UpdateProfile;

public class SettingFragment extends Fragment {


    TextView SignOutTXT,UpdateProfileTXT;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_setting, container, false);

        SignOutTXT = root.findViewById(R.id.SignOutTXT);
        UpdateProfileTXT = root.findViewById(R.id.UpdateProfileTXT);

        UpdateProfileTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateProfile.class);
                startActivity(intent);
            }
        });


        SignOutTXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences pref = getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();

                editor.clear();
                editor.apply();

                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);

            }
        });


        return root;
    }


}