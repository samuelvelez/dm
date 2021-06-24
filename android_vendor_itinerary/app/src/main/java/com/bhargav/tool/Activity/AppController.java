package com.bhargav.tool.Activity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import com.bhargav.tool.ModelClass.ABC;
import com.bhargav.tool.ModelClass.Clientes;
import com.bhargav.tool.ModelClass.ClientesUtils;
import com.bhargav.tool.ModelClass.DetallePlanificacions;
import com.bhargav.tool.ModelClass.FormasPago;
import com.bhargav.tool.ModelClass.Planificaciones;
import com.bhargav.tool.ModelClass.RestClient;
import com.bhargav.tool.Printer.MainActivity;
import com.bhargav.tool.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppController extends Application {

    Context context = AppController.this;
    ArrayList<ClientesUtils> clientesData = new ArrayList<>();
    ArrayList<String> nombreFormaPago = new ArrayList<String>();
    public Handler handler;
    public Utils shareprefrence;
    ArrayList<ClientesUtils> detallePlanificacionsData = new ArrayList<>();

    ArrayList<ClientesUtils> detallePlanificacionsDataNotUp = new ArrayList<>();
    boolean susss = false;


    public void onCreate() {
        super.onCreate();

        this.shareprefrence = new Utils(this);

        if (this.shareprefrence.sharedPreferences.getBoolean("id", false)) {
            this.handler = new Handler();
            this.handler.postDelayed(new AppController.runnable(), 0);
            return;
        }

    }

    public class runnable implements Runnable {
        public runnable() {
        }

        public void run() {
            if (Utils.isOnline(context)) {
                dataCall();
                secondApiCall();

                boolean uploadSucsees = false;

                if (Utils.getData(context, "UploadDetallePlanifi") != null) {
                    Gson gson2 = new Gson();
                    detallePlanificacionsData = gson2.fromJson(Utils.getData(context, "UploadDetallePlanifi"),
                            new TypeToken<ArrayList<ClientesUtils>>() {
                            }.getType());

                    for (int position = 0; detallePlanificacionsData.size() > position; position++) {
                        uploadSucsees = apiCallForUpdateData(position);

                        if (uploadSucsees) {
                            detallePlanificacionsDataNotUp.add(detallePlanificacionsData.get(position));
                        }
                    }
                }

                Utils.saveData(context, "UploadDetallePlanifi", null);
                Gson gson2 = new Gson();
                String json = gson2.toJson(detallePlanificacionsDataNotUp);
                Utils.saveData(context, "UploadDetallePlanifi", json);

            }
        }
    }

    private boolean apiCallForUpdateData(int position) {

        int division_position = detallePlanificacionsData.get(position).getDivisionPosition();

        Call<ABC> call = RestClient.post().registrarReciboCobroForUpload(
                Utils.getData(context, "Personal"),
                detallePlanificacionsData.get(position).getCodigo(),
                String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getFormaPogoPosition()),
                "OK",
                String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getBillTotal()),
                String.valueOf(detallePlanificacionsData.get(position).getDivisionPosition()),
                String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getCuentaPosition()),
                String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getDocumentNumber()),
                detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getInvoiceNoForUpdate(),    //hhmmss
                detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getDateForUpdate());  // aaj ni tarikh

        call.enqueue(new Callback<ABC>() {
            @Override
            public void onResponse(Call<ABC> call, Response<ABC> response) {


                if (response.isSuccessful()) {
                    ABC datas = response.body();

                    if (datas.getMensaje().equals("OK")) {
                        ArrayList<ClientesUtils> detallePlanificacionsDataTemp = new ArrayList<>();

                        for (int i = 0; detallePlanificacionsData.size() > i; i++) {
                            if (position != i) {
                                detallePlanificacionsDataTemp.add(detallePlanificacionsData.get(i));
                            }
                        }
                        detallePlanificacionsData = detallePlanificacionsDataTemp;

                        susss = true;

                    } else {
                        Toast.makeText(context, datas.getError(), Toast.LENGTH_SHORT).show();
                        susss = false;
                    }

                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    susss = false;
                }


            }

            @Override
            public void onFailure(Call<ABC> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                susss = false;
            }
        });
        return susss;
    }

    private void dataCall() {


        Call<Clientes> call = RestClient.post().clientsData("");
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {


                if (response.isSuccessful()) {
                    Clientes datas = response.body();

                    for (int i = 0; i < datas.lsClientes.size(); i++) {
                        clientesData.add(datas.lsClientes.get(i));
                    }

                    Gson gson = new Gson();

                    String json = gson.toJson(clientesData);

                    Utils.saveData(context, "CliantData", json);
                }


            }

            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void secondApiCall() {
        Call<FormasPago> call = RestClient.post().formasPago();

        call.enqueue(new Callback<FormasPago>() {
            @Override
            public void onResponse(Call<FormasPago> call, Response<FormasPago> response) {

                if (response.isSuccessful()) {
                    FormasPago datas = response.body();

                    if (datas.getMensaje().equals("OK")) {

                        for (int i = 0; i < datas.lsDafFormasPagos.size(); i++) {
                            nombreFormaPago.add(datas.lsDafFormasPagos.get(i).getNombreFormaPago());
                        }

                        Gson gson = new Gson();

                        String json = gson.toJson(nombreFormaPago);

                        Utils.saveData(context, "FormaPogo", json);

                    } else {

                        Toast.makeText(context, datas.getMensaje(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<FormasPago> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
