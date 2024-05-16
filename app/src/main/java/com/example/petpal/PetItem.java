package com.example.petpal;

public class PetItem {
    String name;
    String animal;
    String image;

    public PetItem(){

    }

    public PetItem(String name, String animal, String image) {
        this.name = name;
        this.animal = animal;
        this.image = image;

    }

    public String getName() {
        return name;
    }



    public String getAnimal() {
        return animal;
    }

    public String getImage() {
        return image;
    }
}
