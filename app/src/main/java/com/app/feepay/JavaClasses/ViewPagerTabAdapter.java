package com.app.feepay.JavaClasses;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.feepay.MainActivity;
import com.app.feepay.fragments.PaymentFragment;
import com.app.feepay.fragments.ProfileFragment;
import com.app.feepay.fragments.SettingFragment;

public class ViewPagerTabAdapter extends FragmentPagerAdapter {


    public ViewPagerTabAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new ProfileFragment();
        }
        else if(position==1){
            return new PaymentFragment();
        }
        else{
            return new SettingFragment();
        }
    }

    @Override
    public int getCount() {
        return 3; // There are three tabs
    }


    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return "Profile";
        }
        else if(position==1){
            return "Payment";
        }
        else{
            return "Setting";
        }
    }
}
