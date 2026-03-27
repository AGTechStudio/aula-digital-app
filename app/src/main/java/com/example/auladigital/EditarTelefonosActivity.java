package com.example.auladigital;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EditarTelefonosActivity extends AppCompatActivity {

    private LinearLayout layoutTelefonos;
    private Button btnAgregarTelefono, btnGuardar;
    private AppDatabase db;
    private String alumnoId;
    private List<Telefono> telefonos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_telefonos);

        layoutTelefonos = findViewById(R.id.layoutTelefonos);
        btnAgregarTelefono = findViewById(R.id.btnAgregarTelefono);
        btnGuardar = findViewById(R.id.btnGuardar);

        db = AppDatabase.getInstance(this);
        alumnoId = getIntent().getStringExtra("alumnoId");

        telefonos = db.telefonoDao().obtenerPorAlumno(alumnoId);



        mostrarTelefonos();

        btnAgregarTelefono.setOnClickListener(v -> {
            agregarTelefonoVacio();
        });

        btnGuardar.setOnClickListener(v -> guardarTelefonos());
    }

    private void mostrarTelefonos() {
        layoutTelefonos.removeAllViews();
        for (Telefono tel : telefonos) {
            layoutTelefonos.addView(crearTelefonoView(tel));
        }
    }

    private LinearLayout crearTelefonoView(Telefono tel) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        EditText etNumero = new EditText(this);
        etNumero.setHint("Número");
        etNumero.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        etNumero.setText(tel.getNumero());

        EditText etReferencia = new EditText(this);
        etReferencia.setHint("Referencia");
        etReferencia.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
        etReferencia.setText(tel.getReferencia());

        Button btnEliminar = new Button(this);
        btnEliminar.setText("X");
        btnEliminar.setOnClickListener(v -> {
            telefonos.remove(tel);
            layoutTelefonos.removeView(layout);
        });

        layout.addView(etNumero);
        layout.addView(etReferencia);
        layout.addView(btnEliminar);

        // Guardar cambios cuando se modifique el número o referencia
        etNumero.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                tel.setNumero(etNumero.getText().toString());
            }
        });

        etReferencia.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                tel.setReferencia(etReferencia.getText().toString());
            }
        });

        return layout;
    }

    private void agregarTelefonoVacio() {
        Telefono nuevo = new Telefono(UUID.randomUUID().toString(), "", "", alumnoId);
        telefonos.add(nuevo);
        layoutTelefonos.addView(crearTelefonoView(nuevo));
    }

    private void guardarTelefonos() {
        // Eliminar todos los teléfonos antiguos y volver a insertar
        List<Telefono> telefonosAntiguos = db.telefonoDao().obtenerPorAlumno(alumnoId);

        for (Telefono t : telefonosAntiguos) {
            db.telefonoDao().delete(t);
        }
        for (Telefono t : telefonos) {
            if (!t.getNumero().isEmpty()) {
                db.telefonoDao().insert(t);
            }
        }

        Toast.makeText(this, "Teléfonos guardados", Toast.LENGTH_SHORT).show();
        finish();
    }
}

