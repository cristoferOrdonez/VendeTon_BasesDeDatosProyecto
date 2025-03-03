package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.Producto;
import com.example.vendeton.R;

import java.util.ArrayList;

public class AdaptadorProductos extends RecyclerView.Adapter<AdaptadorProductos.EventoViewHolder>{

    ArrayList<Producto> listaProductos;

    public AdaptadorProductos(ArrayList<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public AdaptadorProductos.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_productos, parent, false);

        return new AdaptadorProductos.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProductos.EventoViewHolder holder, int position) {

        Producto producto = listaProductos.get(position);

        holder.textViewNombreProducto.setText(producto.nombre);
        holder.textViewCostoProduccion.setText("Costo de producción: "+producto.costoDeProduccion);
        holder.textViewPrecioAlPorMayor.setText("Precio al por mayor: "+producto.precioAlPorMayor);
        holder.textViewPrecioAlPorMenor.setText("Precio al por menor: "+producto.precioAlPorMenor);
        holder.textViewComision.setText("Comisión por elaboración: "+producto.comision);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNombreProducto, textViewCostoProduccion,
            textViewPrecioAlPorMayor, textViewPrecioAlPorMenor, textViewComision;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNombreProducto = itemView.findViewById(R.id.textViewNombreProducto);
            textViewCostoProduccion = itemView.findViewById(R.id.textViewCostoProduccion);
            textViewPrecioAlPorMayor = itemView.findViewById(R.id.textViewPrecioAlPorMayor);
            textViewPrecioAlPorMenor = itemView.findViewById(R.id.textViewPrecioAlPorMenor);
            textViewComision = itemView.findViewById(R.id.textViewComision);

        }
    }
}

