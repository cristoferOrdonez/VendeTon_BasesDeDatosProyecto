package com.example.vendeton.Activitys;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorDocumentosVM;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_documentos_VM extends AppCompatActivity {

    RecyclerView listaDocumentos;

    ConnectionClass connectionClass;

    Connection con;

    ResultSet rs;

    String name, str;

    ArrayList<DocumentoVM> documentos;

    AdaptadorDocumentosVM adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_vm);

        listaDocumentos = findViewById(R.id.RecyclerViewDocumntosVM);
        documentos = new ArrayList<>();
        documentos.add(new DocumentoVM(1,2,new Date(),2,"","","","","","",""));

        listaDocumentos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new AdaptadorDocumentosVM(documentos);
        listaDocumentos.setAdapter(adapter);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_mostrarTodosLosDocumentosVentaMayorista();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        DocumentoVM documento;

                        while (rs.next()) {

                            documento = new DocumentoVM(

                                    rs.getInt("doc_numero"),
                                    rs.getLong("con_identificacion"),
                                    rs.getDate("fecha"),
                                    rs.getDouble("doc_total"),
                                    rs.getString("doc_proposito_de_compra"),
                                    rs.getString("con_nombre"),
                                    rs.getString("con_apellido"),
                                    rs.getString("con_calle"),
                                    rs.getString("con_barrio"),
                                    rs.getString("con_ciudad"),
                                    rs.getString("con_direccion")
                            );

                            documentos.add(documento);

                        }

                        runOnUiThread(() -> {

                            adapter.notifyDataSetChanged();

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