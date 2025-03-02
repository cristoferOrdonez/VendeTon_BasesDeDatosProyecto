package com.example.vendeton.Activitys;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vendeton.Entidades.BodegaOrigen;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityEditarBodegaOrigen extends AppCompatActivity {

    ConnectionClass connectionClass;

    Connection con;

    String str;

    ArrayList<String> listaBodegas;

    MaterialAutoCompleteTextView spinnerBodegas;

    public String producto, bodega;

    TextInputEditText editTextCantidad;

    Button botonAgregarBodegaOrigen, botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_bodega_origen);

        botonCancelar = findViewById(R.id.botonCancelarCrearBodegaOrigen);

        botonAgregarBodegaOrigen = findViewById(R.id.botonAceptarCrearBodegaOrigen);

        editTextCantidad = findViewById(R.id.editTextCantidad);

        producto = getIntent().getStringExtra("producto");
        bodega = getIntent().getStringExtra("bodega");

        establecerSpinnerProductos();

        botonAgregarBodegaOrigen.setOnClickListener(i -> actualizarBodegaOrigen());

        establecerDatosPrevios();

        botonCancelar.setOnClickListener(I -> cancelarCreacionBodegaOrigen());
        spinnerBodegas.setEnabled(false);

    }

    public void establecerSpinnerProductos(){

        listaBodegas = new ArrayList<>();

        spinnerBodegas = findViewById(R.id.spinnerBodega);

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

                    });
                }
        );

    }

    public void establecerDatosPrevios(){

        BodegaOrigen bodegaOrigen = null;

        for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
            if(ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(producto) == 0
                && ActivityCrearDocumentoVM.bodegaOrigen.get(i).bodega.compareTo(bodega) == 0){
                bodegaOrigen = ActivityCrearDocumentoVM.bodegaOrigen.get(i);
            }
        }

        if(bodegaOrigen == null){
            for(int i = 0; i < ActivityCrearDetalleDocumentoVM.bodegasOrigen.size(); i++){
                if(ActivityCrearDetalleDocumentoVM.bodegasOrigen.get(i).bodega.compareTo(bodega) == 0)
                    bodegaOrigen = ActivityCrearDetalleDocumentoVM.bodegasOrigen.get(i);
            }
        }

        spinnerBodegas.setText(bodegaOrigen.bodega);
        editTextCantidad.setText(""+bodegaOrigen.cantidad);

    }

    public void actualizarBodegaOrigen(){

        for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
            if(ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(producto) == 0
                    && ActivityCrearDocumentoVM.bodegaOrigen.get(i).bodega.compareTo(bodega) == 0){
                BodegaOrigen bodegaOrigen = ActivityCrearDocumentoVM.bodegaOrigen.get(i);
                bodegaOrigen.producto = producto;
                bodegaOrigen.bodega = bodega;
                bodegaOrigen.cantidad = Integer.parseInt(editTextCantidad.getText().toString());
            }
        }

        for(int i = 0; i < ActivityCrearDetalleDocumentoVM.bodegasOrigen.size(); i++){
            if(ActivityCrearDetalleDocumentoVM.bodegasOrigen.get(i).bodega.compareTo(bodega) == 0){
                BodegaOrigen bodegaOrigen = ActivityCrearDetalleDocumentoVM.bodegasOrigen.get(i);
                bodegaOrigen.producto = producto;
                bodegaOrigen.bodega = bodega;
                bodegaOrigen.cantidad = Integer.parseInt(editTextCantidad.getText().toString());
            }
        }

        finish();

    }

    public void cancelarCreacionBodegaOrigen(){
        finish();
    }

}