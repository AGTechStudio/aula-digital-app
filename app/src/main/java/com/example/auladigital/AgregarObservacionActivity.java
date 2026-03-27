package com.example.auladigital;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AgregarObservacionActivity extends AppCompatActivity {

    private EditText etFecha, etTitulo, etObservacion;
    private Button btnGuardar;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_observacion);

        etFecha = findViewById(R.id.etFecha);
        etTitulo = findViewById(R.id.etTitulo);
        etObservacion = findViewById(R.id.etObservacion);
        btnGuardar = findViewById(R.id.btnGuardarObservacion);

        db = AppDatabase.getInstance(this);

        etFecha.setOnClickListener(v -> mostrarDatePicker());

        btnGuardar.setOnClickListener(v -> {
            String fecha = etFecha.getText().toString();
            String titulo = etTitulo.getText().toString();
            String observacion = etObservacion.getText().toString();

            if (fecha.isEmpty() || titulo.isEmpty()) {
                Toast.makeText(this, "Complete al menos la fecha y el título", Toast.LENGTH_SHORT).show();
                return;
            }

            ObservacionBitacora obs = new ObservacionBitacora(fecha, titulo, observacion);
            db.observacionBitacoraDao().insert(obs);
            Toast.makeText(this, "Observación guardada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void mostrarDatePicker() {
        Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            String fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
            etFecha.setText(fechaSeleccionada);
        }, año, mes, dia);

        datePicker.show();
    }
}

