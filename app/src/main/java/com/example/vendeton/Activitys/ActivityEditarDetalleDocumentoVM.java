package com.example.vendeton.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorBodegaOrigen;
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

public class ActivityEditarDetalleDocumentoVM extends AppCompatActivity {

    ConnectionClass connectionClass;

    Connection con;

    String str;

    public static ArrayList<BodegaOrigen> bodegasOrigen;

    MaterialAutoCompleteTextView spinnerProductos;

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
        setContentView(R.layout.activity_editar_detalle_documento_vm);
        producto = getIntent().getStringExtra("producto");
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

        botonAceptar.setOnClickListener(i -> actualizarDetalle());
        botonAgregarBodegaOrigen.setOnClickListener(I -> agregarBodegaOrigen());

        establecerDatosPrevios();

        botonCancelar.setOnClickListener(i -> cancelarCreacionDetalle());
        spinnerProductos.setEnabled(false);

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

    public void establecerSpinnerProductos(){

        listaProductos = new ArrayList<>();

        spinnerProductos = findViewById(R.id.spinnerProducto);

        connectionClass = new ConnectionClass();


    }

    protected void onResume() {
        super.onResume();

        bodegasOrigen = new ArrayList<>();

        if(spinnerProductos == null){
            spinnerProductos = findViewById(R.id.spinnerProducto);
        }

        for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
            if(ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(spinnerProductos.getText().toString()) == 0){
                bodegasOrigen.add(ActivityCrearDocumentoVM.bodegaOrigen.get(i));
            }
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

                String producto = bodegasOrigen.get(position).producto;
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

    public void establecerDatosPrevios(){

        DetalleProductoVendido detalle = null;

        for(int i = 0; i < ActivityCrearDocumentoVM.detalles.size(); i++){
            if(ActivityCrearDocumentoVM.detalles.get(i).producto.compareTo(producto) == 0){
                detalle = ActivityCrearDocumentoVM.detalles.get(i);
                break;
            }
        }

        spinnerProductos.setText(detalle.producto);
        editTextCantidad.setText("" + detalle.cantidad);
        editTextPrecioUnitario.setText("" + detalle.precio_unitario);
        editTextMonto.setText("" + detalle.monto);

    }

    private void agregarBodegaOrigen() {
        Intent miIntent = new Intent(this, ActivityCrearBodegaOrigen.class);
        miIntent.putExtra("producto", spinnerProductos.getText().toString());
        startActivity(miIntent);

    }

    public void actualizarDetalle() {

        String producto = spinnerProductos.getText().toString();

        for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
            if(ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(this.producto) == 0){
                ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto = producto;
            }
        }

        for(int i = 0; i < ActivityCrearDocumentoVM.detalles.size(); i++){
            if(ActivityCrearDocumentoVM.detalles.get(i).producto.compareTo(this.producto) == 0){
                ActivityCrearDocumentoVM.detalles.remove(i);
            }
        }

        ActivityCrearDocumentoVM.detalles.add(new DetalleProductoVendido(
                0,
                spinnerProductos.getText().toString(),
                Integer.parseInt(editTextCantidad.getText().toString()),
                Float.parseFloat(editTextPrecioUnitario.getText().toString()),
                Float.parseFloat(editTextMonto.getText().toString())
        ));

        finish();
    }

    public void abrirBodega(String producto, String bodega){
        Intent miIntent = new Intent(this, ActivityEditarBodegaOrigen.class);
        miIntent.putExtra("producto", producto);
        miIntent.putExtra("bodega", bodega);
        startActivity(miIntent);
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
                    if(bodegasOrigen.get(i).producto.compareTo(producto) == 0){
                        bodegasOrigen.remove(i);
                    }
                }


                adapterBodegas.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abrirBodega(producto, bodega);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void cancelarCreacionDetalle(){
        for(int i = 0; i < ActivityCrearDocumentoVM.bodegaOrigen.size(); i++){
            if(ActivityCrearDocumentoVM.bodegaOrigen.get(i).producto.compareTo(producto) == 0){
                ActivityCrearDocumentoVM.bodegaOrigen.remove(i);
            }
        }
        finish();
    }
}