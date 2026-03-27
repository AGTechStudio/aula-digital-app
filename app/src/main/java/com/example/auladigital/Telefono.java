package com.example.auladigital;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class Telefono implements Serializable {

    @PrimaryKey
    @NonNull
    private String id;

    private String numero;
    private String referencia;
    private String alumnoId;

    public Telefono(@NonNull String id, String numero, String referencia, String alumnoId) {
        this.id = id;
        this.numero = numero;
        this.referencia = referencia;
        this.alumnoId = alumnoId;
    }

    @NonNull
    public String getId() { return id; }
    public String getNumero() { return numero; }
    public String getReferencia() { return referencia; }
    public String getAlumnoId() { return alumnoId; }

    public void setId(@NonNull String id) { this.id = id; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setReferencia(String referencia) { this.referencia = referencia; }
    public void setAlumnoId(String alumnoId) { this.alumnoId = alumnoId; }
}


