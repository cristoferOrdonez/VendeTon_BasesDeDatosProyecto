package com.example.vendeton.Adaptadores;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.Bodega;
import com.example.vendeton.Entidades.BodegaOrigen;
import com.example.vendeton.R;

import java.util.ArrayList;

public class AdaptadorBodegas extends RecyclerView.Adapter<AdaptadorBodegas.EventoViewHolder>{

    ArrayList<Bodega> listaBodegas;

    public AdaptadorBodegas(ArrayList<Bodega> listaBodegas) {
        this.listaBodegas = listaBodegas;
    }

    @NonNull
    @Override
    public AdaptadorBodegas.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_bodegas, parent, false);

        return new AdaptadorBodegas.EventoViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AdaptadorBodegas.EventoViewHolder holder, int position) {

        Bodega bodega = listaBodegas.get(position);

        holder.textViewNombreBodega.setText(bodega.bod_nombre);
        holder.textViewDireccion.setText(bodega.direccion);
        holder.textViewDimensiones.setText(bodega.dimensiones);

    }

    @Override
    public int getItemCount() {
        return listaBodegas.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombreBodega, textViewDireccion, textViewDimensiones;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombreBodega = itemView.findViewById(R.id.textViewNombreBodega);
            textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
            textViewDimensiones = itemView.findViewById(R.id.textViewDimnesiones);

        }
    }
}


