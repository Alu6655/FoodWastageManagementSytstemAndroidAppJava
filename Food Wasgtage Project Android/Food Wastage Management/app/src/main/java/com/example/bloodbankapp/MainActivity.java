package com.example.bloodbankapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
  EditText email,pass;
  CheckBox chck;
  public  static SharedPreferences pref;
  public static SharedPreferences.Editor editor;
  public static String username;
  public  static FirebaseUser user;
  Button st;
  FloatingActionButton login;
  FirebaseAuth auth;
  DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.pass);
        chck=findViewById(R.id.chck);
        login=findViewById(R.id.login);
        st=findViewById(R.id.st);
        ref= FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();
        pref=getSharedPreferences("txtfile",MODE_PRIVATE);
        editor=pref.edit();
        chck.setVisibility(View.GONE);
//       if(!pref.getString("email","").isEmpty())
//       {
//           Intent intent=new Intent(MainActivity.this,EMain.class);
//           startActivity(intent);
//           finish();
//       }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    auth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            username= email.getText().toString();
//                            if(!email.getText().toString().isEmpty()&&!pass.getText().toString().isEmpty())
//                            {
//                                    editor.putString("email", email.getText().toString());
//                                    editor.putString("pass", email.getText().toString());
//                                    editor.commit();

                                    Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(MainActivity.this,EMain.class);
                                    startActivity(intent);
                                    finish();

                            

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Login Unsuccessfully", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Field Required", Toast.LENGTH_LONG).show();
                }
            }
        });
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }
}