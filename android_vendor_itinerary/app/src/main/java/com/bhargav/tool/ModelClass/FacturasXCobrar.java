package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacturasXCobrar {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("fechaEmision")
    @Expose
    private String fechaEmision;
    @SerializedName("factura")
    @Expose
    private String factura;
    @SerializedName("totalFactura")
    @Expose
    private float totalFactura;
    @SerializedName("totalCreditos")
    @Expose
    private float totalCreditos;
    @SerializedName("totalDebitos")
    @Expose
    private float totalDebitos;
    @SerializedName("totalCancelado")
    @Expose
    private float totalCancelado;
    @SerializedName("valorAbonado")
    @Expose
    private float valorAbonado;
    @SerializedName("valorXCobrar")
    @Expose
    private float valorXCobrar;
    @SerializedName("secuenciaComprobante")
    @Expose
    private String secuenciaComprobante;
    @SerializedName("numeroIdentificacionCliente")
    @Expose
    private String numeroIdentificacionCliente;
    @SerializedName("razonSocial")
    @Expose
    private String razonSocial;
    @SerializedName("nombreTipoComprobante")
    @Expose
    private String nombreTipoComprobante;
    @SerializedName("codigoTipoComprobante")
    @Expose
    private float codigoTipoComprobante;
    @SerializedName("codigoDivision")
    @Expose
    private float codigoDivision;

    private int isChecked = 0;

    public int getIsChecked() {
        return isChecked;
    }


    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }


    // Getter Methods

    public String getNombre() {
        return nombre;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public String getFactura() {
        return factura;
    }

    public float getTotalFactura() {
        return totalFactura;
    }

    public float getTotalCreditos() {
        return totalCreditos;
    }

    public float getTotalDebitos() {
        return totalDebitos;
    }

    public float getTotalCancelado() {
        return totalCancelado;
    }

    public float getValorAbonado() {
        return valorAbonado;
    }

    public float getValorXCobrar() {
        return valorXCobrar;
    }

    public String getSecuenciaComprobante() {
        return secuenciaComprobante;
    }

    public String getNumeroIdentificacionCliente() {
        return numeroIdentificacionCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getNombreTipoComprobante() {
        return nombreTipoComprobante;
    }

    public float getCodigoTipoComprobante() {
        return codigoTipoComprobante;
    }

    public float getCodigoDivision() {
        return codigoDivision;
    }

    // Setter Methods

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public void setTotalFactura(float totalFactura) {
        this.totalFactura = totalFactura;
    }

    public void setTotalCreditos(float totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public void setTotalDebitos(float totalDebitos) {
        this.totalDebitos = totalDebitos;
    }

    public void setTotalCancelado(float totalCancelado) {
        this.totalCancelado = totalCancelado;
    }

    public void setValorAbonado(float valorAbonado) {
        this.valorAbonado = valorAbonado;
    }

    public void setValorXCobrar(float valorXCobrar) {
        this.valorXCobrar = valorXCobrar;
    }

    public void setSecuenciaComprobante(String secuenciaComprobante) {
        this.secuenciaComprobante = secuenciaComprobante;
    }

    public void setNumeroIdentificacionCliente(String numeroIdentificacionCliente) {
        this.numeroIdentificacionCliente = numeroIdentificacionCliente;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public void setNombreTipoComprobante(String nombreTipoComprobante) {
        this.nombreTipoComprobante = nombreTipoComprobante;
    }

    public void setCodigoTipoComprobante(float codigoTipoComprobante) {
        this.codigoTipoComprobante = codigoTipoComprobante;
    }

    public void setCodigoDivision(float codigoDivision) {
        this.codigoDivision = codigoDivision;
    }
}
