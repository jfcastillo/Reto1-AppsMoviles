package com.felipe.reto1_appsmoviles;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
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
        navigator = findViewById(R.id.navigator);

        ActivityCompat.requestPermissions(this, new String []{ //Ask for permissions
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
        }, 11);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 11){
            //Intent intent = new Intent(this, MapsActivity.class);
            //startActivity(intent);



            searchPlacesFragment = SearchPlacesFragment.newInstance();
            addplace = AddPlaceFragment.newInstance();
            addplace.setMainActivity(this);
            addplace.setObserver(searchPlacesFragment);
            mapFragment = MapFragment.newInstance();
            mapFragment.setObserver(addplace);
            showFragment(addplace);


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
        }
    }

    public void showFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    public MapFragment getMapFragment() {
        return mapFragment;
    }

    public void setMapFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }
}