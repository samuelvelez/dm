package com.bhargav.tool.ModelClass;

import java.util.ArrayList;

public class DivisionesTempFinal {

    public ArrayList<FacturasXCobrarTempFinal> lsCobXSecComps = new ArrayList<>();
    private int valor = 0;
    private int codigoEmpresa = 1;
    private float codigoDivisiones;
    private long codigoNumeroCuenta = 0;
    private int numeroTransaccion = 0;

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public long getCodigoNumeroCuenta() {
        return codigoNumeroCuenta;
    }

    public void setCodigoNumeroCuenta(long codigoNumeroCuenta) {
        this.codigoNumeroCuenta = codigoNumeroCuenta;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public float getCodigoDivisiones() {
        return codigoDivisiones;
    }

    public void setCodigoDivisiones(float codigoDivisiones) {
        this.codigoDivisiones = codigoDivisiones;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}
