package com.bhargav.tool.ModelClass;


import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiService {

    String BASEURL = "https://sistemadm.delmonte.com.ec/DelMonteComercial/ServicioWS/";

    @GET("clientes")
    Call<Clientes> clientsData(@Query("arg0") String arg0);

    @GET("clientesXCodigo")
    Call<Clientes> clientsDataForPlanning(@Query("arg0") String arg0);

    @GET("registrarReciboCobro")
    Call<ABC> Pagos(@Query("arg0") String arg0);

    @POST("registrarReciboCobro")
    Call<ABC> registrarReciboCobroForUpload(
            @Body JsonObject dataCall);

//    @POST("registrarReciboCobro")
//    Call<ABC> registrarReciboCobroForUpload(
//            @Query("arg1") String arg1,
//            @Query("arg2") String arg2,
//            @Query("arg3") String arg3,
//            @Query("arg4") String arg4,
//            @Query("arg5") String arg5,
//            @Query("arg6") String arg6,
//            @Query("arg7") String arg7,
//            @Query("arg8") String arg8,
//            @Query("arg9") String arg9,
//            @Query("arg10") String arg10);

    @GET("formasPago")
    Call<FormasPago> formasPago();

    @POST("autenticacionUsuario")
    Call<Login> login(@Query("user") String user,
                      @Query("clave") String clave);

    @GET("planificaciones")
    Call<Planificaciones> planificaciones(@Query("arg0") String arg0);
}

