package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface EntrevistaDao {

    @Insert
    void insert(Entrevista entrevista);

    @Update
    void update(Entrevista entrevista);

    @Delete
    void delete(Entrevista entrevista);

    @Query("SELECT * FROM Entrevista WHERE fecha >= DATE('now') ORDER BY fecha, hora")
    List<Entrevista> obtenerProximas();

    @Query("SELECT * FROM Entrevista WHERE fecha < DATE('now') ORDER BY fecha DESC, hora DESC")
    List<Entrevista> obtenerRealizadas();

    @Query("SELECT * FROM Entrevista WHERE fecha = :fecha ORDER BY hora")
    List<Entrevista> buscarPorFecha(String fecha);

    @Query("SELECT * FROM Entrevista ORDER BY fecha, hora")
    List<Entrevista> obtenerTodas();

    @Query("SELECT * FROM Entrevista WHERE id = :id")
    Entrevista buscarPorId(int id);
}




