package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PlanificacionDao {

    @Insert
    void insertar(Planificacion planificacion);

    @Update
    void actualizar(Planificacion planificacion);

    @Delete
    void eliminar(Planificacion planificacion);

    @Query("SELECT * FROM planificaciones ORDER BY fechaSubida DESC")
    List<Planificacion> obtenerTodas();
}
