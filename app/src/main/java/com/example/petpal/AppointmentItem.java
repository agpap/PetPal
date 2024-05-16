package com.example.petpal;

public class AppointmentItem {
    String time;
    String appointment;

    public AppointmentItem(){

    }

    public AppointmentItem(String time, String appointment) {
        this.time = time;
        this.appointment = appointment;
    }

    public String getTime() {
        return time;
    }

    public String getAppointment() {
        return appointment;
    }


}
