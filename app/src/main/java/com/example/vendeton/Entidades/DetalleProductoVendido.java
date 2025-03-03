package com.example.vendeton.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class DetalleProductoVendido implements Parcelable {

    public int doc_numero;
    public String producto;
    public int cantidad;
    public float precio_unitario;
    public float monto;

    // Constructor principal
    public DetalleProductoVendido(int doc_numero, String producto, int cantidad, float precio_unitario, float monto) {
        this.doc_numero = doc_numero;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.monto = monto;
    }

    // Constructor Parcelable
    protected DetalleProductoVendido(Parcel in) {
        doc_numero = in.readInt();
        producto = in.readString();
        cantidad = in.readInt();
        precio_unitario = in.readFloat();
        monto = in.readFloat();
    }

    // Creator para deserialización
    public static final Creator<DetalleProductoVendido> CREATOR = new Creator<DetalleProductoVendido>() {
        @Override
        public DetalleProductoVendido createFromParcel(Parcel in) {
            return new DetalleProductoVendido(in);
        }

        @Override
        public DetalleProductoVendido[] newArray(int size) {
            return new DetalleProductoVendido[size];
        }
    };

    @Override
    public int describeContents() {
        return 0; // No necesita contenido especial
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(doc_numero);
        dest.writeString(producto);
        dest.writeInt(cantidad);
        dest.writeFloat(precio_unitario);
        dest.writeFloat(monto);
    }
}