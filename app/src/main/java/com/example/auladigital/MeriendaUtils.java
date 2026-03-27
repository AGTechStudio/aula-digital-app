package com.example.auladigital;

import java.text.SimpleDateFormat;
import java.util.*;

public class MeriendaUtils {

    public static Map<String, Alumno> generarCalendarioMeriendas(List<Alumno> alumnos, Set<String> feriados, Map<String, String> cumpleaños, int anio) {
        Map<String, Alumno> calendario = new LinkedHashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        // Ordenar alumnos alfabéticamente por apellido
        alumnos.sort(Comparator.comparing(Alumno::getApellido));

        Calendar calendar = Calendar.getInstance();
        calendar.set(anio, Calendar.JANUARY, 1);

        int indexAlumno = 0;

        while (calendar.get(Calendar.YEAR) == anio) {
            String fechaActual = sdf.format(calendar.getTime());
            int diaSemana = calendar.get(Calendar.DAY_OF_WEEK);

            // Saltar sábados, domingos o feriados
            if (diaSemana != Calendar.SATURDAY && diaSemana != Calendar.SUNDAY && !feriados.contains(fechaActual)) {
                // Ver si algún alumno cumple años hoy
                String cumpleNombre = cumpleaños.get(fechaActual);
                Alumno cumpleaniero = null;
                if (cumpleNombre != null) {
                    for (Alumno a : alumnos) {
                        if ((a.getNombre() + " " + a.getApellido()).equals(cumpleNombre)) {
                            cumpleaniero = a;
                            break;
                        }
                    }
                }

                if (cumpleaniero != null) {
                    calendario.put(fechaActual, cumpleaniero);
                } else {
                    Alumno actual = alumnos.get(indexAlumno % alumnos.size());
                    calendario.put(fechaActual, actual);
                    indexAlumno++;
                }
            }

            calendar.add(Calendar.DATE, 1);
        }

        return calendario;
    }
}
