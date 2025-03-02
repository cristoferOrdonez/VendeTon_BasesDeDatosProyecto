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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActivityCrearDocumentoVM extends AppCompatActivity {

    private TextInputLayout layoutFechaDocumentoVM;

    private TextInputEditText fechaDocumentoVM, totalDocumentoVM;
    private Integer[] fecha = {0, -1, 0};

    ArrayList<String> listaClientes;

    MaterialAutoCompleteTextView spinnerClientes;

    private ArrayAdapter<String> clientesAdapter;

    ConnectionClass connectionClass;

    Connection con;

    String str;

    Button botonAgregar;

    public static ArrayList<DetalleProductoVendido> detalles;
    public static ArrayList<BodegaOrigen> bodegaOrigen;

    RecyclerView listaDetalles;

    AdaptadorDetallesProductosVendidos adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_documento_vm);

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

}