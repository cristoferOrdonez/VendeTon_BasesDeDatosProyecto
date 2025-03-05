package com.example.vendeton.db;

import android.util.Log;

import com.example.vendeton.Entidades.ContraparteCliente;
import com.example.vendeton.VendeTon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class ConnectionClass {

    protected static String db = "ventas";

    public static String ip = "192.168.10.28";

    protected static String port = "3306";


    public Connection CONN(){

        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?noAccessToProcedureBodies=true";
            conn = DriverManager.getConnection(connectionString, VendeTon.username, VendeTon.password);

        }catch (Exception e){
            Log.e("ERROR", Objects.requireNonNull(e.getMessage()));
        }

        return conn;

    }

    public boolean comprobarconexion(){

        Connection conn = null;

        try{
            Class.forName("com.mysql.jdbc.Driver");
            String connectionString = "jdbc:mysql://" + ip + ":" + port + "/" + db + "?noAccessToProcedureBodies=true";
            conn = DriverManager.getConnection(connectionString, VendeTon.username, VendeTon.password);

        }catch (Exception e){
            return false;
        }

        return true;

    }

    public static ContraparteCliente ejecutarConsultaContraparte(Connection con, long id) {
        ContraparteCliente cliente = null;
        String query = "call sp_consultarContraparte(?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    try{
                        int column =rs.findColumn("con_identificacion");
                    }catch (SQLException e){
                        return null;
                    }

                     cliente = new ContraparteCliente(
                            rs.getLong("con_identificacion"),
                            rs.getString("con_nombre"),
                            rs.getString("con_apellido"),
                            rs.getString("con_calle"),
                            rs.getString("con_barrio"),
                            rs.getString("con_ciudad"),
                            rs.getString("con_direccion")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }


}
