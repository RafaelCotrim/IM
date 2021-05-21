package com.example.projetofinal1;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class RestaurantFragment extends Fragment {


    private TextView resName;
    private TextView resId;
    private ImageView resImage;


    public RestaurantFragment()
    {
        resId = getView().findViewById(R.id.res_id);
        resName = getView().findViewById(R.id.restaurant_name);
        resImage = getView().findViewById(R.id.restaurantImage);

        resName.setOnClickListener(x -> goToRestaurant());
        resImage.setOnClickListener(x -> goToRestaurant());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }


    public void goToRestaurant(){
        Intent i = new Intent(getActivity(), RestaurantActivity.class);
        i.putExtra(RestaurantActivity.RES_ID, resId.getText().toString());
        startActivity(i);
    }
}