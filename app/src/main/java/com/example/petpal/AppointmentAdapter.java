package com.example.petpal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentViewHolder> {

    Context context;
    List<AppointmentItem> appointmentItems;

    public AppointmentAdapter(Context context, List<AppointmentItem> appointmentItems) {
        this.context = context;
        this.appointmentItems = appointmentItems;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AppointmentViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_view_appointment,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        holder.time.setText(appointmentItems.get(position).getTime());
        holder.appointment.setText(appointmentItems.get(position).getAppointment());
    }

    @Override
    public int getItemCount() {
        return appointmentItems.size();
    }
}
