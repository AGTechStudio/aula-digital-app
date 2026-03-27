package com.example.auladigital;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ObservacionBitacoraAdapter extends RecyclerView.Adapter<ObservacionBitacoraAdapter.ViewHolder> {

    private List<ObservacionBitacora> lista;
    private Context context;
    private OnEliminarListener listener;

    public interface OnEliminarListener {
        void onEliminar(ObservacionBitacora observacion);
    }

    public ObservacionBitacoraAdapter(List<ObservacionBitacora> lista, Context context, OnEliminarListener listener) {
        this.lista = lista;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_observacionbitacora, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObservacionBitacora obs = lista.get(position);
        holder.txtFecha.setText(obs.getFecha());
        holder.txtTitulo.setText(obs.getTitulo());

        // Click en el botón eliminar
        holder.btnEliminar.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar observación")
                    .setMessage("¿Estás seguro que deseas eliminar esta observación?")
                    .setPositiveButton("Sí", (dialog, which) -> listener.onEliminar(obs))
                    .setNegativeButton("No", null)
                    .show();
        });

        // Click en todo el ítem para ver detalle
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleObservacionActivity.class);
            intent.putExtra("id", obs.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtFecha, txtTitulo;
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFecha = itemView.findViewById(R.id.txtFecha);
            txtTitulo = itemView.findViewById(R.id.txtTitulo);
            btnEliminar = itemView.findViewById(R.id.btnEliminarObs);
        }
    }
}


