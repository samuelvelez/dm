package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ComNumerosCuentas {
    @SerializedName("codigoNumeroCuenta")
    @Expose
    private int codigoNumeroCuenta;
    @SerializedName("codigoCliente")
    @Expose
    private int codigoCliente;
    @SerializedName("codigoInstitucion")
    @Expose
    private int codigoInstitucion;
    @SerializedName("numeroCuenta")
    @Expose
    private String numeroCuenta;
    @SerializedName("esActivo")
    @Expose
    private String esActivo;
    @SerializedName("usuarioIngreso")
    @Expose
    private String usuarioIngreso;
    @SerializedName("fechaIngreso")
    @Expose
    private float fechaIngreso;
    @SerializedName("usuarioModificacion")
    @Expose
    private String usuarioModificacion = null;
    @SerializedName("fechaModificacion")
    @Expose
    private String fechaModificacion = null;


    // Getter Methods

    public int getCodigoNumeroCuenta() {
        return codigoNumeroCuenta;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public int getCodigoInstitucion() {
        return codigoInstitucion;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public String getEsActivo() {
        return esActivo;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public float getFechaIngreso() {
        return fechaIngreso;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    // Setter Methods

    public void setCodigoNumeroCuenta(int codigoNumeroCuenta) {
        this.codigoNumeroCuenta = codigoNumeroCuenta;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setCodigoInstitucion(int codigoInstitucion) {
        this.codigoInstitucion = codigoInstitucion;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public void setEsActivo(String esActivo) {
        this.esActivo = esActivo;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public void setFechaIngreso(float fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }
}
