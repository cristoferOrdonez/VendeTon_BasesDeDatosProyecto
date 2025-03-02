package com.example.vendeton.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;

import java.util.ArrayList;

public class AdaptadorDetallesProductosVendidos extends RecyclerView.Adapter<AdaptadorDetallesProductosVendidos.EventoViewHolder>{

    ArrayList<DetalleProductoVendido> listaDetalles;

    public AdaptadorDetallesProductosVendidos(ArrayList<DetalleProductoVendido> listaDetalles) {
        this.listaDetalles = listaDetalles;
    }

    @NonNull
    @Override
    public AdaptadorDetallesProductosVendidos.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_detalle_producto_vendido, parent, false);

        return new AdaptadorDetallesProductosVendidos.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDetallesProductosVendidos.EventoViewHolder holder, int position) {

        DetalleProductoVendido detalle = listaDetalles.get(position);

        holder.textViewProducto.setText(detalle.producto);
        holder.textViewCantidad.setText("" + detalle.cantidad);

        holder.textViewMonto.setText("" + detalle.monto);

    }

    @Override
    public int getItemCount() {
        return listaDetalles.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewProducto, textViewCantidad, textViewMonto;
        CardView cardView;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewProducto = itemView.findViewById(R.id.textViewProducto);
            textViewCantidad = itemView.findViewById(R.id.textViewCantidad);
            textViewMonto = itemView.findViewById(R.id.textViewMonto);
            cardView = itemView.findViewById(R.id.CardViewDetalleProductoVendido);

        }
    }
}
