package com.example.vendeton.db;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.vendeton.Activitys.ActivitySeleccionUsuario;
import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.Entidades.InfoSesion;
import com.example.vendeton.VendeTon;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;


public class DbSesion extends DbLocalVendeton{
    Context context;

    public DbSesion(Context context) {
        super(context);
        this.context=context;
    }
    public void mantenerSesionIniciada(int tipoSesion, long identificacionSesion){
        InfoSesion infoSesion = new InfoSesion(0, tipoSesion, identificacionSesion);
        infoSesion.setTipoSesion(tipoSesion);
        infoSesion.setIdentificacionSesion(identificacionSesion);
        insertarInfoSesion(tipoSesion, identificacionSesion);
    }

    public void cerrarSesion(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INFO_SESION, null, null);
        db.close();
        VendeTon.usuario = null;
        VendeTon.username = null;
        VendeTon.password = null;
        VendeTon.identificacion = 0;
        VendeTon.estadoUsuario = VendeTon.SIN_REGISTRAR;
        this.getWritableDatabase().close();
        Intent intent = new Intent(context, ActivitySeleccionUsuario.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
    public void insertarInfoSesion(int tipoSesion, long identificacionSesion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tipoSesion", tipoSesion);
        values.put("identificacionSesion", identificacionSesion);

        long resultado = db.insert(TABLE_INFO_SESION, null, values);

        db.close();
    }

    public boolean verificarSesionActiva(){
        DbLocalVendeton dbHelper = new DbLocalVendeton(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO_SESION, null);
        boolean sesionActiva = false;
        if (cursorInfo.moveToFirst()) {
            InfoSesion infoSesion = new InfoSesion(0, cursorInfo.getInt(1), cursorInfo.getLong(2));
            sesionActiva = (infoSesion.getTipoSesion() != -1 || infoSesion.getTipoSesion() != 0);
            VendeTon.estadoUsuario = infoSesion.getTipoSesion();
            VendeTon.identificacion = infoSesion.getIdentificacionSesion();
        }
        cursorInfo.close();
        return sesionActiva;
    }

    public void sesionActiva(){
        long identificacion;
        DbLocalVendeton dbHelper = new DbLocalVendeton(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorInfo = db.rawQuery("SELECT * FROM " + TABLE_INFO_SESION, null);
        if (cursorInfo.moveToFirst()) {
            InfoSesion infoSesion = new InfoSesion(0, cursorInfo.getInt(1), cursorInfo.getLong(2));

            identificacion=cursorInfo.getLong(2);

            if (verificarSesionActiva()) {
                if (infoSesion.getTipoSesion() == VendeTon.CLIENTE_MAYORISTA) {
                    VendeTon.username = "cliente_mayorista";
                    VendeTon.password = "clientemayorista";

                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                                ConnectionClass connectionClass = new ConnectionClass();
                                try (Connection con = connectionClass.CONN()) {
                                    if (con == null) throw new SQLException("Conexión nula");
                                    ContraparteCliente resultado = ConnectionClass.ejecutarConsultaContraparte(con, identificacion);
                                    if (resultado == null) {
                                        cerrarSesion();
                                        return;
                                    }

                                    VendeTon.usuario = resultado;
                                    VendeTon.estadoUsuario = VendeTon.CLIENTE_MAYORISTA;


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            });
                    executorService.shutdown();

                } else if (infoSesion.getTipoSesion() == VendeTon.ADMINISTRADOR){

                    // ADMINISTRADOR
                    VendeTon.usuario = null;        // NO HAY INFORMACION PARA GUARDAR
                    VendeTon.estadoUsuario = VendeTon.ADMINISTRADOR;
                    VendeTon.username = "administrador";
                    VendeTon.password = "administrador";
                }

            } else if (infoSesion.getTipoSesion() == VendeTon.USUARIO_PUBLICO){
                VendeTon.username = "usuario_publico";
                VendeTon.password = "";

            }
            else {
                cerrarSesion();
                return;
            }
        } else {
            identificacion = 0;
        }
        cursorInfo.close();
    }
}




