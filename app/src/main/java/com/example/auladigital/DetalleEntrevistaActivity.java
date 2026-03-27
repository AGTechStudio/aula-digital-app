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

public class DetalleEntrevistaActivity extends AppCompatActivity {

    private EditText etFecha, etHora, etDetalle, etResultado;
    private ImageView ivFoto;
    private Button btnCargarFoto, btnGuardar;

    private AppDatabase db;
    private Entrevista entrevista;
    private Uri imagenUriSeleccionada;
    private static final int REQUEST_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_entrevista);

        etFecha = findViewById(R.id.etFecha);
        etHora = findViewById(R.id.etHora);
        etDetalle = findViewById(R.id.etDetalle);
        etResultado = findViewById(R.id.etResultado);
        ivFoto = findViewById(R.id.ivFoto);
        btnCargarFoto = findViewById(R.id.btnCargarFoto);
        btnGuardar = findViewById(R.id.btnGuardarCambios);

        db = AppDatabase.getInstance(this);

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            entrevista = db.entrevistaDao().buscarPorId(id);
            if (entrevista != null) {
                cargarDatos();
            }
        }

        btnCargarFoto.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, REQUEST_IMAGE);
        });

        btnGuardar.setOnClickListener(v -> {
            actualizarEntrevista();
        });
    }

    private void cargarDatos() {
        etFecha.setText(entrevista.getFecha());
        etHora.setText(entrevista.getHora());
        etDetalle.setText(entrevista.getDetalle());
        etResultado.setText(entrevista.getResultado());

        if (entrevista.getImagenUri() != null && !entrevista.getImagenUri().isEmpty()) {
            ivFoto.setImageURI(Uri.parse(entrevista.getImagenUri()));
            imagenUriSeleccionada = Uri.parse(entrevista.getImagenUri());
        } else {
            ivFoto.setImageResource(R.drawable.imagen_placeholder);
        }
    }

    private void actualizarEntrevista() {
        entrevista.setFecha(etFecha.getText().toString().trim());
        entrevista.setHora(etHora.getText().toString().trim());
        entrevista.setDetalle(etDetalle.getText().toString().trim());
        entrevista.setResultado(etResultado.getText().toString().trim());
        entrevista.setImagenUri(imagenUriSeleccionada != null ? imagenUriSeleccionada.toString() : "");

        db.entrevistaDao().update(entrevista);

        Toast.makeText(this, "Entrevista actualizada correctamente.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            imagenUriSeleccionada = data.getData();
            ivFoto.setImageURI(imagenUriSeleccionada);
        }
    }
}


