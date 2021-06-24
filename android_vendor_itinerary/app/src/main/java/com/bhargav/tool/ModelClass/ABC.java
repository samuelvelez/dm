package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ABC {

    @SerializedName("error")
    @Expose
    private String error = null;
    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("errorUsuario")
    @Expose
    private String errorUsuario = null;


    // Getter Methods

    public String getError() {
        return error;
    }

    public String getMensaje() {
        return mensaje;
    }

    public String getErrorUsuario() {
        return errorUsuario;
    }

    // Setter Methods

    public void setError(String error) {
        this.error = error;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setErrorUsuario(String errorUsuario) {
        this.errorUsuario = errorUsuario;
    }
}
