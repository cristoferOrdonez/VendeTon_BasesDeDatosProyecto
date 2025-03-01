package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.BalanceGeneral;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;

import java.math.BigDecimal;
import java.util.List;


public class AdaptadorCorreoElectronico extends RecyclerView.Adapter<AdaptadorCorreoElectronico.EventoViewHolder>{

    List<CorreoElectronico> listaCorreos;
    public AdaptadorCorreoElectronico(List<CorreoElectronico> listaCorreos) {
        this.listaCorreos = listaCorreos;
    }

    @NonNull
    @Override
    public AdaptadorCorreoElectronico.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_balance_general, parent, false);

        return new AdaptadorCorreoElectronico.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCorreoElectronico.EventoViewHolder holder, int position) {

       CorreoElectronico Item = listaCorreos.get(position);

       holder.textViewContenidoDominio.setText(Item.cor_dominio);
       holder.textViewContenidoUsuario.setText(Item.cor_usuario);
    }

    @Override
    public int getItemCount() {
        return listaCorreos.size();

    }

    // Method to add a single item
    public void addItem(CorreoElectronico newItem) {
        listaCorreos.add(newItem); // Add the new item
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewContenidoDominio, textViewContenidoUsuario;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewContenidoDominio = itemView.findViewById(R.id.textViewContenidoMontoBalance);
            textViewContenidoUsuario= itemView.findViewById(R.id.textViewContenidoTipoBalance);
        }
    }


}
