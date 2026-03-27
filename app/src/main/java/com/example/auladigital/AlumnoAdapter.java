package com.example.auladigital;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlumnoAdapter extends RecyclerView.Adapter<AlumnoAdapter.AlumnoViewHolder> {

    // Interface para clicks simples y largos (ListarAlumnosActivity)
    public interface OnItemClickListener {
        void onItemClick(Alumno alumno);
        void onItemLongClick(Alumno alumno);
    }

    // Interface para selección múltiple (AlumnosActivity)
    public interface OnSelectionChangedListener {
        void onSelectionChanged(int selectedCount);
    }

    private List<Alumno> listaAlumnos;
    private Set<Alumno> seleccionados = new HashSet<>();

    private OnItemClickListener clickListener;           // Para clicks simples y largos
    private OnSelectionChangedListener selectionListener; // Para selección múltiple

    // Constructor para ListarAlumnosActivity (click simple)
    public AlumnoAdapter(List<Alumno> listaAlumnos, OnItemClickListener clickListener) {
        this.listaAlumnos = listaAlumnos;
        this.clickListener = clickListener;
        this.selectionListener = null;
    }

    // Constructor para AlumnosActivity (selección múltiple)
    public AlumnoAdapter(List<Alumno> listaAlumnos, OnSelectionChangedListener selectionListener) {
        this.listaAlumnos = listaAlumnos;
        this.selectionListener = selectionListener;
        this.clickListener = null;
    }

    @NonNull
    @Override
    public AlumnoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_alumno, parent, false);
        return new AlumnoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumnoViewHolder holder, int position) {
        Alumno alumno = listaAlumnos.get(position);
        boolean seleccionado = seleccionados.contains(alumno);
        holder.bind(alumno, seleccionado);
    }

    @Override
    public int getItemCount() {
        return listaAlumnos.size();
    }

    public Set<Alumno> getSeleccionados() {
        return seleccionados;
    }

    public void limpiarSeleccion() {
        seleccionados.clear();
        notifyDataSetChanged();
        if (selectionListener != null)
            selectionListener.onSelectionChanged(0);
    }

    public void actualizarLista(List<Alumno> nuevos) {
        listaAlumnos.clear();
        listaAlumnos.addAll(nuevos);
        notifyDataSetChanged();
        limpiarSeleccion();
    }

    class AlumnoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;

        public AlumnoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombreAlumno);
        }

        public void bind(final Alumno alumno, boolean seleccionado) {
            tvNombre.setText(alumno.getApellido() + ", " + alumno.getNombre());

            if (selectionListener != null) {
                // Modo selección múltiple
                itemView.setBackgroundColor(seleccionado ? 0xFFFFE0B2 /* naranja pastel */ : 0xFFFFFFFF /* blanco */);
                itemView.setOnClickListener(v -> {
                    if (seleccionados.contains(alumno)) {
                        seleccionados.remove(alumno);
                    } else {
                        seleccionados.add(alumno);
                    }
                    notifyItemChanged(getAdapterPosition());
                    selectionListener.onSelectionChanged(seleccionados.size());
                });
                // No long click en modo selección múltiple
                itemView.setOnLongClickListener(null);
            } else if (clickListener != null) {
                // Modo click simple
                itemView.setBackgroundColor(0xFFFFFFFF);
                itemView.setOnClickListener(v -> clickListener.onItemClick(alumno));
                itemView.setOnLongClickListener(v -> {
                    clickListener.onItemLongClick(alumno);
                    return true;
                });
            }
        }
    }
}


