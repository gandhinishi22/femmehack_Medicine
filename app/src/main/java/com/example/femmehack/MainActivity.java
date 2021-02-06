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

public class MainActivity extends AppCompatActivity {
    EditText emailId,Password;
    Button btn_Signup;
    TextView tv_signin;
    FirebaseAuth mFirebaseAuth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailId=findViewById(R.id.emailid);
        Password=findViewById(R.id.Password);
        btn_Signup=findViewById(R.id.btn_signup);
        tv_signin=findViewById(R.id.loginredirect);
        mFirebaseAuth= FirebaseAuth.getInstance();
        btn_Signup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View V)
            {
                String email=emailId.getText().toString();
                String password=Password.getText().toString();
                if(email=="")
                {
                    emailId.setError("Please enter valid email-id");
                    emailId.requestFocus();
                }
                else if(password=="")
                {
                    Password.setError("Please Enter password");
                    Password.requestFocus();
                }
                else if(email=="" && password=="")
                {
                    Toast.makeText(MainActivity.this,"Fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(email!="" && password!="")
                {
                    mFirebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Signup is unsuccessful!",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Error!",Toast.LENGTH_SHORT).show();
                }
            }

        });
        tv_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
    }
}