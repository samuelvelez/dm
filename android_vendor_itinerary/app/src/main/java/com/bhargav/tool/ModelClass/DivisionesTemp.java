package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DivisionesTemp {

    public ArrayList<FacturasXCobrarTemp> lsFacturasXCobrar = new ArrayList<FacturasXCobrarTemp>();
    private double valor = 0.0;
    private double valorAplicado = 0.0;
    private double valorSinAplicar = 0.0;
    private String codigoFormaPago;
    private int codigoEmpresa = 1;
    private int codigoDivision;
    private String codigoNumeroCuenta = "0";
    private int numeroTransaccion = 0;

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public String getCodigoNumeroCuenta() {
        return codigoNumeroCuenta;
    }

    public void setCodigoNumeroCuenta(String codigoNumeroCuenta) {
        this.codigoNumeroCuenta = codigoNumeroCuenta;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(int codigoDivision) {
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
