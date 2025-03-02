package com.example.vendeton.Entidades;

public class CatalogoProducto {
    public String nombre_producto;
    public int existencias;
    public float precio_al_por_menor;
    public float precio_al_por_mayor;

    public CatalogoProducto(String nombre_producto, int existencias, float precio_al_por_menor, float precio_al_por_mayor) {
        this.nombre_producto = nombre_producto;
        this.existencias = existencias;
        this.precio_al_por_menor = precio_al_por_menor;
        this.precio_al_por_mayor = precio_al_por_mayor;
    }


}
