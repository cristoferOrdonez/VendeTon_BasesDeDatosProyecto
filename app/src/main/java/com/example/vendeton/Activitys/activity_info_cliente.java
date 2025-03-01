package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorCorreoElectronico;
import com.example.vendeton.Adaptadores.AdaptadorNumeroTelefonico;
import com.example.vendeton.ConnectionClass;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;
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
    Button editarBtn, agregarNumeroTelefono, agregarCorreoElectronico;
    LinearLayout BotonesCuenta, BotonesEditar;
    RecyclerView RecyclerViewNumeroTelefono, RecyclerViewCorreoElectronico;
    List<CorreoElectronico> listaCorreos;
    List<NumeroTelefonico> listaNumeros;
    List<NumeroTelefonico> numerosNuevos;
    List<CorreoElectronico> correosNuevos;
    AdaptadorNumeroTelefonico adaptadornumero;
    AdaptadorCorreoElectronico adaptadorcorreo;

    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;

    ContraparteCliente cliente;

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

        RecyclerViewNumeroTelefono = findViewById(R.id.RecyclerViewNumeroTelefono);
        RecyclerViewCorreoElectronico = findViewById(R.id.RecyclerViewCorreo);


        RecyclerViewCorreoElectronico.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        RecyclerViewNumeroTelefono.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        IdentificacionAcceder.setEnabled(false);
        NombreAcceder.setEnabled(false);
        ApellidoAcceder.setEnabled(false);
        CiudadAcceder.setEnabled(false);
        BarrioAcceder.setEnabled(false);
        CalleAcceder.setEnabled(false);
        agregarNumeroTelefono.setVisibility(View.INVISIBLE);
        agregarCorreoElectronico.setVisibility(View.INVISIBLE);


        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            try {
                listaNumeros = new ArrayList<>();
                listaCorreos = new ArrayList<>();
                numerosNuevos = new ArrayList<>();
                correosNuevos = new ArrayList<>();
                con = connectionClass.CONN();
                String query = "call sp_ConsultarContraparte(1001);";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();

                if(rs.next()){
                    int result;
                    cliente = new ContraparteCliente(rs.getInt("con_identificacion"),rs.getString("con_nombre"),
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


                con = connectionClass.CONN();
                query = "call consultarCorreosElectronicos(1001);";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();

                while(rs.next()){
                    CorreoElectronico correo = new CorreoElectronico(rs.getString("cor_usuario"),rs.getString("cor_dominio"),
                            rs.getString("cor_correo"),rs.getInt("cor_id"),rs.getInt("con_identificacion"));
                    listaCorreos.add(correo);
                }


                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


                con = connectionClass.CONN();
                query = "call  consultarNumerosTelefonicos(1001);";
                stmt = con.prepareStatement(query);
                rs = stmt.executeQuery();
                while(rs.next()){
                    NumeroTelefonico numero = new NumeroTelefonico(rs.getInt("num_id"), rs.getInt("con_identificacion"),
                            rs.getInt("num_prefijo"), rs.getLong("num_numero"), rs.getLong("num_numero_de_contacto"));
                    listaNumeros.add(numero);
                }


                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
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

                adaptadorcorreo = new AdaptadorCorreoElectronico(listaCorreos);
                RecyclerViewCorreoElectronico.setAdapter(adaptadorcorreo);

                adaptadornumero= new AdaptadorNumeroTelefonico(listaNumeros);
                RecyclerViewNumeroTelefono.setAdapter(adaptadornumero);

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

        inserts();

    }


    public void AgregarNumeroTelefono(View view){
        Intent miIntent = new Intent(this, activity_detalles_cliente.class);
        miIntent.putExtra("con_identificacion", cliente.con_identificacion);
        miIntent.putExtra("tipo", "numero");
        startActivityForResult(miIntent,1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) { // el "1" es el numero que pasaste como parametro
            if(resultCode == Activity.RESULT_OK){
                if (data.getStringExtra("intencion").equals("numero")){
                    NumeroTelefonico numeroregresado = data.getParcelableExtra("numero");
                    numerosNuevos.add(numeroregresado);
                    adaptadornumero.addItem(numeroregresado);
                }
                else if (data.getStringExtra("intencion").equals("correo")){
                    CorreoElectronico correoregresado = data.getParcelableExtra("correo");
                    correosNuevos.add(correoregresado);
                    adaptadorcorreo.addItem(correoregresado);
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "no se devolvio",Toast.LENGTH_SHORT).show();
            }
        }
    }//onActivityResult


    public void AgregarCorreoElectronico(View view){
        Intent miIntent = new Intent(this, activity_detalles_cliente.class);
        miIntent.putExtra("con_identificacion", cliente.con_identificacion);
        miIntent.putExtra("tipo", "correo");
        startActivityForResult(miIntent,1);
    }


    public void inserts(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                con = connectionClass.CONN();
                for (CorreoElectronico correos:correosNuevos) {
                    String query = "call sp_insertarCorreoElectronico(?,?,?);";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setLong(1, cliente.con_identificacion);
                    stmt.setString(2, correos.cor_usuario);
                    stmt.setString(3, correos.cor_dominio);

                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        String mensaje = rs.getString("Mensaje");
                        if (stmt != null) stmt.close();
                        if (rs != null) rs.close();
                        runOnUiThread(() -> {
                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                        });

                    }



                }

                for (NumeroTelefonico numeros: numerosNuevos) {
                    String query = "call  sp_insertarNumeroTelefonico(?,?,?);";
                    PreparedStatement stmt = con.prepareStatement(query);
                    stmt.setLong(1, cliente.con_identificacion);
                    stmt.setInt(2, numeros.num_prefijo);
                    stmt.setLong(3, numeros.num_numero);

                    rs = stmt.executeQuery();
                    if (rs.next()) {
                        String mensaje = rs.getString("Mensaje");
                        if (stmt != null) stmt.close();
                        if (rs != null) rs.close();
                        runOnUiThread(() -> {
                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
                        });

                    }
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

}

