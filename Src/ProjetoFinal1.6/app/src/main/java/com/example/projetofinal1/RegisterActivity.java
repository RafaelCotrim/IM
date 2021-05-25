package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetofinal1.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    TextView btn;

    private EditText inputUsername, inputPassword, inputEmail, inputConfirmPassword, inputAddress, inputMobile;
    Button btnRegister;
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();

        btn=findViewById(R.id.alreadyHaveAccount);
        inputUsername=findViewById(R.id.inputPass);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        inputAddress=findViewById(R.id.inputAddress);
        inputMobile=findViewById(R.id.inputMobile);

        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(v -> checkCredentials());
        btn.setOnClickListener(v -> startActivity(new Intent(RegisterActivity.this, LoginActivity.class)));

    }

    public  void goToLogin(View v){
        Intent login = new Intent(this, LoginActivity.class);
        startActivity(login);
    }

    private void checkCredentials() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String address=inputAddress.getText().toString();
        String mobile=inputMobile.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();


        if (username.isEmpty() || username.length()<7){
            showError(inputUsername,"Invalid Username!");
        }
        else if (email.isEmpty() || !email.contains("@")){ // TODO user pattern matching
            showError(inputEmail, "Invalid Email");
        }
        if (username.isEmpty() || username.length()<7){
            showError(inputUsername,"Invalid Username");
        }
        if (address.isEmpty()){
            showError(inputAddress,"Invalid address");
        }
        if (mobile.isEmpty() || mobile.length()<9){
            showError(inputMobile,"Invalid phone number");
        }
        else if (password.isEmpty() || password.length()<7){
            showError(inputPassword,"The password must have at least 7 characters");
        }
        else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(inputConfirmPassword,"The password doesn't match");
        }
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener( RegisterActivity.this,task ->
        {
            if (task.isSuccessful()) {
                User u = new User(username, mobile, address);
                db.getReference("users").child(mAuth.getCurrentUser().getUid()).setValue(u).addOnCompleteListener(addTask ->
                {
                    // Change email mAuth.getCurrentUser().updateEmail();
                    if(addTask.isSuccessful()) {
                        startActivity(new Intent(this, MainActivity.class));
                    } else {
                        Toast.makeText(this, "Unable to store data", Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                Toast.makeText(this, "Unable to reach database", Toast.LENGTH_LONG).show();
            }
        });

    }



    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus(); //ajuda a detetar o erro
    }
}