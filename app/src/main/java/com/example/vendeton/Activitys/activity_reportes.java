package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;
import com.example.vendeton.VendeTon;

public class activity_reportes extends AppCompatActivity {

    LinearLayout layoutClientes, layoutEmpleados, layoutProveedores, layoutProductos, layoutBodegas, layoutMateriales, layoutOtrosReportes;
    ImageButton botonAtras;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        botonAtras = findViewById(R.id.imageButtonAtrasInformes);
        botonAtras.setOnClickListener(i -> volver());

        layoutClientes = findViewById(R.id.LayoutReportesCliente);
        layoutClientes.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_clientes.class);
            startActivity(miIntent);
        });

        layoutEmpleados = findViewById(R.id.LayoutReportesEmpleado);
        layoutEmpleados.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_empleados.class);
            startActivity(miIntent);
        });

        layoutProveedores = findViewById(R.id.LayoutReportesProveedor);
        layoutProveedores.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_proveedores.class);
            startActivity(miIntent);
        });

        layoutProductos = findViewById(R.id.LayoutReportesProductos);

        layoutProductos.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_productos.class);
            startActivity(miIntent);
        });

        layoutBodegas = findViewById(R.id.LayoutReportesBodegas);
        layoutBodegas.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_bodegas.class);
            startActivity(miIntent);
        });

        layoutMateriales = findViewById(R.id.LayoutReportesMateriales);
        layoutMateriales.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_materiales.class);
            startActivity(miIntent);
        });

        layoutOtrosReportes = findViewById(R.id.LayoutReportesOtrosReportes);
        layoutOtrosReportes.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_otros_reportes.class);
            startActivity(miIntent);
        });

        if (VendeTon.estadoUsuario != VendeTon.ADMINISTRADOR){
            layoutClientes.setVisibility(LinearLayout.GONE);
            layoutEmpleados.setVisibility(LinearLayout.GONE);
            layoutProveedores.setVisibility(LinearLayout.GONE);
            layoutBodegas.setVisibility(LinearLayout.GONE);
            layoutMateriales.setVisibility(LinearLayout.GONE);
            layoutOtrosReportes.setVisibility(LinearLayout.GONE);
        }


    }

    private void volver() {
        if (VendeTon.estadoUsuario == VendeTon.ADMINISTRADOR){
            Intent miIntent = new Intent(this, activity_pagina_inicial.class);
            startActivity(miIntent);
            finish();
        }
        else if (VendeTon.estadoUsuario == VendeTon.CLIENTE_MAYORISTA){
            Intent miIntent = new Intent(this, activity_info_cliente.class);
            startActivity(miIntent);
            finish();
        }
    }
}
