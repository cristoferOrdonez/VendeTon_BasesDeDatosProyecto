package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorAreasTrabajo;
import com.example.vendeton.Adaptadores.AdaptadorCorreoElectronico;
import com.example.vendeton.Adaptadores.AdaptadorNumeroTelefonico;
import com.example.vendeton.ConnectionClass;
import com.example.vendeton.Entidades.AreaDeTrabajo;
import com.example.vendeton.Entidades.Contraparte;
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


public class activity_detalles_cliente extends AppCompatActivity {



    ConnectionClass connectionClass;
    Connection con;
    ResultSet rs;
    String name, str, intencion;
    int con_identificacion;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            con_identificacion = extras.getInt("con_identificacion");
            intencion = extras.getString("intencion");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_editar_info_cliente);


        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            con = connectionClass.CONN();

            try {
                if (intencion = "correo"){

                    String query = "call sp_ConsultarContraparte(1001);";

                } else if (intencion = 'numero') {

                    String query = "call sp_ConsultarContraparte(1001);";

                }
                else{

                    String query = "call sp_ConsultarContraparte(1001);";

                }


                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                Contraparte cliente;
                if(rs.next()){
                    int result;
                    cliente = new Contraparte(rs.getInt("con_identificacion"),rs.getString("con_nombre"),
                            rs.getString("con_apellido"),rs.getString("con_calle"),rs.getString("con_barrio"),
                            rs.getString("con_ciudad"), rs.getString("con_direccion"));
                    runOnUiThread(() -> {

                    });
                }

                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }


               ; con = connectionClass.CONN();
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


    } catch (Exception e) {
        throw new RuntimeException(e);
    }


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

