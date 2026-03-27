package com.example.auladigital;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalleObservacionActivity extends AppCompatActivity {

    private EditText etFecha, etTitulo, etDescripcion;
    private Button btnEditar, btnEliminar;
    private AppDatabase db;
    private ObservacionBitacora observacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_observacion);

        etFecha = findViewById(R.id.etFechaDetalle);
        etTitulo = findViewById(R.id.etTituloDetalle);
        etDescripcion = findViewById(R.id.etDescripcionDetalle);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);

        db = AppDatabase.getInstance(this);

        // ✔️ Recibir el ID enviado desde el adapter
        int id = getIntent().getIntExtra("id", -1);

        if (id != -1) {
            observacion = db.observacionBitacoraDao().buscarPorId(id);

            if (observacion != null) {
                etFecha.setText(observacion.getFecha());
                etTitulo.setText(observacion.getTitulo());
                etDescripcion.setText(observacion.getObservacion());
            }
        }

        btnEditar.setOnClickListener(v -> {
            if (observacion != null) {
                observacion.setFecha(etFecha.getText().toString().trim());
                observacion.setTitulo(etTitulo.getText().toString().trim());
                observacion.setObservacion(etDescripcion.getText().toString().trim());

                db.observacionBitacoraDao().update(observacion);
                Toast.makeText(this, "Observación actualizada", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnEliminar.setOnClickListener(v -> {
            if (observacion != null) {
                new AlertDialog.Builder(this)
                        .setTitle("Eliminar")
                        .setMessage("¿Desea eliminar esta observación?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            db.observacionBitacoraDao().delete(observacion);
                            Toast.makeText(this, "Observación eliminada", Toast.LENGTH_SHORT).show();
                            finish();
                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });
    }
}

