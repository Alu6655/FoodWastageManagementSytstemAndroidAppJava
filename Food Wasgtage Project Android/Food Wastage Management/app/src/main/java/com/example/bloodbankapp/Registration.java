package com.example.bloodbankapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button lt;
    FloatingActionButton reg;
    Spinner spinner;
    DatabaseReference ref;
    EditText rname, rage, rcontact, remail, rpass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        lt = findViewById(R.id.lt);
        rname = findViewById(R.id.rname);
        rage = findViewById(R.id.rage);
        rcontact = findViewById(R.id.rcontact);
        reg = findViewById(R.id.reg);
        remail = findViewById(R.id.remail);
        rpass = findViewById(R.id.rpass);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        spinner = findViewById(R.id.spinner);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    auth.createUserWithEmailAndPassword(remail.getText().toString(), rpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user=authResult.getUser();
                            ref.child("Registration").child(user.getUid()).child("Name").setValue(rname.getText().toString());
                            ref.child("Registration").child(user.getUid()).child("Age").setValue(rage.getText().toString());
                            ref.child("Registration").child(user.getUid()).child("Contact").setValue(rcontact.getText().toString());
                            ref.child("Registration").child(user.getUid()).child("Email").setValue(remail.getText().toString());
                            ref.child("Registration").child(user.getUid()).child("Password").setValue(rpass.getText().toString());
                            ref.child("Registration").child(user.getUid()).child("MaleFemale").setValue(spinner.getSelectedItem().toString());
                            Toast.makeText(Registration.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Registration.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registration.this, "Invalid Submission", Toast.LENGTH_LONG).show();
                                }
                            });
                } catch (Exception e) {
                    Toast.makeText(Registration.this, "Required Field", Toast.LENGTH_LONG).show();
                }

            }
        });

        lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Registration.this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    {
    }
}