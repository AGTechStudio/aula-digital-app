package com.example.auladigital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditarAlumnoActivity extends AppCompatActivity {

    private EditText etNombre, etApellido, etDni, etFechaNacimiento, etObservaciones;
    private Spinner spinnerCurso;
    private Button btnGuardar, btnEditarTelefonos;

    private AppDatabase db;
    private Alumno alumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_alumno);

        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        etDni = findViewById(R.id.etDni);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etObservaciones = findViewById(R.id.etObservaciones);
        spinnerCurso = findViewById(R.id.spinnerCurso);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnEditarTelefonos = findViewById(R.id.btnEditarTelefonos);

        db = AppDatabase.getInstance(this);

        // Configurar spinner con ejemplo de cursos
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.cursos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCurso.setAdapter(adapter);

        String alumnoId = getIntent().getStringExtra("alumnoId");
        alumno = db.alumnoDao().obtenerPorId(alumnoId);

        if (alumno != null) {
            etNombre.setText(alumno.getNombre());
            etApellido.setText(alumno.getApellido());
            etDni.setText(alumno.getDni());
            etFechaNacimiento.setText(alumno.getFechaNacimiento());
            etObservaciones.setText(alumno.getObservaciones());

            // Seleccionar curso en spinner
            int spinnerPosition = adapter.getPosition(alumno.getCurso());
            spinnerCurso.setSelection(spinnerPosition);
        }

        btnGuardar.setOnClickListener(v -> {
            if (alumno != null) {
                alumno.setNombre(etNombre.getText().toString().trim());
                alumno.setApellido(etApellido.getText().toString().trim());
                alumno.setDni(etDni.getText().toString().trim());
                alumno.setFechaNacimiento(etFechaNacimiento.getText().toString().trim());
                alumno.setObservaciones(etObservaciones.getText().toString().trim());
                alumno.setCurso(spinnerCurso.getSelectedItem().toString());

                db.alumnoDao().update(alumno);

                Toast.makeText(this, "Alumno actualizado", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnEditarTelefonos.setOnClickListener(v -> {
            if (alumno != null) {
                Intent intent = new Intent(this, EditarTelefonosActivity.class);
                intent.putExtra("alumnoId", alumno.getId());
                startActivity(intent);
            }
        });
    }
}




