package com.example.auladigital;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MeriendaViewModel extends AndroidViewModel {

    private final MeriendaRepository repository;

    public MeriendaViewModel(@NonNull Application application) {
        super(application);
        repository = new MeriendaRepository(application);
    }

    public LiveData<MeriendaRegistro> obtenerPorFecha(String fecha) {
        return repository.obtenerPorFecha(fecha);
    }

    public LiveData<List<MeriendaRegistro>> obtenerPorMes(String mes) {
        return repository.obtenerPorMes(mes);
    }

    public void guardar(String fecha, String tipo, String alumnoId) {
        repository.guardar(fecha, tipo, alumnoId);
    }
    public List<MeriendaRegistro> obtenerPorMesSync(String mes) {
        return AppDatabase.getInstance(getApplication())
                .meriendaDao()
                .obtenerPorMesSync(mes);
    }
}
