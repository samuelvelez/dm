package com.bhargav.tool.ModelClass;

import java.util.ArrayList;

public class  DivisionesTempFinal {

    public ArrayList<FacturasXCobrarTempFinal> lsCobXSecComps = new ArrayList<>();
    private double valor = 0.0;
    private int codigoEmpresa = 1;
    private int codigoDivisiones;
    private long codigoNumeroCuenta = 0;
    private int numeroTransaccion = 0;
    private double valorAplicado = 0;
    private double valorSinAplicar = 0;

    public void setValorAplicado(double valorAplicado) {
        this.valorAplicado = valorAplicado;
    }

    public void setValorSinAplicar(double valorSinAplicar) {
        this.valorSinAplicar = valorSinAplicar;
    }

    public double getValorAplicado() {
        return valorAplicado;
    }

    public double getValorSinAplicar() {
        return valorSinAplicar;
    }

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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCodigoDivisiones() {
        return codigoDivisiones;
    }

    public void setCodigoDivisiones(int codigoDivisiones) {
        this.codigoDivisiones = codigoDivisiones;
    }

    public int getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public void setCodigoEmpresa(int codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }
}
