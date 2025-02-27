package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.AreaDeTrabajo;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;

import java.util.List;


public class AdaptadorAreasTrabajo extends RecyclerView.Adapter<AdaptadorAreasTrabajo.EventoViewHolder>{

    List<AreaDeTrabajo> listaAreasTrabajo;
    public AdaptadorAreasTrabajo(List<AreaDeTrabajo> listaAreasTrabajo) {
        this.listaAreasTrabajo = listaAreasTrabajo;
    }

    @NonNull
    @Override
    public AdaptadorAreasTrabajo.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_una_columna, parent, false);

        return new AdaptadorAreasTrabajo.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorAreasTrabajo.EventoViewHolder holder, int position) {

       AreaDeTrabajo Item = listaAreasTrabajo.get(position);
       holder.textViewContenidoNombre.setText(""+Item.are_nombre);
    }

    @Override
    public int getItemCount() {
        return listaAreasTrabajo.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewContenidoNombre;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewContenidoNombre = itemView.findViewById(R.id.textViewColumnaUno);
        }
    }


}
