package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;
import com.example.vendeton.VendeTon;

public class ActivityDocumentos extends AppCompatActivity {

    ImageButton botonAtras, botonVentasMayoristas, botonVentasMinoristas, botonPagosAEmpleados, botonComprasDeMateriales, botonPagosDelArriendo;
    TextView textViewVentasMayoristas, textViewVentasMinoristas, textViewPagosAEmpleados, textViewComprasDeMateriales, textViewPagosDelArriendo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos);

        botonAtras = findViewById(R.id.imageButtonAtras20);
        botonVentasMayoristas = findViewById(R.id.imageButtonVentasMayoristas);

        textViewVentasMinoristas = findViewById(R.id.TextViewVentasMinoristas);
        textViewPagosAEmpleados = findViewById(R.id.TextViewPagosAEmpleados);
        textViewComprasDeMateriales= findViewById(R.id.TextViewComprasDeMateriales);
        textViewPagosDelArriendo= findViewById(R.id.TextViewPagosDelArriendo);;
        botonVentasMinoristas= findViewById(R.id.imageButtonVentasMinoristas);
        botonPagosAEmpleados= findViewById(R.id.imageButtonPagosAEmpleados);
        botonComprasDeMateriales= findViewById(R.id.imageButtonComprasDeMateriales);
        botonPagosDelArriendo= findViewById(R.id.imageButtonPagosDelArriendo);

        if (VendeTon.estadoUsuario == VendeTon.CLIENTE_MAYORISTA){
            textViewVentasMinoristas.setVisibility(View.GONE);
            textViewPagosAEmpleados.setVisibility(View.GONE);
            textViewComprasDeMateriales.setVisibility(View.GONE);
            textViewPagosDelArriendo.setVisibility(View.GONE);
            botonVentasMinoristas.setVisibility(View.GONE);
            botonPagosAEmpleados.setVisibility(View.GONE);
            botonComprasDeMateriales.setVisibility(View.GONE);
            botonPagosDelArriendo.setVisibility(View.GONE);

        }

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
        if (VendeTon.estadoUsuario == VendeTon.CLIENTE_MAYORISTA){
            Intent miIntent = new Intent(this, activity_documentos_vm_cliente.class);
            startActivity(miIntent);
            finish();
        } else{
            Intent miIntent = new Intent(this, ActivityDocumentosVM.class);
            startActivity(miIntent);
            finish();
        }



    }

}