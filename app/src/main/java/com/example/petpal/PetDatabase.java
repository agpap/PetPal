package com.example.petpal;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.SearchView;

public class PetDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "pet_database.db";
    private static final String TABLE_NAME = "pet_table";
    private static final String TABLE2_NAME = "appointment_table";
    private static final String TABLE3_NAME = "medication_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_ANIMAL = "animal";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_SEX = "sex";
    private static final String COLUMN_CONDITION = "condition";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";
    private static final String COLUMN_APPOINTMENT = "appointment";
    private static final String COLUMN_MEDICATION = "medication";
    private static final String COLUMN_FREQUENCY = "frequency";

    public PetDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


   // @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ANIMAL + " TEXT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AGE + " INT, "
                + COLUMN_SEX + " TEXT, "
                + COLUMN_CONDITION + " TEXT, "
                + COLUMN_IMAGE + " BLOB );";
        db.execSQL(createTableQuery);

        String createTableAppointments = "CREATE TABLE " + TABLE2_NAME + " ("

                + COLUMN_DATE + " TEXT PRIMARY KEY, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_APPOINTMENT + " TEXT, "
                + COLUMN_ID + " INTEGER , "
                + " FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+TABLE_NAME+"("+COLUMN_ID+"))";
        db.execSQL(createTableAppointments);

        String createTableMedication = "CREATE TABLE " + TABLE3_NAME + " ("
                + COLUMN_MEDICATION + "TEXT PRIMARY KEY, "
                + COLUMN_FREQUENCY + "TEXT, "
                + " FOREIGN KEY ("+COLUMN_ID+") REFERENCES "+TABLE_NAME+"("+COLUMN_ID+"))";
        db.execSQL(createTableMedication);
    }

   // @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // handle database upgrade if needed
    }

    public long insertData(String animal,String name, int age, String sex, String condition, byte[] byteArray){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("animal", animal);
        values.put("name", name);
        values.put("age", age);
        values.put("sex", sex);
        values.put("condition", condition);
        values.put("image", byteArray);
        return db.insert("pet_table", null, values);
    }

    public long insertAppointment(String date, String time, String appointment){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("animal", date);
        values.put("name", time);
        values.put("age", appointment);
        return db.insert(TABLE2_NAME, null, values);
    }

    public long insertMedication(String medication, String frequency){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("medication", medication);
        values.put("frequency", frequency);
        return db.insert(TABLE3_NAME, null, values);
    }


}
