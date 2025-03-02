package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;
import com.example.vendeton.VendeTon;
import com.google.android.material.textfield.TextInputEditText;

public class ActivitySeleccionUsuario extends AppCompatActivity {

    Button AccederAdmin, AccederClienteMayorista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_usuario);
        AccederAdmin = findViewById(R.id.accederAdministrador);
        AccederClienteMayorista = findViewById(R.id.accederClienteMayorista);

        if(VendeTon.estadoUsuario == VendeTon.ADMINISTRADOR){
            Toast.makeText(this,"ADMINISTRADOR",Toast.LENGTH_SHORT);
            Intent intent = new Intent(this, activity_pagina_inicial.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else if(VendeTon.estadoUsuario == VendeTon.CLIENTE_MAYORISTA){
            Toast.makeText(this,"CLIENTE_MAYORISTA",Toast.LENGTH_SHORT);
        }
        else {
            Toast.makeText(this,"USUARIO_PUBLICO",Toast.LENGTH_SHORT);
        }


        AccederAdmin.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, activity_iniciar_sesion_admin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

        AccederClienteMayorista.setOnClickListener((View view) -> {
            Intent intent = new Intent(this, activity_iniciar_sesion_cliente.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}