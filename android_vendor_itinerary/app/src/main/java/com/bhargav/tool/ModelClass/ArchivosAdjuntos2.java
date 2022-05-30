package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArchivosAdjuntos2 {
    @SerializedName("nombreArchivo")
    @Expose
    private String nombreArchivo;
    @SerializedName("archivoEnBase64")
    @Expose
    private String archivoEnBase64;

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getArchivoEnBase64() {
        return archivoEnBase64;
    }

    public void setArchivoEnBase64(String archivoEnBase64) {
        this.archivoEnBase64 = archivoEnBase64;
    }
}
