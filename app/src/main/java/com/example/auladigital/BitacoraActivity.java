package com.example.auladigital;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BitacoraActivity extends AppCompatActivity {

    private RecyclerView rvObservaciones;
    private EditText etBuscar;
    private Button btnAgregar;
    private AppDatabase db;
    private List<ObservacionBitacora> lista;
    private ObservacionBitacoraAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitacora);

        rvObservaciones = findViewById(R.id.rvObservaciones);
        etBuscar = findViewById(R.id.etBuscarObservacion);
        btnAgregar = findViewById(R.id.btnAgregarObservacion);

        db = AppDatabase.getInstance(this);

        cargarObservaciones();

        btnAgregar.setOnClickListener(v -> {
            startActivity(new Intent(this, AgregarObservacionActivity.class));
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().isEmpty()){
                    cargarObservaciones();
                } else {
                    lista = db.observacionBitacoraDao().buscarPorTitulo(s.toString());
                    actualizarAdapter();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarObservaciones();
    }

    private void cargarObservaciones() {
        lista = db.observacionBitacoraDao().obtenerTodas();
        actualizarAdapter();
    }

    private void actualizarAdapter() {
        adapter = new ObservacionBitacoraAdapter(lista, this, observacion -> {
            db.observacionBitacoraDao().delete(observacion);
            cargarObservaciones();
        });
        rvObservaciones.setLayoutManager(new LinearLayoutManager(this));
        rvObservaciones.setAdapter(adapter);
    }
}



