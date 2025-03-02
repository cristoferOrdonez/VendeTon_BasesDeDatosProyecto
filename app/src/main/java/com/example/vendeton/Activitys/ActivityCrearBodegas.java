package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityCrearBodegas extends AppCompatActivity {

    TextInputEditText editTextNombre, editTextAlto, editTextAncho, editTextLargo, editTextCalle, editTextBarrio, editTextCiudad;
    Button botonCancelar, botonAcepatar;
    ConnectionClass connectionClass;
    Connection con;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_bodegas);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextAlto = findViewById(R.id.editTextAlto);
        editTextAncho = findViewById(R.id.editTextAncho);
        editTextLargo = findViewById(R.id.editTextLargo);
        editTextCalle = findViewById(R.id.editTextCalle);
        editTextBarrio = findViewById(R.id.editTextBarrio);
        editTextCiudad = findViewById(R.id.editTextCiudad);

        botonCancelar = findViewById(R.id.botonCancelarCrearBodega);
        botonAcepatar = findViewById(R.id.botonAceptarCrearBodega);

        botonCancelar.setOnClickListener(i -> cancelar());
        botonAcepatar.setOnClickListener(i -> crearBodega());

    }

    public void crearBodega() {

        insertarBodega();

    }

    public void cancelar() {
        finish();
    }

    public void insertarBodega(){

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_insertarBodega(?,?,?,?,?,?,?);";
                        CallableStatement stmt = con.prepareCall(query);


                        stmt.setString(1, editTextNombre.getText().toString());
                        stmt.setInt(2, Integer.parseInt(editTextAlto.getText().toString()));
                        stmt.setInt(3, Integer.parseInt(editTextAncho.getText().toString()));
                        stmt.setInt(4, Integer.parseInt(editTextLargo.getText().toString()));
                        stmt.setString(5, editTextCalle.getText().toString());
                        stmt.setString(6, editTextBarrio.getText().toString());
                        stmt.setString(7, editTextCiudad.getText().toString());

                        stmt.executeUpdate();

                        //while (rs.next()) {

                            //numeroDeDocumento = rs.getInt("numero");

                        //}

                        runOnUiThread(() -> {

                            Intent miIntent = new Intent(this, ActivityBodegas.class);
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

}