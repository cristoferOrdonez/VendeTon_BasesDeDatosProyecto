package com.example.vendeton;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.db.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ConnectionClass connectionClass;

    Connection con;

    ResultSet rs;

    String name, str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                try {
                    con = connectionClass.CONN();

                    String query = "CALL sp_mostrarTodosLosDocumentosVentaMayorista();";
                    PreparedStatement stmt = con.prepareStatement(query);
                    ResultSet rs = stmt.executeQuery();
                    rs.next();
                    String mensaje = "" + rs.getString("doc_proposito_de_compra");

                runOnUiThread(() -> {


                    Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();

                });

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                runOnUiThread(() -> {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }

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

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    Toast.makeText(this, str,Toast.LENGTH_SHORT).show();

                });
            }
        );

    }

}