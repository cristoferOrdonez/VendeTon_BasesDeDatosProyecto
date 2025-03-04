package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorClientes;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;
import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_clientes extends AppCompatActivity {

    RecyclerView RecyclerViewClientes;
    public static List<ContraparteCliente> listaClientes;
    ImageButton botonCrearCliente;
    AdaptadorClientes adapter;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        RecyclerViewClientes = findViewById(R.id.RecyclerViewClientes);
        RecyclerViewClientes.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        botonCrearCliente = findViewById(R.id.imageButtonCrearCliente);

        botonCrearCliente.setOnClickListener(i -> {
            Intent miIntent = new Intent(this, activity_crear_cliente.class);
            startActivity(miIntent);
        });

        listaClientes = new ArrayList<>();
        retrieve();
        adapter = new AdaptadorClientes(listaClientes);
        RecyclerViewClientes.setAdapter(adapter);

    }

    public void retrieve(){
        //VendeTon.username = "farid";
        //VendeTon.password = "contrasena";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                ConnectionClass connectionClass = new ConnectionClass();
                Connection con = connectionClass.CONN();

                String query = "CALL sp_consultarClientes();";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                List<ContraparteCliente> clientesTemp = new ArrayList<>();

                while (rs.next()) {
                    String Direccion = rs.getString("Direccion");
                    if (Direccion == null || Direccion.trim().isEmpty()) {
                        Direccion = "No encontrado";
                    }

                    clientesTemp.add(new ContraparteCliente(
                            rs.getLong("Identificacion"),
                            rs.getString("Nombre"),
                            rs.getString("Apellido"),
                            rs.getString("Calle"),
                            rs.getString("Barrio"),
                            rs.getString("Ciudad"),
                            Direccion
                    ));

                    runOnUiThread(() -> {
                        listaClientes.clear();
                        listaClientes.addAll(clientesTemp);
                        RecyclerViewClientes.getAdapter().notifyDataSetChanged();
                    });

                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        });
    }

    public void onResume(){
        super.onResume();
        if (listaClientes == null) listaClientes = new ArrayList<>();
        retrieve();
    }
}
