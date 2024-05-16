package com.example.petpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationViewHolder> {

    Context context;
    List<MedicationItem> medicationItems;

    public MedicationAdapter(Context context, List<MedicationItem> medicationItems){
        this.context = context;
        this.medicationItems = medicationItems;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MedicationViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_medication,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        holder.medication.setText(medicationItems.get(position).getMedication());
        holder.frequency.setText(medicationItems.get(position).getFrequency());
    }

    @Override
    public int getItemCount() {
        return medicationItems.size();
    }

}
