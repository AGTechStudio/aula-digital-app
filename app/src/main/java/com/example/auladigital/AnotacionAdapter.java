package com.example.auladigital;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AnotacionAdapter extends RecyclerView.Adapter<AnotacionAdapter.AnotacionViewHolder> {

    private List<Anotacion> listaAnotaciones;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onEliminarClick(int position);
    }

    public AnotacionAdapter(List<Anotacion> listaAnotaciones, OnItemClickListener listener) {
        this.listaAnotaciones = listaAnotaciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AnotacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anotacion, parent, false);
        return new AnotacionViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AnotacionViewHolder holder, int position) {
        Anotacion anotacion = listaAnotaciones.get(position);
        holder.tvFecha.setText(anotacion.getFecha());
        holder.tvTipo.setText(anotacion.getTipo());
        holder.tvDescripcion.setText(anotacion.getDescripcion());
        holder.btnEliminar.setOnClickListener(v -> listener.onEliminarClick(position));
    }

    @Override
    public int getItemCount() {
        return listaAnotaciones.size();
    }

    public void actualizarLista(List<Anotacion> nuevaLista) {
        listaAnotaciones = nuevaLista;
        notifyDataSetChanged();
    }

    public static class AnotacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvFecha, tvTipo, tvDescripcion;
        ImageButton btnEliminar;

        public AnotacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFecha = itemView.findViewById(R.id.tvFecha);
            tvTipo = itemView.findViewById(R.id.tvTipo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            btnEliminar = itemView.findViewById(R.id.btnEliminarAnotacion);
        }
    }
}
