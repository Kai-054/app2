package com.example.expensemanagerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanagerfirebase.databinding.ActivityMainBinding;
import com.example.expensemanagerfirebase.databinding.ActivitySignUpAcivityBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
  ActivitySignUpAcivityBinding binding;
  FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivitySignUpAcivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseAuth=FirebaseAuth.getInstance();

        binding.goToLoginScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                try {
                    startActivity(intent);
                }catch (Exception e){

                }
            }
        });
        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailForSignUp.getText().toString();
                String password = binding.passwordForSignUp.getText().toString();
                if (email.trim().length() == 0 || password.trim().length() == 0){
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(SignUpActivity.this , "User Created", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SignUpActivity.this , e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}