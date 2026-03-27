package com.example.auladigital;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class AlumnoConTelefonos {
    @Embedded
    public Alumno alumno;

    @Relation(
            parentColumn = "id",
            entityColumn = "alumnoId"
    )
    public List<Telefono> telefonos;
}
