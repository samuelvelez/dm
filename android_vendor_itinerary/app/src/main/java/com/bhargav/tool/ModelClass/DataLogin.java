package com.bhargav.tool.ModelClass;

public class DataLogin {
    private float codigoPersonal;
    private float codigoEmpresa;
    private String nombrePersonal;
    private String cedula;
    private long secuenciaPersonal;


    // Getter Methods

    public float getCodigoPersonal() {
        return codigoPersonal;
    }

    public float getCodigoEmpresa() {
        return codigoEmpresa;
    }

    public String getNombrePersonal() {
        return nombrePersonal;
    }

    public String getCedula() {
        return cedula;
    }

    public long getSecuenciaPersonal() {
        return secuenciaPersonal;
    }

    // Setter Methods

    public void setCodigoPersonal(float codigoPersonal) {
        this.codigoPersonal = codigoPersonal;
    }

    public void setCodigoEmpresa(float codigoEmpresa) {
        this.codigoEmpresa = codigoEmpresa;
    }

    public void setNombrePersonal(String nombrePersonal) {
        this.nombrePersonal = nombrePersonal;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setSecuenciaPersonal(long secuenciaPersonal) {
        this.secuenciaPersonal = secuenciaPersonal;
    }
}
