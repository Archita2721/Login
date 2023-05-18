package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    EditText memail,mpass;
    Button login;
    String email,pass;
    ProgressBar pg;
    TextView fpass,reg;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("HomeBuddy");
        setTitleColor(R.color.whiteTextColor);
        mAuth=FirebaseAuth.getInstance();
        memail=(EditText)findViewById(R.id.email);
        mpass=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.login);
        email=memail.getText().toString();
        pass=mpass.getText().toString();
        pg = findViewById(R.id.progressBar);
        fpass=findViewById(R.id.ForgotPass);
        fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //firebaseUser=mAuth.getCurrentUser();
                email=memail.getText().toString();
                if (email.isEmpty()){
                    memail.setError("Enter Email!!");
                    memail.requestFocus();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Email Sent!!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        reg = findViewById(R.id.registerBtn);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, Register.class);
                startActivity(intent1);
            }
        });
    }

    public void login(View view) {
        email=memail.getText().toString();
        pass=mpass.getText().toString();
        if (validation()) {
            pg.setVisibility(View.VISIBLE);
            mAuth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(this, new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    pg.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, BottomNav.class);
                    startActivity(intent);
                }
            }).addOnFailureListener(this, e -> {
                pg.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                //return;
            });
        }
    }

    public boolean validation()
    {
        //Toast.makeText(MainActivity.this , "Inside Validation" , Toast.LENGTH_SHORT).show();
        boolean valid = true;
        if(email.isEmpty())
        {
            memail.setError("Email is required");
            memail.requestFocus();
            valid = false;
        }
        if(! Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            memail.setError("Please Enter valid Email address");
            memail.requestFocus();
            valid = false;
        }
        if(pass.isEmpty())
        {
            mpass.setError("Please Enter the password");
            mpass.requestFocus();
            valid = false;
        }
        pg.setVisibility(View.GONE);
        return valid;
    }

    public void login2(View view) {
        Intent intent = new Intent(this,SPRegister.class);
        startActivity(intent);
    }


}