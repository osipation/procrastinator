package com.osipation.procrastinator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String fragment = getIntent().getStringExtra("fragment");

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.container, timePickerFragment)
//                .addToBackStack(null)
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//                .commit();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragment != null) {
            if (fragment.equals("notification")) {
                TableFragment tableFragment = new TableFragment();
                fragmentTransaction.replace(R.id.container, tableFragment)
                        .addToBackStack(null)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
            }else {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            fragmentTransaction.add(R.id.container, timePickerFragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }


    }


}
