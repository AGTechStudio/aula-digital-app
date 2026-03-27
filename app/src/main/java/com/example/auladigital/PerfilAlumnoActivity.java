package com.example.auladigital;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class PerfilAlumnoActivity extends AppCompatActivity {

    private TextView tvNombre, tvCurso, tvDni, tvFecha;
    private RecyclerView rvTelefonos, rvObservaciones;
    private Button btnEditar, btnEliminar, btnAgregarObservacion;
    private AppDatabase db;
    private Alumno alumno;

    private TelefonoAdapter telefonoAdapter;
    private ObservacionAdapter observacionAdapter;

    private List<Telefono> telefonos;
    private List<Observacion> observaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_alumno);

        db = AppDatabase.getInstance(this);

        tvNombre = findViewById(R.id.tvNombre);
        tvCurso = findViewById(R.id.tvCurso);
        tvDni = findViewById(R.id.tvDni);
        tvFecha = findViewById(R.id.tvFechaNacimiento);
        rvTelefonos = findViewById(R.id.rvTelefonos);
        rvObservaciones = findViewById(R.id.rvObservaciones);
        btnEditar = findViewById(R.id.btnEditar);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnAgregarObservacion = findViewById(R.id.btnAgregarObservacion);

        String alumnoId = getIntent().getStringExtra("alumnoId");
        alumno = db.alumnoDao().obtenerPorId(alumnoId);

        if (alumno == null) {
            Toast.makeText(this, "Error al cargar datos del alumno", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        cargarDatos();

        btnEliminar.setOnClickListener(v -> {
            db.alumnoDao().delete(alumno);
            Toast.makeText(this, "Alumno eliminado", Toast.LENGTH_SHORT).show();
            finish();
        });

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(this, EditarAlumnoActivity.class);
            intent.putExtra("alumnoId", alumno.getId());
            startActivity(intent);
            finish();
        });

        btnAgregarObservacion.setOnClickListener(v -> {
            seleccionarFecha();
        });
    }

    private void cargarDatos() {
        tvNombre.setText(alumno.getNombre() + " " + alumno.getApellido());
        tvCurso.setText(alumno.getCurso());
        tvDni.setText(alumno.getDni());
        tvFecha.setText(alumno.getFechaNacimiento());

        cargarTelefonos();
        cargarObservaciones();
    }

    private void cargarTelefonos() {
        telefonos = db.telefonoDao().obtenerPorAlumno(alumno.getId());
        rvTelefonos.setLayoutManager(new LinearLayoutManager(this));
        telefonoAdapter = new TelefonoAdapter(telefonos, this, posicion -> {
            Telefono tel = telefonos.get(posicion);
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + tel.getNumero()));
            startActivity(intent);
        });
        rvTelefonos.setAdapter(telefonoAdapter);
    }

    private void cargarObservaciones() {
        observaciones = db.observacionDao().obtenerPorAlumno(alumno.getId());
        rvObservaciones.setLayoutManager(new LinearLayoutManager(this));
        observacionAdapter = new ObservacionAdapter(observaciones, this, posicion -> {
            Observacion obs = observaciones.get(posicion);
            db.observacionDao().delete(obs);
            observaciones.remove(posicion);
            observacionAdapter.notifyItemRemoved(posicion);
        });
        rvObservaciones.setAdapter(observacionAdapter);
    }

    private void seleccionarFecha() {
        Calendar calendario = Calendar.getInstance();
        DatePickerDialog datePicker = new DatePickerDialog(
                this,
                (DatePicker view, int year, int month, int dayOfMonth) -> {
                    String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
                    pedirDescripcionObservacion(fecha);
                },
                calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)
        );
        datePicker.show();
    }

    private void pedirDescripcionObservacion(String fechaSeleccionada) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Descripción de la observación");

        final EditText input = new EditText(this);
        input.setHint("Escriba la observación");
        builder.setView(input);

        builder.setPositiveButton("Guardar", (dialog, which) -> {
            String descripcion = input.getText().toString().trim();
            if (!descripcion.isEmpty()) {
                agregarObservacion(fechaSeleccionada, descripcion);
            } else {
                Toast.makeText(this, "Descripción vacía", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    private void agregarObservacion(String fecha, String descripcion) {
        Observacion observacion = new Observacion(
                UUID.randomUUID().toString(),
                alumno.getId(),
                fecha,
                descripcion
        );
        db.observacionDao().insert(observacion);
        observaciones.add(observacion);
        observacionAdapter.notifyItemInserted(observaciones.size() - 1);
    }
}






