package com.example.vendeton.Entidades;

public class Bodega {

    public int bod_id, bod_alto, bod_ancho, bod_largo;
    public String bod_nombre;
    public String dimensiones;
    public String calle;
    public String barrio;
    public String ciudad;
    public String direccion;

    public Bodega(int bod_id, String bod_nombre, int bod_alto, int bod_ancho, int bod_largo, String dimensiones, String calle, String barrio, String ciudad, String direccion) {
        this.bod_id = bod_id;
        this.bod_alto = bod_alto;
        this.bod_ancho = bod_ancho;
        this.bod_largo = bod_largo;
        this.bod_nombre = bod_nombre;
        this.dimensiones = dimensiones;
        this.calle = calle;
        this.barrio = barrio;
        this.ciudad = ciudad;
        this.direccion = direccion;
    }

}
