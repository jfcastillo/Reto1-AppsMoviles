package com.felipe.reto1_appsmoviles;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class PlacesAdapter extends RecyclerView.Adapter<PlaceView> {

    private ArrayList<Place> places;
    private onClickMapPlaceButton observer;
    private MainActivity mainActivity;

    public PlacesAdapter() {


    }




    public void setObserver(MapFragment observer) {
        this.observer = observer;
    }

    @NonNull
    @Override
    public PlaceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View row = inflater.inflate(R.layout.placerow, parent, false);
        //View row = inflater.inflate(R.layout.placerow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PlaceView placeView = new PlaceView(rowroot);

        return placeView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceView holder, int position) {
        Place place = places.get(position);
        holder.getName().setText(place.getName());
        holder.getRate().setText(place.getRate()+"");
        if (place.getDistance() >= 0){
            holder.getDistance().setText("Distancia: "+Math.round(place.getDistance()*100.0)/100.0+"");
        }
        if(place.getPhotoPath() != null || place.getPhotoPath() != ""){
            Bitmap image = BitmapFactory.decodeFile(place.getPhotoPath());
            holder.getImage().setImageBitmap(image);
        }
        holder.getBtnMapPlace().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LatLng pos = new LatLng(place.getLatitude(), place.getLongitude());
                observer.showOnMap(pos);
                mainActivity.showFragment(mainActivity.getMapFragment());



            }
        });

    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void setPlaces(ArrayList<Place> places) {
        this.places = places;
    }

    public void filterList(ArrayList<Place> filterllist) {
        places = filterllist;
        notifyDataSetChanged();
    }

    public interface onClickMapPlaceButton{
        void showOnMap(LatLng pos);
    }
}
