package com.felipe.reto1_appsmoviles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;


public class AddPlaceFragment extends Fragment implements  View.OnClickListener, MapFragment.OnMapListener {


    private ImageButton btnPinMap;
    private TextView latlongTV;
    private Button btnRegister;

    private MainActivity mainActivity;
    private String latlong;
    private OnAddListener observer;



    public AddPlaceFragment() {
        // Required empty public constructor
    }


    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    // TODO: Rename and change types and number of parameters
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
        latlongTV = root.findViewById(R.id.latlongTV);
        latlongTV.setText(latlong);
        return root;
    }

    public void setObserver(OnAddListener observer) {
        this.observer = observer;
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
                observer.onNewPlace("hola neuv","69");
                break;
        }

    }

    @Override
    public void onNewMarker(LatLng latLng) {
        latlong = "latitude "+latLng.latitude+ " longitude "+latLng.longitude;
        mainActivity.showFragment(this);
    }



    public interface OnAddListener{
        void onNewPlace(String name, String rate);
    }
}