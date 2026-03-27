package com.example.auladigital;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ObservacionBitacoraDao {

    @Insert
    void insert(ObservacionBitacora observacion);

    @Update
    void update(ObservacionBitacora observacion); // ✅ Agregado para editar

    @Delete
    void delete(ObservacionBitacora observacion);

    @Query("SELECT * FROM ObservacionBitacora ORDER BY fecha DESC")
    List<ObservacionBitacora> obtenerTodas();

    @Query("SELECT * FROM ObservacionBitacora WHERE titulo LIKE '%' || :textoBusqueda || '%' ORDER BY fecha DESC")
    List<ObservacionBitacora> buscarPorTitulo(String textoBusqueda);

    @Query("SELECT * FROM ObservacionBitacora WHERE id = :id")
    ObservacionBitacora buscarPorId(String id); // ✅ Agregado para ver detalle

    @Query("SELECT * FROM ObservacionBitacora WHERE id = :id")
    ObservacionBitacora buscarPorId(int id);

}

