package com.felipe.reto1_appsmoviles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker myMarker;

    private OnMapListener observer;

    public MapFragment() {
        // Required empty public constructor
    }


    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapfragment); // ?
        mapFragment.getMapAsync(this); // ?
        // Inflate the layout for this fragment
        //locationManager = (LocationManager)  this.getSystemService(LOCATION_SERVICE); //Esta chimbada solo es cuando es una Activity

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        return view;
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        initLocation();

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
        mMap.setOnMapLongClickListener(this);
    }
    @SuppressLint("MissingPermission")
    public void initLocation(){
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(pos));
                myMarker.setPosition(pos);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
            }



            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {

            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {

            }

            public void updateMyLocation(Location loc){

            }
        });



        }

    @Override
    public void onMapLongClick(LatLng latLng) {
       //mMap.addMarker(new MarkerOptions().position(latLng));
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            String address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1).get(0).getAddressLine(0);
            Log.e(">>>","Direccion "+address);
            observer.onNewMarker(latLng, address);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public interface OnMapListener{
        void onNewMarker(LatLng latLng, String address);
    }

    public void setObserver(OnMapListener observer) {
        this.observer = observer;
    }
}