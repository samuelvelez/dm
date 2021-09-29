package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Divisiones {

    @SerializedName("codigoDivision")
    @Expose
    private float codigoDivision;
    @SerializedName("nombreDivision")
    @Expose
    private String nombreDivision;
    @SerializedName("esActivo")
    @Expose
    private String esActivo = null;
    @SerializedName("fechaIngreso")
    @Expose
    private String fechaIngreso = null;
    @SerializedName("usuarioIngreso")
    @Expose
    private String usuarioIngreso = null;
    @SerializedName("fechaModificacion")
    @Expose
    private String fechaModificacion = null;
    @SerializedName("usuarioModificacion")
    @Expose
    private String usuarioModificacion = null;
    @SerializedName("nemonicoDivision")
    @Expose
    private String nemonicoDivision = null;
    @SerializedName("diasGraciaRep")
    @Expose
    private String diasGraciaRep = null;
    @SerializedName("aplicaServicio")
    @Expose
    private String aplicaServicio = null;
    @SerializedName("aplicaProduccion")
    @Expose
    private String aplicaProduccion = null;
    @SerializedName("aplicaBod80")
    @Expose
    private String aplicaBod80 = null;
    @SerializedName("aplicaEntregaProceso")
    @Expose
    private String aplicaEntregaProceso = null;
    @SerializedName("aplicaEntregaBl")
    @Expose
    private String aplicaEntregaBl = null;
    @SerializedName("aplicaBodegaEspecial")
    @Expose
    private String aplicaBodegaEspecial = null;
    @SerializedName("aplicaCuotasPagos")
    @Expose
    private String aplicaCuotasPagos = null;
    @SerializedName("lsFacturasXCobrar")
    @Expose
    public ArrayList<FacturasXCobrar> lsFacturasXCobrar = new ArrayList<FacturasXCobrar>();


    private int cuentaPosition = 0;
    private int formaPogoPosition = 0;
    private int numeroTransaccion = 0;
    private int valor = 0;
    private String fechaCheque = null;
    private String fechaEntrega = null;
    private String invoiceNoForUpdate = null;
    private String dateForUpdate = null;
    private int cantidad = 0;
    private long codigoNumeroCuenta = 0;
    private int codigoFormaPago = 0;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(int numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }

    public int getCodigoFormaPago() {
        return codigoFormaPago;
    }

    public void setCodigoFormaPago(int codigoFormaPago) {
        this.codigoFormaPago = codigoFormaPago;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public long getCodigoNumeroCuenta() {
        return codigoNumeroCuenta;
    }

    public void setCodigoNumeroCuenta(long codigoNumeroCuenta) {
        this.codigoNumeroCuenta = codigoNumeroCuenta;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCuentaPosition() {
        return cuentaPosition;
    }

    public int getFormaPogoPosition() {
        return formaPogoPosition;
    }

    public String getFechaCheque() {
        return fechaCheque;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public String getInvoiceNoForUpdate() {
        return invoiceNoForUpdate;
    }

    public String getDateForUpdate() {
        return dateForUpdate;
    }


    public void setCuentaPosition(int cuentaPosition) {
        this.cuentaPosition = cuentaPosition;
    }

    public void setFormaPogoPosition(int formaPogoPosition) {
        this.formaPogoPosition = formaPogoPosition;
    }

    public void setFechaCheque(String fechaCheque) {
        this.fechaCheque = fechaCheque;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setInvoiceNoForUpdate(String invoiceNoForUpdate) {
        this.invoiceNoForUpdate = invoiceNoForUpdate;
    }

    public void setDateForUpdate(String dateForUpdate) {
        this.dateForUpdate = dateForUpdate;
    }


    // Getter Methods

    public float getCodigoDivision() {
        return codigoDivision;
    }

    public String getNombreDivision() {
        return nombreDivision;
    }

    public String getEsActivo() {
        return esActivo;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public String getNemonicoDivision() {
        return nemonicoDivision;
    }

    public String getDiasGraciaRep() {
        return diasGraciaRep;
    }

    public String getAplicaServicio() {
        return aplicaServicio;
    }

    public String getAplicaProduccion() {
        return aplicaProduccion;
    }

    public String getAplicaBod80() {
        return aplicaBod80;
    }

    public String getAplicaEntregaProceso() {
        return aplicaEntregaProceso;
    }

    public String getAplicaEntregaBl() {
        return aplicaEntregaBl;
    }

    public String getAplicaBodegaEspecial() {
        return aplicaBodegaEspecial;
    }

    public String getAplicaCuotasPagos() {
        return aplicaCuotasPagos;
    }

    // Setter Methods

    public void setCodigoDivision(float codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public void setNombreDivision(String nombreDivision) {
        this.nombreDivision = nombreDivision;
    }

    public void setEsActivo(String esActivo) {
        this.esActivo = esActivo;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public void setNemonicoDivision(String nemonicoDivision) {
        this.nemonicoDivision = nemonicoDivision;
    }

    public void setDiasGraciaRep(String diasGraciaRep) {
        this.diasGraciaRep = diasGraciaRep;
    }

    public void setAplicaServicio(String aplicaServicio) {
        this.aplicaServicio = aplicaServicio;
    }

    public void setAplicaProduccion(String aplicaProduccion) {
        this.aplicaProduccion = aplicaProduccion;
    }

    public void setAplicaBod80(String aplicaBod80) {
        this.aplicaBod80 = aplicaBod80;
    }

    public void setAplicaEntregaProceso(String aplicaEntregaProceso) {
        this.aplicaEntregaProceso = aplicaEntregaProceso;
    }

    public void setAplicaEntregaBl(String aplicaEntregaBl) {
        this.aplicaEntregaBl = aplicaEntregaBl;
    }

    public void setAplicaBodegaEspecial(String aplicaBodegaEspecial) {
        this.aplicaBodegaEspecial = aplicaBodegaEspecial;
    }

    public void setAplicaCuotasPagos(String aplicaCuotasPagos) {
        this.aplicaCuotasPagos = aplicaCuotasPagos;
    }
}
