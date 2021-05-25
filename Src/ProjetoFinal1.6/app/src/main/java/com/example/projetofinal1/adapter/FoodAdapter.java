package com.example.projetofinal1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal1.R;
import com.example.projetofinal1.glidestuff.GlideApp;
import com.example.projetofinal1.models.CartEntry;
import com.example.projetofinal1.models.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder>{

    private static final String TAG = "FoodAdapter";

    private List<Food> foods = new ArrayList<>();
    private FirebaseStorage storage;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private Context context;

    public FoodAdapter(List<Food> foods, FirebaseStorage storage, FirebaseFirestore db, FirebaseAuth mAuth, Context context) {
        this.storage = storage;
        this.context = context;
        this.foods = foods;
        this.db = db;
        this.mAuth = mAuth;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView idView;
        private final TextView descriptionView;
        private final TextView priceView;
        private final Button addToCartButton;

        public ViewHolder(View v, FirebaseFirestore db, FirebaseAuth mAuth, Context context) {
            super(v);

            idView = v.findViewById(R.id.foodId);
            nameView = v.findViewById(R.id.foodName);
            imageView = v.findViewById(R.id.foodImage);
            descriptionView = v.findViewById(R.id.foodDescription);;
            priceView = v.findViewById(R.id.foodPrice);
            addToCartButton = v.findViewById(R.id.addToCartButton);

            // Define click listener for the ViewHolder's View.
            addToCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAbsoluteAdapterPosition() + " clicked.");
                    db.collection("cart").document().set(new CartEntry(mAuth.getCurrentUser().getUid(), idView.getText().toString()));
                    Toast.makeText(context, "Item added to cart!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getNameView() {
            return nameView;
        }

        public TextView getIdView() {
            return idView;
        }

        public TextView getDescriptionView() {
            return descriptionView;
        }

        public TextView getPriceView() {
            return priceView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_food, viewGroup, false);

        return new FoodAdapter.ViewHolder(v, db, mAuth, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food f = foods.get(position);

        holder.getIdView().setText(f.getId());
        holder.getDescriptionView().setText(f.getDescription());
        holder.getNameView().setText(f.getName());
        holder.getPriceView().setText(f.getPrice() + " €");

        if(f.getImagePath() != null){
            GlideApp.with(context)
                    .load(storage.getReferenceFromUrl(f.getImagePath()))
                    .placeholder(R.drawable.asiatico)
                    .into(holder.getImageView());
        }

    }

    @Override
    public int getItemCount() {
        return foods.size();
    }

}
