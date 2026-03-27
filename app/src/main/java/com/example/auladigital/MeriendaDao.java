package com.example.auladigital;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MeriendaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void guardar(MeriendaRegistro registro);

    @Query("SELECT * FROM merienda_registros WHERE fecha = :fecha LIMIT 1")
    LiveData<MeriendaRegistro> obtenerPorFecha(String fecha);

    @Query("SELECT * FROM merienda_registros WHERE fecha LIKE :mes || '%'")
    LiveData<List<MeriendaRegistro>> obtenerPorMes(String mes);
    @Query("SELECT * FROM merienda_registros WHERE fecha LIKE :mes || '%'")
    List<MeriendaRegistro> obtenerPorMesSync(String mes);
}
