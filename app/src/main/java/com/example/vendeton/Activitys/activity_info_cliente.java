package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.NumeroTelefonico;
import com.example.vendeton.R;
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


public class activity_info_cliente extends AppCompatActivity {

    TextInputEditText IdentificacionAcceder, NombreAcceder, ApellidoAcceder, CiudadAcceder, BarrioAcceder, CalleAcceder;
    TextInputLayout layoutIdentificacion, layoutNombre, layoutApellido, layoutCiudad, layoutBarrio, layoutCalle;
    Button editarBtn, agregarNumeroTelefono, agregarCorreoElectronico;
    LinearLayout BotonesCuenta, BotonesEditar;
    RecyclerView RecyclerViewNumeroTelefono, RecyclerViewCorreoElectronico;
    public static List<CorreoElectronico> listaCorreos;
    public  static List<NumeroTelefonico> listaNumeros;
    public static List<NumeroTelefonico> numerosNuevos;
    public static List<CorreoElectronico> correosNuevos;
    public static List<CorreoElectronico> correosActualizar;
    public static List<NumeroTelefonico> numerosActualizar;
    public static List<NumeroTelefonico> numerosEliminar;
    public static List<CorreoElectronico> correosEliminar;
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
        agregarNumeroTelefono.setVisibility(View.GONE);
        agregarCorreoElectronico.setVisibility(View.GONE);

