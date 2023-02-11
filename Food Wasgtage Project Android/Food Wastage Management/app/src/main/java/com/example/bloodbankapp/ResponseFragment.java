package com.example.bloodbankapp;

import android.content.Context;
import android.os.Bundle;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResponseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResponseFragment extends Fragment {
    ListView rlst;
    DatabaseReference mdbref;
    ArrayList<response> objlist= new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResponseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResponseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResponseFragment newInstance(String param1, String param2) {
        ResponseFragment fragment = new ResponseFragment();
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
        View vieww=inflater.inflate(R.layout.fragment_response,container,false);
        rlst=vieww.findViewById(R.id.rlst);
        mdbref= FirebaseDatabase.getInstance().getReference();
        mdbref.child("Responses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    objlist.add(new response(ds.getKey(), ds.child("Name").getValue(String.class), ds.child("Coming").getValue(String.class),ds.child("Dressing").getValue(String.class),ds.child("Food Preference").getValue(String.class),ds.child("Email").getValue(String.class)));
                }
                ResponseAdapter adapter = new ResponseAdapter(getActivity(), objlist);
                rlst.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return vieww;
    }
}
class response
{
    String id,rname,rcome,rdress,rfood,remail;

    public response(String id, String rname, String rcome, String rdress, String rfood,String remail) {
        this.id = id;
        this.rname = rname;
        this.rcome = rcome;
        this.rdress = rdress;
        this.rfood = rfood;
        this.remail=remail;
    }

    public String getRemail() {
        return remail;
    }

    public void setRemail(String remail) {
        this.remail = remail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public String getRcome() {
        return rcome;
    }

    public void setRcome(String rcome) {
        this.rcome = rcome;
    }

    public String getRdress() {
        return rdress;
    }

    public void setRdress(String rdress) {
        this.rdress = rdress;
    }

    public String getRfood() {
        return rfood;
    }

    public void setRfood(String rfood) {
        this.rfood = rfood;
    }
}
class ResponseAdapter extends BaseAdapter {
    Context context;
    ArrayList<response> data = new ArrayList<>();

    public ResponseAdapter(Context context, ArrayList<response> data) {
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
        try {
            TextView rname, remail, rfood, rcome, rdress;
            convertView = LayoutInflater.from(context).inflate(R.layout.recresponses, null);
            rcome = convertView.findViewById(R.id.rcome);
            remail = convertView.findViewById(R.id.remail);
            rdress = convertView.findViewById(R.id.rdress);
            rfood = convertView.findViewById(R.id.rfood);
            rname = convertView.findViewById(R.id.rname);
            rcome.setText(data.get(position).rcome);
            rfood.setText(data.get(position).rfood);
            rdress.setText(data.get(position).rdress);
            rname.setText(data.get(position).rname);
            remail.setText(data.get(position).remail);
        }
        catch(Exception ex)
        {}
        return convertView;



    }
}