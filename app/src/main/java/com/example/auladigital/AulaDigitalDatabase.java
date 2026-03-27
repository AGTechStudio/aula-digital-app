package com.example.auladigital;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Alumno.class, Telefono.class}, version = 1)
public abstract class AulaDigitalDatabase extends RoomDatabase {

    private static AulaDigitalDatabase instance;

    public abstract AlumnoDao alumnoDao();
    public abstract TelefonoDao telefonoDao();

    public static synchronized AulaDigitalDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AulaDigitalDatabase.class, "aula_digital_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()  // Solo para apps pequeñas o educativas
                    .build();
        }
        return instance;
    }
}
