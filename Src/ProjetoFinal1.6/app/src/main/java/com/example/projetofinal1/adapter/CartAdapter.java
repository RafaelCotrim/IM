package com.example.projetofinal1.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal1.R;
import com.example.projetofinal1.glidestuff.GlideApp;
import com.example.projetofinal1.models.CartEntry;
import com.example.projetofinal1.models.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private static final String TAG = "CartAdapter";

    private List<CartEntry> cart = new ArrayList<>();
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Context context;

    public CartAdapter(Context context) {
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView nameView;
        private final TextView idView;
        private final TextView descriptionView;
        private final TextView priceView;
        private final TextView idEntry;
        private final Button removeFromCartButton;

        public ViewHolder(CartAdapter ca, View v, FirebaseFirestore db) {
            super(v);

            idView = v.findViewById(R.id.foodId);
            nameView = v.findViewById(R.id.foodName);
            imageView = v.findViewById(R.id.foodImage);
            descriptionView = v.findViewById(R.id.foodDescription);;
            priceView = v.findViewById(R.id.foodPrice);
            removeFromCartButton = v.findViewById(R.id.removeFromCartButton);
            idEntry = v.findViewById(R.id.idEntry);

            // Define click listener for the ViewHolder's View.
            removeFromCartButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAbsoluteAdapterPosition() + " clicked.");
                    db.collection("cart").document(idEntry.getText().toString()).delete()
                            .addOnSuccessListener(task -> {
                                ca.updateData();
                            });
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

        public TextView getIdEntry() {
            return idEntry;
        }
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_cart_entry_fragmnet, viewGroup, false);

        return new CartAdapter.ViewHolder(this, v, db);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        CartEntry ce = cart.get(position);
        Food f = ce.getFood();
        holder.getIdView().setText(ce.getId());
        holder.getDescriptionView().setText(f.getDescription());
        holder.getNameView().setText(f.getName());
        holder.getPriceView().setText(f.getPrice() + " €");
        holder.getIdEntry().setText(ce.getId());

        if(f.getImagePath() != null){
            GlideApp.with(context)
                    .load(storage.getReferenceFromUrl(f.getImagePath()))
                    .placeholder(R.drawable.asiatico)
                    .into(holder.getImageView());
        }

    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public void updateData(){
        db.collection("cart")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        cart.clear();
                        for (DocumentSnapshot doc: task.getResult().getDocuments()) {
                            CartEntry ce = doc.toObject(CartEntry.class);
                            ce.setId(doc.getId());

                            db.collection("foods").document(ce.getFoodId()).get()
                                    .addOnSuccessListener(task2 -> {
                                        ce.setFood(task2.toObject(Food.class));
                                        cart.add(ce);
                                        this.notifyDataSetChanged();
                                    });
                        }
                    } else {
                        cart.clear();
                        this.notifyDataSetChanged();
                    }
                });

    }

}
