package com.example.bloodbankapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EMain extends AppCompatActivity {
BottomNavigationView bnav;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FloatingActionButton logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_main);
        EventManagerFragment eventm=new EventManagerFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fhome,eventm).commit();
        bnav=findViewById(R.id.bnav);
        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref=getSharedPreferences("txtfile",MODE_PRIVATE);
                editor=pref.edit();
                editor.clear().commit();
                Intent intent=new Intent(EMain.this,MainActivity.class);
                startActivity(intent);
            }
        });
        bnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp=null;
                switch(item.getItemId())
                {
                    case R.id.home:temp=new HomeFragment();
                    break;
                    case R.id.contact:temp=new ContactFragment();
                    break;
                    case R.id.event:temp=new CreateEventFragment();
                    break;
                    case R.id.eventm:temp=new EventManagerFragment();
                    break;
                    case R.id.response:temp=new ResponseFragment();
                    break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fhome,temp).commit();

                return true;
            }
        });

    }
}