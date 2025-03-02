package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityAportacionDeProductos extends AppCompatActivity {

    private ArrayAdapter<String> productosAdapter, bodegasAdapter;
    MaterialAutoCompleteTextView spinnerProductos, spinnerBodegas;
    ArrayList<String> listaProductos, listaBodegas;
    ConnectionClass connectionClass;
    TextInputEditText editTextCantidad;
    Button botonAceptar, botonCancelar;

    Connection con;

    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aportacion_de_productos);

        botonCancelar = findViewById(R.id.botonCancelarCrearAportacionProducto);

        botonAceptar = findViewById(R.id.botonAceptarCrearAportacionProducto);

        editTextCantidad = findViewById(R.id.editTextCantidad);

        establecerSpinnerProductos();

        establecerSpinnerBodegas();

        botonAceptar.setOnClickListener(i -> crearProductoDeAportacion());
        botonCancelar.setOnClickListener(i -> cancelarCreacionProuctoDeAportacion());

    }

    private void cancelarCreacionProuctoDeAportacion() {

        Intent miIntent = new Intent(this, ActivityReporteProductosPorBodega.class);
        startActivity(miIntent);
        finish();

    }

    private void crearProductoDeAportacion() {

        insertarProducto();
    }

    public void establecerSpinnerProductos(){

        listaProductos = new ArrayList<>();

        spinnerProductos = findViewById(R.id.spinnerProducto);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarMercanciaProductoGeneral();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();

                        String nombre;

                        while (rs.next()) {

                            nombre = rs.getString("mer_nombre");

                            listaProductos.add(nombre);

                        }

                        runOnUiThread(() -> {

                            productosAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, listaProductos);
                            spinnerProductos.setAdapter(productosAdapter);

                        });

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

    }

    public void establecerSpinnerBodegas(){

        listaBodegas = new ArrayList<>();

        spinnerBodegas = findViewById(R.id.spinnerBodegas);

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

                        while (rs.next()) {

                            nombre = rs.getString("bod_nombre");

                            listaBodegas.add(nombre);

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

    public void insertarProducto(){
        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_insertarProductoEnBodega(?,?,?);";
                        CallableStatement stmt = con.prepareCall(query);


                        stmt.setString(1, spinnerProductos.getText().toString());
                        stmt.setString(2, spinnerBodegas.getText().toString());
                        stmt.setInt(3, Integer.parseInt(editTextCantidad.getText().toString()));

                        stmt.executeUpdate();


                        runOnUiThread(() -> {

                            Intent miIntent = new Intent(this, ActivityReporteProductosPorBodega.class);
                            startActivity(miIntent);
                            finish();

                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> {


                    });
                }
        );
    }



}