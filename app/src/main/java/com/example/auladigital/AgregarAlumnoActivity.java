package com.example.auladigital;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.UUID;

public class AgregarAlumnoActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etCurso, etDni, etFechaNacimiento, etObservaciones, etTelefono;
    private Button btnGuardar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_alumno);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etCurso = findViewById(R.id.etCurso);
        etDni = findViewById(R.id.etDni);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etObservaciones = findViewById(R.id.etObservaciones);
        etTelefono = findViewById(R.id.etTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = AppDatabase.getInstance(this);

        // Calendario para Fecha de Nacimiento
        etFechaNacimiento.setOnClickListener(v -> mostrarDatePicker());

        btnGuardar.setOnClickListener(v -> guardarAlumno());
    }

    private void mostrarDatePicker() {
        final Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year, month, dayOfMonth) -> {
                    String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                    etFechaNacimiento.setText(fechaSeleccionada);
                },
                año, mes, dia
        );

        datePickerDialog.show();
    }

    private void guardarAlumno() {
        String id = UUID.randomUUID().toString();
        String nombre = etNombre.getText().toString().trim();
        String apellido = etApellido.getText().toString().trim();
        String curso = etCurso.getText().toString().trim();
        String dni = etDni.getText().toString().trim();
        String fechaNacimiento = etFechaNacimiento.getText().toString().trim();
        String observaciones = etObservaciones.getText().toString().trim();
        String telefono = etTelefono.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || curso.isEmpty() || fechaNacimiento.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Alumno alumno = new Alumno(id, nombre, apellido, curso, dni, fechaNacimiento, observaciones);
        db.alumnoDao().insert(alumno);

        if (!telefono.isEmpty()) {
            Telefono tel = new Telefono(UUID.randomUUID().toString(), telefono, "", id);
            db.telefonoDao().insert(tel);
        }

        Toast.makeText(this, "Alumno guardado", Toast.LENGTH_SHORT).show();
        finish();
    }
}

