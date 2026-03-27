package com.example.auladigital;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EntrevistaAdapter extends RecyclerView.Adapter<EntrevistaAdapter.ViewHolder> {

    private List<Entrevista> lista;
    private Context context;

    public EntrevistaAdapter(List<Entrevista> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entrevista, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Entrevista entrevista = lista.get(position);

        holder.txtFechaHora.setText(entrevista.getFecha() + " - " + entrevista.getHora());
        holder.txtDetalle.setText(entrevista.getDetalle());

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleEntrevistaActivity.class);
            intent.putExtra("id", entrevista.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtFechaHora, txtDetalle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtFechaHora = itemView.findViewById(R.id.txtFechaHora);
            txtDetalle = itemView.findViewById(R.id.txtDetalle);
        }
    }
}



