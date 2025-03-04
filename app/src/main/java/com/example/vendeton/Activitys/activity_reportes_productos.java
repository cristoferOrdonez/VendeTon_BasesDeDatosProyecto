package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.cardview.widget.CardView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;

import org.w3c.dom.Text;

public class activity_reportes_productos extends AppCompatActivity {

    LinearLayout  layoutClientes, layoutEmpleados, layoutProveedores, layoutProductos, layoutBodegas, layoutMateriales;
    CardView TextViewPreciosExistenciasTotales;
    ImageButton imageButtonAtras12;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_productos);
        TextViewPreciosExistenciasTotales = findViewById(R.id.CardViewPreciosExistenciasTotales);
        TextViewPreciosExistenciasTotales.setOnClickListener(view -> {
            IrACatalogo();
        });

        imageButtonAtras12 = findViewById(R.id.imageButtonAtras12);
        imageButtonAtras12.setOnClickListener(view -> {
            volver();
        });

    }

    private void IrACatalogo() {
        Intent miIntent = new Intent(this, activity_informe_catalogo.class);
        startActivity(miIntent);
        finish();
    }

    private void volver() {
        Intent miIntent = new Intent(this, activity_pagina_inicial.class);
        startActivity(miIntent);
        finish();
    }
}
