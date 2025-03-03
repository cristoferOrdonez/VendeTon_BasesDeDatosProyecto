package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vendeton.R;

public class ActivityEntregadosYSuministrados extends AppCompatActivity {

    ImageButton botonAtras, botonBodegas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entregados_ysuministrados);

        botonAtras = findViewById(R.id.imageButtonAtras);
        botonBodegas = findViewById(R.id.imageButtonBodegas);

        botonAtras.setOnClickListener(i -> volverMenuPrincipal());
        botonBodegas.setOnClickListener(i -> irABodegas());

    }

    private void irABodegas() {

        Intent miIntent = new Intent(this, ActivityBodegas.class);
        startActivity(miIntent);
        finish();

    }

    private void volverMenuPrincipal() {

        Intent miIntent = new Intent(this, activity_pagina_inicial.class);
        startActivity(miIntent);
        finish();

    }

}