package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorDocumentosVM;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityDocumentosVM extends AppCompatActivity {

    RecyclerView listaDocumentos;

    ConnectionClass connectionClass;

    Connection con;

    String str;

    ArrayList<DocumentoVM> documentos;

    AdaptadorDocumentosVM adapter;

    ImageButton botonCrearDocumentoVM, botonAtras, botonRevisarDocumento;

    MaterialAutoCompleteTextView spinnerClientes;

    private ArrayAdapter<String> clientesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documentos_vm);

        botonAtras = findViewById(R.id.imageButtonAtras20);
        botonAtras.setOnClickListener(i -> volverADocumentos());

        botonCrearDocumentoVM = findViewById(R.id.imageButtonCrearDocumentoVM);

        botonCrearDocumentoVM.setOnClickListener(i -> registroDeDocumentosVM());

        showFadeInAnimation(botonCrearDocumentoVM, 500);

        listaDocumentos = findViewById(R.id.RecyclerViewDocumntosVM);
        documentos = new ArrayList<>();

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

                    });
                }
        );

    }

    private void volverADocumentos() {

        Intent miIntent = new Intent(this, ActivityDocumentos.class);
        startActivity(miIntent);
        finish();

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

    private void showFadeInAnimation(View view, long duration){

        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(duration);

        view.clearAnimation();
        view.startAnimation(fadeIn);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setVisibility(View.VISIBLE);
            }
        }, duration);

    }

    private void registroDeDocumentosVM(){
        Intent miIntent = new Intent(this, ActivityCrearDocumentoVM.class);
        startActivity(miIntent);
        finishAffinity();
    }

}