package com.example.auladigital;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "merienda_registros")
public class MeriendaRegistro {

    @PrimaryKey
    @NonNull
    private String fecha; // yyyy-MM-dd

    @NonNull
    private String tipo; // "FERIADO", "PLENARIA", "ALUMNO"

    private String alumnoId; // null si no es alumno

    public MeriendaRegistro(@NonNull String fecha, @NonNull String tipo, String alumnoId) {
        this.fecha = fecha;
        this.tipo = tipo;
        this.alumnoId = alumnoId;
    }

    @NonNull
    public String getFecha() {
        return fecha;
    }

    public void setFecha(@NonNull String fecha) {
        this.fecha = fecha;
    }

    @NonNull
    public String getTipo() {
        return tipo;
    }

    public void setTipo(@NonNull String tipo) {
        this.tipo = tipo;
    }

    public String getAlumnoId() {
        return alumnoId;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }
}

