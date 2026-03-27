package com.example.auladigital;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ObservacionAdapter extends RecyclerView.Adapter<ObservacionAdapter.ViewHolder> {

    public interface OnEliminarObservacionListener {
        void onEliminar(int posicion);
    }

    private List<Observacion> listaObservaciones;
    private Context context;
    private OnEliminarObservacionListener listener;

    public ObservacionAdapter(List<Observacion> listaObservaciones, Context context, OnEliminarObservacionListener listener) {
        this.listaObservaciones = listaObservaciones;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ObservacionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_observacion, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ObservacionAdapter.ViewHolder holder, int position) {
        Observacion obs = listaObservaciones.get(position);

        holder.tvFecha.setText(obs.getFecha());
        holder.tvDescripcion.setText(obs.getDescripcion());

        holder.btnEliminar.setOnClickListener(v -> {
            listener.onEliminar(position);
        });
    }

    @Override
    public int getItemCount() {
        return listaObservaciones.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvFecha, tvDescripcion;
        ImageButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFechaObservacion);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcionObservacion);
            btnEliminar = itemView.findViewById(R.id.btnEliminarObservacion);

        }
    }
}

