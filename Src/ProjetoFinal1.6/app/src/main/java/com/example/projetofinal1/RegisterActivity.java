package com.example.projetofinal1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {


    TextView btn;

    private EditText inputUsername, inputPassword, inputEmail, inputConfirmPassword, inputAddress, inputMobile;
    Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btn=findViewById(R.id.alreadyHaveAccount);
        inputUsername=findViewById(R.id.inputPass);
        inputEmail=findViewById(R.id.inputEmail);
        inputPassword=findViewById(R.id.inputPassword);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        inputAddress=findViewById(R.id.inputAddress);
        inputMobile=findViewById(R.id.inputMobile);

        btnRegister=findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkCredentials();
            }
        });

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
        else if (email.isEmpty() || !email.contains("@")){
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
        else {
            Toast.makeText(this, "Ir para o método de registo", Toast.LENGTH_SHORT).show();

        }



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

    }



    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus(); //ajuda a detetar o erro
    }
}