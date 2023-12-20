package com.example.expensemanagerfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.expensemanagerfirebase.databinding.ActivityUpdateBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {
    ActivityUpdateBinding binding;
    String  newType;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();

        String id=getIntent().getStringExtra("id");
        String amount=getIntent().getStringExtra("amount");
        String note=getIntent().getStringExtra("note");
        String type=getIntent().getStringExtra("type");

        binding.userAmountAdd.setText(amount);
        binding.userNoteAdd.setText(note);

        switch (type){
            case "Income":
                newType="Income";
                binding.incomeCheckBox.setChecked(true);
                break;
            case "Expense":
                newType="Expense";
                binding.expenseCheckBox.setChecked(true);
                break;
        }
        binding.incomeCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newType="Income";
                binding.incomeCheckBox.setChecked(true);
                binding.expenseCheckBox.setChecked(false);
            }
        });
        binding.expenseCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newType="Expense";
                binding.incomeCheckBox.setChecked(false);
                binding.expenseCheckBox.setChecked(true);
            }
        });
        binding.btnUpdateTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = binding.userAmountAdd.getText().toString();
                String note = binding.userNoteAdd.getText().toString();

                  firebaseFirestore.collection("Expense").document(firebaseAuth.getUid())
                          .collection("Notes").document(id)
                          .update("amount", amount, "note", note ,"type", type)
                          .addOnSuccessListener(new OnSuccessListener<Void>() {
                              @Override
                              public void onSuccess(Void unused) {
                                  onBackPressed();
                                  Toast.makeText(UpdateActivity.this ,"Updated", Toast.LENGTH_SHORT).show();
                              }
                          })
                          .addOnFailureListener(new OnFailureListener() {
                              @Override
                              public void onFailure(@NonNull Exception e) {
                                  Toast.makeText(UpdateActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                              }
                          });
            }
        });
        binding.btnDeleteTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseFirestore.collection("Expenses").document(firebaseAuth.getUid())
                        .collection("Notes")
                        .document(id).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                onBackPressed();
                                Toast.makeText(UpdateActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}