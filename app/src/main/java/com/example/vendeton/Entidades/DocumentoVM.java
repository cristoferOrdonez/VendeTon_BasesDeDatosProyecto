package com.example.vendeton.Entidades;

import java.util.Date;

public class DocumentoVM {

    public int doc_numero;
    public long con_identificacion;
    public Date fecha;
    public double doc_total;
    public String proposito_de_compra, con_nombre, con_apellido, con_calle, con_barrio, con_ciudad, con_direccion;


    public DocumentoVM(
            int doc_numero,
            long con_identificacion,
            Date fecha,
            double doc_total,
            String proposito_de_compra,
            String con_nombre,
            String con_apellido,
            String con_calle,
            String con_barrio,
            String con_ciudad,
            String con_direccion
    ) {
        this.doc_numero = doc_numero;
        this.con_identificacion = con_identificacion;
        this.fecha = fecha;
        this.doc_total = doc_total;
        this.proposito_de_compra = proposito_de_compra;
        this.con_nombre = con_nombre;
        this.con_apellido = con_apellido;
        this.con_calle = con_calle;
        this.con_barrio = con_barrio;
        this.con_ciudad = con_ciudad;
        this.con_direccion = con_direccion;
    }

}
