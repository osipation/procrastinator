package com.osipation.procrastinator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
        if (fragment != null) {
            if (fragment.equals("notification")) {
                TableFragment tableFragment = new TableFragment();
                tableFragment.setArguments(new Bundle());
                replaceFragment(R.id.container, tableFragment, false);
            }
            }else {
            TimePickerFragment timePickerFragment = new TimePickerFragment();
            replaceFragment(R.id.container, timePickerFragment, true);
        }


    }

    private void replaceFragment(int containerId, Fragment fragment, boolean addToBackStack){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction()
                .replace(containerId, fragment);
        if(addToBackStack) ft.addToBackStack(fragment.getClass().getSimpleName());
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


}
