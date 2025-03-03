package com.example.vendeton.Activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorCorreoElectronico;
import com.example.vendeton.Adaptadores.AdaptadorNumeroTelefonico;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;
import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_crear_cliente extends AppCompatActivity {
    TextInputEditText IdentificacionAcceder, NombreAcceder, ApellidoAcceder, CiudadAcceder, BarrioAcceder, CalleAcceder;
    TextInputLayout layoutIdentificacion, layoutNombre, layoutApellido, layoutCiudad, layoutBarrio, layoutCalle;
    Button editarBtn, agregarNumeroTelefono, agregarCorreoElectronico;
    LinearLayout BotonesCuenta, BotonesEditar;
    RecyclerView RecyclerViewNumeroTelefono, RecyclerViewCorreoElectronico;
    public static List<CorreoElectronico> listaCorreos;
    public static List<NumeroTelefonico> listaNumeros;
    AdaptadorNumeroTelefonico adaptadornumero;
    AdaptadorCorreoElectronico adaptadorcorreo;

    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str;

    long con_identificacion;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_editar_info_cliente);

        VendeTon.username = "farid";
        VendeTon.password = "contrasena";

        IdentificacionAcceder = findViewById(R.id.editTextIdentificacion);
        NombreAcceder = findViewById(R.id.editTextNombre);
        ApellidoAcceder = findViewById(R.id.editTextApellido);
        CiudadAcceder = findViewById(R.id.editTextCiudad);
        BarrioAcceder = findViewById(R.id.editTextBarrio);
        CalleAcceder = findViewById(R.id.editTextCalle);
        layoutIdentificacion = findViewById(R.id.layoutIdentificacion);

        IdentificacionAcceder.setInputType(InputType.TYPE_CLASS_NUMBER);
        NombreAcceder.setInputType(InputType.TYPE_CLASS_TEXT);
        ApellidoAcceder.setInputType(InputType.TYPE_CLASS_TEXT);
        CiudadAcceder.setInputType(InputType.TYPE_CLASS_TEXT);
        BarrioAcceder.setInputType(InputType.TYPE_CLASS_TEXT);
        CalleAcceder.setInputType(InputType.TYPE_CLASS_TEXT);

        layoutNombre = findViewById(R.id.layoutNombre);
        layoutApellido = findViewById(R.id.layoutApellido);
        layoutCiudad = findViewById(R.id.layoutCiudad);
        layoutBarrio = findViewById(R.id.layoutBarrio);
        layoutCalle = findViewById(R.id.layoutCalle);

        BotonesCuenta = findViewById(R.id.layoutBotonesCuenta);
        BotonesEditar = findViewById(R.id.layoutBotonesEdicion);

        agregarNumeroTelefono = findViewById(R.id.BotonAgregarNumeroTelefono);
        agregarCorreoElectronico = findViewById(R.id.BotonAgregarCorreoElectronico);

        agregarNumeroTelefono.setVisibility(View.VISIBLE);
        agregarCorreoElectronico.setVisibility(View.VISIBLE);

        listaCorreos = new ArrayList<>();
        listaNumeros = new ArrayList<>();

        RecyclerViewNumeroTelefono = findViewById(R.id.RecyclerViewNumeroTelefono);
        RecyclerViewCorreoElectronico = findViewById(R.id.RecyclerViewCorreo);



        RecyclerViewCorreoElectronico.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        RecyclerViewNumeroTelefono.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));

        LinearLayout.LayoutParams nuevoParametroEdicion = (LinearLayout.LayoutParams) BotonesEditar.getLayoutParams();
        nuevoParametroEdicion.weight = 1.5f;
        BotonesEditar.setLayoutParams(nuevoParametroEdicion);

        LinearLayout.LayoutParams nuevoParametroCuenta = (LinearLayout.LayoutParams) BotonesCuenta.getLayoutParams();
        nuevoParametroCuenta.weight = 0;
        BotonesCuenta.setLayoutParams(nuevoParametroCuenta);

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

    public void guardarInformacion(View view){
        inserts();
        finish();
    }

    /*@Override
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
    }//onActivityResult*/

    public void AgregarNumeroTelefono(View view){
        Intent miIntent = new Intent(this, activity_detalles_cliente.class);
        miIntent.putExtra("con_identificacion", (long) -1);
        miIntent.putExtra("tipo", "numero");
        miIntent.putExtra("vista", "agregar");
        miIntent.putExtra("objeto", "nuevo");
        startActivity(miIntent);
    }

    public void AgregarCorreoElectronico(View view){
        Intent miIntent = new Intent(this, activity_detalles_cliente.class);
        miIntent.putExtra("con_identificacion", (long) -1);
        miIntent.putExtra("tipo", "correo");
        miIntent.putExtra("vista", "agregar");
        miIntent.putExtra("objeto", "nuevo");
        startActivity(miIntent);
    }

    public void onResume(){
        super.onResume();
        if (listaCorreos == null)listaCorreos = new ArrayList<>();
        if(listaNumeros == null) listaNumeros = new ArrayList<>();

        adaptadorcorreo = new AdaptadorCorreoElectronico(listaCorreos);
        RecyclerViewCorreoElectronico.setAdapter(adaptadorcorreo);

        adaptadornumero = new AdaptadorNumeroTelefonico(listaNumeros);
        RecyclerViewNumeroTelefono.setAdapter(adaptadornumero);

        RecyclerViewCorreoElectronico.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);

                CorreoElectronico correo = listaCorreos.get(position);
                mostrarDialogo(correo);
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        RecyclerViewNumeroTelefono.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);

                NumeroTelefonico numero = listaNumeros.get(position);
                mostrarDialogo(numero);
                return true;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }


    public void mostrarDialogo(CorreoElectronico correoel){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listaCorreos.remove(correoel);

                adaptadorcorreo.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean nuevo = false;
                Intent intent = new Intent(activity_crear_cliente.this, activity_detalles_cliente.class);
                intent.putExtra("tipo", "correo");
                activity_info_cliente.listaCorreos.remove(correoel);
                intent.putExtra("vista", "editar");
                intent.putExtra("objeto", "nuevo");
                intent.putExtra("correo", correoel);
                activity_crear_cliente.this.startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void mostrarDialogo(NumeroTelefonico numerote){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                listaNumeros.remove(numerote);

                adaptadornumero.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(activity_crear_cliente.this, activity_detalles_cliente.class);
                intent.putExtra("tipo", "numero");

                intent.putExtra("objeto", "nuevo");
                activity_info_cliente.listaNumeros.remove(numerote);

                intent.putExtra("vista", "editar");
                intent.putExtra("numero", numerote);
                activity_crear_cliente.this.startActivity(intent);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public void inserts(){
        connectionClass = new ConnectionClass();
        con_identificacion = Long.parseLong(IdentificacionAcceder.getText().toString());
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                con = connectionClass.CONN();

                String query = "call sp_insertarContraparte(?,?,?,?,?,?,?);";

                try (CallableStatement stmt = con.prepareCall(query)) {
                    stmt.setLong(1, con_identificacion);
                    stmt.setString(2, NombreAcceder.getText().toString());
                    stmt.setString(3, ApellidoAcceder.getText().toString());
                    stmt.setString(4, CalleAcceder.getText().toString());
                    stmt.setString(5, BarrioAcceder.getText().toString());
                    stmt.setString(6, CiudadAcceder.getText().toString());
                    stmt.setString(7, CalleAcceder.getText().toString()+","+BarrioAcceder.getText().toString()+","+CiudadAcceder.getText().toString());

                    boolean hasResults = stmt.execute();

                    if (hasResults) {
                        try (ResultSet rs = stmt.getResultSet()) {
                            if (rs.next()) {
                                String mensaje = rs.getString("Mensaje");
                                runOnUiThread(() ->
                                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                                );
                            }
                        }
                    }

                } catch (SQLException e) {
                    Log.e("DB_ERROR", "Error", e);
                    runOnUiThread(() ->
                            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }

                query = "call sp_insertarContraparteCliente(?);";

                try (CallableStatement stmt = con.prepareCall(query)) {
                    stmt.setLong(1, con_identificacion);

                    boolean hasResults = stmt.execute();

                    if (hasResults) {
                        try (ResultSet rs = stmt.getResultSet()) {
                            if (rs.next()) {
                                String mensaje = rs.getString("Mensaje");
                                runOnUiThread(() ->
                                        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                                );
                            }
                        }
                    }

                } catch (SQLException e) {
                    Log.e("DB_ERROR", "Error", e);
                    runOnUiThread(() ->
                            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show()
                    );
                }

                for (CorreoElectronico correos:listaCorreos) {
                    query = "call sp_insertarCorreoElectronico(?,?,?);";

                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setLong(1, con_identificacion);
                        stmt.setString(2, correos.cor_usuario);
                        stmt.setString(3, correos.cor_dominio);

                        boolean hasResults = stmt.execute();

                        if (hasResults) {
                            try (ResultSet rs = stmt.getResultSet()) {
                                if (rs.next()) {
                                    String mensaje = rs.getString("Mensaje");
                                    runOnUiThread(() ->
                                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                                    );
                                }
                            }
                        }

                    } catch (SQLException e) {
                        Log.e("DB_ERROR", "Error", e);
                        runOnUiThread(() ->
                                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show()
                        );
                    }


                }

                for (NumeroTelefonico numeros: listaNumeros) {
                    query = "call  sp_insertarNumeroTelefonico(?,?,?);";
                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setLong(1, con_identificacion);
                        stmt.setInt(2, numeros.num_prefijo);
                        stmt.setLong(3, numeros.num_numero);

                        boolean hasResults = stmt.execute();

                        if (hasResults) {
                            try (ResultSet rs = stmt.getResultSet()) {
                                if (rs.next()) {
                                    String mensaje = rs.getString("Mensaje");
                                    runOnUiThread(() ->
                                            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
                                    );
                                }
                            }
                        }

                    } catch (SQLException e) {
                        Log.e("DB_ERROR", "Error", e);
                        runOnUiThread(() ->
                                Toast.makeText(this, ""+ e.getMessage(), Toast.LENGTH_LONG).show()
                        );
                    }
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

}
