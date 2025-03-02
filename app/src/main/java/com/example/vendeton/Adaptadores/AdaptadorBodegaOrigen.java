package com.example.vendeton.Adaptadores;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.BodegaOrigen;
import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.R;

import java.util.ArrayList;

public class AdaptadorBodegaOrigen extends RecyclerView.Adapter<AdaptadorBodegaOrigen.EventoViewHolder>{

    ArrayList<BodegaOrigen> listaBodegas;

    public AdaptadorBodegaOrigen(ArrayList<BodegaOrigen> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    @NonNull
    @Override
    public AdaptadorBodegaOrigen.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_bodega_origen, parent, false);

        return new AdaptadorBodegaOrigen.EventoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdaptadorBodegaOrigen.EventoViewHolder holder, int position) {

        BodegaOrigen bodegaOrigen = listaBodegas.get(position);

        holder.textViewBodega.setText(bodegaOrigen.bodega);
        holder.textViewCantidad.setText("" + bodegaOrigen.cantidad);

    }

    @Override
    public int getItemCount() {
        return listaBodegas.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewBodega, textViewCantidad;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewBodega = itemView.findViewById(R.id.textViewBodega);
            textViewCantidad = itemView.findViewById(R.id.textViewCantidad);

        }
    }
}

