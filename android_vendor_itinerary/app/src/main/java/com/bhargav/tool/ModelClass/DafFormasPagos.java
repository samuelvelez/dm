package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DafFormasPagos {
    @SerializedName("codigoFormaPago")
    @Expose
    private float codigoFormaPago;
    @SerializedName("nombreFormaPago")
    @Expose
    private String nombreFormaPago;
    @SerializedName("observacion")
    @Expose
    private String observacion;
    @SerializedName("fechaIngreso")
    @Expose
    private float fechaIngreso;
    @SerializedName("usuarioIngreso")
    @Expose
    private String usuarioIngreso;
    @SerializedName("fechaModificacion")
    @Expose
    private float fechaModificacion;
    @SerializedName("usuarioModificacion")
    @Expose
    private String usuarioModificacion;
    @SerializedName("mostrarEnFac")
    @Expose
    private String mostrarEnFac;
    @SerializedName("mostrarEnCXC")
    @Expose
    private String mostrarEnCXC;
    @SerializedName("esActivo")
    @Expose
    private String esActivo;
    @SerializedName("nemonico")
    @Expose
    private String nemonico;
    @SerializedName("mostrarEnFin")
    @Expose
    private String mostrarEnFin;
    @SerializedName("codigoCuentaContable")
    @Expose
    private String codigoCuentaContable;
    @SerializedName("codigoEmpresa")
    @Expose
    private float codigoEmpresa;
    @SerializedName("codigoCuentaCtbleCXP")
    @Expose
    private String codigoCuentaCtbleCXP = null;
    @SerializedName("mostrarEnProveedor")
    @Expose
    private String mostrarEnProveedor;
    @SerializedName("codigoCtaContableCxc")
    @Expose
    private String codigoCtaContableCxc;
    @SerializedName("codigoEmpresaCxc")
    @Expose
    private float codigoEmpresaCxc;
    @SerializedName("codigoCtaContableCxp")
    @Expose
    private String codigoCtaContableCxp = null;
    @SerializedName("codigoEmpresaCxp")
    @Expose
    private String codigoEmpresaCxp = null;


    // Getter Methods

    public float getCodigoFormaPago() {
        return codigoFormaPago;
    }

    public String getNombreFormaPago() {
        return nombreFormaPago;
    }

    public String getObservacion() {
        return observacion;
    }

    public float getFechaIngreso() {
        return fechaIngreso;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public float getFechaModificacion() {
        return fechaModificacion;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public String getMostrarEnFac() {
        return mostrarEnFac;
    }

    public String getMostrarEnCXC() {
        return mostrarEnCXC;
    }

    public String getEsActivo() {
        return esActivo;
    }

    public String getNemonico() {
        return nemonico;
    }

    public String getMostrarEnFin() {
        return mostrarEnFin;
    }

    public String getCodigoCuentaContable() {
        return codigoCuentaContable;
    }

    public float getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public String getCodigoCuentaCtbleCXP() {
        return codigoCuentaCtbleCXP;
    }

    public String getMostrarEnProveedor() {
        return mostrarEnProveedor;
    }

    public String getCodigoCtaContableCxc() {
        return codigoCtaContableCxc;
    }

    public float getCodigoEmpresaCxc() {
        return codigoEmpresaCxc;
    }

    public String getCodigoCtaContableCxp() {
        return codigoCtaContableCxp;
    }

    public String getCodigoEmpresaCxp() {
        return codigoEmpresaCxp;
    }

    // Setter Methods

    public void setCodigoFormaPago(float codigoFormaPago) {
        this.codigoFormaPago = codigoFormaPago;
    }

    public void setNombreFormaPago(String nombreFormaPago) {
        this.nombreFormaPago = nombreFormaPago;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setFechaIngreso(float fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public void setFechaModificacion(float fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public void setMostrarEnFac(String mostrarEnFac) {
        this.mostrarEnFac = mostrarEnFac;
    }

    public void setMostrarEnCXC(String mostrarEnCXC) {
        this.mostrarEnCXC = mostrarEnCXC;
    }

    public void setEsActivo(String esActivo) {
        this.esActivo = esActivo;
    }

    public void setNemonico(String nemonico) {
        this.nemonico = nemonico;
    }

    public void setMostrarEnFin(String mostrarEnFin) {
        this.mostrarEnFin = mostrarEnFin;
    }

    public void setCodigoCuentaContable(String codigoCuentaContable) {
        this.codigoCuentaContable = codigoCuentaContable;
    }

    public void setCodigoEmpresa(float codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public void setCodigoCuentaCtbleCXP(String codigoCuentaCtbleCXP) {
        this.codigoCuentaCtbleCXP = codigoCuentaCtbleCXP;
    }

    public void setMostrarEnProveedor(String mostrarEnProveedor) {
        this.mostrarEnProveedor = mostrarEnProveedor;
    }

    public void setCodigoCtaContableCxc(String codigoCtaContableCxc) {
        this.codigoCtaContableCxc = codigoCtaContableCxc;
    }

    public void setCodigoEmpresaCxc(float codigoEmpresaCxc) {
        this.codigoEmpresaCxc = codigoEmpresaCxc;
    }

    public void setCodigoCtaContableCxp(String codigoCtaContableCxp) {
        this.codigoCtaContableCxp = codigoCtaContableCxp;
    }

    public void setCodigoEmpresaCxp(String codigoEmpresaCxp) {
        this.codigoEmpresaCxp = codigoEmpresaCxp;
    }
}
