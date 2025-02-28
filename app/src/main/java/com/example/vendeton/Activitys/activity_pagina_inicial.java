package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorBalanceGeneral;
import com.example.vendeton.ConnectionClass;
import com.example.vendeton.Entidades.BalanceGeneral;
import com.example.vendeton.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class activity_pagina_inicial extends AppCompatActivity {

    RecyclerView RecyclerViewBalance;
    ConnectionClass connectionClass;
    List<BalanceGeneral> listaBalance;


    Connection con;

    ResultSet rs;
    String name, str;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagina_inicio);

        RecyclerViewBalance = findViewById(R.id.RecyclerViewBalanceGeneral);
        RecyclerViewBalance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));


        connectionClass = new ConnectionClass();
        connect();

/*
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        List<BalanceGeneral> tempList = new ArrayList<>();
                        con = connectionClass.CONN();
                        String query = "call sp_consultarMontoTipoTransaccion();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();
                        Double monto_total;
                        String tipo_de_transaccion;
                        while(rs.next()){
                            tipo_de_transaccion = rs.getString("tipo_de_transaccion");
                            monto_total= rs.getDouble("monto_total");
                            BalanceGeneral balanceItem = new BalanceGeneral(tipo_de_transaccion, monto_total);
                            tempList.add(balanceItem);
                        }

                        runOnUiThread(() -> {
                            listaBalance = new ArrayList<>();
                            listaBalance.addAll(tempList);

                        });


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {

                        try {
                            Thread.sleep(800);

                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

                        AdaptadorBalanceGeneral adaptador = new AdaptadorBalanceGeneral(listaBalance);
                        RecyclerViewBalance.setAdapter(adaptador);

                    });


                }
        );*/
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
