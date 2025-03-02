package com.example.vendeton.Entidades;

public class BodegaOrigen {

    public int doc_documento;
    public String producto;
    public String bodega;
    public int cantidad;

    public BodegaOrigen(int doc_documento, String producto, String bodega, int cantidad) {
        this.cantidad = cantidad;
        this.bodega = bodega;
        this.producto = producto;
        this.doc_documento = doc_documento;
    }

}
