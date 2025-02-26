package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.os.Bundle;
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

import com.example.vendeton.ConnectionClass;
import com.example.vendeton.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class activity_info_cliente extends AppCompatActivity {

    TextInputEditText IdentificacionAcceder, NombreAcceder, ApellidoAcceder, CiudadAcceder, BarrioAcceder, CalleAcceder;
    TextInputLayout layoutIdentificacion, layoutNombre, layoutApellido, layoutCiudad, layoutBarrio, layoutCalle;
    Button editarBtn;
    LinearLayout BotonesCuenta, BotonesEditar;

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

        IdentificacionAcceder.setEnabled(false);
        NombreAcceder.setEnabled(false);
        ApellidoAcceder.setEnabled(false);
        CiudadAcceder.setEnabled(false);
        BarrioAcceder.setEnabled(false);
        CalleAcceder.setEnabled(false);


        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            try {
                con = connectionClass.CONN();
                String query = "call sp_ConsultarContraparte(1001);";
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                if(rs.next()){
                    int result;
                    result= rs.getInt("con_identificacion");
                    runOnUiThread(() -> {
                        Toast.makeText(this, ""+ result, Toast.LENGTH_SHORT).show();
                        IdentificacionAcceder.setText(""+result);
                    });
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

    }

}

