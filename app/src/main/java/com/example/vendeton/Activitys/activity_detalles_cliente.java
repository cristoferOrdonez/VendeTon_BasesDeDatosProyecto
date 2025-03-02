package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class activity_detalles_cliente extends AppCompatActivity {



    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str, intencion;
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

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_editar_dos_campos);

        PrimerCampo = findViewById(R.id.editTextPrimerCampo);
        SegundoCampo = findViewById(R.id.editTextSegundoCampo);
        layoutPrimerCampo = findViewById(R.id.layoutPrimerCampo);
        layoutSegundoCampo = findViewById(R.id.layoutSegundoCampo);

        if (intencion.equals("numero")){
            layoutPrimerCampo.setHint("Prefijo");
            layoutSegundoCampo.setHint("Número");
        } else{
            layoutPrimerCampo.setHint("Usuario");
            layoutSegundoCampo.setHint("Dominio");
        }

}
    public void guardar(View view){
        if (intencion.equals("numero")){
            numero = new NumeroTelefonico(0,con_identificacion,Integer.parseInt(PrimerCampo.getText().toString()),
                    Long.parseLong(SegundoCampo.getText().toString()),
                    Long.parseLong(PrimerCampo.getText().toString()+SegundoCampo.getText().toString()));

            Intent miIntent = new Intent(this, activity_info_cliente.class);
            miIntent.putExtra("numero", numero);
            miIntent.putExtra("intencion", "numero");
            setResult(Activity.RESULT_OK, miIntent);
            finish();
        }
        else if (intencion.equals("correo")){
            correo= new CorreoElectronico(PrimerCampo.getText().toString(),
                    SegundoCampo.getText().toString(),
                    PrimerCampo.getText().toString()+SegundoCampo.getText().toString(),
                    0,
                    con_identificacion);

            Intent miIntent = new Intent(this, activity_info_cliente.class);
            miIntent.putExtra("correo", correo);
            miIntent.putExtra("intencion", "correo");
            setResult(Activity.RESULT_OK, miIntent);
            finish();
        }

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

