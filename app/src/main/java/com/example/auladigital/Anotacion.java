package com.example.auladigital;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "anotaciones")
public class Anotacion implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fecha;
    private String descripcion;
    private String tipo;

    // Constructor
    public Anotacion(String fecha, String descripcion, String tipo) {
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}

