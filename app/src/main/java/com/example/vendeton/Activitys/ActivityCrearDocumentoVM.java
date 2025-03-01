package com.example.vendeton.Activitys;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.vendeton.Entidades.ContraparteCliente;
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

    private TextInputEditText fechaDocumentoVM;
    private Integer[] fecha = {0, -1, 0};

    ArrayList<String> listaClientes;

    MaterialAutoCompleteTextView spinnerClientes;

    private ArrayAdapter<String> clientesAdapter;

    ConnectionClass connectionClass;

    Connection con;

    String str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_documento_vm);

        layoutFechaDocumentoVM = findViewById(R.id.layoutFechaDocumento);
        fechaDocumentoVM = findViewById(R.id.editTextFechaDocumento);

        fechaDocumentoVM.setOnClickListener(view -> mostrarDatePicker());
        fechaDocumentoVM.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus)
                mostrarDatePicker();
        });

        establecerSpinnerClientes();

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

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e){
                            e.printStackTrace();
                        }

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

}