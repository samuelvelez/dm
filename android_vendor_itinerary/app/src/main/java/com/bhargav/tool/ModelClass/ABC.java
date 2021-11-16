package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ABC {

    @SerializedName("mensaje")
    @Expose
    private String mensaje;
    @SerializedName("causa")
    @Expose
    private String causa;
    @SerializedName("errorSistemas")
    @Expose
    private String errorSistemas;


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCausa() {
        return causa;
    }

    public void setCausa(String causa) {
        this.causa = causa;
    }

    public String getErrorSistemas() {
        return errorSistemas;
    }

    public void setErrorSistemas(String errorSistemas) {
        this.errorSistemas = errorSistemas;
    }
}
