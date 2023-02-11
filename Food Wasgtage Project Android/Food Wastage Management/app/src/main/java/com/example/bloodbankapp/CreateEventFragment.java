package com.example.bloodbankapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import static android.content.Context.NETWORK_STATS_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateEventFragment extends Fragment {
    DatabaseReference mdbref;
    int cyear, cmonth, cday;
    int chour, cmin;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CreateEventFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateEventFragment newInstance(String param1, String param2) {
        CreateEventFragment fragment = new CreateEventFragment();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vieww = inflater.inflate(R.layout.fragment_create_event, container, false);
        EditText ename, eemail, evname, estart, eend, egloc, econtact, eaddress;
        mdbref = FirebaseDatabase.getInstance().getReference();
        FloatingActionButton generate;
        evname = vieww.findViewById(R.id.eventname);
        estart = vieww.findViewById(R.id.eventstart);
        eemail = vieww.findViewById(R.id.eemail);
        ename = vieww.findViewById(R.id.ename);
        eend = vieww.findViewById(R.id.eventend);
        egloc = vieww.findViewById(R.id.eventmap);
        econtact = vieww.findViewById(R.id.eventcontact);
        eaddress = vieww.findViewById(R.id.eventaddress);
        generate = vieww.findViewById(R.id.geb);

        egloc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getActivity()).inflate(R.layout.createmap, null);
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps"));
                startActivity(intent);
            }
        });
        eend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                cyear = calendar.get(Calendar.YEAR);
                cmonth = calendar.get(Calendar.MONTH);
                cday = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        eend.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, cyear, cmonth, cday);
                datePickerDialog.show();

            }
        });
        estart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                chour = calendar.get(Calendar.HOUR_OF_DAY);
                cmin = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        estart.setText(hourOfDay + ":" + minute);
                    }
                }, chour, cmin, false);
                timePickerDialog.show();
            }
        });
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ename.getText().toString().equals("") && !eemail.getText().toString().equals("") && !evname.getText().toString().equals("")
                        && !estart.getText().toString().equals("") && !eend.getText().toString().equals("") && !egloc.getText().toString().equals("")
                        && !econtact.getText().toString().equals("") && !eaddress.getText().toString().equals("")) {
                    Map<String, String> realtime = new HashMap<>();
                    realtime.put("Username", ename.getText().toString());
                    realtime.put("EventEmail", eemail.getText().toString());
                    realtime.put("EventName", evname.getText().toString());
                    realtime.put("EventStart", estart.getText().toString());
                    realtime.put("EventEnd", eend.getText().toString());
                    realtime.put("EventLocation", egloc.getText().toString());
                    realtime.put("EventContact", econtact.getText().toString());
                    realtime.put("EventAddress", eaddress.getText().toString());
                    mdbref.child("CreateEvent").push().setValue(realtime);
                    Toast.makeText(getActivity(), "Event Generated Successfully", Toast.LENGTH_LONG).show();
                    NotificationManager mNotificationManager;

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getContext(), "notify_001");


                    NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                    bigText.setBigContentTitle("Event Generated");
                    bigText.setSummaryText("Done");

                    mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
                    mBuilder.setContentTitle("Events Successfully Generated");
                    mBuilder.setContentText("Thanking You for making here an event");
                    mBuilder.setPriority(Notification.PRIORITY_MAX);
                    mBuilder.setStyle(bigText);

                    mNotificationManager =
                            (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    {
                        String channelId = "Your_channel_id";
                        NotificationChannel channel = new NotificationChannel(
                                channelId,
                                "Channel human readable title",
                                NotificationManager.IMPORTANCE_HIGH);
                        mNotificationManager.createNotificationChannel(channel);
                        mBuilder.setChannelId(channelId);
                    }

                    mNotificationManager.notify(0, mBuilder.build());
                } else {
                    Toast.makeText(getActivity(), "Field Required", Toast.LENGTH_LONG).show();
                }
            }
        });
        return vieww;
    }




}