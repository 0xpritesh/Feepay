package com.app.feepay;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.app.feepay.JavaClasses.ViewPagerTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tab;
    ViewPager viewPager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpager);

        ViewPagerTabAdapter tabAdapter = new ViewPagerTabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);

        tab.setupWithViewPager(viewPager); // this to sync viewpager to tabs



    }
}