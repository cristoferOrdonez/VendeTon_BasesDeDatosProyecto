package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;

public class activity_reportes_productos extends AppCompatActivity {

    LinearLayout  layoutClientes, layoutEmpleados, layoutProveedores, layoutProductos, layoutBodegas, layoutMateriales;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_productos);


    }
}
