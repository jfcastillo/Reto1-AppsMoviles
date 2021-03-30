package com.felipe.reto1_appsmoviles;

import android.view.View;
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





    public PlaceView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.nameTextView);
        rate = root.findViewById(R.id.rateTextView);
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
}
