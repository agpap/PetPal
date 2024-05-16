package com.example.petpal;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class AddMedicationFragment extends Fragment {

    TextView medicationInput;
    TextView frequencyInput;
    String key;
    Bundle bundle;
    Button addButton;
    DatabaseReference ref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_medication, container, false);

        bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
        }

        ref = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Pet");

        addButton = view.findViewById(R.id.button6);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicationInput = getActivity().findViewById(R.id.editTextText3);
                frequencyInput = getActivity().findViewById(R.id.editTextText4);

                String medication = medicationInput.getText().toString();
                String frequency = frequencyInput.getText().toString();

                ref.child(key).child("medicines").child(medication).child("medication").setValue(medication);
                ref.child(key).child("medicines").child(medication).child("frequency").setValue(frequency);


            }
        });



        return view;
    }


}