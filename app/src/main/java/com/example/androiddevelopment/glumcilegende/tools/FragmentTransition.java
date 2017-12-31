package com.example.androiddevelopment.glumcilegende.tools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.androiddevelopment.glumcilegende.R;

/**
 * Created by BBLOJB on 24.12.2017..
 */

public class FragmentTransition {

    public static void to(Fragment newFragment, FragmentActivity activity){
        to(newFragment, activity, true);
    }

    public static void to(Fragment newFragment, FragmentActivity activity, boolean addToBackStack){
        FragmentTransaction transaction = activity.getSupportFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.mainContent, newFragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }
}
