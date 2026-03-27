package com.example.auladigital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

public class AlumnosActivity extends AppCompatActivity {

    private RecyclerView rvAlumnos;
    private Button btnAgregarAlumno, btnEliminarAlumno, btnVerDetalle;
    private AlumnoAdapter adapter;
    private AppDatabase db;
    private List<Alumno> listaAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumnos);

        db = AppDatabase.getInstance(this);

        rvAlumnos = findViewById(R.id.rvAlumnos);
        btnAgregarAlumno = findViewById(R.id.btnAgregarAlumno);
        btnEliminarAlumno = findViewById(R.id.btnEliminarAlumno);
        btnVerDetalle = findViewById(R.id.btnVerDetalle);

        btnEliminarAlumno.setEnabled(false);
        btnVerDetalle.setEnabled(false);

        listaAlumnos = db.alumnoDao().obtenerTodos();

        adapter = new AlumnoAdapter(listaAlumnos, selectedCount -> {
            btnEliminarAlumno.setEnabled(selectedCount > 0);
            btnVerDetalle.setEnabled(selectedCount == 1); // Solo habilita Ver Detalle si hay 1 seleccionado
        });

        rvAlumnos.setLayoutManager(new LinearLayoutManager(this));
        rvAlumnos.setAdapter(adapter);

        btnAgregarAlumno.setOnClickListener(v -> {
            startActivity(new Intent(this, AgregarAlumnoActivity.class));
        });

        btnEliminarAlumno.setOnClickListener(v -> {
            Set<Alumno> seleccionados = adapter.getSeleccionados();
            if (!seleccionados.isEmpty()) {
                for (Alumno a : seleccionados) {
                    db.alumnoDao().delete(a);
                    listaAlumnos.remove(a);
                }
                adapter.limpiarSeleccion();
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Alumnos eliminados", Toast.LENGTH_SHORT).show();
                btnEliminarAlumno.setEnabled(false);
                btnVerDetalle.setEnabled(false);
            }
        });

        btnVerDetalle.setOnClickListener(v -> {
            Alumno alumnoSeleccionado = adapter.getSeleccionados().iterator().next();
            Intent intent = new Intent(this, DetalleAlumnoActivity.class);
            intent.putExtra("alumnoId", alumnoSeleccionado.getId());
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listaAlumnos.clear();
        listaAlumnos.addAll(db.alumnoDao().obtenerTodos());
        adapter.limpiarSeleccion();
        adapter.notifyDataSetChanged();
        btnEliminarAlumno.setEnabled(false);
        btnVerDetalle.setEnabled(false);
    }
}





