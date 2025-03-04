package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorDocumentosVM;
import com.example.vendeton.Adaptadores.AdaptadorReporteProductosPorBodega;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.Entidades.ReporteProductosPorBodega;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityReporteProductosPorBodega extends AppCompatActivity {

    RecyclerView listaProductos;
    ArrayList<ReporteProductosPorBodega> productos;
    AdaptadorReporteProductosPorBodega adapter;

    ConnectionClass connectionClass;
    Connection con;
    String str;

    ImageButton botonCrearAporteDeProducto, botonAtras;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_productos_por_bodega);

        botonCrearAporteDeProducto = findViewById(R.id.imageButtonCrearAporteDeProductos);

        botonCrearAporteDeProducto.setOnClickListener(i -> registroDeProductos());
        botonAtras = findViewById(R.id.imageButtonAtras20);
        botonAtras.setOnClickListener(i -> volver());

        showFadeInAnimation(botonCrearAporteDeProducto, 500);

        listaProductos = findViewById(R.id.RecyclerViewProductosPorBodega);
        productos = new ArrayList<>();

        listaProductos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new AdaptadorReporteProductosPorBodega(productos);
        listaProductos.setAdapter(adapter);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarExistenciasProductosPorBodega();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        ReporteProductosPorBodega producto;

                        while (rs.next()) {

                            producto = new ReporteProductosPorBodega(

                                    rs.getString("nombre_bodega"),
                                    rs.getString("nombre_producto"),
                                    rs.getInt("existencias")
                            );

                            productos.add(producto);

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

    private void volver() {
        Intent miIntent = new Intent(this, activity_reportes_bodegas.class);
        startActivity(miIntent);
        finish();
    }

    private void registroDeProductos() {

        Intent miIntent = new Intent(this, ActivityAportacionDeProductos.class);
        startActivity(miIntent);
        finish();
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