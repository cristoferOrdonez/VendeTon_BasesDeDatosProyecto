package com.example.vendeton.Entidades;

public class ReporteProductosPorBodega {

    public String nombre_bodega, nombre_producto;
    public int existencias;

    public ReporteProductosPorBodega(String nombre_bodega, String nombre_producto, int existencias) {
        this.nombre_bodega = nombre_bodega;
        this.nombre_producto = nombre_producto;
        this.existencias = existencias;
    }

}
