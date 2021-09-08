package com.bhargav.tool.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargav.tool.ModelClass.Clientes;
import com.bhargav.tool.ModelClass.ClientesUtils;
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
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientsSerchActivity extends AppCompatActivity {

    Context context = ClientsSerchActivity.this;
    ArrayList<ClientesUtils> clientesData = new ArrayList<>();
    RecyclerView simpleList;
    EditText data_search;
    CustomAdapter customAdapter;
    ArrayList<ClientesUtils> SaveDetallePlanifi = new ArrayList<>();
    TextView nodataerror;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        data_search = findViewById(R.id.data_search);
        nodataerror = findViewById(R.id.nodataerror);
        simpleList = (RecyclerView) findViewById(R.id.simpleListView);
        simpleList.setLayoutManager(new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false));

        //  jfhjdmnhcddh

        if (Utils.getData(context, "SaveDetallePlanifi") != null) {
            Gson gson = new Gson();
            SaveDetallePlanifi = gson.fromJson(Utils.getData(context, "SaveDetallePlanifi"),
                    new TypeToken<ArrayList<ClientesUtils>>() {
                    }.getType());
        }


        Gson gson2 = new Gson();
        clientesData = gson2.fromJson(Utils.getData(context, "CliantData"), new TypeToken<List<ClientesUtils>>() {
        }.getType());

        customAdapter = new CustomAdapter(clientesData);
        simpleList.setNestedScrollingEnabled(false);
        simpleList.setAdapter(customAdapter);


        if (Utils.isOnline(context)) {
            if (clientesData.size() == 0) {
                dataCallFirstTime("");
            } else {
                dataCall("");
            }
        } else {
            if (clientesData.size() == 0) {
                Toast.makeText(context, "Encienda su Internet para continuar con la búsqueda", Toast.LENGTH_SHORT).show();
            }
        }

        data_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                //after the change calling the method and passing the search input
                //    customAdapter.getFilter().filter(data_search.getText().toString());
                if (Utils.isOnline(context)) {
                    dataCall(data_search.getText().toString());
                } else {
                    Toast.makeText(context, "Encienda su Internet para continuar con la búsqueda", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void dataCall(String search_word) {

        Call<Clientes> call = RestClient.post().clientsData(search_word);
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {

                clientesData.clear();

                if (response.isSuccessful()) {
                    Clientes datas = response.body();

                    for (int i = 0; i < datas.lsClientes.size(); i++) {
                        clientesData.add(datas.lsClientes.get(i));
                    }

                    Gson gson = new Gson();

                    String json = gson.toJson(clientesData);

                    customAdapter = new CustomAdapter(clientesData);
                    simpleList.setNestedScrollingEnabled(false);
                    simpleList.setAdapter(customAdapter);

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

    private void dataCallFirstTime(String search_word) {

        final Dialog startdialog = new Dialog(context);
        startdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Call<Clientes> call = RestClient.post().clientsData(search_word);
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {

                clientesData.clear();

                if (response.isSuccessful()) {
                    Clientes datas = response.body();

                    for (int i = 0; i < datas.lsClientes.size(); i++) {
                        clientesData.add(datas.lsClientes.get(i));
                    }

                    Gson gson = new Gson();

                    String json = gson.toJson(clientesData);

                    customAdapter = new CustomAdapter(clientesData);
                    simpleList.setNestedScrollingEnabled(false);
                    simpleList.setAdapter(customAdapter);
                    startdialog.dismiss();
                    Utils.saveData(context, "CliantData", json);
                } else {
                    startdialog.dismiss();
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
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


    public class CustomAdapter extends com.bhargav.tool.Activity.CustomAdapter {

        ArrayList<ClientesUtils> venueDataFinal;
        ArrayList<ClientesUtils> venueDataFinalFull;

        public CustomAdapter(ArrayList<ClientesUtils> venueData) {
            this.venueDataFinal = venueData;
            venueDataFinalFull = new ArrayList<>(venueData);
        }


        @Override
        public CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);

            ViewHolder viewHolder = null;
            View contactView = inflater.inflate(R.layout.cliant_data_view, parent, false);
            viewHolder = new ViewHolder(contactView);
            return viewHolder;

        }


        @Override
        public void onBindViewHolder(CustomAdapter.ViewHolder holder, final int position) {


            holder.cust_id.setText(venueDataFinal.get(position).getCodigo());
            holder.cust_name.setText(venueDataFinal.get(position).getNombres());
            holder.id.setText(venueDataFinal.get(position).getCedula());

            holder.select_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String Codigo = venueDataFinal.get(position).getCodigo();

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    if (Utils.isOnline(context)) {
                        builder.setMessage("Desea Agregar el Cliente a la planificación diaria?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Date c = Calendar.getInstance().getTime();
                                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                                        String formattedDate = df.format(c);

                                        Utils.saveData(context, "DataSaveDate", formattedDate);
                                        callPlanningDataCliant(Codigo);

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
                    } else {
                        Toast.makeText(context, "Lo siento, hubo un error al obtener datos de Internet.\n ¡Red no disponible!", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }

        @Override
        public int getItemCount() {
            return venueDataFinal.size();
        }

        public Filter getFilter() {
            return ClientesUtils;
        }

        private Filter ClientesUtils = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<ClientesUtils> filteredList = new ArrayList<>();
                if (constraint == null || constraint.length() == 0) {
                    filteredList.addAll(venueDataFinalFull);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (ClientesUtils item : venueDataFinalFull) {
                        if (item.getCodigo().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                        if (item.getCedula().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                        if (item.getNombres().toLowerCase().contains(filterPattern)) {
                            filteredList.add(item);
                        }
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                venueDataFinal.clear();
                venueDataFinal.addAll((List) results.values);
                notifyDataSetChanged();
            }
        };

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView cust_id, cust_name, id;
            RelativeLayout select_view;

            public ViewHolder(View view) {
                super(view);
                cust_id = view.findViewById(R.id.cust_id);
                cust_name = view.findViewById(R.id.cust_name);
                id = view.findViewById(R.id.id);
                select_view = view.findViewById(R.id.select_view);

            }
        }
    }

    private void callPlanningDataCliant(String search_word) {

        final Dialog startdialog = new Dialog(context);
        startdialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        Call<Clientes> call = RestClient.post().clientsDataForPlanning(search_word);
        call.enqueue(new Callback<Clientes>() {
            @Override
            public void onResponse(Call<Clientes> call, Response<Clientes> response) {

                if (response.isSuccessful()) {
                    Clientes datas = response.body();

                    if (SaveDetallePlanifi != null) {
                        for (int i = 0; SaveDetallePlanifi.size() > i; i++) {
                            if (SaveDetallePlanifi.get(i).getCodigo().equals(search_word)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Estos datos ya existen.\n Si desea crear un nuevo cliente dice que sí.")
                                        .setCancelable(false)
                                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                SaveDetallePlanifi.add(datas.lsClientes.get(0));

                                                Gson gson2 = new Gson();
                                                String json = gson2.toJson(SaveDetallePlanifi);
                                                Utils.saveData(context, "SaveDetallePlanifi", json);

                                                Intent intent1 = new Intent(context, PlanningActivity.class);
                                                startActivity(intent1);
                                                finish();
                                                startdialog.dismiss();

                                            }
                                        })
                                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                startdialog.dismiss();
                                            }
                                        });
                                AlertDialog alert = builder.create();
                                alert.setTitle("Alert");
                                alert.show();
                                startdialog.dismiss();
                                return;
                            }
                        }
                    }

                    SaveDetallePlanifi.add(datas.lsClientes.get(0));

                    Gson gson2 = new Gson();
                    String json = gson2.toJson(SaveDetallePlanifi);
                    Utils.saveData(context, "SaveDetallePlanifi", json);

                    Intent intent1 = new Intent(context, PlanningActivity.class);
                    startActivity(intent1);
                    finish();

                    startdialog.dismiss();

                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                    startdialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Clientes> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                startdialog.dismiss();
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(context, PlanningActivity.class);
        startActivity(intent);
        finish();
    }
}