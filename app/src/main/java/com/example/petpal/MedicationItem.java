package com.example.petpal;

public class MedicationItem {
    String medication;
    String frequency;

    public MedicationItem() {

    }

    public MedicationItem(String medication, String frequency){
        this.medication = medication;
        this.frequency = frequency;
    }

    public String getMedication() {
        return medication;
    }



    public String getFrequency(){
        return frequency;
    }


}
