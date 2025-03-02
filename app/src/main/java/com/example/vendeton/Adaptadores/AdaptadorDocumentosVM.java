package com.example.vendeton.Adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Activitys.activity_info_cliente;
import com.example.vendeton.Activitys.activity_ver_info_documento_vm;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class AdaptadorDocumentosVM extends RecyclerView.Adapter<AdaptadorDocumentosVM.EventoViewHolder>{

    ArrayList<DocumentoVM> listaDocumentosVM;

    public AdaptadorDocumentosVM(ArrayList<DocumentoVM> listaEventos) {
        this.listaDocumentosVM = listaEventos;
    }

    @NonNull
    @Override
    public AdaptadorDocumentosVM.EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout_documentos_vm, parent, false);

        return new AdaptadorDocumentosVM.EventoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorDocumentosVM.EventoViewHolder holder, int position) {

        DocumentoVM documento = listaDocumentosVM.get(position);

        holder.textViewNumeroDocumento.setText("Numero " + documento.doc_numero);
        holder.textViewFechaDocumento.setText("Fecha: " + documento.fecha);
        holder.textViewClienteDocumento.setText("Cliente: " + documento.con_nombre + " " + documento.con_apellido);
        holder.textViewTotalDocumento.setText("Total: " + documento.doc_total);

        holder.botonVerDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent miIntent = new Intent(v.getContext(), activity_ver_info_documento_vm.class);
                miIntent.putExtra("documento", documento);
                v.getContext().startActivity(miIntent);
                ((Activity)v.getContext()).finishAffinity();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDocumentosVM.size();

    }

    public class EventoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewNumeroDocumento, textViewFechaDocumento, textViewClienteDocumento, textViewTotalDocumento;
        ImageButton botonVerDocumento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewNumeroDocumento = itemView.findViewById(R.id.textViewNumeroDocumentoVM);
            textViewFechaDocumento = itemView.findViewById(R.id.textViewFechaDocumentoVM);
            textViewClienteDocumento = itemView.findViewById(R.id.textViewNombreClienteDocumentoVM);
            textViewTotalDocumento = itemView.findViewById(R.id.textViewTotalDocumentoVM);
            botonVerDocumento = itemView.findViewById(R.id.imageButtonRevisarDocumentoVM);
        }
    }
}