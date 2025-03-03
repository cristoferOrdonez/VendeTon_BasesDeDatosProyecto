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
import com.example.vendeton.VendeTon;

public class ActivityDocumentos extends AppCompatActivity {

    ImageButton botonAtras, botonVentasMayoristas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos);

        botonAtras = findViewById(R.id.imageButtonAtras);
        botonVentasMayoristas = findViewById(R.id.imageButtonVentasMayoristas);

        botonAtras.setOnClickListener(i -> volverMenuPrincipal());
        botonVentasMayoristas.setOnClickListener(i -> irADocumentosVM());

    }

    private void volverMenuPrincipal() {

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

    private void irADocumentosVM() {

        Intent miIntent = new Intent(this, ActivityDocumentosVM.class);
        startActivity(miIntent);
        finish();

    }

}