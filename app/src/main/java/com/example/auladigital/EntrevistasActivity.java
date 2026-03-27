package com.example.auladigital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class EntrevistasActivity extends AppCompatActivity {

    private CalendarView calendarView;
    private TextView txtProxima;
    private Button btnProximas, btnRealizadas, btnHoy;

    private AppDatabase db;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrevistas);

        calendarView = findViewById(R.id.calendarViewEntrevistas);
        txtProxima = findViewById(R.id.txtProximaEntrevista);
        btnProximas = findViewById(R.id.btnProximasEntrevistas);
        btnRealizadas = findViewById(R.id.btnEntrevistasRealizadas);
        btnHoy = findViewById(R.id.btnHoy);

        db = AppDatabase.getInstance(this);

        cargarProximaEntrevista();

        // Click en una fecha del calendario
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String fechaSeleccionada = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            Intent intent = new Intent(this, AgregarEditarEntrevistaActivity.class);
            intent.putExtra("fecha", fechaSeleccionada);
            startActivity(intent);
        });

        btnHoy.setOnClickListener(v -> {
            String fechaActual = sdf.format(new Date());
            Intent intent = new Intent(this, AgregarEditarEntrevistaActivity.class);
            intent.putExtra("fecha", fechaActual);
            startActivity(intent);
        });

        btnProximas.setOnClickListener(v -> {
            startActivity(new Intent(this, ProximasEntrevistasActivity.class));
        });

        btnRealizadas.setOnClickListener(v -> {
            startActivity(new Intent(this, EntrevistasRealizadasActivity.class));
        });
    }

    private void cargarProximaEntrevista() {
        List<Entrevista> lista = db.entrevistaDao().obtenerProximas();
        if (!lista.isEmpty()) {
            Entrevista proxima = lista.get(0);
            txtProxima.setText("Próxima entrevista:\n" +
                    proxima.getFecha() + " a las " + proxima.getHora() + "\n" +
                    "Detalle: " + proxima.getDetalle());

            txtProxima.setOnClickListener(v -> {
                Intent intent = new Intent(this, DetalleEntrevistaActivity.class);
                intent.putExtra("id", proxima.getId());
                startActivity(intent);
            });
        } else {
            txtProxima.setText("Próxima Entrevista: Sin entrevistas pactadas");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarProximaEntrevista();
    }
}


