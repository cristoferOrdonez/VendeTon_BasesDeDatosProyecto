package com.example.vendeton.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    Button AccederAdmin, AccederClienteMayorista, AccederUsuarioPublico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_usuario);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Eliminar Evento");
        //builder.setMessage("¿Está seguro de que desea eliminar este evento?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                AccederAdmin = findViewById(R.id.accederAdministrador);
                AccederClienteMayorista = findViewById(R.id.accederClienteMayorista);
                AccederUsuarioPublico = findViewById(R.id.accederUusarioPublico);

                if(VendeTon.estadoUsuario == VendeTon.ADMINISTRADOR){
                    Toast.makeText(ActivitySeleccionUsuario.this,"ADMINISTRADOR",Toast.LENGTH_SHORT);
                    Intent intent = new Intent(ActivitySeleccionUsuario.this, activity_pagina_inicial.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                } else if(VendeTon.estadoUsuario == VendeTon.CLIENTE_MAYORISTA){
                    Toast.makeText(ActivitySeleccionUsuario.this,"CLIENTE_MAYORISTA",Toast.LENGTH_SHORT);
                    Intent intent = new Intent(ActivitySeleccionUsuario.this, activity_info_cliente.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else if(VendeTon.estadoUsuario == VendeTon.USUARIO_PUBLICO) {
                    Toast.makeText(ActivitySeleccionUsuario.this,"USUARIO_PUBLICO",Toast.LENGTH_SHORT);
                    Intent intent = new Intent(ActivitySeleccionUsuario.this, activity_informe_catalogo.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ActivitySeleccionUsuario.this,"SIN_REGISTRAR",Toast.LENGTH_SHORT);
                }


                AccederAdmin.setOnClickListener((View view) -> {
                    Intent intent = new Intent(ActivitySeleccionUsuario.this, activity_iniciar_sesion_admin.class);
                    startActivity(intent);
                });

                AccederClienteMayorista.setOnClickListener((View view) -> {
                    Intent intent = new Intent(ActivitySeleccionUsuario.this, activity_iniciar_sesion_cliente.class);
                    startActivity(intent);
                });

                AccederUsuarioPublico.setOnClickListener((View view) -> {
                    VendeTon.username = "usuario_publico";
                    VendeTon.password = "";
                    Intent intent = new Intent(ActivitySeleccionUsuario.this, activity_informe_catalogo.class);
                    startActivity(intent);
                });

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }
}