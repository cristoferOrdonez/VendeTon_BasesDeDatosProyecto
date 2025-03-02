package com.example.vendeton.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.BalanceGeneral;
import com.example.vendeton.Entidades.CatalogoProducto;
import com.example.vendeton.R;

import java.math.BigDecimal;
import java.util.List;


public class AdaptadorInformeCatalogoProductos extends RecyclerView.Adapter<AdaptadorInformeCatalogoProductos.EventoViewHolder>{

    List<CatalogoProducto> listaProductos;
    public AdaptadorInformeCatalogoProductos(List<CatalogoProducto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @NonNull
    @Override
    public AdaptadorInformeCatalogoProductos.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_catalogo_productos, parent, false);

        return new AdaptadorInformeCatalogoProductos.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorInformeCatalogoProductos.EventoViewHolder holder, int position) {

       CatalogoProducto Item = listaProductos.get(position);

       holder.textViewCatalogoProductoNombre.setText("" + Item.nombre_producto);
       holder.textViewCatalogoPrecioPorMayor.setText("Precio al por mayor: $"+ Item.precio_al_por_mayor);
       holder.textViewCatalogoPrecioPorMenor.setText("Precio al por menor: $"+ Item.precio_al_por_menor);
       holder.textViewCatalogoExistencias.setText("Existencias: "+ Item.existencias);

    }

    @Override
    public int getItemCount() {
        return listaProductos.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewCatalogoProductoNombre, textViewCatalogoPrecioPorMayor, textViewCatalogoPrecioPorMenor, textViewCatalogoExistencias;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewCatalogoProductoNombre = itemView.findViewById(R.id.textViewCatalogoNombreProducto);
            textViewCatalogoPrecioPorMayor = itemView.findViewById(R.id.textViewCatalogoPrecioPorMayor);
            textViewCatalogoPrecioPorMenor = itemView.findViewById(R.id.textViewCatalogoPrecioPorMenor);
            textViewCatalogoExistencias = itemView.findViewById(R.id.textViewCostoECatalogoExistencias);
        }
    }


}
