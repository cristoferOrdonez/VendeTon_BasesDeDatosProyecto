package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;

public class activity_contrapartes extends AppCompatActivity {

    LinearLayout  layoutClientes;
    ImageButton imageButtonAtras6;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrapartes);

        layoutClientes = findViewById(R.id.LayoutContraparteCliente);
        layoutClientes.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_clientes.class);
            startActivity(miIntent);
        });
        imageButtonAtras6 = findViewById(R.id.imageButtonAtras6);
        imageButtonAtras6.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_pagina_inicial.class);
            startActivity(miIntent);
        });

    }
}
