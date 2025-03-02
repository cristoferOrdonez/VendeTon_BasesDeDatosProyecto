package com.example.vendeton.Entidades;

public class InfoSesion {
    private int  TipoSesion;
    private long IdentificacionSesion;

    public InfoSesion(long id, int tipoSesion, long identificacionSesion){

        if (identificacionSesion == 0) {
            throw new IllegalArgumentException("Identificación no puede ser nulo o vacío");
        }
        this.TipoSesion = tipoSesion;
        this.IdentificacionSesion = identificacionSesion;
    };
    public int getTipoSesion() {
        return TipoSesion;
    }

    public void setTipoSesion(int tipoSesion) {
        this.TipoSesion = tipoSesion;
    }
    public  long getIdentificacionSesion() {
        return IdentificacionSesion;
    }

    public void setIdentificacionSesion(long identificacionSesion) {
        IdentificacionSesion = identificacionSesion;
    }



    ;

}
