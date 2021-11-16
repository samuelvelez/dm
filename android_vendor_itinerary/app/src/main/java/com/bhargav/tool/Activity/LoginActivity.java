package com.bhargav.tool.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bhargav.tool.ModelClass.Clientes;
import com.bhargav.tool.ModelClass.ClientesUtils;
import com.bhargav.tool.ModelClass.DataLogin;
import com.bhargav.tool.ModelClass.DetallePlanificacions;
import com.bhargav.tool.ModelClass.FormasPago;
import com.bhargav.tool.ModelClass.Login;
import com.bhargav.tool.ModelClass.Planificaciones;
import com.bhargav.tool.ModelClass.RestClient;
import com.bhargav.tool.R;
import com.bhargav.tool.Utils;
import com.google.gson.Gson;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText et_user_name, et_password;
    Context context = LoginActivity.this;
    public Utils shareprefrence;
    public Handler handler;

    ArrayList<ClientesUtils> SaveDetallePlanifi = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.shareprefrence = new Utils(this);


        if (this.shareprefrence.sharedPreferences.getBoolean("id", false)) {
            this.handler = new Handler();
            this.handler.postDelayed(new runnable(), 0);
            return;
        }

        btn_login = findViewById(R.id.btn_login);
        et_user_name = findViewById(R.id.et_user_name);
        et_password = findViewById(R.id.et_password);

        et_user_name.setText("MMUNOZ");
        et_password.setText("123456");

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_user_name.getText().toString().equals("")) {
                    et_user_name.setError("Vacía");
                } else if (et_password.getText().toString().equals("")) {
                    et_password.setError("Vacía");
                } else {

                    if (Utils.isOnline(context)) {
                        login(et_user_name.getText().toString(), et_password.getText().toString());
                    } else {
                        Toast.makeText(context, "Encienda su Internet para continuar con la búsqueda", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    public class runnable implements Runnable {
        public runnable() {
        }

        public void run() {
            startActivity(new Intent(getApplicationContext(), PlanningActivity.class));
            finish();
        }
    }

    private void login(String user_name, String password) {

        final Dialog startdialog = new Dialog(context);
        startdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Call<Login> call = RestClient.post().login(user_name, password);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {

                Login datas = response.body();

                if (datas.getObjeto().equals("null")) {
                    Toast.makeText(context, datas.getError(), Toast.LENGTH_SHORT).show();
                    startdialog.dismiss();
                    return;
                }

                Gson gson = new Gson();
                DataLogin dataLogin = gson.fromJson(datas.getObjeto(), DataLogin.class);

                Utils.saveData(context, "Personal", String.valueOf(dataLogin.getSecuenciaPersonal()));
                Utils.saveData(context, "INVOICENO", String.valueOf(dataLogin.getInvoice_no()));
                Utils.saveData(context, "USERNAME", String.valueOf(dataLogin.getNombrePersonal()));

                dataCall();
                secondApiCall();

                planificationApiCall();

                shareprefrence.sharedPreferences.edit().putBoolean("id", true).apply();

                startdialog.dismiss();

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                startdialog.dismiss();
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        startdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        startdialog.setContentView(R.layout.custom_progressdialog);
        startdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        startdialog.setCancelable(false);
        startdialog.setCanceledOnTouchOutside(false);

        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) startdialog.findViewById(R.id.avi);
        avi.smoothToShow();
        startdialog.show();

    }

    private void planificationApiCall() {
        final Dialog startdialog = new Dialog(context);
        startdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Call<Planificaciones> call = RestClient.post().planificaciones(Utils.getData(context, "Personal"));
        call.enqueue(new Callback<Planificaciones>() {
            @Override
            public void onResponse(Call<Planificaciones> call, Response<Planificaciones> response) {

                if (response.isSuccessful()) {
                    Planificaciones datas = response.body();

                    if (datas.getMensaje().equals("OK")) {

                        for (int i = 0; i < datas.lsDetallePlanificacions.size(); i++) {
                            SaveDetallePlanifi.add(datas.lsDetallePlanificacions.get(i).lsClientesUtils.get(0));
                        }

                        Gson gson = new Gson();

                        String json = gson.toJson(SaveDetallePlanifi);

                        Utils.saveData(context, "SaveDetallePlanifi", json);

                    } else {

                        Toast.makeText(context, datas.getMensaje(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }

                startActivity(new Intent(getApplicationContext(), PlanningActivity.class));
                finish();

                startdialog.dismiss();

            }

            @Override
            public void onFailure(Call<Planificaciones> call, Throwable t) {
                startdialog.dismiss();
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

        startdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        startdialog.setContentView(R.layout.custom_progressdialog);
        startdialog.getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        startdialog.setCancelable(false);
        startdialog.setCanceledOnTouchOutside(false);

        AVLoadingIndicatorView avi = (AVLoadingIndicatorView) startdialog.findViewById(R.id.avi);
        avi.smoothToShow();
        startdialog.show();

    }

    private void secondApiCall() {
        ArrayList<String> nombreFormaPago = new ArrayList<String>();
        ArrayList<Float> codigoFormaPago = new ArrayList<Float>();

        Call<FormasPago> call = RestClient.post().formasPago();

        call.enqueue(new Callback<FormasPago>() {
            @Override
            public void onResponse(Call<FormasPago> call, Response<FormasPago> response) {

                if (response.isSuccessful()) {
                    FormasPago datas = response.body();

                    if (datas.getMensaje().equals("OK")) {

                        for (int i = 0; i < datas.lsDafFormasPagos.size(); i++) {
                            nombreFormaPago.add(datas.lsDafFormasPagos.get(i).getNombreFormaPago());
                            codigoFormaPago.add(datas.lsDafFormasPagos.get(i).getCodigoFormaPago());
                        }

                        Gson gson = new Gson();
                        String json = gson.toJson(nombreFormaPago);
                        Utils.saveData(context, "FormaPogo", json);

                        Gson gson2 = new Gson();
                        String json2 = gson2.toJson(codigoFormaPago);
                        Utils.saveData(context, "codigoFormaPago", json2);

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

    private void dataCall() {

        ArrayList<ClientesUtils> clientesData = new ArrayList<>();
        Call<Clientes> call = RestClient.post().clientsData(Utils.getData(context, "Personal"));
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
                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

}