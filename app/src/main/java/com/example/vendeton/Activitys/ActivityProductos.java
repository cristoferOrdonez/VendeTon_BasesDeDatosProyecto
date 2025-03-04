package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorProductos;
import com.example.vendeton.Entidades.Producto;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityProductos extends AppCompatActivity {

    ImageButton botonCrearProducto, botonAtras;
    RecyclerView listaProductos;
    ArrayList<Producto> productos;
    AdaptadorProductos adapter;
    ConnectionClass connectionClass;
    Connection con;
    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos);
        botonAtras = findViewById(R.id.imageButtonAtras20);
        botonAtras.setOnClickListener(i -> irAMercancia());
        botonCrearProducto = findViewById(R.id.imageButtonCrearBodega);

        botonCrearProducto.setOnClickListener(i -> registroDeBodegas());




        showFadeInAnimation(botonCrearProducto, 500);

        listaProductos = findViewById(R.id.RecyclerViewBodegas);
        productos = new ArrayList<>();

        listaProductos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new AdaptadorProductos(productos);
        listaProductos.setAdapter(adapter);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarMercanciaProductoGeneral();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        Producto producto;

                        while (rs.next()) {

                            producto = new Producto(

                                    rs.getInt("mer_id"),
                                    rs.getInt("mer_cantidad_maxima"),
                                    rs.getInt("tip_id"),
                                    rs.getInt("mer_alto"),
                                    rs.getInt("mer_largo"),
                                    rs.getInt("mer_ancho"),
                                    rs.getString("mer_nombre"),
                                    rs.getString("mer_descripcion"),
                                    rs.getString("mer_dimensiones"),
                                    rs.getFloat("mer_precio_al_por_menor"),
                                    rs.getFloat("mer_precio_al_por_mayor"),
                                    rs.getFloat("mer_costo_de_produccion"),
                                    rs.getFloat("mer_comision_por_elaboracion")
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

    private void irAMercancia() {

        Intent miIntent = new Intent(this, ActivityMercancia.class);
        startActivity(miIntent);
        finish();

    }

    private void registroDeBodegas() {

        Intent miIntent = new Intent(this, ActivityCrearProducto.class);
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