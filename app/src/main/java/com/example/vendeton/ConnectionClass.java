package com.example.vendeton;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

public class ConnectionClass {

    protected static String db = "ventas";

    protected static String ip = "192.168.1.4";

    protected static String port = "3306";

    protected static String username = "farid";

    protected static String password = "contrasena";

    public Connection CONN(){

        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db;
            conn = DriverManager.getConnection(connectionString, username, password);

        }catch (Exception e){
            Log.e("ERRO", Objects.requireNonNull(e.getMessage()));
        }

        return conn;

    }

}
