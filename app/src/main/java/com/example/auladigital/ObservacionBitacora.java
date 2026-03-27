package com.example.auladigital;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ObservacionBitacora {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fecha;
    private String titulo;
    private String observacion;

    // Constructor
    public ObservacionBitacora(String fecha, String titulo, String observacion) {
        this.fecha = fecha;
        this.titulo = titulo;
        this.observacion = observacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}

