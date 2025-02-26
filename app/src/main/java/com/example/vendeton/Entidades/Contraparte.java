package com.example.vendeton.Entidades;

public class Contraparte {

    public String con_nombre , con_apellido, con_calle, con_barrio, con_ciudad, con_direccion;
    public int con_identificacion;

    public Contraparte(int con_identificacion, String con_nombre, String con_apellido, String con_calle, String con_barrio, String con_ciudad, String con_direccion) {
        this.con_nombre = con_nombre;
        this.con_apellido = con_apellido;
        this.con_calle = con_calle;
        this.con_barrio = con_barrio;
        this.con_ciudad = con_ciudad;
        this.con_direccion = con_direccion;
        this.con_identificacion = con_identificacion;
    }


}
