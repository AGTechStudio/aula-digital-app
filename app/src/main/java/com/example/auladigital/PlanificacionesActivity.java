package com.example.auladigital;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlanificacionesActivity extends AppCompatActivity implements PlanificacionAdapter.OnPlanificacionListener {

    private Spinner spinnerAnio, spinnerMes;
    private Button btnSubirArchivo, btnVerTodos;
    private RecyclerView rvArchivos;
    private PlanificacionAdapter adapter;
    private List<Planificacion> listaPlanificaciones;
    private List<Planificacion> listaFiltrada;
    private EditText etBuscar;

    private static final int REQUEST_CODE_ARCHIVO = 101;

    private String anioSeleccionado;
    private String mesSeleccionado;
    private String textoBusqueda = "";

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planificaciones);

        db = AppDatabase.getInstance(this);

        spinnerAnio = findViewById(R.id.spinnerCurso);
        spinnerMes = findViewById(R.id.spinnerSemana);
        btnSubirArchivo = findViewById(R.id.btnSubirArchivo);
        btnVerTodos = findViewById(R.id.btnVerTodos);
        rvArchivos = findViewById(R.id.rvArchivos);
        etBuscar = findViewById(R.id.etBuscar);

        listaPlanificaciones = db.planificacionDao().obtenerTodas();
        listaFiltrada = new ArrayList<>();

        rvArchivos.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlanificacionAdapter(listaFiltrada, this, this);
        rvArchivos.setAdapter(adapter);

        cargarAnios();
        cargarMeses();

        spinnerAnio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                anioSeleccionado = (String) parent.getItemAtPosition(position);
                filtrar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinnerMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mesSeleccionado = (String) parent.getItemAtPosition(position);
                filtrar();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        btnSubirArchivo.setOnClickListener(v -> seleccionarArchivo());

        btnVerTodos.setOnClickListener(v -> {
            etBuscar.setText("");
            textoBusqueda = "";
            listaFiltrada.clear();
            listaFiltrada.addAll(listaPlanificaciones);
            ordenarPorFechaDescendente();
            adapter.notifyDataSetChanged();
        });

        etBuscar.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s,int start,int count,int after){}
            public void onTextChanged(CharSequence s,int start,int before,int count){
                textoBusqueda = s.toString().toLowerCase(Locale.getDefault());
                filtrar();
            }
            public void afterTextChanged(Editable s){}
        });
    }

    // ✅ AÑOS
    private void cargarAnios() {
        List<String> anios = new ArrayList<>();
        for (int i = 2025; i <= 2030; i++) {
            anios.add(String.valueOf(i));
        }

        ArrayAdapter<String> adapterAnios = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                anios
        );

        adapterAnios.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAnio.setAdapter(adapterAnios);

        anioSeleccionado = anios.get(0);
    }

    // ✅ MESES
    private void cargarMeses() {
        List<String> meses = Arrays.asList(
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        );

        ArrayAdapter<String> adapterMeses = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                meses
        );

        adapterMeses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMes.setAdapter(adapterMeses);

        mesSeleccionado = meses.get(0);
    }

    private void seleccionarArchivo() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, REQUEST_CODE_ARCHIVO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == REQUEST_CODE_ARCHIVO && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            if(uri != null){
                String nombreArchivo = obtenerNombreArchivo(uri);
                File destino = new File(getFilesDir(), nombreArchivo);

                copiarArchivo(this, uri, destino);

                String fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
                        .format(new Date());

                Planificacion nueva = new Planificacion(
                        nombreArchivo,
                        destino.getAbsolutePath(),
                        anioSeleccionado,
                        mesSeleccionado,
                        fecha
                );

                db.planificacionDao().insertar(nueva);

                listaPlanificaciones = db.planificacionDao().obtenerTodas();
                filtrar();

                Toast.makeText(this,"Archivo guardado",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void filtrar(){
        listaFiltrada.clear();

        for(Planificacion p: listaPlanificaciones){

            boolean filtroAnioMes = p.getAnio().equals(anioSeleccionado)
                    && p.getMes().equals(mesSeleccionado);

            boolean filtroBusqueda = p.getNombreArchivo()
                    .toLowerCase(Locale.getDefault())
                    .contains(textoBusqueda);

            if(filtroAnioMes && filtroBusqueda){
                listaFiltrada.add(p);
            }
        }

        ordenarPorFechaDescendente();
        adapter.notifyDataSetChanged();
    }

    private void ordenarPorFechaDescendente(){
        listaFiltrada.sort((a,b) -> b.getFechaSubida().compareTo(a.getFechaSubida()));
    }

    @Override
    public void onClickEditar(int posicion){
        Planificacion plan = listaFiltrada.get(posicion);

        plan.setMes("Editado");
        db.planificacionDao().actualizar(plan);

        listaPlanificaciones = db.planificacionDao().obtenerTodas();
        filtrar();
    }

    @Override
    public void onClickEliminar(int posicion){
        Planificacion plan = listaFiltrada.get(posicion);

        db.planificacionDao().eliminar(plan);

        listaPlanificaciones = db.planificacionDao().obtenerTodas();
        filtrar();
    }

    private String obtenerNombreArchivo(Uri uri){
        String nombre = "archivo";

        Cursor cursor = getContentResolver().query(uri,null,null,null,null);

        if(cursor != null){
            if(cursor.moveToFirst()){
                int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                if(index != -1){
                    nombre = cursor.getString(index);
                }
            }
            cursor.close();
        }

        return nombre;
    }

    private void copiarArchivo(Context context, Uri uri, File destino){
        try(InputStream in = context.getContentResolver().openInputStream(uri);
            OutputStream out = new FileOutputStream(destino)){

            byte[] buffer = new byte[4096];
            int read;

            while((read = in.read(buffer)) != -1){
                out.write(buffer,0,read);
            }

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}