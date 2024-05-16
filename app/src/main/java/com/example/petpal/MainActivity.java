package com.example.petpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseReference ref;
    RecyclerView recyclerView;
    ArrayList<PetItem> petItems;

    PetAdapter petAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        recyclerView = findViewById(R.id.gg);
        ref = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Pet");
        recyclerView.setHasFixedSize(true);

        petItems = new ArrayList<PetItem>();


        petAdapter = new PetAdapter(this, petItems);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(petAdapter);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                petItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    PetItem petItem = dataSnapshot.getValue(PetItem.class);
                    petItems.add(petItem);

                }

                petAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



    public void addButton(View view) {
        Button addButton = findViewById(R.id.button);

        if (addButton.isEnabled()) {

            Intent addIntent = new Intent(this, AddPet.class);
            startActivity(addIntent);
        }

    }
}

