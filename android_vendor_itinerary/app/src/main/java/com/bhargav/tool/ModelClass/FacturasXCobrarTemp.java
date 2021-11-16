package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FacturasXCobrarTemp {

    private double valor = 0.0;
    private int codigoEmpresa = 1;
    private int secuenciaComprobante;
    private int isChecked = 0;

    public int getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(int isChecked) {
        this.isChecked = isChecked;
    }

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
