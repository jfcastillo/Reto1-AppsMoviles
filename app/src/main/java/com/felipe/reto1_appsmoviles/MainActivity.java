package com.felipe.reto1_appsmoviles;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private AddPlaceFragment addplace;
    private MapFragment mapFragment;
    private SearchPlacesFragment searchPlacesFragment;
    private BottomNavigationView navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addplace = AddPlaceFragment.newInstance();
        mapFragment = MapFragment.newInstance();
        searchPlacesFragment = SearchPlacesFragment.newInstance();

        navigator = findViewById(R.id.navigator);
        navigator.setOnNavigationItemSelectedListener(
                (menuItem ) ->{
                    switch (menuItem.getItemId()){
                        case R.id.newPlace:
                            showFragment(addplace);
                            break;
                        case R.id.map:
                            showFragment(mapFragment);
                            break;
                        case R.id.search:
                            showFragment(searchPlacesFragment);
                            break;
                    }

                    return true;

                }
        );

        showFragment(addplace);
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}