package com.example.vendeton.Entidades;

public class DetalleProductoVendido {

    public int doc_numero;
    public String producto;
    public int cantidad;
    public float precio_unitario;
    public float monto;

    public DetalleProductoVendido(int doc_numero, String producto, int cantidad, float precio_unitario, float monto) {
        this.doc_numero = doc_numero;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.monto = monto;
    }

}