package com.example.auladigital;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.Nullable;

@Entity
public class Entrevista {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String fecha;
    private String hora;
    private String detalle;
    private String resultado;
    private String imagenUri;  // URI de la foto (opcional)

    public Entrevista(String fecha, String hora, String detalle, String resultado, @Nullable String imagenUri) {
        this.fecha = fecha;
        this.hora = hora;
        this.detalle = detalle;
        this.resultado = resultado;
        this.imagenUri = imagenUri;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getDetalle() { return detalle; }
    public void setDetalle(String detalle) { this.detalle = detalle; }

    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }

    public String getImagenUri() { return imagenUri; }
    public void setImagenUri(String imagenUri) { this.imagenUri = imagenUri; }
}


