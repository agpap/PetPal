package com.example.petpal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MedicationFragment extends Fragment {

    DatabaseReference ref;
    RecyclerView recyclerView;
    List<MedicationItem> medicationItems;
    MedicationAdapter medicationAdapter;
    String key;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medication, container, false);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            key = bundle.getString("key");
            Log.d("fragput", key);
        }

        recyclerView = view.findViewById(R.id.recyclerView2);
        medicationItems = new ArrayList<MedicationItem>();

        ref = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Pet").child(key).child("medicines");
        recyclerView.setHasFixedSize(true);
        medicationAdapter = new MedicationAdapter(view.getContext(), medicationItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(medicationAdapter);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    MedicationItem medicationItem = dataSnapshot.getValue(MedicationItem.class);
                    medicationItems.add(medicationItem);
                }
                medicationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        return view;
    }

}