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

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {


    TextView btn;

    private EditText inputUsername, inputPassword, inputEmail, inputConfirmPassword, inputAddress, inputMobile;
    Button btnRegister;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

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

    private void checkCredentials() {
        String username=inputUsername.getText().toString();
        String email=inputEmail.getText().toString();
        String address=inputAddress.getText().toString();
        String mobile=inputMobile.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();


        if (username.isEmpty() || username.length()<7){
            showError(inputUsername,"Username inválido!");
        }
        else if (email.isEmpty() || !email.contains("@")){ // TODO user pattern matching
            showError(inputEmail, "Email inválido");
        }
        if (username.isEmpty() || username.length()<7){
            showError(inputUsername,"Username inválido!");
        }
        if (address.isEmpty()){
            showError(inputAddress,"Morada inválida!");
        }
        if (mobile.isEmpty() || mobile.length()<9){
            showError(inputMobile,"Número inválido!");
        }
        else if (password.isEmpty() || password.length()<7){
            showError(inputPassword,"A password tem de ter pelo menos 7 careteres!");
        }
        else if (confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(inputConfirmPassword,"A password não correspondente!");
        }

        mAuth.createUserWithEmailAndPassword(address, password).addOnCompleteListener( RegisterActivity.this,task ->
        {
            if (task.isSuccessful()) {
                Toast.makeText(this, "It does work!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                Log.d("HIIIIIIIIIIIII", "It does work!!!!!!!!!!!");

            } else {
                Toast.makeText(this, "It does not work!!!!!!!!!!!", Toast.LENGTH_SHORT).show();
                Log.d("HIIIIIIIIIIIII", "It does not work!!!!!!!!!!!");
            }
        });

    }



    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus(); //ajuda a detetar o erro
    }
}