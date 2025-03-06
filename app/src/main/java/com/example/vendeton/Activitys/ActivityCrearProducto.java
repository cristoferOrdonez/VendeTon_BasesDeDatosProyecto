package com.example.vendeton.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vendeton.Adaptadores.AdaptadorProductos;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityCrearProducto extends AppCompatActivity {

    TextInputEditText editTextNombre, editTextPrecioMayorista, editTextPrecioMinorista,
        editTextCostoProduccion, editTextComision, editTextCantidadMaxima, editTextAlto,
        editTextAncho, editTextLargo, editTextDescripcion;
    MaterialAutoCompleteTextView spinnerTiposProducto;

    Button botonCancelar, botonAcepatar;

    ConnectionClass connectionClass;

    Connection con;

    String str;

    ArrayList<String> listaTiposDeProducto;

    ArrayAdapter<String> tiposDeProductoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_producto);
        editTextNombre = findViewById(R.id.editTextNombreProducto);
        editTextAlto = findViewById(R.id.editTextAlto);
        editTextAncho = findViewById(R.id.editTextAncho);
        editTextLargo = findViewById(R.id.editTextLargo);
        editTextCantidadMaxima = findViewById(R.id.editTextCantidadMaxima);
        editTextComision = findViewById(R.id.editTextComision);
        editTextPrecioMinorista = findViewById(R.id.editTextPrecioAlPorMenor);
        editTextPrecioMayorista = findViewById(R.id.editTextPrecioAlPorMayor);
        editTextCostoProduccion = findViewById(R.id.editTextCostoDeProduccion);
        editTextDescripcion = findViewById(R.id.editTextDescripción);

        spinnerTiposProducto = findViewById(R.id.spinnerTipoDeProducto);

        botonCancelar = findViewById(R.id.botonCancelarCrearProductoNuevo);
        botonAcepatar = findViewById(R.id.botonAceptarCrearProductoNuevo);

        botonCancelar.setOnClickListener(i -> cancelar());
        botonAcepatar.setOnClickListener(i -> crearProducto());

        establecerSpinnerClientes();

    }

    public void crearProducto() {

        insertarProducto();

    }

    public void cancelar() {
        Intent miIntent = new Intent(this, ActivityProductos.class);
        startActivity(miIntent);
        finish();
    }

    public void insertarProducto(){
        Map<Integer, String> tiposdeproducto = new HashMap<>();
        final int[] tip_id = new int[1];

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();


                        String query = "CALL sp_consultarTipoDeProductoGeneral();";

                        try (PreparedStatement stmt = con.prepareStatement(query)) {
                            //boolean hasResults = stmt.execute();
                            ResultSet rs = stmt.executeQuery();

                            while(rs.next()){
                                tiposdeproducto.put(rs.getInt("tip_id"),rs.getString("tip_nombre"));
                            }

                            Boolean existe = false;
                            for (Map.Entry <Integer, String> entry : tiposdeproducto.entrySet()){
                                if (entry.getValue().equals(spinnerTiposProducto.getText().toString())){
                                    existe = true;
                                    tip_id[0] = entry.getKey();
                                    break;
                                }
                            }

                            if (!existe){
                                runOnUiThread(() ->
                                        Toast.makeText(this, "El tipo de producto no existe", Toast.LENGTH_SHORT).show()
                                );
                                return;
                            }


                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }

                        query = "CALL sp_insertarMercanciaProducto(?,?,?,?,?,?,?,?,?,?,?);";
                        CallableStatement stmt = con.prepareCall(query);

                        short ancho, largo, alto;
                        float costoProduccion, precioMayorista, precioMinorista, comision;
                        byte cantidadMaxima;

                        if(editTextAncho.getText().toString().compareTo("") == 0)
                            ancho = 0;
                        else
                            ancho = Short.parseShort(editTextAncho.getText().toString());

                        if(editTextLargo.getText().toString().compareTo("") == 0)
                            largo = 0;
                        else
                            largo = Short.parseShort(editTextLargo.getText().toString());

                        if(editTextAlto.getText().toString().compareTo("") == 0)
                            alto = 0;
                        else
                            alto = Short.parseShort(editTextAlto.getText().toString());

                        if(editTextCostoProduccion.getText().toString().compareTo("") == 0)
                            costoProduccion = 0;
                        else
                            costoProduccion = Float.parseFloat(editTextCostoProduccion.getText().toString());

                        if(editTextPrecioMayorista.getText().toString().compareTo("") == 0)
                            precioMayorista = 0;
                        else
                            precioMayorista = Float.parseFloat(editTextPrecioMayorista.getText().toString());

                        if(editTextPrecioMinorista.getText().toString().compareTo("") == 0)
                            precioMinorista = 0;
                        else
                            precioMinorista = Float.parseFloat(editTextPrecioMinorista.getText().toString());

                        if(editTextComision.getText().toString().compareTo("") == 0)
                            comision = 0;
                        else
                            comision = Float.parseFloat(editTextComision.getText().toString());

                        if(editTextCantidadMaxima.getText().toString().compareTo("") == 0)
                            cantidadMaxima = 0;
                        else
                            cantidadMaxima = Byte.parseByte(editTextCantidadMaxima.getText().toString());

                        stmt.setString(1, editTextNombre.getText().toString());
                        stmt.setShort(2, alto);
                        stmt.setShort(3, largo);
                        stmt.setShort(4, ancho);
                        stmt.setFloat(5, costoProduccion);
                        stmt.setFloat(6, precioMayorista);
                        stmt.setFloat(7, precioMinorista);
                        stmt.setByte(8, cantidadMaxima);
                        stmt.setFloat(9, comision);
                        stmt.setInt(10, tip_id[0]);
                        stmt.setString(11, editTextDescripcion.getText().toString());




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
                        } else {

                            runOnUiThread(() -> {

                                Intent miIntent = new Intent(this, ActivityProductos.class);
                                startActivity(miIntent);
                                finish();

                            });
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
        );

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

    public void establecerSpinnerClientes(){
        listaTiposDeProducto = new ArrayList<>();

        spinnerTiposProducto = findViewById(R.id.spinnerTipoDeProducto);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarTipoDeProductoGeneral();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {

                            listaTiposDeProducto.add(rs.getString("tip_nombre"));

                        }

                        runOnUiThread(() -> {

                            tiposDeProductoAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, listaTiposDeProducto);
                            spinnerTiposProducto.setAdapter(tiposDeProductoAdapter);

                        });

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

    }

}