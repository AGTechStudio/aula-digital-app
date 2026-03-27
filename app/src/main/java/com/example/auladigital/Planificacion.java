package com.example.auladigital;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "planificaciones")
public class Planificacion {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombreArchivo;
    private String rutaArchivo;
    private String anio;
    private String mes;
    private String fechaSubida;

    public Planificacion(String nombreArchivo, String rutaArchivo, String anio, String mes, String fechaSubida) {
        this.nombreArchivo = nombreArchivo;
        this.rutaArchivo = rutaArchivo;
        this.anio = anio;
        this.mes = mes;
        this.fechaSubida = fechaSubida;
    }

    // GETTERS
    public int getId() { return id; }
    public String getNombreArchivo() { return nombreArchivo; }
    public String getRutaArchivo() { return rutaArchivo; }
    public String getAnio() { return anio; }
    public String getMes() { return mes; }
    public String getFechaSubida() { return fechaSubida; }

    // SETTERS
    public void setId(int id) { this.id = id; }
    public void setNombreArchivo(String nombreArchivo) { this.nombreArchivo = nombreArchivo; }
    public void setRutaArchivo(String rutaArchivo) { this.rutaArchivo = rutaArchivo; }
    public void setAnio(String anio) { this.anio = anio; }
    public void setMes(String mes) { this.mes = mes; }
    public void setFechaSubida(String fechaSubida) { this.fechaSubida = fechaSubida; }
}