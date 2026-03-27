package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AnotacionDao {
    @Insert
    void insertar(Anotacion anotacion);

    @Query("SELECT * FROM anotaciones ORDER BY fecha DESC")
    List<Anotacion> obtenerTodas();

    @Query("DELETE FROM anotaciones WHERE id = :id")
    void eliminarPorId(int id);
}

