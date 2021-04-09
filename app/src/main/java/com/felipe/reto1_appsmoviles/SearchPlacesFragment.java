package com.felipe.reto1_appsmoviles;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.telephony.RadioAccessSpecifier;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPlacesFragment extends Fragment implements AddPlaceFragment.OnAddListener{

    private RecyclerView placesViewList;
    private SearchPlacesAdapter adapter;


    public SearchPlacesFragment() {
        // Required empty public constructor
    }


    public static SearchPlacesFragment newInstance() {
        SearchPlacesFragment fragment = new SearchPlacesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search_places, container, false);
        placesViewList = root.findViewById(R.id.placesViewList);
        //placesViewList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(root.getContext());
        placesViewList.setLayoutManager(layoutManager);

        adapter = new SearchPlacesAdapter();
        placesViewList.setAdapter(adapter);
        return root;
    }

    @Override
    public void onNewPlace(String name, String rate) {
        adapter.addPlace(name, rate);
        Log.e(">>>","tamaño"+adapter.getItemCount());

        //Toast.makeText(this.getContext(), "Tamaño "+adapter.getItemCount(), Toast.LENGTH_SHORT).show();
    }
}