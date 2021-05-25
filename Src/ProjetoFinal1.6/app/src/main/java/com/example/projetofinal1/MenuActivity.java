package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projetofinal1.adapter.FoodAdapter;
import com.example.projetofinal1.adapter.RestaurantAdapter;
import com.example.projetofinal1.models.Food;
import com.example.projetofinal1.models.Restaurant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivity";
    private String resId;


    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected FoodAdapter mAdapter;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db= FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private List<Food> foods = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mRecyclerView = findViewById(R.id.recycleView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FoodAdapter(foods, storage, db, mAuth, this);
        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        resId = getIntent().getStringExtra(RestaurantActivity.RES_ID);

        initDataset();
    }

    private void initDataset(){
        db.collection("foods")
                .whereEqualTo("resId", resId)
                .get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        foods.clear();
                        for (DocumentSnapshot doc: task.getResult().getDocuments()) {
                            Food f = doc.toObject(Food.class);
                            f.setId(doc.getId());
                            foods.add(f);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        // TODO handle error
                    }
        });

    }
}