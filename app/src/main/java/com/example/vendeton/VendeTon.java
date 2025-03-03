package com.example.vendeton;

import android.app.Application;

import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.db.DbSesion;

public class VendeTon extends Application {
    public final static int USUARIO_PUBLICO = 0;
    public final static int CLIENTE_MAYORISTA = 1;
    public final static int ADMINISTRADOR = 2;
    public static int estadoUsuario = USUARIO_PUBLICO;
    public static ContraparteCliente usuario;
    public static String username = "CelularRoot";
    public static String password = "CelularRoot";
    public static long identificacion;


    @Override
    public void onCreate() {
        super.onCreate();
        usuario = null;
        username = null;
        password = null;
        DbSesion dbSesion=new DbSesion(getApplicationContext());
        dbSesion.sesionActiva();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

}
