package com.example.vendeton.Activitys;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class ActivityCrearBodegaOrigen extends AppCompatActivity {

    ConnectionClass connectionClass;

    Connection con;

    String str;

    ArrayList<String> listaBodegas;

    MaterialAutoCompleteTextView spinnerBodegas;

    private ArrayAdapter<String> bodegasAdapter;

    public String producto;

    TextInputEditText editTextCantidad;

    Button botonAgregarBodegaOrigen, botonCancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_bodega_origen);

        botonCancelar = findViewById(R.id.botonCancelarCrearBodegaOrigen);

        botonAgregarBodegaOrigen = findViewById(R.id.botonAceptarCrearBodegaOrigen);

        editTextCantidad = findViewById(R.id.editTextCantidad);

        producto = getIntent().getStringExtra("producto");

        establecerSpinnerBodegas();

        botonAgregarBodegaOrigen.setOnClickListener(i -> {
            if(comprobar())
                crearBodegaOrigen();
        });
        botonCancelar.setOnClickListener(i -> cancelarCreacionBodegaOrigen());

    }

    public void establecerSpinnerBodegas(){

        listaBodegas = new ArrayList<>();

        spinnerBodegas = findViewById(R.id.spinnerBodega);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarBodegasGeneral();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();

                        String nombre;

                        boolean flag = true;

                        while (rs.next()) {

                            nombre = rs.getString("bod_nombre");

                            for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
                                if(nombre.compareTo(ActivityCrearDocumentoVM.bodegaOrigen.get(i).bodega) == 0
                                    && ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(producto) == 0){
                                    flag = false;
                                }
                            }

                            if(ActivityCrearDetalleDocumentoVM.bodegasOrigen != null) {
                                for (int i = 0; i < ActivityCrearDetalleDocumentoVM.bodegasOrigen.size(); i++) {
                                    if (nombre.compareTo(ActivityCrearDetalleDocumentoVM.bodegasOrigen.get(i).bodega) == 0) {
                                        flag = false;
                                    }
                                }
                            }


                            if(flag) {
                                listaBodegas.add(nombre);
                            }

                            flag = true;

                        }

                        runOnUiThread(() -> {

                            bodegasAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, listaBodegas);
                            spinnerBodegas.setAdapter(bodegasAdapter);

                        });

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

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

    public void crearBodegaOrigen(){

        BodegaOrigen bodegaOrigen = new BodegaOrigen(0, null, spinnerBodegas.getText().toString(), Integer.parseInt(editTextCantidad.getText().toString()));

        if(ActivityCrearDetalleDocumentoVM.bodegasOrigen != null){
            ActivityCrearDetalleDocumentoVM.bodegasOrigen.add(bodegaOrigen);
        } else {
            Toast.makeText(this, producto,Toast.LENGTH_LONG).show();
            bodegaOrigen.producto = producto;
            ActivityCrearDocumentoVM.bodegaOrigen.add(bodegaOrigen);
        }

        finish();

    }

    public boolean comprobar(){

        boolean flag = true;

        if(spinnerBodegas.getText().toString().toString().compareTo("") == 0){
            flag = false;
            Toast.makeText(this, "Debe seleccionar una bodega", Toast.LENGTH_LONG).show();
        }

        if(editTextCantidad.getText().toString().compareTo("") == 0 || Integer.parseInt(editTextCantidad.getText().toString()) <= 0){
            flag = false;
            Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_LONG).show();
        }

        return flag;
    }

    public void cancelarCreacionBodegaOrigen(){
        finish();
    }

}