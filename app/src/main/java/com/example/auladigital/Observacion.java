package com.example.auladigital;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Observacion implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String alumnoId;
    private String fecha;
    private String descripcion;

    public Observacion(@NonNull String id, String alumnoId, String fecha, String descripcion) {
        this.id = id;
        this.alumnoId = alumnoId;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getAlumnoId() {
        return alumnoId;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId = alumnoId;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}


