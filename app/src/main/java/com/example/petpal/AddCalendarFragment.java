package com.example.petpal;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddCalendarFragment extends Fragment {

    DatabaseReference appointmentRef;
    FirebaseDatabase petDatabase;
    Spinner daySpinner;
    Spinner monthSpinner;
    Spinner yearSpinner;
    TextView timeText;
    TextView appointmentText;
    Button addButton;
    Bundle bundle;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_calendar, container, false);

        bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
            Log.d("fragput", key);
        }

        addButton = view.findViewById(R.id.button4);
        petDatabase = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/");
        appointmentRef = petDatabase.getReference().child("Pet");

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daySpinner = getActivity().findViewById(R.id.spinner4);
                monthSpinner = getActivity().findViewById(R.id.spinner5);
                yearSpinner = getActivity().findViewById(R.id.spinner6);
                timeText = getActivity().findViewById(R.id.editTextTime);
                appointmentText = getActivity().findViewById(R.id.editTextText2);

                String date = daySpinner.getSelectedItem().toString() + " " + monthSpinner.getSelectedItem().toString() + " " + yearSpinner.getSelectedItem().toString();
                String time = timeText.getText().toString();
                String appointment = appointmentText.getText().toString();

                //Log.d("calendar", date + time + appointment);

                appointmentRef.child(key).child("appointments").child(date).child("time").setValue(time);
                appointmentRef.child(key).child("appointments").child(date).child("appointment").setValue(appointment);

            }

        });

        return view;
    }

}