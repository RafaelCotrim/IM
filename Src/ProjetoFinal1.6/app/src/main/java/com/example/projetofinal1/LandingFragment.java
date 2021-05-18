package com.example.projetofinal1;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LandingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LandingFragment extends Fragment {

    FirebaseFirestore db= FirebaseFirestore.getInstance();

    public LandingFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static LandingFragment newInstance(String param1, String param2) {
        LandingFragment fragment = new LandingFragment();
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

    public void goToRestaurant(){
        Intent intent = new Intent(getActivity(),RestaurantActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_landing, container, false);
    }

    private class RestaurantAdapter extends BaseAdapter{

        private CollectionReference restaurants;

        public RestaurantAdapter(CollectionReference collection){
            this.restaurants = collection;
        }

        @Override
        public int getCount() {
            int c = 0;
            return restaurants.get().addOnCompleteListener(task ->
            {
                if(!task.isSuccessful()){
                    Toast.makeText(getActivity(), "Unable to access data", Toast.LENGTH_LONG).show();
                }
            }).getResult().size();
        }

        @Override
        public Object getItem(int position) {
            return restaurants.startAt(position).limit(1).get().getResult();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.fragment_restaurant, parent, false);

            convertView = new RestaurantFragment();

            ((TextView) convertView.findViewById(android.R.id.text1))
                    .setText(getItem(position));
            return convertView;
        }
    }
}