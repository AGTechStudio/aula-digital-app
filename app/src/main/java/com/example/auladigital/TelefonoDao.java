package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface TelefonoDao {

    @Insert
    void insert(Telefono telefono);

    @Update
    void update(Telefono telefono);

    @Delete
    void delete(Telefono telefono);

    @Query("SELECT * FROM Telefono WHERE alumnoId = :alumnoId")
    List<Telefono> obtenerPorAlumno(String alumnoId);
}


