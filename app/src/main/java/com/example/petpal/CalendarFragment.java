package com.example.petpal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class CalendarFragment extends Fragment {

    List<AppointmentItem> appointmentItems;
    CalendarView calendarView;
    AppointmentAdapter appointmentAdapter;
    DatabaseReference ref;
    String key;
    String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
        }

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        appointmentItems = new ArrayList<AppointmentItem>();

        appointmentAdapter = new AppointmentAdapter(getContext(), appointmentItems);

        ref = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Pet").child(key).child("appointments");

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(appointmentAdapter);

        calendarView = view.findViewById(R.id.calendarView2);






        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year,month,day);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                int monthOfYear;
                if (calendar.get(Calendar.MONTH) != 12) {
                    monthOfYear = (calendar.get(Calendar.MONTH)) + 1;
                }else {
                    monthOfYear = calendar.get(Calendar.MONTH);
                };
                int exactYear = calendar.get(Calendar.YEAR);


                selectedDate = dayOfMonth + " " + monthOfYear + " " + exactYear;
                appointmentItems.clear();

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                            if(Objects.equals(dataSnapshot.getKey(), selectedDate)){
                                recyclerView.setVisibility(View.VISIBLE);
                                Log.d("test", selectedDate);
                                AppointmentItem appointmentItem = dataSnapshot.getValue(AppointmentItem.class);
                                appointmentItems.add(appointmentItem);
                            }

                        }
                        appointmentAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });



        return view;

    }



    

}