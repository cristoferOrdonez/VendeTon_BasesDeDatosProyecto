package com.example.vendeton.db;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {

    protected static String db = "ventas";

    protected static String ip = "192.168.0.4";

    protected static String port = "3306";

    protected static String username = "CelularRoot";

    protected static String password = "CelularRoot";

    public Connection CONN(){

        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?noAccessToProcedureBodies=true";
            conn = DriverManager.getConnection(connectionString, username, password);

        }catch (Exception e){
            Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
        }

        return conn;

    }

}
