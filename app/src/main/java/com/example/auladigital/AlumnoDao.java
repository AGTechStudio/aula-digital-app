package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AlumnoDao {

    @Insert
    void insert(Alumno alumno);

    @Update
    void update(Alumno alumno);

    @Delete
    void delete(Alumno alumno);

    @Query("SELECT * FROM Alumno WHERE id = :id LIMIT 1")
    Alumno obtenerPorId(String id);

    @Query("SELECT * FROM Alumno")
    List<Alumno> obtenerTodos();
}




