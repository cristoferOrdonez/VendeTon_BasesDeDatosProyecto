package com.example.vendeton.Entidades;

public class Producto {

    public int id, cantidadMaxima, tipoDeProducto, alto, largo, ancho;
    public String nombre, descripcion, dimensiones;
    public float precioAlPorMenor, precioAlPorMayor, costoDeProduccion, comision;

    public Producto(int id, int cantidadMaxima, int tipoDeProducto, int alto, int largo, int ancho, String nombre, String descripcion, String dimensiones, float precioAlPorMenor, float precioAlPorMayor, float costoDeProduccion, float comision) {
        this.id = id;
        this.cantidadMaxima = cantidadMaxima;
        this.tipoDeProducto = tipoDeProducto;
        this.alto = alto;
        this.largo = largo;
        this.ancho = ancho;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.dimensiones = dimensiones;
        this.precioAlPorMenor = precioAlPorMenor;
        this.precioAlPorMayor = precioAlPorMayor;
        this.costoDeProduccion = costoDeProduccion;
        this.comision = comision;
    }


}
