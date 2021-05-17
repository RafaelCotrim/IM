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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {


    TextView btn;
    EditText inputEmail, inputPass;
    Button btnLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn=findViewById(R.id.textViewSingUp);
        inputEmail=findViewById(R.id.inputEmail);
        inputPass=findViewById(R.id.inputPass);
        btnLogin=findViewById(R.id.btnlogin);
        mAuth = FirebaseAuth.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }

    }

    public  void signUp(View v){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }

    public void login(View v){
        checkCredentials();
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

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener( this,task ->
            {
                if (task.isSuccessful()) {
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {
                    Toast.makeText(this, "You idiot!", Toast.LENGTH_SHORT).show();
                    // Log.d("HIIIIIIIIIIIII", "It does not work!!!!!!!!!!!");
                }
            });
        }
    }

    private void showError(EditText input, String s) {
        input.setError(s);
        input.requestFocus(); //ajuda a detetar o erro
    }
}