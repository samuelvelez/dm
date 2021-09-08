package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DivisionesTemp {

    public ArrayList<FacturasXCobrarTemp> lsFacturasXCobrar = new ArrayList<FacturasXCobrarTemp>();
    private int valor = 0;
    private String codigoFormaPago;
    private int codigoEmpresa = 1;
    private float codigoDivision;
    private int codigoNumeroCuenta = 0;
    private int numeroTransaccion = 0;

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public int getCodigoNumeroCuenta() {
        return codigoNumeroCuenta;
    }

    public void setCodigoNumeroCuenta(int codigoNumeroCuenta) {
        this.codigoNumeroCuenta = codigoNumeroCuenta;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public float getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(float codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public String getCodigoFormaPago() {
        return codigoFormaPago;
    }

    public void setCodigoFormaPago(String codigoFormaPago) {
        this.codigoFormaPago = codigoFormaPago;
    }
}
