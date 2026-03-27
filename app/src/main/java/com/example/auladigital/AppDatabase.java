package com.example.auladigital;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {
        Alumno.class,
        Telefono.class,
        Anotacion.class,
        ObservacionBitacora.class,
        Observacion.class,
        Entrevista.class,
        MeriendaRegistro.class,
        Planificacion.class // ✅ AGREGADO
}, version = 7, exportSchema = false) // ✅ SUBIMOS VERSION
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract AlumnoDao alumnoDao();
    public abstract TelefonoDao telefonoDao();
    public abstract AnotacionDao anotacionDao();
    public abstract ObservacionDao observacionDao();
    public abstract ObservacionBitacoraDao observacionBitacoraDao();
    public abstract EntrevistaDao entrevistaDao();
    public abstract MeriendaDao meriendaDao();

    public abstract PlanificacionDao planificacionDao(); // ✅ NUEVO

    public static synchronized AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "aula_database"
                    )
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }
}









