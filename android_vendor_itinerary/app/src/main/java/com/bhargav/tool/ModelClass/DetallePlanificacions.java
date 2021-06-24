package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetallePlanificacions {
    @SerializedName("codigoPlanificacion")
    @Expose
    private float codigoPlanificacion;
    @SerializedName("secuenciaDetalle")
    @Expose
    private float secuenciaDetalle;
    @SerializedName("secuenciaPersonal")
    @Expose
    private float secuenciaPersonal;
    @SerializedName("codigoCliente")
    @Expose
    private String codigoCliente;
    @SerializedName("codigoPais")
    @Expose
    private String codigoPais = null;
    @SerializedName("codigoProvincia")
    @Expose
    private String codigoProvincia = null;
    @SerializedName("codigoCiudad")
    @Expose
    private String codigoCiudad = null;
    @SerializedName("codigoParroquia")
    @Expose
    private String codigoParroquia = null;
    @SerializedName("direccionCliente")
    @Expose
    private String direccionCliente = null;
    @SerializedName("contacto")
    @Expose
    private String contacto = null;
    @SerializedName("fecha")
    @Expose
    private String fecha;
    @SerializedName("hora")
    @Expose
    private String hora;
    @SerializedName("codigoMotivo")
    @Expose
    private float codigoMotivo;
    @SerializedName("esActivo")
    @Expose
    private String esActivo;
    @SerializedName("usuarioIngreso")
    @Expose
    private String usuarioIngreso;
    @SerializedName("fechaIngreso")
    @Expose
    private String fechaIngreso;
    @SerializedName("usuarioModificacion")
    @Expose
    private String usuarioModificacion = null;
    @SerializedName("fechaModificacion")
    @Expose
    private String fechaModificacion = null;
    @SerializedName("nombreCliente")
    @Expose
    private String nombreCliente;
    @SerializedName("fechaFin")
    @Expose
    private String fechaFin;
    @SerializedName("horaFin")
    @Expose
    private String horaFin;
    @SerializedName("colorBorde")
    @Expose
    private String colorBorde;
    @SerializedName("colorContenido")
    @Expose
    private String colorContenido;
    @SerializedName("observacion")
    @Expose
    private String observacion = null;
    @SerializedName("visitado")
    @Expose
    private String visitado = null;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;
    @SerializedName("lsClientesUtils")
    @Expose
    public ArrayList<ClientesUtils> lsClientesUtils = new ArrayList<ClientesUtils>();


    // Getter Methods

    public float getCodigoPlanificacion() {
        return codigoPlanificacion;
    }

    public float getSecuenciaDetalle() {
        return secuenciaDetalle;
    }

    public float getSecuenciaPersonal() {
        return secuenciaPersonal;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public String getCodigoProvincia() {
        return codigoProvincia;
    }

    public String getCodigoCiudad() {
        return codigoCiudad;
    }

    public String getCodigoParroquia() {
        return codigoParroquia;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public String getContacto() {
        return contacto;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public float getCodigoMotivo() {
        return codigoMotivo;
    }

    public String getEsActivo() {
        return esActivo;
    }

    public String getUsuarioIngreso() {
        return usuarioIngreso;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public String getFechaModificacion() {
        return fechaModificacion;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public String getColorBorde() {
        return colorBorde;
    }

    public String getColorContenido() {
        return colorContenido;
    }

    public String getObservacion() {
        return observacion;
    }

    public String getVisitado() {
        return visitado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    // Setter Methods

    public void setCodigoPlanificacion(float codigoPlanificacion) {
        this.codigoPlanificacion = codigoPlanificacion;
    }

    public void setSecuenciaDetalle(float secuenciaDetalle) {
        this.secuenciaDetalle = secuenciaDetalle;
    }

    public void setSecuenciaPersonal(float secuenciaPersonal) {
        this.secuenciaPersonal = secuenciaPersonal;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public void setCodigoPais(String codigoPais) {
        this.codigoPais = codigoPais;
    }

    public void setCodigoProvincia(String codigoProvincia) {
        this.codigoProvincia = codigoProvincia;
    }

    public void setCodigoCiudad(String codigoCiudad) {
        this.codigoCiudad = codigoCiudad;
    }

    public void setCodigoParroquia(String codigoParroquia) {
        this.codigoParroquia = codigoParroquia;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setCodigoMotivo(float codigoMotivo) {
        this.codigoMotivo = codigoMotivo;
    }

    public void setEsActivo(String esActivo) {
        this.esActivo = esActivo;
    }

    public void setUsuarioIngreso(String usuarioIngreso) {
        this.usuarioIngreso = usuarioIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public void setUsuarioModificacion(String usuarioModificacion) {
        this.usuarioModificacion = usuarioModificacion;
    }

    public void setFechaModificacion(String fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setColorBorde(String colorBorde) {
        this.colorBorde = colorBorde;
    }

    public void setColorContenido(String colorContenido) {
        this.colorContenido = colorContenido;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void setVisitado(String visitado) {
        this.visitado = visitado;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
