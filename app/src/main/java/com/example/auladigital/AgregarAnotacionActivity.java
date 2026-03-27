package com.example.auladigital;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AgregarAnotacionActivity extends AppCompatActivity {

    private EditText etFecha, etTipo, etDescripcion;
    private Button btnGuardar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_anotacion);

        etFecha = findViewById(R.id.etFecha);
        etTipo = findViewById(R.id.etTipo);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnGuardar = findViewById(R.id.btnGuardarAnotacion);

        db = AppDatabase.getInstance(this);

        etFecha.setOnClickListener(v -> {
            final Calendar calendario = Calendar.getInstance();
            int año = calendario.get(Calendar.YEAR);
            int mes = calendario.get(Calendar.MONTH);
            int dia = calendario.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
                etFecha.setText(fechaSeleccionada);
            }, año, mes, dia);

            datePickerDialog.show();
        });

        btnGuardar.setOnClickListener(v -> {
            String fecha = etFecha.getText().toString().trim();
            String tipo = etTipo.getText().toString().trim();
            String descripcion = etDescripcion.getText().toString().trim();

            if (fecha.isEmpty() || tipo.isEmpty()) {
                Toast.makeText(this, "La fecha y el título son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            Anotacion nuevaAnotacion = new Anotacion(fecha, descripcion, tipo);
            db.anotacionDao().insertar(nuevaAnotacion);

            Toast.makeText(this, "Anotación guardada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}

