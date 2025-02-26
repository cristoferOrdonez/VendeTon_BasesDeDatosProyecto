package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.vendeton.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class activity_info_cliente extends AppCompatActivity {

    TextInputEditText IdentificacionAcceder, NombreAcceder, ApellidoAcceder, CiudadAcceder, BarrioAcceder, CalleAcceder;
    TextInputLayout layoutIdentificacion, layoutNombre, layoutApellido, layoutCiudad, layoutBarrio, layoutCalle;
    Button editarBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_editar_info_cliente);


        IdentificacionAcceder = findViewById(R.id.editTextIdentificacion);
        NombreAcceder = findViewById(R.id.editTextNombre);
        ApellidoAcceder = findViewById(R.id.editTextApellido);
        CiudadAcceder = findViewById(R.id.editTextCiudad);
        BarrioAcceder = findViewById(R.id.editTextBarrio);
        CalleAcceder = findViewById(R.id.editTextCalle);
        layoutIdentificacion = findViewById(R.id.layoutIdentificacion);
        layoutNombre = findViewById(R.id.layoutNombre);
        layoutApellido = findViewById(R.id.layoutApellido);
        layoutCiudad = findViewById(R.id.layoutCiudad);
        layoutBarrio = findViewById(R.id.layoutBarrio);
        layoutCalle = findViewById(R.id.layoutCalle);

        //editarBtn = findViewById(R.id.layoutBotonesCu);
    }

}