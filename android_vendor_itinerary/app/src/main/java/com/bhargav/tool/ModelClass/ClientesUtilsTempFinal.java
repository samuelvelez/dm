package com.bhargav.tool.ModelClass;

import java.util.ArrayList;

public class ClientesUtilsTempFinal {

    public ArrayList<DivisionesTempFinal> lsPagoReciboCobros = new ArrayList<>(); //codDivision
    private int secuenciaPersonal = 0;
    private int codigoCliente;
    private String fechaReciboCobro;
    private String invoce_No;
    private int codigoEmpresa = 1;
    private String esActivo = "S";
    private int totalAbonado = 0;
    private int codigoFormaPago = 0;

    public String getInvoce_No() {
        return invoce_No;
    }

    public void setInvoce_No(String invoce_No) {
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

    public int getTotalAbonado() {
        return totalAbonado;
    }

    public void setTotalAbonado(int totalAbonado) {
        this.totalAbonado = totalAbonado;
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


    public int getSecuenciaPersonal() {
        return secuenciaPersonal;
    }

    public void setSecuenciaPersonal(int secuenciaPersonal) {
        this.secuenciaPersonal = secuenciaPersonal;
    }
}
