package com.example.auladigital;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AgregarEditarEntrevistaActivity extends AppCompatActivity {

    private EditText etFecha, etHora, etDetalle, etResultado;
    private Button btnGuardar, btnCargarFoto;
    private ImageView ivFotoActa;

    private AppDatabase db;
    private Uri imagenUriSeleccionada;

    private static final int REQUEST_IMAGE = 1001;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_editar_entrevista);

        etFecha = findViewById(R.id.etFechaEntrevista);
        etHora = findViewById(R.id.etHoraEntrevista);
        etDetalle = findViewById(R.id.etDetalleEntrevista);
        etResultado = findViewById(R.id.etResultadoEntrevista);
        btnGuardar = findViewById(R.id.btnGuardarEntrevista);
        btnCargarFoto = findViewById(R.id.btnCargarFoto);
        ivFotoActa = findViewById(R.id.ivFotoActa);

        db = AppDatabase.getInstance(this);

        // Recibir fecha desde intent
        String fecha = getIntent().getStringExtra("fecha");
        if (fecha != null) {
            etFecha.setText(fecha);
        } else {
            etFecha.setText(sdf.format(new Date()));
        }

        btnCargarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE);
        });

        btnGuardar.setOnClickListener(v -> {
            String fechaTxt = etFecha.getText().toString().trim();
            String horaTxt = etHora.getText().toString().trim();
            String detalleTxt = etDetalle.getText().toString().trim();
            String resultadoTxt = etResultado.getText().toString().trim();

            if (fechaTxt.isEmpty() || horaTxt.isEmpty() || detalleTxt.isEmpty()) {
                Toast.makeText(this, "Completa Fecha, Hora y Detalle.", Toast.LENGTH_SHORT).show();
                return;
            }

            String imagenUriString = imagenUriSeleccionada != null ? imagenUriSeleccionada.toString() : "";

            Entrevista entrevista = new Entrevista(
                    fechaTxt,
                    horaTxt,
                    detalleTxt,
                    resultadoTxt,
                    imagenUriString
            );

            db.entrevistaDao().insert(entrevista);

            Toast.makeText(this, "Entrevista guardada correctamente.", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            imagenUriSeleccionada = data.getData();
            ivFotoActa.setImageURI(imagenUriSeleccionada);
        }
    }
}


