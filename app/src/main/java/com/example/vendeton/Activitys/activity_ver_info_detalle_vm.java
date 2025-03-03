package com.example.vendeton.Activitys;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vendeton.Entidades.DetalleProductoVendido;
import com.example.vendeton.R;
import com.google.android.material.textfield.TextInputEditText;

public class activity_ver_info_detalle_vm extends AppCompatActivity {

    public static DetalleProductoVendido detalle;
    TextInputEditText editTextDetalleNombreProducto, editTextDetallePrecioUnitario, editTextDetalleCantidad, editTextDetalleMonto;
    Button botonAceptar;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detalle_vm);
        detalle = getIntent().getParcelableExtra("detalle");

        editTextDetalleNombreProducto = findViewById(R.id.editTextDetalleNombreProducto);
        editTextDetallePrecioUnitario = findViewById(R.id.editTextDetallePrecioUnitario);
        editTextDetalleCantidad = findViewById(R.id.editTextDetalleCantidad);
        editTextDetalleMonto = findViewById(R.id.editTextDetalleMonto);

        editTextDetalleNombreProducto.setText(detalle.producto);
        editTextDetallePrecioUnitario.setText(""+detalle.precio_unitario);
        editTextDetalleCantidad.setText(""+detalle.cantidad);
        editTextDetalleMonto.setText(""+detalle.monto);

        editTextDetalleNombreProducto.setEnabled(false);
        editTextDetallePrecioUnitario.setEnabled(false);
        editTextDetalleCantidad.setEnabled(false);
        editTextDetalleMonto.setEnabled(false);

        botonAceptar = findViewById(R.id.BotonDetalleAceptar);
        botonAceptar.setOnClickListener(view -> {
            finish();
        });

    }
}
