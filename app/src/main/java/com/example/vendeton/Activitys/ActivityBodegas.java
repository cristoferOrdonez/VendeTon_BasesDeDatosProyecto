package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorBodegas;
import com.example.vendeton.Adaptadores.AdaptadorDocumentosVM;
import com.example.vendeton.Entidades.Bodega;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityBodegas extends AppCompatActivity {

    ImageButton botonCrearBodega, botonAtras;

    RecyclerView listaBodegas;

    ArrayList<Bodega> bodegas;

    AdaptadorBodegas adapter;

    ConnectionClass connectionClass;

    Connection con;

    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodegas);
        botonCrearBodega = findViewById(R.id.imageButtonCrearBodega);
        botonAtras = findViewById(R.id.imageButtonAtras);

        botonAtras.setOnClickListener(I -> volverASuministroYEntrega());

        botonCrearBodega.setOnClickListener(i -> registroDeBodegas());

        showFadeInAnimation(botonCrearBodega, 500);

        listaBodegas = findViewById(R.id.RecyclerViewBodegas);
        bodegas = new ArrayList<>();

        listaBodegas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new AdaptadorBodegas(bodegas);
        listaBodegas.setAdapter(adapter);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarBodegasGeneral();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        Bodega bodega;

                        while (rs.next()) {

                            bodega = new Bodega(

                                    rs.getShort("bod_id"),
                                    rs.getString("bod_nombre"),
                                    rs.getInt("bod_alto"),
                                    rs.getInt("bod_ancho"),
                                    rs.getInt("bod_largo"),
                                    rs.getString("bod_dimensiones"),
                                    rs.getString("bod_calle"),
                                    rs.getString("bod_barrio"),
                                    rs.getString("bod_ciudad"),
                                    rs.getString("bod_direccion")
                            );

                            bodegas.add(bodega);

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

    private void volverASuministroYEntrega() {

        Intent miIntent = new Intent(this, ActivityBodegas.class);
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

    public void registroDeBodegas(){
        Intent miIntent = new Intent(this, ActivityCrearBodegas.class);
        startActivity(miIntent);
    }

}