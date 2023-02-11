package com.example.bloodbankapp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ContactFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    ListView lst;
    DatabaseReference mdbref;
    ArrayList<Registered> objlist= new ArrayList<>();
    public ContactFragment() {
    }
    public static ContactFragment newInstance(String param1, String param2) {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vieww=inflater.inflate(R.layout.fragment_contact,container,false);
        lst=vieww.findViewById(R.id.lst);
         mdbref=FirebaseDatabase.getInstance().getReference();
        mdbref.child("Registration").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    objlist.add(new Registered(ds.getKey(), ds.child("Name").getValue(String.class), ds.child("Contact").getValue(String.class)));
                }
                CustomAdapter adapter = new CustomAdapter(getActivity(), objlist);
                lst.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return vieww;

    }
}
class Registered
{
    String id ,name,contact;

    public Registered(String id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
    }
 public  Registered()
 {}
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Registered> data =  new ArrayList<>();
    public CustomAdapter(Context context, ArrayList<Registered> data) {
        this.context = context;
        this.data = data;
    }
    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView Name, Contact, ID;
        convertView = LayoutInflater.from(context).inflate(R.layout.contactlayout, null);
        Name = convertView.findViewById(R.id.name);
        ID = convertView.findViewById(R.id.idd);
        ID.setText(position+"");
        Contact = convertView.findViewById(R.id.contact);
        Name.setText(data.get(position).name);
        Contact.setText(data.get(position).contact);
        return convertView;
    }
}