package com.example.vendeton.Activitys;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;
import com.example.vendeton.db.DbSesion;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.R;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void revisar(View view) {
        //VendeTon.username = "cliente_mayorista";
        //VendeTon.password = "clientemayorista";
        VendeTon.username = "administrador";
        VendeTon.password = "administrador";

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            ConnectionClass connection = new ConnectionClass();
            Boolean comprobacion = connection.comprobarconexion();
            Connection con = null;

            try {
                if (comprobacion) {
                    long identificacion = Long.parseLong(EditTextIdentificacion.getText().toString());
                    String query = "call sp_existeContraparteCliente(?)";
                    //con = connection.CONN();

/*
                    try (PreparedStatement stmt = con.prepareStatement(query)) {
                        stmt.setLong(1, identificacion);
                        boolean hasResults = stmt.execute();
                        //ResultSet rs = stmt.executeQuery();

                        if (hasResults) {
                            // Si existe algún resultado
                            runOnUiThread(() ->
                                    Toast.makeText(this, "Identificación errónea", Toast.LENGTH_SHORT).show()
                            );
                            return;
                        }
                    }

 */

                    DbSesion dbSesion = new DbSesion(this);
                    dbSesion.mantenerSesionIniciada(VendeTon.CLIENTE_MAYORISTA, identificacion);
                    VendeTon.estadoUsuario = VendeTon.CLIENTE_MAYORISTA;
                    VendeTon.identificacion = identificacion;

                    runOnUiThread(() -> {
                        Intent intent = new Intent(this, activity_info_cliente.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    });

                } else {
                    runOnUiThread(() ->
                            Toast.makeText(this, "Conexión fallida", Toast.LENGTH_SHORT).show()
                    );
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                runOnUiThread(() ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                );
            } finally {
                try {
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        executorService.shutdown();
    }
}