package com.example.auladigital;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    Button btnAlumnos, btnMeriendas, btnPlanificaciones, btnBitacora, btnEntrevistas, btnCerrarSesion;
    TextView txtBienvenida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();

        txtBienvenida = findViewById(R.id.txtBienvenida);
        btnAlumnos = findViewById(R.id.btnAlumnos);
        btnMeriendas = findViewById(R.id.btnMeriendas);
        btnPlanificaciones = findViewById(R.id.btnPlanificaciones);
        btnBitacora = findViewById(R.id.btnBitacora);
        btnEntrevistas = findViewById(R.id.btnEntrevistas);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnAlumnos.setOnClickListener(v -> {
            startActivity(new Intent(this, AlumnosActivity.class));
        });

        btnMeriendas.setOnClickListener(v -> {
            startActivity(new Intent(this, MeriendasActivity.class));
        });

        btnPlanificaciones.setOnClickListener(v -> {
            startActivity(new Intent(this, PlanificacionesActivity.class));
        });

        btnBitacora.setOnClickListener(v -> {
            startActivity(new Intent(this, BitacoraActivity.class));
        });

        btnEntrevistas.setOnClickListener(v -> {
            startActivity(new Intent(this, EntrevistasActivity.class));
        });

        btnCerrarSesion.setOnClickListener(v -> {
            firebaseAuth.signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
