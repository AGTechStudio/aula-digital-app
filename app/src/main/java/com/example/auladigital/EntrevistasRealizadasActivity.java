package com.example.auladigital;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EntrevistasRealizadasActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EntrevistaAdapter adapter;
    private Button btnVolver;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrevistas_realizadas);

        recyclerView = findViewById(R.id.recyclerRealizadas);
        btnVolver = findViewById(R.id.btnVolver);

        db = AppDatabase.getInstance(this);

        List<Entrevista> entrevistas = db.entrevistaDao().obtenerRealizadas();

        if (entrevistas.isEmpty()) {
            Toast.makeText(this, "No hay entrevistas realizadas.", Toast.LENGTH_LONG).show();
        }

        adapter = new EntrevistaAdapter(entrevistas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnVolver.setOnClickListener(v -> finish());
    }
}

