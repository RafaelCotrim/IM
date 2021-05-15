package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetofinal1.models.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    TextView btn;
    EditText inputEmail, inputPass;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.textViewSingUp);
        inputEmail=findViewById(R.id.inputEmail);
        inputPass=findViewById(R.id.inputPass);
        btnLogin=findViewById(R.id.btnlogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

        Intent goTolanding = new Intent(this, RegisterActivity.class);
        startActivity(goTolanding);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });


    }

    private void checkCredentials() {

        String email=inputEmail.getText().toString();
        String password=inputPass.getText().toString();


        if (email.isEmpty() || !email.contains("@")){
            showError(inputEmail, "Email inválido");
        }
        else if (password.isEmpty() || password.length()<7){
            showError(inputPass,"A password tem de ter pelo menos 7 careteres!");
        }

        else {
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });
        }




    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus(); //ajuda a detetar o erro
    }
}