package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.Entidades.ReporteProductosPorBodega;
import com.example.vendeton.R;

import java.util.ArrayList;

public class AdaptadorReporteProductosPorBodega extends RecyclerView.Adapter<AdaptadorReporteProductosPorBodega.EventoViewHolder>{

    ArrayList<ReporteProductosPorBodega> listaProductos;

    public AdaptadorReporteProductosPorBodega(ArrayList<ReporteProductosPorBodega> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public AdaptadorReporteProductosPorBodega.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_productos_por_bodega, parent, false);

        return new AdaptadorReporteProductosPorBodega.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorReporteProductosPorBodega.EventoViewHolder holder, int position) {

        ReporteProductosPorBodega producto = listaProductos.get(position);

        holder.textViewBodega.setText(producto.nombre_bodega);
        holder.textViewProducto.setText(producto.nombre_producto);
        holder.textViewExistencias.setText(""+producto.existencias);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewBodega, textViewProducto, textViewExistencias;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewBodega = itemView.findViewById(R.id.textViewBodega);
            textViewProducto = itemView.findViewById(R.id.textViewProducto);
            textViewExistencias = itemView.findViewById(R.id.textViewExistencias);

        }
    }
}
