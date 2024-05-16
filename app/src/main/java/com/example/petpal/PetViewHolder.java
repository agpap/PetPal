package com.example.petpal;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PetViewHolder extends RecyclerView.ViewHolder {

    TextView name, animal;
    ImageView image;

    public PetViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.textView15);
        animal = itemView.findViewById(R.id.textView16);
        image = itemView.findViewById(R.id.imageButton3);

    }


}
