package com.example.vendeton.Activitys;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import 	android.icu.text.DateFormat;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorCorreoElectronico;
import com.example.vendeton.Adaptadores.AdaptadorDetallesProductosVendidos;
import com.example.vendeton.Adaptadores.AdaptadorNumeroTelefonico;
import com.example.vendeton.Entidades.CorreoElectronico;
import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.Entidades.DocumentoVM;
import com.example.vendeton.R;
import com.example.vendeton.VendeTon;
import com.example.vendeton.db.ConnectionClass;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_ver_info_documento_vm extends AppCompatActivity {

    private TextInputLayout layoutFechaDocumentoVM;

    private TextInputEditText fechaDocumentoVM, totalDocumentoVM, propositoDeCompraDocumentoVM, clienteVM;

    public static ArrayList<DetalleProductoVendido> listadetalles;

    DocumentoVM documento;
    RecyclerView RecyclerViewDetalles;
    ConnectionClass connectionClass;
    Button BotonInfoDocumentoVMAceptar;

    AdaptadorDetallesProductosVendidos adapter;

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_info_documentos_vm);

        documento = getIntent().getParcelableExtra("documento");

        propositoDeCompraDocumentoVM = findViewById(R.id.editTextPropositoCompra);
        totalDocumentoVM = findViewById(R.id.editTextTotal);
        layoutFechaDocumentoVM = findViewById(R.id.layoutFechaDocumento);
        fechaDocumentoVM = findViewById(R.id.editTextFechaDocumento);
        clienteVM = findViewById(R.id.editTextCliente);

        fechaDocumentoVM.setEnabled(false);
        clienteVM.setEnabled(false);
        propositoDeCompraDocumentoVM.setEnabled(false);
        totalDocumentoVM.setEnabled(false);

        BotonInfoDocumentoVMAceptar = findViewById(R.id.BotonInfoDocumentoVMAceptar);

        BotonInfoDocumentoVMAceptar.setOnClickListener(View -> {
            finish();
        });

        RecyclerViewDetalles = findViewById(R.id.RecyclerViewDetalles);
        RecyclerViewDetalles.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        RecyclerViewDetalles.addOnItemTouchListener(new RecyclerView.OnItemTouchListener(){

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                int position = rv.getChildAdapterPosition(child);

                DetalleProductoVendido detalle = listadetalles.get(position);
                Intent intent = new Intent(activity_ver_info_documento_vm.this, activity_ver_info_detalle_vm.class);
                intent.putExtra("detalle", detalle);
                startActivity(intent);

                return true;

            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String fecha = df.format(documento.fecha);

        propositoDeCompraDocumentoVM.setText(documento.proposito_de_compra);
        totalDocumentoVM.setText(""+documento.doc_total);
        fechaDocumentoVM.setText(""+fecha);
        clienteVM.setText(""+documento.con_nombre);


        connectionClass = new ConnectionClass();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
                    try {
                        listadetalles = new ArrayList<>();

                        try (Connection con = connectionClass.CONN()) {
                            if (con == null) throw new SQLException("Conexión nula");
                            consultarDetalles(con);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            });

        runOnUiThread(() -> {

            try {
                Thread.sleep(600);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            AdaptadorDetallesProductosVendidos adapter = new AdaptadorDetallesProductosVendidos(listadetalles);
            RecyclerViewDetalles.setAdapter(adapter);

        });







    }

    public void consultarDetalles(Connection con){
        String query = "call sp_mostrarTodosLosDetallesProductoVendido(?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, documento.doc_numero);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listadetalles.add( new DetalleProductoVendido(
                            documento.doc_numero,
                            rs.getString("mer_nombre"),
                            rs.getInt("det_cantidad"),
                            rs.getFloat("det_precio_unitario"),
                            rs.getFloat("det_monto")
                            )
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
