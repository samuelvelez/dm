package com.bhargav.tool.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargav.tool.ModelClass.ABC;
import com.bhargav.tool.ModelClass.Clientes;
import com.bhargav.tool.ModelClass.ClientesUtils;
import com.bhargav.tool.ModelClass.DetallePlanificacions;
import com.bhargav.tool.ModelClass.Planificaciones;
import com.bhargav.tool.ModelClass.RestClient;
import com.bhargav.tool.R;
import com.bhargav.tool.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlanningActivity extends AppCompatActivity {

    ImageView btn_cliants_add;
    ArrayList<ClientesUtils> detallePlanificacionsDataSave = new ArrayList<>();
    ArrayList<ClientesUtils> ApidetallePlanificacionsDataSave = new ArrayList<>();


    Context context = PlanningActivity.this;
    RecyclerView simpleList;
    ListView gridList;
    TextView nodataerror;
    ImageView btn_logout, btn_sync;
    public Utils shareprefrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planning);

        this.shareprefrence = new Utils(this);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

        btn_cliants_add = findViewById(R.id.btn_cliants_add);
        nodataerror = findViewById(R.id.nodataerror);
        btn_logout = findViewById(R.id.btn_logout);
        btn_sync = findViewById(R.id.btn_sync);

        gridList = (ListView) findViewById(R.id.listview_for_grid);

        simpleList = findViewById(R.id.simpleListView);

        if (Utils.getData(context, "DataSaveDate") == null) {
            Utils.saveData(context, "DataSaveDate", formattedDate);
        }

        if (Utils.getData(context, "SaveDetallePlanifi") != null) {

            if (Utils.getData(context, "DataSaveDate").equals(formattedDate)) {
                Gson gson = new Gson();

                detallePlanificacionsDataSave = gson.fromJson(Utils.getData(context, "SaveDetallePlanifi"),
                        new TypeToken<ArrayList<ClientesUtils>>() {
                        }.getType());

                AdapterForList adapterForList = new AdapterForList(detallePlanificacionsDataSave);
                gridList.setAdapter(adapterForList);
            } else {
                Gson gson2 = new Gson();
                String json = gson2.toJson(detallePlanificacionsDataSave);
                Utils.saveData(context, "SaveDetallePlanifi", json);
            }
        }
        if (detallePlanificacionsDataSave.size() == 0) {
            nodataerror.setVisibility(View.VISIBLE);
        } else {
            nodataerror.setVisibility(View.GONE);
        }


        btn_cliants_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ClientsSerchActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btn_sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isOnline(context)) {

                    planificationApiCall();

                } else {
                    Toast.makeText(context, "Sin conexión a Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setMessage("¿Estás segura de cerrar sesión en esta aplicación?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Utils.saveData(context, "UploadDetallePlanifi", null);
                                Utils.saveData(context, "DataSaveDate", null);
                                Utils.saveData(context, "Personal", null);
                                Utils.saveData(context, "SaveDetallePlanifi", null);
                                Utils.saveData(context, "CUST_NO", null);
                                Utils.saveData(context, "PRINT_URL", null);

                                shareprefrence.sharedPreferences.edit().putBoolean("id", false).apply();

                                Intent intent = new Intent(context, LoginActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Alert");
                alert.show();


            }
        });

    }

    private void apiCallForUpdateData() {

        if (Utils.getData(context, "SaveDetallePlanifi") != null) {

            Gson gson33 = new Gson();

            ApidetallePlanificacionsDataSave = gson33.fromJson(Utils.getData(context, "ApiSaveDetallePlanifi"),
                    new TypeToken<ArrayList<ClientesUtils>>() {
                    }.getType());

            for (int i = 0; ApidetallePlanificacionsDataSave.size() > i; i++) {
                ArrayList<ClientesUtils> detallePlanificacionsDataTemp = new ArrayList<>();
                for (int j = 0; detallePlanificacionsDataSave.size() > j; j++) {
                    if (!ApidetallePlanificacionsDataSave.get(i).getCodigo()
                            .equals(detallePlanificacionsDataSave.get(j).getCodigo())) {
                        detallePlanificacionsDataTemp.add(detallePlanificacionsDataSave.get(j));
                    }
                }
                detallePlanificacionsDataSave = detallePlanificacionsDataTemp;
            }

            for (int i = 0; i < ApidetallePlanificacionsDataSave.size(); i++) {
                detallePlanificacionsDataSave.add(ApidetallePlanificacionsDataSave.get(i));
            }

            Gson gson = new Gson();
            String json = gson.toJson(detallePlanificacionsDataSave);
            Utils.saveData(context, "SaveDetallePlanifi", json);

            AdapterForList adapterForList = new AdapterForList(detallePlanificacionsDataSave);
            gridList.setAdapter(adapterForList);
        }
    }

    private void planificationApiCall() {

        ArrayList<ClientesUtils> detallePlanificacionsDataSaveNew = new ArrayList<>();

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
                            detallePlanificacionsDataSaveNew.add(datas.lsDetallePlanificacions.get(i).lsClientesUtils.get(0));
                        }

                        Gson gson = new Gson();

                        String json = gson.toJson(detallePlanificacionsDataSaveNew);

                        Utils.saveData(context, "ApiSaveDetallePlanifi", json);

                        apiCallForUpdateData();

                        startdialog.dismiss();
                    } else {
                        Toast.makeText(context, datas.getMensaje(), Toast.LENGTH_SHORT).show();
                        startdialog.dismiss();
                    }
                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    startdialog.dismiss();
                }
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


    public class AdapterForList extends BaseAdapter {

        ArrayList<ClientesUtils> detallePlanificacionsDataFinal = new ArrayList<>();

        public AdapterForList(ArrayList<ClientesUtils> detallePlanificacionsData) {
            this.detallePlanificacionsDataFinal = detallePlanificacionsData;
        }

        @Override
        public int getCount() {
            return detallePlanificacionsDataFinal.size();
        }

        @Override
        public Object getItem(int i) {
            return detallePlanificacionsDataFinal.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View v, ViewGroup viewGroup) {
            LayoutInflater inflter = (LayoutInflater.from(context));
            v = inflter.inflate(R.layout.planning_data_view, null);

            ViewHolderForList viewHolderForList = new ViewHolderForList(v);

            viewHolderForList.time.setText("12:00:00");
            viewHolderForList.cust_name.setText(detallePlanificacionsDataFinal.get(i).getNombres());
            viewHolderForList.list_view_next_page.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.saveData(context, "CUST_NO", String.valueOf(i));
                    Intent intent = new Intent(context, FormActivity.class);
                    startActivity(intent);
                    finish();
                }
            });

            return v;

        }
    }

    private class ViewHolderForList {
        TextView cust_name, time;
        RelativeLayout list_view_next_page;


        public ViewHolderForList(View view) {
            time = view.findViewById(R.id.time);
            cust_name = view.findViewById(R.id.cust_name);
            list_view_next_page = view.findViewById(R.id.list_view_next_page);
        }
    }

}