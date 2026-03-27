package com.example.auladigital;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Alumno implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String nombre;
    private String apellido;
    private String curso;
    private String dni;
    private String fechaNacimiento;
    private String observaciones;

    public Alumno(@NonNull String id, String nombre, String apellido, String curso, String dni, String fechaNacimiento, String observaciones) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.curso = curso;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.observaciones = observaciones;
    }

    @NonNull
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCurso() { return curso; }
    public String getDni() { return dni; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getObservaciones() { return observaciones; }

    public void setId(@NonNull String id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setCurso(String curso) { this.curso = curso; }
    public void setDni(String dni) { this.dni = dni; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}


