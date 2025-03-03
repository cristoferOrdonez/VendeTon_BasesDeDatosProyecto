package com.example.vendeton.Activitys;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;
import com.example.vendeton.db.DbSesion;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_iniciar_sesion_cliente extends AppCompatActivity {

    TextInputEditText EditTextIdentificacion, EditTextContrasena;
    Button ButtonAcceder;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_de_sesion_cliente);

        EditTextIdentificacion = findViewById(R.id.editTextIdentificacion);

        ButtonAcceder = findViewById(R.id.botonAccederCuenta);

        ButtonAcceder.setOnClickListener((View view) -> {
           revisar(view);
        });



    }

    public void revisar(View view){
        VendeTon.username = "CelularRoot";
        VendeTon.password = "CelularRoot";


        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ConnectionClass con = new ConnectionClass();
            Boolean comprobacion = con.comprobarconexion();

            if (comprobacion){
                long identificacion = Long.parseLong(EditTextIdentificacion.getText().toString());
                DbSesion dbSesion= new DbSesion(this);
                dbSesion.mantenerSesionIniciada(1, identificacion);
                VendeTon.identificacion = identificacion;
                runOnUiThread(() -> {
                    Intent intent = new Intent(this, activity_info_cliente.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                });
            }
            else{
                runOnUiThread(() -> {
                    Toast.makeText(this, "Conexión fallada",Toast.LENGTH_SHORT).show();
                });
            }
        });
    }
}