package com.example.auladigital;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProximasEntrevistasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EntrevistaAdapter adapter;
    private Button btnVolver;
    private TextView txtTitulo;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximas_entrevistas);

        txtTitulo = findViewById(R.id.txtTitulo);
        recyclerView = findViewById(R.id.recyclerProximas);
        btnVolver = findViewById(R.id.btnVolver);

        db = AppDatabase.getInstance(this);

        List<Entrevista> entrevistas = db.entrevistaDao().obtenerProximas();

        if (entrevistas.isEmpty()) {
            txtTitulo.setText("No hay entrevistas pactadas.");
            Toast.makeText(this, "No hay entrevistas pactadas.", Toast.LENGTH_LONG).show();
        } else {
            txtTitulo.setText("Entrevistas Pactadas");
        }

        adapter = new EntrevistaAdapter(entrevistas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnVolver.setOnClickListener(v -> finish());
    }
}


