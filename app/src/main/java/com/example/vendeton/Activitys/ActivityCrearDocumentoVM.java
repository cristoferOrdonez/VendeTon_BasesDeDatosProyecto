package com.example.vendeton.Activitys;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
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

import com.example.vendeton.Adaptadores.AdaptadorDetallesProductosVendidos;
import com.example.vendeton.Adaptadores.AdaptadorDocumentosVM;
import com.example.vendeton.Entidades.BodegaOrigen;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.example.vendeton.pickers.MostrarDatePicker;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityCrearDocumentoVM extends AppCompatActivity {

    private TextInputLayout layoutFechaDocumentoVM;

    private TextInputEditText fechaDocumentoVM, totalDocumentoVM, propositoDeCompraDocumentoVM;
    private Integer[] fecha = {0, -1, 0};

    ArrayList<String> listaClientes;

    MaterialAutoCompleteTextView spinnerClientes;

    private ArrayAdapter<String> clientesAdapter;

    ConnectionClass connectionClass;

    Connection con;

    String str;

    Button botonAgregar, botonGuardar;

    int numeroDeDocumento;

    public static ArrayList<DetalleProductoVendido> detalles;
    public static ArrayList<BodegaOrigen> bodegaOrigen;

    RecyclerView listaDetalles;

    AdaptadorDetallesProductosVendidos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_documento_vm);

        botonGuardar = findViewById(R.id.botonAceptarCrearDocumentoVM);

        propositoDeCompraDocumentoVM = findViewById(R.id.editTextPropositoCompra);

        totalDocumentoVM = findViewById(R.id.editTextTotal);

        detalles = new ArrayList<>();
        bodegaOrigen = new ArrayList<>();

        layoutFechaDocumentoVM = findViewById(R.id.layoutFechaDocumento);
        fechaDocumentoVM = findViewById(R.id.editTextFechaDocumento);

        fechaDocumentoVM.setOnClickListener(view -> mostrarDatePicker());
        fechaDocumentoVM.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });

        establecerSpinnerClientes();

        botonAgregar = findViewById(R.id.botonAgregarDetalleDocumentoVM);
        botonAgregar.setOnClickListener(i -> agregarProductoVendido());

        botonGuardar.setOnClickListener(i -> crearDocumento());

    }

    public void establecerDetalles(){
        listaDetalles = findViewById(R.id.RecyclerViewDetalles);
        listaDetalles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        adapter = new AdaptadorDetallesProductosVendidos(detalles);
        listaDetalles.setAdapter(adapter);
        listaDetalles.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);

                String producto = detalles.get(position).producto;
                mostrarDialogo(producto);
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

    private void mostrarDatePicker() {
        MostrarDatePicker datePicker = new MostrarDatePicker(this, this.fechaDocumentoVM, this.fecha);
    }

    public void establecerSpinnerClientes(){
        listaClientes = new ArrayList<>();

        spinnerClientes = findViewById(R.id.spinnerClientes);

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_consultarClientes();";
                        PreparedStatement stmt = con.prepareStatement(query);
                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {

                            listaClientes.add(rs.getLong("Identificacion")+" - "+
                                            rs.getString("Nombre")+" "+
                                            rs.getString("Apellido"));

                        }

                        runOnUiThread(() -> {

                            clientesAdapter = new ArrayAdapter<>(this, R.layout.list_item_dropdown_menu, listaClientes);
                            spinnerClientes.setAdapter(clientesAdapter);

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

    private void agregarProductoVendido() {
        Intent miIntent = new Intent(this, ActivityCrearDetalleDocumentoVM.class);
        startActivity(miIntent);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(detalles == null){
            detalles = new ArrayList<>();
        }
        establecerDetalles();
        actualizarTotal();

    }

    private void actualizarTotal(){

        float nuevoTotal = 0;

        for(int i = 0; i < detalles.size(); i++){
            nuevoTotal += detalles.get(i).monto;
        }

        if(totalDocumentoVM == null){
            totalDocumentoVM = findViewById(R.id.editTextTotal);
        }

        totalDocumentoVM.setText("" + nuevoTotal);

    }

    public void abrirDetalle(String producto){
        Intent miIntent = new Intent(this, ActivityEditarDetalleDocumentoVM.class);
        miIntent.putExtra("producto", producto);
        startActivity(miIntent);
    }

    public void mostrarDialogo(String producto){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i = 0; i < bodegaOrigen.size(); i++){
                    if(bodegaOrigen.get(i).producto.compareTo(producto) == 0){
                        bodegaOrigen.remove(i);
                    }
                }

                for(int i = 0; i < detalles.size(); i++){
                    if(detalles.get(i).producto.compareTo(producto) == 0){
                        detalles.remove(i);
                    }
                }

                adapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton("Editar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                abrirDetalle(producto);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void crearDocumento(){
        insertarDocumento();

    }

    public void insertarDocumento(){

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "SELECT fn_insertarDocumentoVentaMayorista(?,?,?,?) AS numero;";
                        PreparedStatement stmt = con.prepareStatement(query);
                        Date x = new Date(fecha[0]- 1900,fecha[1] - 1,fecha[2]);

                        String cliente = spinnerClientes.getText().toString();
                        String identiicacionS = cliente.substring(0, cliente.indexOf(" -"));
                        int identificacion = Integer.parseInt(identiicacionS);

                        int total = 0;
                        for (DetalleProductoVendido detalle : detalles) {
                            total += detalle.monto;
                        }


                        stmt.setDate(1, x);
                        stmt.setFloat(2, total);
                        stmt.setLong(3, identificacion);
                        stmt.setString(4, propositoDeCompraDocumentoVM.getText().toString());

                        ResultSet rs = stmt.executeQuery();

                        while (rs.next()) {

                            numeroDeDocumento = rs.getInt("numero");

                        }

                        runOnUiThread(() -> {

                            Toast.makeText(this, ""+numeroDeDocumento, Toast.LENGTH_SHORT).show();
                            for(int i = 0; i < detalles.size(); i++){
                                insertarDetalle(detalles.get(i));
                            }

                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

    }

    public void insertarDetalle(DetalleProductoVendido detalle){

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_insertarDetalleProductoVendido(?,?,?,?);";
                        PreparedStatement stmt = con.prepareStatement(query);


                        stmt.setInt(1, numeroDeDocumento);
                        stmt.setString(2, detalle.producto);
                        stmt.setInt(3, detalle.cantidad);
                        stmt.setFloat(4, detalle.precio_unitario);

                        ResultSet rs = stmt.executeQuery();

                        runOnUiThread(() -> {

                            Toast.makeText(this, ""+numeroDeDocumento, Toast.LENGTH_SHORT).show();
                            if(detalle == detalles.get(detalles.size()-1)){
                                for(int i = 0; i < bodegaOrigen.size(); i++){
                                    insertarBodegaOrigen(bodegaOrigen.get(i));
                                }
                            }

                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

    }

    public void insertarBodegaOrigen(BodegaOrigen bodegaOrigen){

        connectionClass = new ConnectionClass();
        connect();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

                    try {
                        con = connectionClass.CONN();

                        String query = "CALL sp_insertarRegistra(?,?,?,?);";
                        PreparedStatement stmt = con.prepareStatement(query);


                        stmt.setInt(1, numeroDeDocumento);
                        stmt.setString(2, bodegaOrigen.producto);
                        stmt.setString(3, bodegaOrigen.bodega);
                        stmt.setInt(4, bodegaOrigen.cantidad);

                        ResultSet rs = stmt.executeQuery();

                        runOnUiThread(() -> {

                            Toast.makeText(this, ""+numeroDeDocumento, Toast.LENGTH_SHORT).show();

                        });

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(() -> {


                    });
                }
        );

    }

}