package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ClientesUtils {
    @SerializedName("id")
    @Expose
    private String id = null;
    @SerializedName("codigo")
    @Expose
    private String codigo;
    @SerializedName("nombres")
    @Expose
    private String nombres;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("cedula")
    @Expose
    private String cedula;
    @SerializedName("cupoAutorizado")
    @Expose
    private String cupoAutorizado = null;
    @SerializedName("cupoUtilizado")
    @Expose
    private String cupoUtilizado = null;
    @SerializedName("cupoDisponible")
    @Expose
    private String cupoDisponible = null;
    @SerializedName("lsDivisiones")
    @Expose
    public ArrayList<Divisiones> lsDivisiones = new ArrayList<Divisiones>();
    @SerializedName("lsComNumerosCuentas")
    @Expose
    public ArrayList<ComNumerosCuentas> lsComNumerosCuentas = new ArrayList<ComNumerosCuentas>();
    @SerializedName("lsArchivosAdjuntos2")
    @Expose
    public ArrayList<ArchivosAdjuntos2> lsArchivosAdjuntos2 = new ArrayList<ArchivosAdjuntos2>();

    private int divisionPosition = 0;
    private int divisionMaster = 0;
    private int codigoDivision;
    private String observaciones;
    private String image;
    private Boolean filled = false;

    public Boolean getFilled() {
        return filled;
    }

    public void setFilled(Boolean filled) {
        this.filled = filled;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodigoDivision() {
        return codigoDivision;
    }

    public void setCodigoDivision(int codigoDivision) {
        this.codigoDivision = codigoDivision;
    }

    public int getDivisionMaster() {
        return divisionMaster;
    }

    public void setDivisionMaster(int divisionMaster) {
        this.divisionMaster = divisionMaster;
    }

    public int getDivisionPosition() {
        return divisionPosition;
    }

    public void setDivisionPosition(int divisionPosition) {
        this.divisionPosition = divisionPosition;
    }


    // Getter Methods

    public String getId() {
        return id;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombres() {
        return nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCupoAutorizado() {
        return cupoAutorizado;
    }

    public String getCupoUtilizado() {
        return cupoUtilizado;
    }

    public String getCupoDisponible() {
        return cupoDisponible;
    }


    // Setter Methods

    public void setId(String id) {
        this.id = id;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public void setCupoAutorizado(String cupoAutorizado) {
        this.cupoAutorizado = cupoAutorizado;
    }

    public void setCupoUtilizado(String cupoUtilizado) {
        this.cupoUtilizado = cupoUtilizado;
    }

    public void setCupoDisponible(String cupoDisponible) {
        this.cupoDisponible = cupoDisponible;
    }


}
