package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projetofinal1.models.Restaurant;
import com.example.projetofinal1.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    EditText userName;
    EditText userPhoneNumber;
    EditText userAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        userName = findViewById(R.id.userName);
        userPhoneNumber = findViewById(R.id.userPhoneNumber);
        userAddress = findViewById(R.id.userAddress);


        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        database.getReference("users").child(mAuth.getCurrentUser().getUid()).get().addOnSuccessListener(task -> {
                User u = task.getValue(User.class);
                userName.setText(u.getName());
                userAddress.setText(u.getAddress());
                userPhoneNumber.setText(u.getPhoneNumber());

                });



        }

    public void logout(View v){
        mAuth.signOut();
        startActivity(new Intent(this, LoginActivity.class));
    }


    public void update(View v){
        User u = new User();
        u.setName(userName.getText().toString());
        u.setAddress(userAddress.getText().toString());
        u.setPhoneNumber(userPhoneNumber.getText().toString());

        database.getReference("users").child(mAuth.getCurrentUser().getUid()).setValue(u).addOnSuccessListener( task -> {
            Toast.makeText(this, "User updated!", Toast.LENGTH_LONG).show();
        });
    }
}