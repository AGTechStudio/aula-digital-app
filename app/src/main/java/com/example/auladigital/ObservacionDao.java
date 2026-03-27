package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObservacionDao {

    @Insert
    void insert(Observacion observacion);

    @Update
    void update(Observacion observacion);

    @Delete
    void delete(Observacion observacion);

    @Query("SELECT * FROM Observacion WHERE alumnoId = :alumnoId")
    List<Observacion> obtenerPorAlumno(String alumnoId);
}



