package com.example.projetofinal1.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projetofinal1.R;
import com.example.projetofinal1.RestaurantActivity;
import com.example.projetofinal1.RestaurantFragment;
import com.example.projetofinal1.glidestuff.GlideApp;
import com.example.projetofinal1.models.Restaurant;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder>{

    private static final String TAG = "RestaurantAdapter";

    private List<Restaurant> restaurants = new ArrayList<>();
    private FirebaseStorage storage;
    private Context context;



    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final TextView resId;
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);

            textView = (TextView) v.findViewById(R.id.restaurant_name);
            imageView = (ImageView) v.findViewById(R.id.restaurant_icon);
            resId = (TextView) v.findViewById(R.id.res_id);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAbsoluteAdapterPosition() + " clicked.");
                    goToRestaurant(resId.getText().toString(), v);

                }
            });
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

        public void setRestaurantId(String id){
            resId.setText(id);
        }

        public void goToRestaurant(String id, View v){
            Intent i = new Intent(v.getContext(), RestaurantActivity.class);
            i.putExtra(RestaurantActivity.RES_ID, id);
            v.getContext().startActivity(i);
        }
    }

    public RestaurantAdapter(List<Restaurant> restaurants, FirebaseStorage storage, Context context) {
        this.storage = storage;
        this.context = context;
        if(restaurants != null)
            this.restaurants = restaurants;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_restaurant, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurant r = restaurants.get(position);
        holder.getTextView().setText(r.getName());
        holder.setRestaurantId(r.getId());
        if(r.getImagePath() != null){
            GlideApp.with(context)
                    .load(storage.getReferenceFromUrl(r.getImagePath()))
                    .placeholder(R.drawable.food_icon)
                    .into(holder.getImageView());
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }



}
