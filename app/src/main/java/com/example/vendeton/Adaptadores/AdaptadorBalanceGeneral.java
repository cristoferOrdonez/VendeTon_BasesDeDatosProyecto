package com.example.vendeton.Adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.BalanceGeneral;
import com.example.vendeton.R;

import java.math.BigDecimal;
import java.util.List;



public class AdaptadorBalanceGeneral extends RecyclerView.Adapter<AdaptadorBalanceGeneral.EventoViewHolder>{

    List<BalanceGeneral> listaBalance;
    public AdaptadorBalanceGeneral(List<BalanceGeneral> listaBalance) {
        this.listaBalance = listaBalance;
    }

    @NonNull
    @Override
    public AdaptadorBalanceGeneral.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_balance_general, parent, false);

        return new AdaptadorBalanceGeneral.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorBalanceGeneral.EventoViewHolder holder, int position) {

       BalanceGeneral Item = listaBalance.get(position);

       holder.textViewContenidoTipoBalance.setText("" + Item.tip_nombre);
       Double monto= Item.monto;
       holder.textViewContenidoMontoBalance.setText("$"+new BigDecimal(monto.toString()).stripTrailingZeros().toPlainString());
    }

    @Override
    public int getItemCount() {
        return listaBalance.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewContenidoTipoBalance, textViewContenidoMontoBalance;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewContenidoTipoBalance = itemView.findViewById(R.id.textViewContenidoTipoBalance);
            textViewContenidoMontoBalance= itemView.findViewById(R.id.textViewContenidoMontoBalance);
        }
    }


}
