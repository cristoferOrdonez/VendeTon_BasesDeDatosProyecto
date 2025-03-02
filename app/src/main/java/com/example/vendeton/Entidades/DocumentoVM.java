package com.example.vendeton.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DocumentoVM implements Parcelable {

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
    protected DocumentoVM(Parcel in) {
        doc_numero = in.readInt();
        con_identificacion = in.readLong();
        fecha = new Date(in.readLong());
        doc_total = in.readDouble();
        proposito_de_compra = in.readString();
        con_nombre = in.readString();
        con_apellido = in.readString();
        con_calle = in.readString();
        con_barrio = in.readString();
        con_ciudad = in.readString();
        con_direccion = in.readString();
    }

    public static final Parcelable.Creator<DocumentoVM> CREATOR = new Parcelable.Creator<DocumentoVM>() {
        @Override
        public DocumentoVM createFromParcel(Parcel in) {
            return new DocumentoVM(in);
        }

        @Override
        public DocumentoVM[] newArray(int size) {
            return new DocumentoVM[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(doc_numero);
        dest.writeLong(con_identificacion);
        dest.writeLong(fecha.getTime());
        dest.writeDouble(doc_total);
        dest.writeString(proposito_de_compra);
        dest.writeString(con_nombre);
        dest.writeString(con_apellido);
        dest.writeString(con_calle);
        dest.writeString(con_barrio);
        dest.writeString(con_ciudad);
        dest.writeString(con_direccion);
    }


}
