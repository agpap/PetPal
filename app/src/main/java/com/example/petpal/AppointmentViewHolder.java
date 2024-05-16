package com.example.petpal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AppointmentViewHolder extends RecyclerView.ViewHolder {

    TextView time, appointment;

    public AppointmentViewHolder(@NonNull View itemView) {
        super(itemView);
        time = itemView.findViewById(R.id.time);
        appointment = itemView.findViewById(R.id.appointment);
    }
}
