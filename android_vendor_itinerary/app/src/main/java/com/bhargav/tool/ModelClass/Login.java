package com.bhargav.tool.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Login {

    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("objeto")
    @Expose
    private String objeto;


    // Getter Methods

    public String getError() {
        return error;
    }

    public String getObjeto() {
        return objeto;
    }

    // Setter Methods

    public void setError(String error) {
        this.error = error;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }
}
