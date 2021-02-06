package com.example.femmehack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText emailId, pwd;
    Button btn_login;
    TextView signup;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId = findViewById(R.id.emailid);
        pwd = findViewById(R.id.Password);
        btn_login = findViewById(R.id.button);
        signup = findViewById(R.id.textView);
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseuser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseuser != null) {
                    Toast.makeText(LoginActivity.this, "User is created,logged in!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class
                    ));
                } else {
                    Toast.makeText(LoginActivity.this, "Please signup", Toast.LENGTH_SHORT).show();
                }
            }

        };
        btn_login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = emailId.getText().toString();
                String password = pwd.getText().toString();
                if (email == "") {
                    emailId.setError("Please enter valid email-id");
                    emailId.requestFocus();
                } else if (password == "") {
                    pwd.setError("Please Enter password");
                    pwd.requestFocus();
                } else if (email == "" && password == "") {
                    Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                } else if (email != "" && password != "") {
                    mFirebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login unsuceesful", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent toHome = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(toHome);
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "Some error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
    }

    protected void onStart()
    {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }
}