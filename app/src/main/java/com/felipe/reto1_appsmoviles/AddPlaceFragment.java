package com.felipe.reto1_appsmoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AddPlaceFragment extends Fragment implements  View.OnClickListener, MapFragment.OnMapListener {


    private ImageButton btnPinMap;
    private TextView addressTV;
    private Button btnRegister;
    private EditText editTextNamePlace;

    private MainActivity mainActivity;
    private LatLng latlong;
    private String address;
    private ArrayList<Place> places;



    public AddPlaceFragment() {
        // Required empty public constructor
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public static AddPlaceFragment newInstance() {
        AddPlaceFragment fragment = new AddPlaceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_place, container, false);
        btnPinMap = root.findViewById(R.id.btnPinMap);
        btnPinMap.setOnClickListener(this);
        btnRegister = root.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        addressTV = root.findViewById(R.id.adressTV);
        addressTV.setText(address);
        editTextNamePlace = root.findViewById(R.id.editTextNamePlace);
        loadPlaces();
        return root;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnPinMap:
                //MapFragment mapFragment = MapFragment.newInstance();
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, mapFragment).commit();
                mainActivity.showFragment(mainActivity.getMapFragment());

                break;
            case R.id.btnRegister:
                String name = editTextNamePlace.getText().toString();
                Log.e(">>>","Nombre "+name);
                double latitude = latlong.latitude;
                double longitude = latlong.longitude;

                places.add(new Place(name, 4.0, longitude, latitude, address));
                Gson gson = new Gson();
                String json = gson.toJson(places);
                SharedPreferences preferences = getActivity().getSharedPreferences("Places", Context.MODE_PRIVATE);
                preferences.edit().putString("placesList", json).apply();
                break;

            case R.id.imageView:

        }

    }

    @Override
    public void onNewMarker(LatLng latLng, String address) {

        latlong = latLng;
        this.address = address;
        mainActivity.showFragment(this);
    }

    public void loadPlaces(){

        Gson gson = new Gson();
        SharedPreferences preferences = getActivity().getSharedPreferences("Places", Context.MODE_PRIVATE);
        String json = preferences.getString("placesList", "NO_OBJ");
        if (!json.equals("NO_OBJ")){
            Type arrayListTypeToken = new TypeToken<ArrayList<Place>>(){}.getType();
            places = gson.fromJson(json, arrayListTypeToken);
        }
        else{
            Log.e(">>>","Inicializo AL");
            places = new ArrayList<>();
        }
    }
}