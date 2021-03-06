package com.felipe.reto1_appsmoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


public class AddPlaceFragment extends Fragment implements  View.OnClickListener, MapFragment.OnMapListener, CameraGallerySelectionDialog.onAddImageListener {


    private ImageButton btnPinMap;
    private TextView addressTV;
    private Button btnRegister;
    private EditText editTextNamePlace;
    private ImageButton btnAddImage;
    private ImageView placeImageView;
    private CameraGallerySelectionDialog imageDialog;

    private MainActivity mainActivity;
    private LatLng latlong;

    private SharedPreferences preferences;
    private ArrayList<Place> places;

    private String cameraGalleyImage;
    private String address;
    private String namePlace;


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

        btnAddImage = root.findViewById(R.id.addImage);
        btnAddImage.setOnClickListener(this);
        btnPinMap = root.findViewById(R.id.btnPinMap);
        btnPinMap.setOnClickListener(this);
        btnRegister = root.findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
        addressTV = root.findViewById(R.id.adressTV);
        addressTV.setText(address);
        editTextNamePlace = root.findViewById(R.id.editTextNamePlace);
        editTextNamePlace.setText(namePlace);
        placeImageView = root.findViewById(R.id.placeImageView);


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
                namePlace = editTextNamePlace.getText().toString();
                double latitude = latlong.latitude;
                double longitude = latlong.longitude;
                places.add(new Place(namePlace, (float)4.0, longitude, latitude, address, cameraGalleyImage));
                Gson gson = new Gson();
                String json = gson.toJson(places);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("placesList", json);
                editor.apply();
                Toast.makeText(getContext(), "Se registr?? el lugar correctamente", Toast.LENGTH_SHORT).show();
                restartFields();

                break;

            case R.id.addImage:
                imageDialog = CameraGallerySelectionDialog.newInstance();
                imageDialog.setListener(this);
                imageDialog.show(getChildFragmentManager(), "Selection image dialog");

                break;

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
        preferences = getActivity().getSharedPreferences("Places", Context.MODE_PRIVATE);
        String json = preferences.getString("placesList", "NO_OBJ");
        if (!json.equals("NO_OBJ")){
            Type arrayListTypeToken = new TypeToken<ArrayList<Place>>(){}.getType();
            places = gson.fromJson(json, arrayListTypeToken);
        }
        else{
            places = new ArrayList<>();
        }
    }
    public void restartFields(){

        namePlace = "";
        address = "";
        cameraGalleyImage = "";
        editTextNamePlace.setText("");
    }

    @Override
    public void onAddImage(String imagePath) {
        imageDialog.dismiss();
        cameraGalleyImage = imagePath;
        if (cameraGalleyImage != null && !cameraGalleyImage.equals("")){
            Bitmap image = BitmapFactory.decodeFile(cameraGalleyImage);
            placeImageView.setImageBitmap(image);
        }



    }
}