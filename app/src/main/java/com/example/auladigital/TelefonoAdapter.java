package com.example.auladigital;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TelefonoAdapter extends RecyclerView.Adapter<TelefonoAdapter.ViewHolder> {

    public interface OnEliminarTelefonoListener {
        void onEliminar(int posicion);
    }

    private List<Telefono> listaTelefonos;
    private Context context;
    private OnEliminarTelefonoListener listener;

    public TelefonoAdapter(List<Telefono> listaTelefonos, Context context, OnEliminarTelefonoListener listener) {
        this.listaTelefonos = listaTelefonos;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_telefono, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Telefono t = listaTelefonos.get(position);

        holder.tvNumero.setText(t.getNumero());
        holder.tvReferencia.setText(t.getReferencia());

        holder.btnLlamar.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + t.getNumero()));
            context.startActivity(i);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            listener.onEliminar(position);
        });
    }

    @Override
    public int getItemCount() {
        return listaTelefonos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNumero, tvReferencia;
        ImageButton btnLlamar, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNumero = itemView.findViewById(R.id.tvNumero);       // ✔️ Correcto
            tvReferencia = itemView.findViewById(R.id.tvReferencia); // ✔️ Correcto
            btnLlamar = itemView.findViewById(R.id.btnLlamar);
            btnEliminar = itemView.findViewById(R.id.btnEliminarTelefono);
        }
    }
}

