package com.example.vendeton.Activitys;

import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendeton.Adaptadores.AdaptadorInformeCatalogoProductos;
import com.example.vendeton.Entidades.CatalogoProducto;
import com.example.vendeton.R;
import com.example.vendeton.db.ConnectionClass;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class activity_informe_catalogo extends AppCompatActivity {
    ImageButton imageButtonAtras;
    RecyclerView RecyclerViewCatalogo;
    Connection con;
    List<CatalogoProducto> listaCatalogo;
    AdaptadorInformeCatalogoProductos adaptador;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_catalogo_productos);

        imageButtonAtras = findViewById(R.id.imageButtonAtras5);
        imageButtonAtras.setOnClickListener(v -> irAtras());

        RecyclerViewCatalogo = findViewById(R.id.RecyclerViewClientes);
        RecyclerViewCatalogo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try{
                listaCatalogo = new ArrayList<>();
                //VendeTon.username = "farid";
                //VendeTon.password ="contrasena";
                ConnectionClass connectionClass = new ConnectionClass();
                con = connectionClass.CONN();
                String query = "call sp_consultarCatalogoProductos()";
                try (PreparedStatement stmt = con.prepareStatement(query)) {
                    try (ResultSet rs = stmt.executeQuery()) {
                        while (rs.next()) {
                            listaCatalogo.add( new CatalogoProducto(
                                    rs.getString("nombre_producto"),
                                    rs.getInt("existencias"),
                                    rs.getFloat("precio_al_por_menor"),
                                    rs.getFloat("precio_al_por_mayor")
                                    )
                            );
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            } catch(Exception e){
                e.printStackTrace();
            }


            runOnUiThread(() -> {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }

                adaptador = new AdaptadorInformeCatalogoProductos(listaCatalogo);
                RecyclerViewCatalogo.setAdapter(adaptador);
            });
        });

    }

    private void irAtras() {
        finish();
    }
}
