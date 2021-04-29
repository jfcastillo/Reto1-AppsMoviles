package com.felipe.reto1_appsmoviles;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.maps.android.SphericalUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;


public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener, RatingBar.OnRatingBarChangeListener, PlacesAdapter.onClickMapPlaceButton {


    private ConstraintLayout layoutInfoPlace;
    private TextView placeNameTextView;
    private TextView addressPlaceTextView;
    private RatingBar ratingBar;
    private ImageView imageViewPlace;
    private GoogleMap mMap;
    private LocationManager locationManager;
    private Marker myMarker;

    private OnMapListener observer;
    private ArrayList<Place> places;
    private int nearestPlaceIndex;

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
        layoutInfoPlace = view.findViewById(R.id.layoutPlaceInfo);
        placeNameTextView = view.findViewById(R.id.placeNameTextView);
        addressPlaceTextView = view.findViewById(R.id.addressPlaceTextView);
        ratingBar = view.findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(this);
        imageViewPlace = view.findViewById(R.id.imageViewPlace);

        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        return view;
    }

    @Override
    @SuppressLint("MissingPermission")
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        loadPlaces();
        initLocation();

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        myMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(0,0)));
        for (int i = 0; i < places.size(); i++){
            Place place = places.get(i);
            LatLng latlng = new LatLng(place.getLatitude(), place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latlng).title(place.getName()));
        }
        mMap.setOnMapLongClickListener(this);
    }
    @SuppressLint("MissingPermission")
    public void initLocation(){
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 2, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
//                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
//                mMap.addMarker(new MarkerOptions().position(pos));
//                myMarker.setPosition(pos);
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));

                LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
                myMarker.setPosition(pos);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
                computeDistances();
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
            observer.onNewMarker(latLng, address);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            places = new ArrayList<>();
        }
    }

    public void computeDistances(){
        for (int i = 0; i < places.size(); i++){
            Place place = places.get(i);
            LatLng placeLocation = new LatLng(place.getLatitude(), place.getLongitude());
            LatLng myLocation = myMarker.getPosition();
            double meters = SphericalUtil.computeDistanceBetween(placeLocation, myLocation);
            if (meters < 100){
                nearestPlaceIndex = i;
                layoutInfoPlace.setVisibility(View.VISIBLE);
                placeNameTextView.setText(place.getName());
                addressPlaceTextView.setText(place.getAddress());
                Bitmap image = BitmapFactory.decodeFile(place.getPhotoPath());
                imageViewPlace.setImageBitmap(image);
                if (place.getRate() > 0){
                    ratingBar.setRating(place.getRate());
                }

            }
            else{
                layoutInfoPlace.setVisibility(View.INVISIBLE);
                nearestPlaceIndex = -1;
            }
        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        if (nearestPlaceIndex >= 0){
            places.get(nearestPlaceIndex).setRate(rating);
            Gson gson = new Gson();
            String json = gson.toJson(places);
            SharedPreferences preferences = getActivity().getSharedPreferences("Places", Context.MODE_PRIVATE);
            preferences.edit().putString("placesList", json).apply();

        }
    }

    @Override
    public void showOnMap(LatLng pos) {
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
        //CameraUpdateFactory.newLatLngZoom(pos, 16);
        float zoomLevel = 16.0f; //This goes up to 21
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, zoomLevel));
        myMarker.setPosition(pos);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 16));
    }

    public interface OnMapListener{
        void onNewMarker(LatLng latLng, String address);
    }

    public void setObserver(OnMapListener observer) {
        this.observer = observer;
    }




}
