package com.example.auladigital;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executors;

public class MeriendaRepository {

    private final MeriendaDao dao;

    public MeriendaRepository(Context context) {
        dao = AppDatabase.getInstance(context).meriendaDao();
    }

    public LiveData<MeriendaRegistro> obtenerPorFecha(String fecha) {
        return dao.obtenerPorFecha(fecha);
    }

    public LiveData<List<MeriendaRegistro>> obtenerPorMes(String mes) {
        return dao.obtenerPorMes(mes);
    }

    public void guardar(String fecha, String tipo, String alumnoId) {
        Executors.newSingleThreadExecutor().execute(() -> {
            dao.guardar(new MeriendaRegistro(fecha, tipo, alumnoId));
        });
    }
}