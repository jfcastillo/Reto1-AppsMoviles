package com.felipe.reto1_appsmoviles;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchPlacesAdapter extends RecyclerView.Adapter<PlaceView> {

    private ArrayList<Place> places;

    public SearchPlacesAdapter() {
        places = new ArrayList<>();
        places.add(new Place("Estadio Pascual Guerrero", "4.0"));
        places.add(new Place("Tardes Caleñas", "4.0"));
    }

    @NonNull
    @Override
    public PlaceView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.placerow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PlaceView placeView = new PlaceView(rowroot);

        return placeView;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceView holder, int position) {
        holder.getName().setText(places.get(position).getName());
        holder.getRate().setText(places.get(position).getRate());

    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
