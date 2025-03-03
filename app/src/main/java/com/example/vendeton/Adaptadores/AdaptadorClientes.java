package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Activitys.activity_detalles_cliente;
import com.example.vendeton.Activitys.activity_info_cliente;
import com.example.vendeton.Entidades.BalanceGeneral;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;


public class AdaptadorClientes extends RecyclerView.Adapter<AdaptadorClientes.EventoViewHolder>{

    List<ContraparteCliente> listaClientes;
    public AdaptadorClientes( List<ContraparteCliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorClientes.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_clientes, parent, false);

        return new AdaptadorClientes.EventoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AdaptadorClientes.EventoViewHolder holder, int position) {

        ContraparteCliente Item = listaClientes.get(position);

        holder.textViewIdentificacion.setText("Identificación: "+Item.con_identificacion);
        holder.textViewNombreApellido.setText(Item.con_nombre + " " + Item.con_apellido);
        holder.textViewDireccion.setText("Dirección: "+Item.con_direccion);

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();

    }

    // Method to add a single item
    public void addItem(ContraparteCliente newItem) {
        listaClientes.add(newItem); // Add the new item
        notifyDataSetChanged(); // Notify the adapter that the dataset has changed
    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewIdentificacion, textViewNombreApellido, textViewApellido, textViewDireccion;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIdentificacion = itemView.findViewById(R.id.textViewClienteIdentificacion);
            textViewNombreApellido = itemView.findViewById(R.id.textViewClienteNombre);
            textViewDireccion= itemView.findViewById(R.id.textViewClienteDireccion);
        }
    }


}

