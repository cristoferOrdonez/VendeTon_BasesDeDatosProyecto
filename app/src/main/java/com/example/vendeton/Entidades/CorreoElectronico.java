package com.example.vendeton.Entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class CorreoElectronico implements Parcelable {

    public String cor_usuario, cor_dominio, cor_correo, cor_usuario_anterior, cor_dominio_anterior;
    public int cor_id;
    public long con_identificacion;

    public CorreoElectronico(String cor_usuario, String cor_dominio, String cor_correo,
                             int cor_id, long con_identificacion,
                             String cor_usuario_anterior, String cor_dominio_anterior) {
        this.cor_usuario = cor_usuario;
        this.cor_dominio = cor_dominio;
        this.cor_correo = cor_correo;
        this.cor_id = cor_id;
        this.con_identificacion = con_identificacion;
        this.cor_usuario_anterior = cor_usuario_anterior;
        this.cor_dominio_anterior = cor_dominio_anterior;
    }

    public CorreoElectronico(String cor_usuario, String cor_dominio, String cor_correo,
                             int cor_id, long con_identificacion) {
        this(cor_usuario, cor_dominio, cor_correo, cor_id, con_identificacion, null, null);
    }

    protected CorreoElectronico(Parcel in) {
        cor_usuario = in.readString();
        cor_dominio = in.readString();
        cor_correo = in.readString();
        cor_id = in.readInt();
        con_identificacion = in.readLong();
        cor_usuario_anterior = in.readString();
        cor_dominio_anterior = in.readString();
    }

    public static final Creator<CorreoElectronico> CREATOR = new Creator<CorreoElectronico>() {
        @Override
        public CorreoElectronico createFromParcel(Parcel in) {
            return new CorreoElectronico(in);
        }

        @Override
        public CorreoElectronico[] newArray(int size) {
            return new CorreoElectronico[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cor_usuario);
        dest.writeString(cor_dominio);
        dest.writeString(cor_correo);
        dest.writeInt(cor_id);
        dest.writeLong(con_identificacion);
        dest.writeString(cor_usuario_anterior);
        dest.writeString(cor_dominio_anterior);
    }
}