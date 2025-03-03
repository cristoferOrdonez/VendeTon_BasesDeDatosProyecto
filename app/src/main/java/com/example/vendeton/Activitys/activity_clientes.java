package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_clientes extends AppCompatActivity {

    RecyclerView RecyclerViewClientes;
    public static ContraparteCliente listaClientes;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        RecyclerViewClientes = findViewById(R.id.RecyclerViewClientes);



    }
/*
    public void inserts(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                ConnectionClass connectionClass = new ConnectionClass();
                con = connectionClass.CONN();

                String query = "call sp_modificarContraparte(?,?,?,?,?,?,?);";

                try (CallableStatement stmt = con.prepareCall(query)) {
                    stmt.setLong(1, cliente.con_identificacion);
                    stmt.setString(2, NombreAcceder.getText().toString());
                    stmt.setString(3, ApellidoAcceder.getText().toString());
                    stmt.setString(4, CalleAcceder.getText().toString());
                    stmt.setString(5, BarrioAcceder.getText().toString());
                    stmt.setString(6, CiudadAcceder.getText().toString());
                    stmt.setString(7, CalleAcceder.getText().toString()+","+BarrioAcceder.getText().toString()+","+CiudadAcceder.getText().toString());

                    boolean hasResults = stmt.execute();

                    if (hasResults) {
                        try (ResultSet rs = stmt.getResultSet()) {
                            if (rs.next()) {
                                String mensaje = rs.getString("Mensaje");
                                runOnUiThread(() ->
                                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                                );
                            }
                        }
                    }

                } catch (SQLException e) {
                    Log.e("DB_ERROR", "Error", e);
                    runOnUiThread(() ->
                            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }catch (Exception e){
                    Log.e("DB_ERROR", "Error", e);
                    runOnUiThread(() ->
                            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }
    }*/
}
