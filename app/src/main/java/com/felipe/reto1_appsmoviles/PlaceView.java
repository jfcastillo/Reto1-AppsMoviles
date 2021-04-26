package com.felipe.reto1_appsmoviles;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class PlaceView extends RecyclerView.ViewHolder {

    private ConstraintLayout root;
    private ImageView image;
    private TextView name;
    private TextView rate;
    private ImageButton btnMapPlace;





    public PlaceView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.nameTextView);
        rate = root.findViewById(R.id.rateTextView);
        image = root.findViewById(R.id.placeImage);
        btnMapPlace = root.findViewById(R.id.btnMapButton);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getName() {
        return name;
    }

    public TextView getRate() {
        return rate;
    }

    public ImageButton getBtnMapPlace(){return btnMapPlace;}

}
