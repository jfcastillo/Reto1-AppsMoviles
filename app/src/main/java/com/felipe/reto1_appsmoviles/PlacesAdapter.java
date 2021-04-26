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


//        places.add(new Place("Estadio Pascual Guerrero", "4.0"));
//        places.add(new Place("Tardes Caleñas", "4.0"));
    }

//    public void addPlace(String name, String rate){
//        places.add(new Place(name, rate));
//        this.notifyDataSetChanged();
//
//        Log.e(">>>","actualicé "+places.size()+"nombre "+name);
//    }


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
        holder.getName().setText(places.get(position).getName());
        holder.getRate().setText(places.get(position).getRate()+"");
        Bitmap image = BitmapFactory.decodeFile(places.get(position).getPhotoPath());
        holder.getImage().setImageBitmap(image);

        holder.getBtnMapPlace().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.showFragment(mainActivity.getMapFragment());
                LatLng pos = new LatLng(places.get(position).getLatitude(), places.get(position).getLongitude());
                observer.showOnMap(pos);


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
