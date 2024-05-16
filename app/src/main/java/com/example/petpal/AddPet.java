package com.example.petpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPet extends AppCompatActivity {


    private DatabaseReference PetRef;
    private DatabaseReference Animal;
    private DatabaseReference Age;
    private DatabaseReference Sex;
    private DatabaseReference Condition;
    FirebaseStorage storage;
    StorageReference storageRef;
    Button addButton;
    Spinner animalSpinner;
    Spinner sexSpinner;
    Spinner conditionSpinner;
    TextView nameText;
    TextView ageNum;
    Button pictureButton;
    String name;
    ImageView imageView;
    UploadTask uploadTask;
    ByteArrayOutputStream stream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pet);

        addButton = findViewById(R.id.button3);
        pictureButton = findViewById(R.id.button5);

        animalSpinner = findViewById(R.id.spinner);
        sexSpinner = findViewById(R.id.spinner2);
        conditionSpinner = findViewById(R.id.spinner3);
        nameText = findViewById(R.id.editTextText);
        ageNum = findViewById(R.id.editTextNumber);
        imageView = findViewById(R.id.imageButton2);

        FirebaseDatabase petDatabase = FirebaseDatabase.getInstance("https://petpal-16434-default-rtdb.europe-west1.firebasedatabase.app/");

        PetRef = petDatabase.getReference().child("Pet");




        addButton.setOnClickListener(view -> {

            String animal = animalSpinner.getSelectedItem().toString();
            String sex = sexSpinner.getSelectedItem().toString();
            String condition = conditionSpinner.getSelectedItem().toString();
            name = nameText.getText().toString();
            String ageText = ageNum.getText().toString();


            PetRef.child(name).child("name").setValue(name);
            PetRef.child(name).child("animal").setValue(animal);
            PetRef.child(name).child("age").setValue(ageText);
            PetRef.child(name).child("sex").setValue(sex);
            PetRef.child(name).child("condition").setValue(condition);

            storage = FirebaseStorage.getInstance();
            storageRef = storage.getReference().child("images").child(name);

            uploadTask = storageRef.putBytes(stream.toByteArray());

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        // Image upload is successful
                        // Get the download URL of the uploaded image
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Uri contains the download URL of the image
                                String imageUrl = uri.toString();
                                PetRef.child(name).child("image").setValue(imageUrl);

                                // Save the imageUrl to the Firebase Realtime Database or perform any other operations you require
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to get the download URL
                            }
                        });
                    } else {
                        // Image upload failed
                    }
                }
            });

            Intent intent = new Intent(view.getContext(), MainActivity.class);
            startActivity(intent);
        });

        pictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });

    }



    public void chooseImage() {
        final CharSequence[] optionsMenu = {"Take Photo", "Choose from Gallery", "Exit"}; // create a menuOption Array
        // create a dialog for showing the optionsMenu
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // set the items in builder
        builder.setItems(optionsMenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (optionsMenu[i].equals("Take Photo")) {
                    // Open the camera and get the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                } else if (optionsMenu[i].equals("Choose from Gallery")) {
                    // choose from  external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                } else if (optionsMenu[i].equals("Exit")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        stream = new ByteArrayOutputStream();
                        imageView.setImageBitmap(selectedImage);

                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);




                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri imageUri = data.getData();
                        imageView.setImageURI(imageUri);
                        InputStream input = null;
                        try {
                            input = getContentResolver().openInputStream(imageUri);
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                        Bitmap selectedImage = BitmapFactory.decodeStream(input);
                        stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);


                    }
                    break;
            }
        }
    }
}



