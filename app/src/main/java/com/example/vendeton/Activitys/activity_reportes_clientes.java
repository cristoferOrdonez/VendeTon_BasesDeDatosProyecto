package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;

public class activity_reportes_clientes extends AppCompatActivity {

    LinearLayout  layoutClientes, layoutEmpleados, layoutProveedores, layoutProductos, layoutBodegas, layoutMateriales;
    ImageButton imageButtonAtras;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_clientes);

        imageButtonAtras = findViewById(R.id.imageButtonAtras8);
        imageButtonAtras.setOnClickListener(view -> {
            volver();
        });
    }

    private void volver() {
        Intent miIntent = new Intent(this, activity_pagina_inicial.class);
        startActivity(miIntent);
        finish();
    }
}
