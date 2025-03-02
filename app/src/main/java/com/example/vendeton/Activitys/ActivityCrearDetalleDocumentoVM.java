package com.example.vendeton.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorBodegaOrigen;
import com.example.vendeton.Adaptadores.AdaptadorDetallesProductosVendidos;
import com.example.vendeton.Entidades.BodegaOrigen;
import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityCrearDetalleDocumentoVM extends AppCompatActivity {

    ConnectionClass connectionClass;

    Connection con;

    String str;

    public static ArrayList<BodegaOrigen> bodegasOrigen;

    MaterialAutoCompleteTextView spinnerProductos;

    private ArrayAdapter<String> productosAdapter;

    ArrayList<String> listaProductos;

    TextInputEditText editTextPrecioUnitario, editTextMonto, editTextCantidad;

    Button botonAceptar, botonAgregarBodegaOrigen, botonCancelar;

    float precioUnitario, cantidad;

    public RecyclerView listaBodegasOrigen;

    AdaptadorBodegaOrigen adapterBodegas;

    String producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_detalle_documento_vm);

        botonCancelar = findViewById(R.id.botonCancelarCrearDetalle);

        bodegasOrigen = new ArrayList<>();

        botonAgregarBodegaOrigen = findViewById(R.id.botonAgregarBodegaOrigen);

        editTextPrecioUnitario = findViewById(R.id.editTextPrecioUnitario);
        editTextMonto = findViewById(R.id.editTextMonto);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        botonAceptar = findViewById(R.id.botonAceptarCrearDetalle);

        editTextPrecioUnitario.setEnabled(false);
        editTextMonto.setEnabled(false);
        editTextCantidad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                establecerPrecioUnitario(s.toString());

            }
        });

        establecerSpinnerProductos();

        spinnerProductos.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                establecerPrecioUnitario(s.toString());

            }
        });

        botonAceptar.setOnClickListener(i -> {
            if(comprobar())
                crearDetalle();
        });
        botonAgregarBodegaOrigen.setOnClickListener(I -> agregarBodegaOrigen());
        botonCancelar.setOnClickListener(i -> cancelarCreacionDetalle());

    }

    public void crearDetalle() {

        ActivityCrearDocumentoVM.detalles.add(new DetalleProductoVendido(
                0,
                spinnerProductos.getText().toString(),
                Integer.parseInt(editTextCantidad.getText().toString()),
                Float.parseFloat(editTextPrecioUnitario.getText().toString()),
                Float.parseFloat(editTextMonto.getText().toString())
        ));

        for(int i = 0; i < bodegasOrigen.size(); i++) {
            BodegaOrigen bodegaOrigen = bodegasOrigen.get(i);
            bodegaOrigen.producto = spinnerProductos.getText().toString();
            ActivityCrearDocumentoVM.bodegaOrigen.add(bodegaOrigen);
        }

        finish();
    }

    public void establecerPrecioUnitario(String producto){

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarMercanciaProductoNombre(?);";
                        CallableStatement stmt = con.prepareCall(query);
                        stmt.setString(1, producto);


                        ResultSet rs = stmt.executeQuery();


                        try {
                            cantidad = Integer.parseInt(editTextCantidad.getText().toString());
                        } catch (NumberFormatException e){
                            cantidad = 0;
                        }

                        int cantidadMaxima;

                        while (rs.next()) {

                            cantidadMaxima = rs.getInt("mer_cantidad_maxima");

                            if(cantidad > cantidadMaxima){
                                precioUnitario = rs.getFloat("mer_precio_al_por_mayor");
                            } else {
                                precioUnitario = rs.getFloat("mer_precio_al_por_menor");
                            }

                        }

                        runOnUiThread(() -> {

                            editTextPrecioUnitario.setText("" + precioUnitario);
                            editTextMonto.setText("" + (cantidad * precioUnitario));

                        });

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {


                    });
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

    public void establecerSpinnerProductos(){

        listaProductos = new ArrayList<>();

        spinnerProductos = findViewById(R.id.spinnerProducto);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarMercanciaProductoGeneral();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();

                        String nombre;

                        boolean flag = true;

                        while (rs.next()) {

                            nombre = rs.getString("mer_nombre");

                            for(int i = 0; i < ActivityCrearDocumentoVM.detalles.size(); i++){
                                if(nombre.compareTo(ActivityCrearDocumentoVM.detalles.get(i).producto) == 0){
                                    flag = false;
                                }
                            }
                            if(flag) {
                                listaProductos.add(nombre);
                            }

                            flag = true;

                        }

                        runOnUiThread(() -> {

                            productosAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, listaProductos);
                            spinnerProductos.setAdapter(productosAdapter);

                        });

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

    }

    private void agregarBodegaOrigen() {

        for(int i = 0; i < bodegasOrigen.size(); i++)
            bodegasOrigen.get(i).producto = spinnerProductos.getText().toString();

        Intent miIntent = new Intent(this, ActivityCrearBodegaOrigen.class);
        miIntent.putExtra("producto", spinnerProductos.getText().toString());
        startActivity(miIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(bodegasOrigen == null)
            bodegasOrigen = new ArrayList<>();

        if(spinnerProductos == null){
            spinnerProductos = findViewById(R.id.spinnerProducto);
        }

        listaBodegasOrigen = findViewById(R.id.RecyclerViewBodegasOrigen);
        listaBodegasOrigen.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapterBodegas = new AdaptadorBodegaOrigen(bodegasOrigen);
        listaBodegasOrigen.setAdapter(adapterBodegas);
        listaBodegasOrigen.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);

                String producto = spinnerProductos.getText().toString();
                String bodega = bodegasOrigen.get(position).bodega;
                mostrarDialogo(producto, bodega);
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

    public void mostrarDialogo(String producto, String bodega){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("Eliminar Evento");
        //builder.setMessage("¿Está seguro de que desea eliminar este evento?");

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
                    if(ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(producto) == 0){
                        ActivityCrearDocumentoVM.bodegaOrigen.remove(i);
                    }
                }

                for(int i = 0; i < bodegasOrigen.size(); i++){
                    if(bodegasOrigen.get(i).bodega.compareTo(bodega) == 0){
                        bodegasOrigen.remove(i);
                    }
                }


                adapterBodegas.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < bodegasOrigen.size(); i++)
                    bodegasOrigen.get(i).producto = spinnerProductos.getText().toString();

                abrirBodega(producto, bodega);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void abrirBodega(String producto, String bodega){
        for(int i = 0; i < bodegasOrigen.size(); i++)
            bodegasOrigen.get(i).producto = spinnerProductos.getText().toString();
        Intent miIntent = new Intent(this, ActivityEditarBodegaOrigen.class);
        miIntent.putExtra("producto", producto);
        miIntent.putExtra("bodega", bodega);
        startActivity(miIntent);
    }

    public void cancelarCreacionDetalle(){

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bodegasOrigen = null;
    }

    public boolean comprobar(){

        boolean flag = true;

        if(spinnerProductos.getText().toString().toString().compareTo("") == 0){
            flag = false;
            Toast.makeText(this, "Debe seleccionar un producto", Toast.LENGTH_LONG).show();
        }

        if(editTextCantidad.getText().toString().compareTo("") == 0 || Integer.parseInt(editTextCantidad.getText().toString()) <= 0){
            flag = false;
            Toast.makeText(this, "La cantidad debe ser mayor a 0", Toast.LENGTH_LONG).show();
        }

        if(bodegasOrigen.isEmpty()){
            flag = false;
            Toast.makeText(this, "Debe indicar de que bodegas salen los productos", Toast.LENGTH_SHORT).show();
        }

        return flag;
    }
}