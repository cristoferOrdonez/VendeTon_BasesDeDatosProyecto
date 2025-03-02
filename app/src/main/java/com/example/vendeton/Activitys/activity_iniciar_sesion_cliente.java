package com.example.vendeton.Activitys;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.vendeton.db.DbSesion;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;
import com.google.android.material.textfield.TextInputEditText;

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
        DbSesion dbSesion= new DbSesion(this);
        dbSesion.mantenerSesionIniciada(1, Integer.parseInt(EditTextIdentificacion.getText().toString()));
    }
}