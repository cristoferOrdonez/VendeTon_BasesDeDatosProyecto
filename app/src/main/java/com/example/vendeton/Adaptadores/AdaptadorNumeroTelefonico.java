package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;

import java.util.List;


public class AdaptadorNumeroTelefonico extends RecyclerView.Adapter<AdaptadorNumeroTelefonico.EventoViewHolder>{

    List<NumeroTelefonico> listaNumeros;
    public AdaptadorNumeroTelefonico(List<NumeroTelefonico> listaCorreos) {
        this.listaNumeros = listaCorreos;
    }

    @NonNull
    @Override
    public AdaptadorNumeroTelefonico.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_balance_general, parent, false);

        return new AdaptadorNumeroTelefonico.EventoViewHolder(view);
    }

    // Method to add a single item
    public void addItem(NumeroTelefonico newItem) {
        listaNumeros.add(newItem); // Add the new item
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorNumeroTelefonico.EventoViewHolder holder, int position) {

       NumeroTelefonico Item = listaNumeros.get(position);
       holder.textViewContenidoUsuario.setText("+"+Item.num_prefijo);
        holder.textViewContenidoDominio.setText(""+Item.num_numero);
    }

    @Override
    public int getItemCount() {
        return listaNumeros.size();

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
