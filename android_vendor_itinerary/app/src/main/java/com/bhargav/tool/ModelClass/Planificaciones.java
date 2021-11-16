package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Planificaciones {
    @SerializedName("lsDetallePlanificacions")
    @Expose
    public ArrayList<DetallePlanificacions> lsDetallePlanificacions = new ArrayList<DetallePlanificacions>();
    @SerializedName("mensaje")
    @Expose
    private String mensaje;


    // Getter Methods

    public String getMensaje() {
        return mensaje;
    }

    // Setter Methods

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
