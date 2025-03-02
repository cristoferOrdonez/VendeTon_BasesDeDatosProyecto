package com.example.vendeton.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NumeroTelefonico implements Parcelable {

    public int num_id, num_prefijo, num_prefijo_anterior;
    public long num_numero, num_numero_de_contacto, con_identificacion, num_numero_anterior;


    public NumeroTelefonico(int num_id, long con_identificacion, int num_prefijo, long num_numero, long num_numero_de_contacto) {
        this.num_id = num_id;
        this.con_identificacion = con_identificacion;
        this.num_prefijo = num_prefijo;
        this.num_numero = num_numero;
        this.num_numero_de_contacto = num_numero_de_contacto;
        this.num_prefijo_anterior = -1;
        this.num_numero_anterior = -1;
    }


    public NumeroTelefonico(int num_id, long con_identificacion, int num_prefijo,
                            long num_numero, long num_numero_de_contacto,
                            int num_prefijo_anterior, long num_numero_anterior) {

        this.num_id = num_id;
        this.con_identificacion = con_identificacion;
        this.num_prefijo = num_prefijo;
        this.num_numero = num_numero;
        this.num_numero_de_contacto = num_numero_de_contacto;
        this.num_prefijo_anterior = num_prefijo_anterior;
        this.num_numero_anterior = num_numero_anterior;
    }



    protected NumeroTelefonico(Parcel in) {
        num_id = in.readInt();
        con_identificacion = in.readLong();
        num_prefijo = in.readInt();
        num_numero = in.readLong();
        num_numero_de_contacto = in.readLong();
        num_prefijo_anterior = in.readInt();
        num_numero_anterior = in.readLong();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(num_id);
        dest.writeLong(con_identificacion);
        dest.writeInt(num_prefijo);
        dest.writeLong(num_numero);
        dest.writeLong(num_numero_de_contacto);
        dest.writeInt(num_prefijo_anterior);
        dest.writeLong(num_numero_anterior);
    }

}