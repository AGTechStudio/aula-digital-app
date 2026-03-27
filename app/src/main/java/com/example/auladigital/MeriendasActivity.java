package com.example.auladigital;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.text.SimpleDateFormat;
import java.util.*;

public class MeriendasActivity extends AppCompatActivity {

    private MaterialCalendarView calendarView;
    private LinearLayout containerHoy, containerLista;

    private MeriendaViewModel viewModel;
    private List<Alumno> alumnos;

    private Calendar mesActual = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meriendas);

        calendarView = findViewById(R.id.calendarView);
        containerHoy = findViewById(R.id.containerHoy);
        containerLista = findViewById(R.id.containerLista);

        viewModel = new ViewModelProvider(this).get(MeriendaViewModel.class);

        alumnos = AppDatabase.getInstance(this)
                .alumnoDao()
                .obtenerTodos();

        alumnos.sort(Comparator.comparing(Alumno::getApellido));

        // 🔥 CARGA INICIAL (FIX DEL BUG)
        mesActual = Calendar.getInstance();
        cargarHoy();
        cargarMes();

        calendarView.setOnDateChangedListener((widget, date, selected) -> {

            Calendar cal = Calendar.getInstance();
            cal.set(date.getYear(), date.getMonth(), date.getDay());

            int diaSemana = cal.get(Calendar.DAY_OF_WEEK);

            if (diaSemana == Calendar.SATURDAY || diaSemana == Calendar.SUNDAY) {
                return;
            }

            mostrarDialogoSeleccion(formatFecha(cal));
        });

        calendarView.setOnMonthChangedListener((widget, date) -> {
            mesActual.set(date.getYear(), date.getMonth(), 1);
            cargarMes();
        });
    }

    private void mostrarDialogoSeleccion(String fecha) {

        List<String> opciones = new ArrayList<>();

        opciones.add("SIN ASIGNAR");
        opciones.add("FERIADO");
        opciones.add("PLENARIA");
        opciones.add("AUTO (ROTACIÓN)");

        for (Alumno a : alumnos) {
            opciones.add(formatearNombre(a));
        }

        new AlertDialog.Builder(this)
                .setTitle("Asignar merienda")
                .setItems(opciones.toArray(new String[0]), (dialog, which) -> {

                    if (which == 0) {
                        viewModel.guardar(fecha, "SIN", null);

                    } else if (which == 1) {
                        viewModel.guardar(fecha, "FERIADO", null);

                    } else if (which == 2) {
                        viewModel.guardar(fecha, "PLENARIA", null);

                    } else if (which == 3) {
                        Alumno auto = obtenerSiguienteAlumno();
                        if (auto != null) {
                            viewModel.guardar(fecha, "ALUMNO", auto.getId());
                        }

                    } else {
                        Alumno alumno = alumnos.get(which - 4);
                        viewModel.guardar(fecha, "ALUMNO", alumno.getId());
                    }

                    cargarHoy();
                    cargarMes();
                })
                .show();
    }

    // 🔥 ROTACIÓN INTELIGENTE
    private Alumno obtenerSiguienteAlumno() {

        String mes = new SimpleDateFormat("yyyy-MM", Locale.getDefault())
                .format(mesActual.getTime());

        List<MeriendaRegistro> registros = viewModel.obtenerPorMesSync(mes);

        Set<String> usados = new HashSet<>();
        for (MeriendaRegistro r : registros) {
            if ("ALUMNO".equals(r.getTipo())) {
                usados.add(r.getAlumnoId());
            }
        }

        for (Alumno a : alumnos) {
            if (!usados.contains(a.getId())) {
                return a;
            }
        }

        return alumnos.isEmpty() ? null : alumnos.get(0);
    }

    private void cargarHoy() {

        String hoy = formatFecha(Calendar.getInstance());

        viewModel.obtenerPorFecha(hoy).observe(this, registro -> {

            containerHoy.removeAllViews();

            TextView card = crearCard(registro);

            if (registro == null) {
                card.setText("HOY: SIN ASIGNAR");
            } else {
                card.setText("HOY: " + obtenerTextoRegistro(registro));
            }

            containerHoy.addView(card);
        });
    }

    private void cargarMes() {

        String mes = new SimpleDateFormat("yyyy-MM", Locale.getDefault())
                .format(mesActual.getTime());

        viewModel.obtenerPorMes(mes).observe(this, lista -> {

            containerLista.removeAllViews();

            for (MeriendaRegistro r : lista) {

                TextView card = crearCard(r);

                String texto = r.getFecha() + "  -  " + obtenerTextoRegistro(r);
                card.setText(texto);

                containerLista.addView(card);
            }
        });
    }

    private String obtenerTextoRegistro(MeriendaRegistro r) {

        switch (r.getTipo()) {
            case "FERIADO": return "FERIADO";
            case "PLENARIA": return "PLENARIA";
            case "SIN": return "SIN ASIGNAR";
            default:
                Alumno a = buscarAlumno(r.getAlumnoId());
                return a != null ? formatearNombre(a) : "";
        }
    }

    private TextView crearCard(MeriendaRegistro r) {

        TextView tv = new TextView(this);

        int color = 0xFFFFE0B2; // default naranja

        if (r != null) {
            switch (r.getTipo()) {
                case "FERIADO": color = 0xFFFFCDD2; break; // rojo suave
                case "PLENARIA": color = 0xFFBBDEFB; break; // azul
                case "ALUMNO": color = 0xFFC8E6C9; break; // verde
            }
        }

        tv.setBackgroundColor(color);
        tv.setPadding(32, 32, 32, 32);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        params.setMargins(0, 16, 0, 0);
        tv.setLayoutParams(params);

        tv.setTextSize(16);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setGravity(Gravity.CENTER_VERTICAL);

        return tv;
    }

    private String formatearNombre(Alumno a) {
        return capitalizar(a.getApellido()) + ", " + capitalizar(a.getNombre());
    }

    private String capitalizar(String texto) {
        if (texto == null || texto.isEmpty()) return "";
        return texto.substring(0,1).toUpperCase() + texto.substring(1).toLowerCase();
    }

    private Alumno buscarAlumno(String id) {
        for (Alumno a : alumnos) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    private String formatFecha(Calendar cal) {
        return String.format("%04d-%02d-%02d",
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH) + 1,
                cal.get(Calendar.DAY_OF_MONTH));
    }
}