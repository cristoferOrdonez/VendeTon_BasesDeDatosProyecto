package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.vendeton.R;

public class activity_reportes_bodegas extends AppCompatActivity {

    LinearLayout  layoutClientes, layoutEmpleados, layoutProveedores, layoutProductos, layoutBodegas, layoutMateriales;
    CardView ProductosPorBodega;
    ImageButton imageButtonAtras;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_bodegas);

        imageButtonAtras = findViewById(R.id.imageButtonAtras7);
        imageButtonAtras.setOnClickListener(v -> volver());

        ProductosPorBodega = findViewById(R.id.CardViewProductosPorBodega);
        ProductosPorBodega.setOnClickListener(v -> productosPorBodega());
    }

    private void productosPorBodega() {
        Intent miIntent = new Intent(this, ActivityReporteProductosPorBodega.class);
        startActivity(miIntent);
        finish();
    }

    private void volver() {
        Intent miIntent = new Intent(this, activity_pagina_inicial.class);
        startActivity(miIntent);
        finish();
    }
}
