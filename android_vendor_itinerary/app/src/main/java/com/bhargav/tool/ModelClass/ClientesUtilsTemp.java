package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClientesUtilsTemp {

    public ArrayList<DivisionesTemp> lsDivisiones = new ArrayList<DivisionesTemp>(); //codDivision
    private int secuencialPersonal = 0;
    private int codigoCliente;
    private String fechaReciboCobro;
    private int invoce_No;
    private int codigoEmpresa = 1;
    private String esActivo = "S";
    private int codigoDivision;
    private double totalAbonado = 0;
    private int codigoFormaPago = 0;
    private String codigoNumeroCuenta = "0";
    private int secuenciaReciboCobro = 0;
    private String observaciones;
    private String image;
    public ArrayList<ArchivosAdjuntos2> lsArchivosAdjuntos2 = new ArrayList<>();

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getSecuenciaReciboCobro() {
        return secuenciaReciboCobro;
    }

    public void setSecuenciaReciboCobro(int secuenciaReciboCobro) {
        this.secuenciaReciboCobro = secuenciaReciboCobro;
    }





    public void setCodigoNumeroCuenta(String codigoNumeroCuenta) {
        this.codigoNumeroCuenta = codigoNumeroCuenta;
    }

    public String getCodigoNumeroCuenta() {
        return codigoNumeroCuenta;
    }

    public int getInvoce_No() {
        return invoce_No;
    }

    public void setInvoce_No(int invoce_No) {
        this.invoce_No = invoce_No;
    }

    public int getCodigoFormaPago() {
        return codigoFormaPago;
    }

    public void setCodigoFormaPago(int codigoFormaPago) {
        this.codigoFormaPago = codigoFormaPago;
    }

    public int getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(int codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public double getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(double totalAbonado) {
        this.totalAbonado = totalAbonado;
    }

    public int getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(int codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public String getEsActivo() {
        return esActivo;
    }

    public void setEsActivo(String esActivo) {
        this.esActivo = esActivo;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getFechaReciboCobro() {
        return fechaReciboCobro;
    }

    public void setFechaReciboCobro(String fechaReciboCobro) {
        this.fechaReciboCobro = fechaReciboCobro;
    }

    public int getSecuencialPersonal() {
        return secuencialPersonal;
    }

    public void setSecuencialPersonal(int secuencialPersonal) {
        this.secuencialPersonal = secuencialPersonal;
    }

}
