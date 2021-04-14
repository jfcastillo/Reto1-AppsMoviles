package com.felipe.reto1_appsmoviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchPlacesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchPlacesFragment extends Fragment implements SearchView.OnQueryTextListener {

    private RecyclerView placesViewList;
    private SearchView searchView;


    private PlacesAdapter adapter;

    private ArrayList<Place> places;


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

        placesViewList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        placesViewList.setLayoutManager(layoutManager);

        searchView = root.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);

        adapter = new PlacesAdapter();
        loadPlaces();
        adapter.setPlaces(places);
        placesViewList.setAdapter(adapter);

        return root;
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
            Log.e(">>>","Inicializo AL SP");
            places = new ArrayList<>();
        }
    }

    private void filter(String text) {
        ArrayList<Place> filteredlist = new ArrayList<>();

        for (Place item : places) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getActivity(), "No hay lugares para mostrar", Toast.LENGTH_SHORT).show();
        } else {
            adapter.filterList(filteredlist);
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        filter(newText);
        return false;
    }
}