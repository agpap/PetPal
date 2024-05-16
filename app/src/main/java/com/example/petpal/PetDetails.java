package com.example.petpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PetDetails extends AppCompatActivity {

    DatabaseReference ref;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);

        Intent mainIntent = getIntent();
        key = mainIntent.getStringExtra("key");
        ref = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Pet");
        Log.d("extra", key);

        TextView nameText = findViewById(R.id.textView4);
        TextView ageText = findViewById(R.id.textView5);
        TextView sexText = findViewById(R.id.textView8);
        TextView conditionText = findViewById(R.id.textView9);
        ImageView image = findViewById(R.id.imageView);



        ref.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String age = snapshot.child("age").getValue().toString();
                String sex = snapshot.child("sex").getValue().toString();
                String condition = snapshot.child("condition").getValue().toString();
                String imageUrl = snapshot.child("image").getValue().toString();

                Log.d("ggd", "success");

                nameText.setText(name);
                ageText.setText(age + " years old");
                sexText.setText(sex);
                conditionText.setText(condition);
                Picasso.get().load(imageUrl).into(image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FloatingActionButton calendarButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        FloatingActionButton medicationButton = (FloatingActionButton) findViewById(R.id.floatingActionButton2);
        final boolean[] calendarButtonLongPressed = new boolean[1];
        final boolean[] medicationButtonLongPressed = new boolean[1];

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarButtonLongPressed[0]) {
                    fragmentChange(new AddCalendarFragment());
                    calendarButtonLongPressed[0]=false;
                }
                else {
                    fragmentChange(new CalendarFragment());
                }
            }
        });

        calendarButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fragmentChange(new AddCalendarFragment());
                calendarButtonLongPressed[0] = true;
                return false;
            }
        });

        medicationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (medicationButtonLongPressed[0]) {
                    fragmentChange(new AddMedicationFragment());
                    medicationButtonLongPressed[0]=false;
                }
                else{
                    fragmentChange(new MedicationFragment());
                }
            }
        });

        medicationButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                fragmentChange(new AddMedicationFragment());
                medicationButtonLongPressed[0] = true;
                return false;
            }
        });

    }

    public void fragmentChange(Fragment fragment){

        Bundle bundle = new Bundle();
        bundle.putString("key", key);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.linear1, fragment);
        fragmentTransaction.commit();

    }

}