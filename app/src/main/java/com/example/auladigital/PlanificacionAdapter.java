package com.example.auladigital;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

public class PlanificacionAdapter extends RecyclerView.Adapter<PlanificacionAdapter.PlanificacionViewHolder> {

    private List<Planificacion> listaPlanificaciones;
    private Context context;
    private OnPlanificacionListener listener;

    public interface OnPlanificacionListener {
        void onClickEditar(int posicion);
        void onClickEliminar(int posicion);
    }

    public PlanificacionAdapter(List<Planificacion> listaPlanificaciones, Context context, OnPlanificacionListener listener){
        this.listaPlanificaciones = listaPlanificaciones;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PlanificacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_planificacion, parent, false);
        return new PlanificacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanificacionViewHolder holder, int position) {
        Planificacion plan = listaPlanificaciones.get(position);
        holder.tvNombreArchivo.setText(plan.getNombreArchivo());
        holder.tvFecha.setText("Subido: " + plan.getFechaSubida());

        String ruta = plan.getRutaArchivo().toLowerCase();
        if(ruta.endsWith(".pdf")){
            holder.ivIcono.setImageResource(R.drawable.ic_pdf);
        } else if(ruta.endsWith(".jpg") || ruta.endsWith(".jpeg") || ruta.endsWith(".png")){
            holder.ivIcono.setImageResource(R.drawable.ic_image);
        } else if(ruta.endsWith(".doc") || ruta.endsWith(".docx")){
            holder.ivIcono.setImageResource(R.drawable.ic_word);
        } else if(ruta.endsWith(".xls") || ruta.endsWith(".xlsx")){
            holder.ivIcono.setImageResource(R.drawable.ic_excel);
        } else {
            holder.ivIcono.setImageResource(R.drawable.ic_file);
        }

        holder.itemView.setOnClickListener(v -> {
            File file = new File(plan.getRutaArchivo());
            Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", file);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, obtenerMimeType(ruta));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try{
                context.startActivity(intent);
            } catch(Exception e){
                Toast.makeText(context, "No se puede abrir este archivo", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnEditar.setOnClickListener(v -> {
            if(listener != null){
                listener.onClickEditar(position);
            }
        });

        holder.btnEliminar.setOnClickListener(v -> {
            if(listener != null){
                listener.onClickEliminar(position);
            }
        });
    }

    private String obtenerMimeType(String rutaArchivo){
        if(rutaArchivo.endsWith(".pdf")) return "application/pdf";
        if(rutaArchivo.endsWith(".jpg") || rutaArchivo.endsWith(".jpeg") || rutaArchivo.endsWith(".png")) return "image/*";
        if(rutaArchivo.endsWith(".doc") || rutaArchivo.endsWith(".docx")) return "application/msword";
        if(rutaArchivo.endsWith(".xls") || rutaArchivo.endsWith(".xlsx")) return "application/vnd.ms-excel";
        return "*/*";
    }

    @Override
    public int getItemCount() {
        return listaPlanificaciones.size();
    }

    static class PlanificacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreArchivo, tvFecha;
        ImageView ivIcono;
        ImageButton btnEditar, btnEliminar;

        public PlanificacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreArchivo = itemView.findViewById(R.id.tvNombreArchivo);
            tvFecha = itemView.findViewById(R.id.tvFechaSubida);
            ivIcono = itemView.findViewById(R.id.ivIcono);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}



