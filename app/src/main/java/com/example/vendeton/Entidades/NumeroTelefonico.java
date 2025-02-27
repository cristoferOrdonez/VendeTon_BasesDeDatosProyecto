package com.example.vendeton.Entidades;

public class NumeroTelefonico {

    public NumeroTelefonico(int num_id, int con_identificacion, int num_prefijo, long num_numero, long num_numero_de_contacto) {
        this.num_id = num_id;
        this.con_identificacion = con_identificacion;
        this.num_prefijo = num_prefijo;
        this.num_numero = num_numero;
        this.num_numero_de_contacto = num_numero_de_contacto;
    }

    public int num_id, con_identificacion, num_prefijo;
    public long num_numero, num_numero_de_contacto;


}
