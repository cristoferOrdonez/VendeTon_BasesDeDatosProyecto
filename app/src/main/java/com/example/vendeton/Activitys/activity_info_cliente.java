package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorCorreoElectronico;
import com.example.vendeton.ConnectionClass;
import com.example.vendeton.Entidades.BalanceGeneral;
import com.example.vendeton.Entidades.Contraparte;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class activity_info_cliente extends AppCompatActivity {

    TextInputEditText IdentificacionAcceder, NombreAcceder, ApellidoAcceder, CiudadAcceder, BarrioAcceder, CalleAcceder;
    TextInputLayout layoutIdentificacion, layoutNombre, layoutApellido, layoutCiudad, layoutBarrio, layoutCalle;
    Button editarBtn, agregarNumeroTelefono, agregarCorreoElectronico, agregarAreaTrabajo;
    LinearLayout BotonesCuenta, BotonesEditar;
    RecyclerView RecyclerViewNumeroTelefono, RecyclerViewCorreoElectronico, RecyclerViewAreaTrabajo;
    List<CorreoElectronico> listaCorreos;

    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_editar_info_cliente);


        IdentificacionAcceder = findViewById(R.id.editTextIdentificacion);
        NombreAcceder = findViewById(R.id.editTextNombre);
        ApellidoAcceder = findViewById(R.id.editTextApellido);
        CiudadAcceder = findViewById(R.id.editTextCiudad);
        BarrioAcceder = findViewById(R.id.editTextBarrio);
        CalleAcceder = findViewById(R.id.editTextCalle);
        layoutIdentificacion = findViewById(R.id.layoutIdentificacion);
        layoutNombre = findViewById(R.id.layoutNombre);
        layoutApellido = findViewById(R.id.layoutApellido);
        layoutCiudad = findViewById(R.id.layoutCiudad);
        layoutBarrio = findViewById(R.id.layoutBarrio);
        layoutCalle = findViewById(R.id.layoutCalle);

        editarBtn = findViewById(R.id.BotonEditarCliente);

        BotonesCuenta = findViewById(R.id.layoutBotonesCuenta);
        BotonesEditar = findViewById(R.id.layoutBotonesEdicion);

        agregarNumeroTelefono = findViewById(R.id.BotonAgregarNumeroTelefono);
        agregarCorreoElectronico = findViewById(R.id.BotonAgregarCorreoElectronico);
        agregarAreaTrabajo = findViewById(R.id.BotonAgregarAreaTrabajo);

        RecyclerViewNumeroTelefono = findViewById(R.id.RecyclerViewNumeroTelefono);
        RecyclerViewCorreoElectronico = findViewById(R.id.RecyclerViewCorreo);
        RecyclerViewAreaTrabajo = findViewById(R.id.RecyclerViewAreaTrabajo);


        RecyclerViewCorreoElectronico.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        RecyclerViewAreaTrabajo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        RecyclerViewNumeroTelefono.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        IdentificacionAcceder.setEnabled(false);
        NombreAcceder.setEnabled(false);
        ApellidoAcceder.setEnabled(false);
        CiudadAcceder.setEnabled(false);
        BarrioAcceder.setEnabled(false);
        CalleAcceder.setEnabled(false);
        agregarNumeroTelefono.setVisibility(View.INVISIBLE);
        agregarCorreoElectronico.setVisibility(View.INVISIBLE);
        agregarAreaTrabajo.setVisibility(View.INVISIBLE);




        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            try {
                con = connectionClass.CONN();
                String query = "call sp_ConsultarContraparte(1001);";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                Contraparte cliente;
                if(rs.next()){
                    int result;
                    cliente = new Contraparte(rs.getInt("con_identificacion"),rs.getString("con_nombre"),
                            rs.getString("con_apellido"),rs.getString("con_calle"),rs.getString("con_barrio"),
                            rs.getString("con_ciudad"), rs.getString("con_direccion"));
                    runOnUiThread(() -> {
                        IdentificacionAcceder.setText(""+cliente.con_identificacion);
                        NombreAcceder.setText(cliente.con_nombre);
                        ApellidoAcceder.setText(cliente.con_apellido);
                        CiudadAcceder.setText(cliente.con_ciudad);
                        CalleAcceder.setText(cliente.con_calle);
                        BarrioAcceder.setText(cliente.con_barrio);
                    });
                }

                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                List<CorreoElectronico> tempList = new ArrayList<>();
                con = connectionClass.CONN();
                query = "call consultarCorreosElectronicos(1001);";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                while(rs.next()){
                    CorreoElectronico correo = new CorreoElectronico(rs.getString("cor_usuario"),rs.getString("cor_dominio"),
                            rs.getString("cor_correo"),rs.getInt("cor_id"),rs.getInt("con_identificacion"));
                    tempList.add(correo);
                }

                runOnUiThread(() -> {
                    listaCorreos = new ArrayList<>();
                    listaCorreos.addAll(tempList);

                });

/*

                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                tempList = new ArrayList<>();
                con = connectionClass.CONN();
                query = "call consultarCorreosElectronicos(1001);";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                while(rs.next()){
                    CorreoElectronico correo = new CorreoElectronico(rs.getString("cor_usuario"),rs.getString("cor_dominio"),
                            rs.getString("cor_correo"),rs.getInt("cor_id"),rs.getInt("con_identificacion"));
                    tempList.add(correo);
                }

                runOnUiThread(() -> {
                    listaCorreos = new ArrayList<>();
                    listaCorreos.addAll(tempList);

                });*/




            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            runOnUiThread(() -> {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                AdaptadorCorreoElectronico adaptadorcorreo = new AdaptadorCorreoElectronico(listaCorreos);

                RecyclerViewCorreoElectronico.setAdapter(adaptadorcorreo);

            });
    });

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

    public void editarInformacion(View view){

        LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) BotonesEditar.getLayoutParams();
        nuevoParametroEdicion.weight = 3.5f;
        BotonesEditar.setLayoutParams(nuevoParametroEdicion);

        LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) BotonesCuenta.getLayoutParams();
        nuevoParametroCuenta.weight = 0;
        BotonesCuenta.setLayoutParams(nuevoParametroCuenta);


        NombreAcceder.setEnabled(true);
        ApellidoAcceder.setEnabled(true);
        CiudadAcceder.setEnabled(true);
        BarrioAcceder.setEnabled(true);
        CalleAcceder.setEnabled(true);

        agregarNumeroTelefono.setVisibility(View.VISIBLE);
        agregarCorreoElectronico.setVisibility(View.VISIBLE);
        agregarAreaTrabajo.setVisibility(View.VISIBLE);

    }


    public void guardarInformacion(View view){

        LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) BotonesEditar.getLayoutParams();
        nuevoParametroEdicion.weight = 0;
        BotonesEditar.setLayoutParams(nuevoParametroEdicion);

        LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) BotonesCuenta.getLayoutParams();
        nuevoParametroCuenta.weight = 3.5f;
        BotonesCuenta.setLayoutParams(nuevoParametroCuenta);


        NombreAcceder.setEnabled(false);
        ApellidoAcceder.setEnabled(false);
        CiudadAcceder.setEnabled(false);
        BarrioAcceder.setEnabled(false);
        CalleAcceder.setEnabled(false);

        agregarNumeroTelefono.setVisibility(View.INVISIBLE);
        agregarCorreoElectronico.setVisibility(View.INVISIBLE);
        agregarAreaTrabajo.setVisibility(View.INVISIBLE);


    }

}

