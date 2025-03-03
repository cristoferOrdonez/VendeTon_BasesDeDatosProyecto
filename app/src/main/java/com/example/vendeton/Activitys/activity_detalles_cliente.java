package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.db.ConnectionClass;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class activity_detalles_cliente extends AppCompatActivity {



    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str, intencion, vista, objeto;
    long con_identificacion;
    TextInputEditText PrimerCampo, SegundoCampo;
    TextInputLayout layoutPrimerCampo, layoutSegundoCampo;
    Button btnGuardar, btnCancelar;
    NumeroTelefonico numero;
    CorreoElectronico correo;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        con_identificacion = getIntent().getLongExtra("con_identificacion",0);
        intencion = getIntent().getStringExtra("tipo");
        vista = getIntent().getStringExtra("vista");
        objeto = getIntent().getStringExtra("objeto");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_editar_dos_campos);

        PrimerCampo = findViewById(R.id.editTextPrimerCampo);
        SegundoCampo = findViewById(R.id.editTextSegundoCampo);
        layoutPrimerCampo = findViewById(R.id.layoutPrimerCampo);
        layoutSegundoCampo = findViewById(R.id.layoutSegundoCampo);
        btnGuardar = findViewById(R.id.BotonDetalleGuardar);
        btnCancelar = findViewById(R.id.BotonDetalleCancelar);

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }

        });

        if (intencion.equals("numero")){
            layoutPrimerCampo.setHint("Prefijo");
            layoutSegundoCampo.setHint("Número");
            PrimerCampo.setInputType(InputType.TYPE_CLASS_NUMBER);
            SegundoCampo.setInputType(InputType.TYPE_CLASS_NUMBER);
            if (vista.equals("editar")){
                numero = getIntent().getParcelableExtra("numero");
                PrimerCampo.setText(""+numero.num_prefijo);
                SegundoCampo.setText(""+numero.num_numero);
            }
        } else{
            layoutPrimerCampo.setHint("Usuario");
            layoutSegundoCampo.setHint("Dominio");
            if (vista.equals("editar")){
                correo = getIntent().getParcelableExtra("correo");
                PrimerCampo.setText(""+correo.cor_usuario);
                SegundoCampo.setText(""+correo.cor_dominio);
            }
        }



}
    public void guardar(View view){
        if (intencion.equals("numero")){
            if (vista.equals("editar")){
                numero.num_prefijo_anterior = numero.num_prefijo;
                numero.num_prefijo = Integer.parseInt(PrimerCampo.getText().toString());
                numero.num_numero_anterior = numero.num_numero;
                numero.num_numero = Long.parseLong(SegundoCampo.getText().toString());

                if (objeto.equals("nuevo")){
                    if (con_identificacion != -1)
                        activity_info_cliente.numerosNuevos.add(numero);
                }
                else{
                    if (con_identificacion != -1)
                        activity_info_cliente.numerosActualizar.add(numero);
                }
            } else{
                numero = new NumeroTelefonico(0,con_identificacion,Integer.parseInt(PrimerCampo.getText().toString()),
                        Long.parseLong(SegundoCampo.getText().toString()),
                        Long.parseLong(PrimerCampo.getText().toString()+SegundoCampo.getText().toString()));

                if (con_identificacion != -1)
                    activity_info_cliente.numerosNuevos.add(numero);
            }
            if (con_identificacion != -1)
                activity_info_cliente.listaNumeros.add(numero);
            else
                activity_crear_cliente.listaNumeros.add(numero);
        }
        else if (intencion.equals("correo")){
            if (vista.equals("editar")){
                correo.cor_dominio_anterior = correo.cor_dominio;
                correo.cor_usuario_anterior = correo.cor_usuario;
                correo.cor_usuario = PrimerCampo.getText().toString();
                correo.cor_dominio = SegundoCampo.getText().toString();
                if (objeto.equals("nuevo")){
                    if (con_identificacion != -1)
                        activity_info_cliente.correosNuevos.add(correo);
                }
                else{
                    if (con_identificacion != -1)
                        activity_info_cliente.correosActualizar.add(correo);
                }
            }
            else{
                correo= new CorreoElectronico(PrimerCampo.getText().toString(),
                        SegundoCampo.getText().toString(),
                        PrimerCampo.getText().toString()+SegundoCampo.getText().toString(),
                        0,
                        con_identificacion);
                if (con_identificacion != -1)
                    activity_info_cliente.correosNuevos.add(correo);
            }
            if (con_identificacion != -1)
                activity_info_cliente.listaCorreos.add(correo);
            else
                activity_crear_cliente.listaCorreos.add(correo);
        }

        finish();

    }




    public void connect() {

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        if(con == null){
                            str = "Error in connection with MySQL server";
                        } else {
                            str = "Connected with MySQL server";
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        Toast.makeText(this, str,Toast.LENGTH_SHORT).show();

                    });
                }
        );

    }

}

