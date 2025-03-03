package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;

public class activity_reportes extends AppCompatActivity {

    LinearLayout layoutClientes, layoutEmpleados, layoutProveedores, layoutProductos, layoutBodegas, layoutMateriales, layoutOtrosReportes;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informes);

        layoutClientes = findViewById(R.id.LayoutReportesCliente);
        layoutClientes.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_clientes.class);
            startActivity(miIntent);
        });

        layoutEmpleados = findViewById(R.id.LayoutReportesEmpleado);
        layoutEmpleados.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_empleados.class);
            startActivity(miIntent);
        });

        layoutProveedores = findViewById(R.id.LayoutReportesProveedor);
        layoutProveedores.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_proveedores.class);
            startActivity(miIntent);
        });

        layoutProductos = findViewById(R.id.LayoutReportesProductos);

        layoutProductos.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_productos.class);
            startActivity(miIntent);
        });

        layoutBodegas = findViewById(R.id.LayoutReportesBodegas);
        layoutBodegas.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_bodegas.class);
            startActivity(miIntent);
        });

        layoutMateriales = findViewById(R.id.LayoutReportesMateriales);
        layoutMateriales.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_reportes_materiales.class);
            startActivity(miIntent);
        });

        layoutOtrosReportes = findViewById(R.id.LayoutReportesOtrosReportes);
        layoutOtrosReportes.setOnClickListener(v -> {
            Intent miIntent = new Intent(this, activity_otros_reportes.class);
            startActivity(miIntent);
        });



    }
}
