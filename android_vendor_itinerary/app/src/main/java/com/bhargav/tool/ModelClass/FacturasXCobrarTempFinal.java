package com.bhargav.tool.ModelClass;

public class FacturasXCobrarTempFinal {

    private double valor = 0.0;
    private int codigoEmpresa = 1;
    private int secuenciaComprobante;

    public int getSecuenciaComprobante() {
        return secuenciaComprobante;
    }

    public void setSecuenciaComprobante(int secuenciaComprobante) {
        this.secuenciaComprobante = secuenciaComprobante;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
