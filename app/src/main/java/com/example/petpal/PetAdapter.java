package com.example.petpal;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetViewHolder> {

    Context context;
    ArrayList<PetItem> petItems;


    public PetAdapter(Context context, ArrayList<PetItem> petItems){
        this.context = context;
        this.petItems = petItems;

    }



    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.recycler_view_pet,parent,false);



       return new PetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        PetItem petItem = petItems.get(position);

        holder.name.setText(petItem.getName());
        holder.animal.setText(petItem.getAnimal());



        DatabaseReference petRef = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/").getReference("Pet").child(petItem.getName());
        petRef.child("image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String imageUrl = snapshot.getValue(String.class);
                    Picasso.get().load(imageUrl).into(holder.image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = petItem.getName();
                Intent detailsIntent = new Intent(view.getContext(), PetDetails.class);
                detailsIntent.putExtra("key", name);
                view.getContext().startActivity(detailsIntent);
                Log.d("key", name);
            }
        });



    }

    @Override
    public int getItemCount() {
        return petItems.size();
    }


}
