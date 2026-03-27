package com.example.auladigital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DetalleAlumnoActivity extends AppCompatActivity {

    private TextView tvNombre, tvCurso, tvDni, tvFechaNacimiento, tvObservaciones;
    private RecyclerView rvTelefonos;
    private AppDatabase db;
    private Alumno alumno;
    private List<Telefono> telefonos;
    private TelefonoAdapter telefonoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumno);

        tvNombre = findViewById(R.id.tvNombre);
        tvCurso = findViewById(R.id.tvCurso);
        tvDni = findViewById(R.id.tvDni);
        tvFechaNacimiento = findViewById(R.id.tvFechaNacimiento);
        tvObservaciones = findViewById(R.id.tvObservaciones);
        rvTelefonos = findViewById(R.id.rvTelefonos);

        db = AppDatabase.getInstance(this);

        String alumnoId = getIntent().getStringExtra("alumnoId");
        alumno = db.alumnoDao().obtenerPorId(alumnoId);

        if (alumno == null) {
            Toast.makeText(this, "Alumno no encontrado", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarDatos();
    }

    private void cargarDatos() {
        tvNombre.setText(alumno.getNombre() + " " + alumno.getApellido());
        tvCurso.setText("Curso: " + alumno.getCurso());
        tvDni.setText("DNI: " + alumno.getDni());
        tvFechaNacimiento.setText("Fecha de Nacimiento: " + alumno.getFechaNacimiento());
        tvObservaciones.setText("Observaciones: " + alumno.getObservaciones());

        telefonos = db.telefonoDao().obtenerPorAlumno(alumno.getId());
        rvTelefonos.setLayoutManager(new LinearLayoutManager(this));
        telefonoAdapter = new TelefonoAdapter(telefonos, this, posicion -> {
            // Acción de eliminar teléfono:
            Telefono telefonoAEliminar = telefonos.get(posicion);
            db.telefonoDao().delete(telefonoAEliminar);
            telefonos.remove(posicion);
            telefonoAdapter.notifyItemRemoved(posicion);
            Toast.makeText(this, "Teléfono eliminado", Toast.LENGTH_SHORT).show();
        });

        rvTelefonos.setAdapter(telefonoAdapter);
    }
}



