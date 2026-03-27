package com.example.auladigital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListadoEntrevistasActivity extends AppCompatActivity {

    private TextView txtTituloListado;
    private RecyclerView recyclerView;
    private Button btnVolver;

    private AppDatabase db;
    private String tipoListado; // "proximas" o "realizadas"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_entrevistas);

        txtTituloListado = findViewById(R.id.txtTituloListado);
        recyclerView = findViewById(R.id.recyclerEntrevistas);
        btnVolver = findViewById(R.id.btnVolver);

        db = AppDatabase.getInstance(this);

        tipoListado = getIntent().getStringExtra("tipo");

        if ("proximas".equals(tipoListado)) {
            txtTituloListado.setText("Próximas Entrevistas");
            cargarProximas();
        } else {
            txtTituloListado.setText("Entrevistas Pactadas");
            cargarRealizadas();
        }

        btnVolver.setOnClickListener(v -> finish());
    }

    private void cargarProximas() {
        List<Entrevista> lista = db.entrevistaDao().obtenerProximas();
        if (lista.isEmpty()) {
            txtTituloListado.setText("No hay entrevistas próximas.");
        }
        EntrevistaAdapter adapter = new EntrevistaAdapter(lista, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void cargarRealizadas() {
        List<Entrevista> lista = db.entrevistaDao().obtenerRealizadas();
        if (lista.isEmpty()) {
            txtTituloListado.setText("No hay entrevistas realizadas.");
        }
        EntrevistaAdapter adapter = new EntrevistaAdapter(lista, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }
}

