package com.example.vendeton.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NumeroTelefonico implements Parcelable {

    public NumeroTelefonico(int num_id, int con_identificacion, int num_prefijo, long num_numero, long num_numero_de_contacto) {
        this.num_id = num_id;
        this.con_identificacion = con_identificacion;
        this.num_prefijo = num_prefijo;
        this.num_numero = num_numero;
        this.num_numero_de_contacto = num_numero_de_contacto;
    }

    public int num_id, con_identificacion, num_prefijo;
    public long num_numero, num_numero_de_contacto;


    // Parcelable Constructor
    protected NumeroTelefonico(Parcel in) {
        num_id = in.readInt();
        con_identificacion = in.readInt();
        num_prefijo = in.readInt();
        num_numero = in.readLong();
        num_numero_de_contacto = in.readLong();
    }

    // Creator (for deserialization)
    public static final Creator<NumeroTelefonico> CREATOR = new Creator<NumeroTelefonico>() {
        @Override
        public NumeroTelefonico createFromParcel(Parcel in) {
            return new NumeroTelefonico(in);
        }

        @Override
        public NumeroTelefonico[] newArray(int size) {
            return new NumeroTelefonico[size];
        }
    };

    // describeContents method
    @Override
    public int describeContents() {
        return 0; // No special contents
    }

    // writeToParcel method
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(num_id);
        dest.writeInt(con_identificacion);
        dest.writeInt(num_prefijo);
        dest.writeLong(num_numero);
        dest.writeLong(num_numero_de_contacto);
    }
}
