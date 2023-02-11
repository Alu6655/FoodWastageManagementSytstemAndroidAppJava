package com.example.bloodbankapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventManagerFragment extends Fragment {
    ListView emlst;
   public static DatabaseReference mdbref;
   public static FirebaseUser user;
   public static FirebaseAuth auth;
   public static TextView eemail;
   public static Event currentObj;
   public static ArrayList<Event> objlist= new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventManagerFragment newInstance(String param1, String param2) {
        EventManagerFragment fragment = new EventManagerFragment();
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
        View vieww=inflater.inflate(R.layout.fragment_event_manager,container,false);
        emlst=vieww.findViewById(R.id.emlst);
        mdbref= FirebaseDatabase.getInstance().getReference();
        mdbref.child("CreateEvent").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    objlist.clear();
                    objlist.add(new Event(ds.getKey(), ds.child("EventEmail").getValue(String.class),ds.child("Username").getValue(String.class),ds.child("EventName").getValue(String.class), ds.child("EventStart").getValue(String.class),ds.child("EventEnd").getValue(String.class),ds.child("EventLocation").getValue(String.class),ds.child("EventContact").getValue(String.class),ds.child("EventAddress").getValue(String.class)));
                }
                EventAdapter adapter = new EventAdapter(getActivity(), objlist);
                emlst.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return vieww;
    }

}
class Event {
String id,evname,eemail,ename,estart,eend,egloc,econtact,eaddress;

    public Event(String id, String evname, String eemail, String ename, String estart, String eend, String egloc, String econtact, String eaddress) {
        this.ename = ename;
        this.estart = estart;
        this.eend = eend;
        this.egloc = egloc;
        this.econtact = econtact;
        this.eaddress = eaddress;
        this.id=id;
        this.evname = evname;
        this.eemail = eemail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvname() {
        return evname;
    }

    public void setEvname(String evname) {
        this.evname = evname;
    }

    public String getEemail() {
        return eemail;
    }

    public void setEemail(String eemail) {
        this.eemail = eemail;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getEstart() {
        return estart;
    }

    public void setEstart(String estart) {
        this.estart = estart;
    }

    public String getEend() {
        return eend;
    }

    public void setEend(String eend) {
        this.eend = eend;
    }

    public String getEgloc() {
        return egloc;
    }

    public void setEgloc(String egloc) {
        this.egloc = egloc;
    }

    public String getEcontact() {
        return econtact;
    }

    public void setEcontact(String econtact) {
        this.econtact = econtact;
    }

    public String getEaddress() {
        return eaddress;
    }

    public void setEaddress(String eaddress) {
        this.eaddress = eaddress;
    }
}
class EventAdapter extends BaseAdapter {
    Context context;
    ArrayList<Event> data = new ArrayList<>();

    public EventAdapter(Context context, ArrayList<Event> data) {
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

            TextView evname, ename, estart, eend, egloc, econtact, eaddress, eemailt;
            Button response, del;
            convertView = LayoutInflater.from(context).inflate(R.layout.eventmanager, null);
            evname = convertView.findViewById(R.id.evname);
            response = convertView.findViewById(R.id.response);
            del = convertView.findViewById(R.id.del);
            ename = convertView.findViewById(R.id.ename);
            EventManagerFragment.eemail = convertView.findViewById(R.id.eemail);
            estart = convertView.findViewById(R.id.estart);
            eend = convertView.findViewById(R.id.eend);
            econtact = convertView.findViewById(R.id.econtact);
            eaddress = convertView.findViewById(R.id.eaddress);
            ename.setText(data.get(position).eemail);
            EventManagerFragment.eemail.setText(data.get(position).evname);
            evname.setText(data.get(position).ename);
            estart.setText(data.get(position).estart);
            eend.setText(data.get(position).eend);
            econtact.setText(data.get(position).econtact);
            eaddress.setText(data.get(position).eaddress);
            EventManagerFragment.currentObj = EventManagerFragment.objlist.get(position);
            DatabaseReference mdbref;
            del.setVisibility(View.GONE);
            if (MainActivity.username.equals(EventManagerFragment.eemail.getText().toString())) {
                del.setVisibility(View.VISIBLE);
            }
            del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EventManagerFragment.mdbref.child("CreateEvent").child(EventManagerFragment.currentObj.id).setValue(null);
                }
            });
            response.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View alertview = LayoutInflater.from(context).inflate(R.layout.des, null);
                    Button done, map;
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(alertview);
                    AlertDialog alert = builder.create();
                    FloatingActionButton close;
                    close = alertview.findViewById(R.id.close);
                    done = alertview.findViewById(R.id.done);
                    map = alertview.findViewById(R.id.btngloc);
                    map.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CreateEventFragment create = new CreateEventFragment();
//                        View view= LayoutInflater.from(context).inflate(R.layout.seemap,null);
//                        WebView webVieww;
//                        webVieww=view.findViewById(R.id.seemap);
//                        webVieww.setWebViewClient(new WebViewClient());
//                        webVieww.loadUrl(data.get(position).egloc);
//                        AlertDialog.Builder builder=new AlertDialog.Builder(context);
//                        builder.setView(view);
//                        AlertDialog alert=builder.create();
//                        alert.show();
                            try {
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                                        Uri.parse(data.get(position).egloc));
                                context.startActivity(intent);
                            }
                            catch (Exception ex)
                            {
                                Intent intent=new Intent(context,EventManagerFragment.class);
                                context.startActivity(intent);
                            }
                        }
                    });
                    close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            alert.hide();
                        }
                    });
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                EditText rname, remail, rpass;
                                rname = alertview.findViewById(R.id.rname);
                                remail = alertview.findViewById(R.id.remail);
                                rpass = alertview.findViewById(R.id.rpass);
                                RadioGroup rgf, rgc, rgd;
                                rgc = alertview.findViewById(R.id.rgc);
                                rgf = alertview.findViewById(R.id.rgf);
                                rgd = alertview.findViewById(R.id.rgd);
                                int selected = rgf.getCheckedRadioButtonId();
                                RadioButton rbtn;
                                rbtn = alertview.findViewById(selected);
                                int selected1 = rgc.getCheckedRadioButtonId();
                                RadioButton rbtn1;
                                rbtn1 = alertview.findViewById(selected1);
                                int selected2 = rgd.getCheckedRadioButtonId();
                                RadioButton rbtn2;
                                rbtn2 = alertview.findViewById(selected2);
                                EventManagerFragment.mdbref = FirebaseDatabase.getInstance().getReference();
                                EventManagerFragment.auth = FirebaseAuth.getInstance();
                                EventManagerFragment.auth.signInWithEmailAndPassword(remail.getText().toString(), rpass.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        Toast.makeText(context, "Response Recorded", Toast.LENGTH_LONG).show();
                                        Map<String, String> realtime = new HashMap<>();
                                        realtime.put("Name", rname.getText().toString());
                                        realtime.put("Email", remail.getText().toString());
                                        realtime.put("Food Preference", rbtn.getText().toString());
                                        realtime.put("Coming", rbtn1.getText().toString());
                                        realtime.put("Dressing", rbtn2.getText().toString());
                                        EventManagerFragment.mdbref.child("Responses").push().setValue(realtime);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, "Invalid Information", Toast.LENGTH_LONG).show();
                                    }
                                });


                            } catch (Exception e) {
                                Toast.makeText(context, "Select Responses", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    alert.show();
                }
            });


            return convertView;



    }
}