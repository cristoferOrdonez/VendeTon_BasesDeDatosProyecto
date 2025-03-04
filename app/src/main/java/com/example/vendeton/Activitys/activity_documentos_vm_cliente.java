package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorDocumentosVM;
import com.example.vendeton.Adaptadores.AdaptadorDocumentosVMCliente;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.MainActivity;
import com.example.vendeton.R;
import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_documentos_vm_cliente extends AppCompatActivity {

    RecyclerView listaDocumentos;

    ConnectionClass connectionClass;

    Connection con;

    String str;

    ArrayList<DocumentoVM> documentos;

    AdaptadorDocumentosVMCliente adapter;

    ImageButton botonVerDocumentoVM, imageButtonEditar, imageButtonEliminar, imageButtonCrearDocumentoVM, imageButtonAtras;

    MaterialAutoCompleteTextView spinnerClientes;

    private ArrayAdapter<String> clientesAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_vm);

        listaDocumentos = findViewById(R.id.RecyclerViewDocumntosVM);
        documentos = new ArrayList<>();

        imageButtonAtras = findViewById(R.id.imageButtonAtras);
        imageButtonAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent miIntent = new Intent(activity_documentos_vm_cliente.this, activity_info_cliente.class);
                startActivity(miIntent);
                finishAffinity();
            }
        });

        listaDocumentos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new AdaptadorDocumentosVMCliente(documentos);
        listaDocumentos.setAdapter(adapter);
        imageButtonCrearDocumentoVM = findViewById(R.id.imageButtonCrearDocumentoVM);
        imageButtonCrearDocumentoVM.setVisibility(View.GONE);

        //VendeTon.username = "farid";
       // VendeTon.password = "contrasena";
        //VendeTon.identificacion = 1001;
        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {

                        con = connectionClass.CONN();

                        String query = "CALL sp_mostrarDocumentoVentaMayoristaId(?);";
                        PreparedStatement stmt = con.prepareStatement(query);
                        stmt.setLong(1, VendeTon.identificacion);
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

                }
        );

    }



    /*
    private void registroDeDocumentosVM(){
        Intent miIntent = new Intent(this, ActivityCrearDocumentoVM.class);
        startActivity(miIntent);
        finishAffinity();
    }*/

}