        establecerAdaptadores();

}



    private void ejecutarConsultaContraparte(Connection con, long id) {
        String query = "call sp_ConsultarContraparte(?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = new ContraparteCliente(
                            rs.getLong("con_identificacion"),
                            rs.getString("con_nombre"),
                            rs.getString("con_apellido"),
                            rs.getString("con_calle"),
                            rs.getString("con_barrio"),
                            rs.getString("con_ciudad"),
                            rs.getString("con_direccion")
                    );
                    actualizarUIcliente();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void ejecutarConsultaCorreos(Connection con, long id) {
        String query = "call consultarCorreosElectronicos(?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaCorreos.add(new CorreoElectronico(
                            rs.getString("cor_usuario"),
                            rs.getString("cor_dominio"),
                            rs.getString("cor_correo"),
                            rs.getInt("cor_id"),
                            rs.getInt("con_identificacion")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void ejecutarConsultaNumeros(Connection con, long id) {
        String query = "call consultarNumerosTelefonicos(?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaNumeros.add(new NumeroTelefonico(
                            rs.getInt("num_id"),
                            rs.getInt("con_identificacion"),
                            rs.getInt("num_prefijo"),
                            rs.getLong("num_numero"),
                            rs.getLong("num_numero_de_contacto")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    private void actualizarUIcliente() {
        runOnUiThread(() -> {
            IdentificacionAcceder.setText(String.valueOf(cliente.con_identificacion));
            NombreAcceder.setText(cliente.con_nombre);
            ApellidoAcceder.setText(cliente.con_apellido);
            CiudadAcceder.setText(cliente.con_ciudad);
            CalleAcceder.setText(cliente.con_calle);
            BarrioAcceder.setText(cliente.con_barrio);
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
        nuevoParametroEdicion.weight = 1.5f;
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
        nuevoParametroCuenta.weight = 1.5f;
        BotonesCuenta.setLayoutParams(nuevoParametroCuenta);


        NombreAcceder.setEnabled(false);
        ApellidoAcceder.setEnabled(false);
        CiudadAcceder.setEnabled(false);
        BarrioAcceder.setEnabled(false);
        CalleAcceder.setEnabled(false);

        agregarNumeroTelefono.setVisibility(View.GONE);
        agregarCorreoElectronico.setVisibility(View.GONE);

        inserts();

    }


    public void establecerAdaptadores(){
        numerosNuevos = new ArrayList<>();
        correosNuevos = new ArrayList<>();
        correosActualizar = new ArrayList<>();
        numerosActualizar = new ArrayList<>();
        numerosEliminar = new ArrayList<>();
        correosEliminar = new ArrayList<>();
        listaCorreos = new ArrayList<>();
        listaNumeros = new ArrayList<>();


        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            try {

                try (Connection con = connectionClass.CONN()) {
                    if (con == null) throw new SQLException("Conexión nula");

                    ejecutarConsultaContraparte(con, VendeTon.identificacion);
                    ejecutarConsultaCorreos(con, VendeTon.identificacion);
                    ejecutarConsultaNumeros(con, VendeTon.identificacion);

                }

            } catch (Exception e) {
                e.printStackTrace();
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

    public void AgregarNumeroTelefono(View view){
        Intent miIntent = new Intent(this, activity_detalles_cliente.class);
        miIntent.putExtra("con_identificacion", cliente.con_identificacion);
        miIntent.putExtra("tipo", "numero");
        miIntent.putExtra("vista", "agregar");
        startActivity(miIntent);
    }

    public void AgregarCorreoElectronico(View view){
        Intent miIntent = new Intent(this, activity_detalles_cliente.class);
        miIntent.putExtra("con_identificacion", cliente.con_identificacion);
        miIntent.putExtra("tipo", "correo");
        miIntent.putExtra("vista", "agregar");
        startActivity(miIntent);
    }

    public void onResume(){
        super.onResume();
        if (numerosNuevos == null) numerosNuevos = new ArrayList<>();
        if (correosNuevos == null) correosNuevos = new ArrayList<>();
        if (correosActualizar == null) correosActualizar = new ArrayList<>();
        if (numerosActualizar == null) numerosActualizar = new ArrayList<>();
        if (numerosEliminar == null) numerosEliminar = new ArrayList<>();
        if (correosEliminar == null) correosEliminar = new ArrayList<>();
        if (listaCorreos == null)listaCorreos = new ArrayList<>();
        if(listaNumeros == null) listaNumeros = new ArrayList<>();

        adaptadorcorreo = new AdaptadorCorreoElectronico(listaCorreos);
        RecyclerViewCorreoElectronico.setAdapter(adaptadorcorreo);

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


        adaptadornumero= new AdaptadorNumeroTelefonico(listaNumeros);
        RecyclerViewNumeroTelefono.setAdapter(adaptadornumero);
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
        //builder.setTitle("Eliminar Evento");
        //builder.setMessage("¿Está seguro de que desea eliminar este evento?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean nuevo = false;
                for (Iterator<CorreoElectronico> it = activity_info_cliente.correosNuevos.iterator(); it.hasNext(); ) {
                    CorreoElectronico correo = it.next();
                    if (correo.equals(correoel)){
                        nuevo = true;
                    }
                }

                if (!nuevo)
                    correosEliminar.add(correoel);

                listaCorreos.remove(correoel);

                adaptadorcorreo.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean nuevo = false;
                Intent intent = new Intent(activity_info_cliente.this, activity_detalles_cliente.class);
                intent.putExtra("tipo", "correo");
                for (Iterator<CorreoElectronico> it = activity_info_cliente.correosNuevos.iterator(); it.hasNext(); ) {
                    CorreoElectronico correo = it.next();
                    if (correo.equals(correoel)){
                        nuevo = true;
                    }
                }

                if (nuevo)
                    intent.putExtra("objeto", "nuevo");
                else intent.putExtra("objeto", "actualizar");

                activity_info_cliente.listaCorreos.remove(correoel);

                intent.putExtra("vista", "editar");
                intent.putExtra("correo", correoel);
                activity_info_cliente.this.startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void mostrarDialogo(NumeroTelefonico numerote){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Eliminar Evento");
        //builder.setMessage("¿Está seguro de que desea eliminar este evento?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean nuevo = false;
                for (Iterator<NumeroTelefonico> it = activity_info_cliente.numerosNuevos.iterator(); it.hasNext(); ) {
                    NumeroTelefonico numero = it.next();
                    if (numero.equals(numerote)){
                        nuevo = true;
                    }
                }

                if (!nuevo)
                    numerosEliminar.add(numerote);

                listaCorreos.remove(numerote);

                adaptadornumero.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean nuevo = false;
                Intent intent = new Intent(activity_info_cliente.this, activity_detalles_cliente.class);
                intent.putExtra("tipo", "numero");
                for (Iterator<NumeroTelefonico> it = activity_info_cliente.numerosNuevos.iterator(); it.hasNext(); ) {
                    NumeroTelefonico numero = it.next();
                    if (numero.equals(numerote)){
                        nuevo = true;
                    }
                }

                if (nuevo)
                    intent.putExtra("objeto", "nuevo");
                else intent.putExtra("objeto", "actualizar");

                activity_info_cliente.listaNumeros.remove(numerote);

                intent.putExtra("vista", "editar");
                intent.putExtra("numero", numerote);
                activity_info_cliente.this.startActivity(intent);

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public void inserts(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                con = connectionClass.CONN();
                for (CorreoElectronico correos:correosNuevos) {
                    String query = "call sp_insertarCorreoElectronico(?,?,?);";

                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setLong(1, cliente.con_identificacion);
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

                for (CorreoElectronico correos:correosActualizar) {
                    String query = "call sp_modificarCorreoElectronico(?,?,?,?);";
                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setString(1, correos.cor_usuario_anterior);
                        stmt.setString(2, correos.cor_dominio_anterior);
                        stmt.setString(3, correos.cor_usuario);
                        stmt.setString(4, correos.cor_dominio);

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


                for (CorreoElectronico correos:correosEliminar) {
                    String query = "call sp_eliminarCorreoElectronico(?,?);";
                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setString(1, correos.cor_usuario);
                        stmt.setString(2, correos.cor_dominio);

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

                for (NumeroTelefonico numeros: numerosNuevos) {
                    String query = "call  sp_insertarNumeroTelefonico(?,?,?);";
                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setLong(1, cliente.con_identificacion);
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


                for ( NumeroTelefonico numeros:numerosActualizar) {
                    String query = "call sp_modificarNumeroTelefonico(?,?,?,?);";
                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setInt(1, numeros.num_prefijo_anterior);
                        stmt.setLong(2, numeros.num_numero_anterior);
                        stmt.setInt(3, numeros.num_prefijo);
                        stmt.setLong(4, numeros.num_numero);

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

                for (NumeroTelefonico numeros: numerosNuevos) {
                    String query = "call  sp_eliminarNumeroTelefonico(?,?);";
                    try (CallableStatement stmt = con.prepareCall(query)) {
                        stmt.setInt(1, numeros.num_prefijo);
                        stmt.setLong(2, numeros.num_numero);

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

                correosActualizar.clear();
                correosNuevos.clear();
                correosEliminar.clear();
                numerosActualizar.clear();
                numerosNuevos.clear();
                numerosEliminar.clear();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });


    }

}

