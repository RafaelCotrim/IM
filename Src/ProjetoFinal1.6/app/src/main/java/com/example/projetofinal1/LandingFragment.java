package com.example.projetofinal1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetofinal1.adapter.RestaurantAdapter;
import com.example.projetofinal1.models.Restaurant;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class LandingFragment extends Fragment {

    private static final String TAG = "LandingFragment";

    protected RecyclerView mRecyclerView;
    protected RestaurantAdapter mAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    FirebaseFirestore db= FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();

    private List<Restaurant> restaurants = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initDataset();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_landing, container, false);
            rootView.setTag(TAG);

            // BEGIN_INCLUDE(initializeRecyclerView)
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.restaurantList);

            // LinearLayoutManager is used here, this will layout the elements in a similar fashion
            // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
            // elements are laid out.
            mLayoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(mLayoutManager);

            mAdapter = new RestaurantAdapter(restaurants, storage, getActivity());
            // Set CustomAdapter as the adapter for RecyclerView.
            mRecyclerView.setAdapter(mAdapter);
            // END_INCLUDE(initializeRecyclerView)

            return rootView;
    }

    public void goToRestaurant(){
        Intent intent = new Intent(getActivity(),RestaurantActivity.class);
        startActivity(intent);
    }

    private void initDataset(){
        db.collection("restaurants").get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                restaurants.clear();
                for (DocumentSnapshot doc: task.getResult().getDocuments()) {
                    Restaurant r = doc.toObject(Restaurant.class);
                    r.setId(doc.getId());
                    restaurants.add(r);
                }
                mAdapter.notifyDataSetChanged();
            } else {
                // TODO handle error
            }
        });

    }


}