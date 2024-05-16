package com.example.petpal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MedicationViewHolder extends RecyclerView.ViewHolder {

    TextView medication, frequency;

    public MedicationViewHolder(@NonNull View itemView) {
        super(itemView);
        medication = itemView.findViewById(R.id.medication);
        frequency = itemView.findViewById(R.id.frequency);
    }
}
