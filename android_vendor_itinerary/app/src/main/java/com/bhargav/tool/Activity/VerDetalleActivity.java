package com.bhargav.tool.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bhargav.tool.ModelClass.ClientesUtils;
import com.bhargav.tool.ModelClass.DetallePlanificacions;
import com.bhargav.tool.ModelClass.FacturasXCobrar;
import com.bhargav.tool.R;
import com.bhargav.tool.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class VerDetalleActivity extends AppCompatActivity {

    ArrayList<ClientesUtils> detallePlanificacionsData = new ArrayList<>();
    int position, division_position;
    Context context = VerDetalleActivity.this;
    ListView gridList;
    int colorSet = 0;
    TextView totalll;
    Button btn_aceptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_detalle);

        position = Integer.parseInt(Utils.getData(context, "CUST_NO"));
        gridList = (ListView) findViewById(R.id.listview_for_grid);
        totalll = findViewById(R.id.totalll);
        btn_aceptor = findViewById(R.id.btn_aceptor);

        Gson gson = new Gson();
        detallePlanificacionsData = gson.fromJson(Utils.getData(context, "SaveDetallePlanifi"),
                new TypeToken<ArrayList<ClientesUtils>>() {
                }.getType());

        division_position = detallePlanificacionsData.get(position).getDivisionPosition();

        System.out.println("division_position :" + division_position);

        AdapterForList adapterForList = new AdapterForList();
        gridList.setAdapter(adapterForList);

        btn_aceptor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        Gson gson2 = new Gson();
        String json = gson2.toJson(detallePlanificacionsData);
        Utils.saveData(context, "SaveDetallePlanifi", json);
    }

    public class AdapterForList extends BaseAdapter {


        public AdapterForList() {
        }

        @Override
        public int getCount() {
            return detallePlanificacionsData.get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.size();
        }

        @Override
        public Object getItem(int i) {
            return detallePlanificacionsData.get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View v, ViewGroup viewGroup) {
            LayoutInflater inflter = (LayoutInflater.from(context));
            v = inflter.inflate(R.layout.ver_data_view, null);

            ViewHolderForList viewHolderForList = new ViewHolderForList(v);

            if (detallePlanificacionsData
                    .get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i).getIsChecked() == 0) {
                viewHolderForList.checkboxx.setChecked(false);
            } else {
                viewHolderForList.checkboxx.setChecked(true);

            }

            viewHolderForList.checkboxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i).setIsChecked(1);

                        int total = detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getBillTotal();
                        total = total + Math.round(detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i).getTotalFactura());
                        detallePlanificacionsData.get(position).lsDivisiones.get(division_position).setBillTotal(total);
                        abcd();
                    } else {
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i).setIsChecked(0);

                        int total = detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getBillTotal();
                        total = total - Math.round(detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i).getTotalFactura());
                        detallePlanificacionsData.get(position).lsDivisiones.get(division_position).setBillTotal(total);
                        abcd();
                    }

                }
            });

            viewHolderForList.verid.setText(String.valueOf(detallePlanificacionsData
                    .get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i).getSecuenciaComprobante()));

            viewHolderForList.facrura.setText(String.valueOf(detallePlanificacionsData
                    .get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i).getNumeroIdentificacionCliente()));

            viewHolderForList.saldo.setText(String.valueOf(detallePlanificacionsData
                    .get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i).getTotalFactura()));

            if (colorSet == 0) {
                viewHolderForList.plate.setBackgroundColor(Color.parseColor("#DADADA"));
                colorSet++;
            } else {
                colorSet--;
                viewHolderForList.plate.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            return v;

        }
    }

    private void abcd() {
        totalll.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getBillTotal()));
    }

    private class ViewHolderForList {
        TextView verid, facrura, saldo;
        LinearLayout plate;
        CheckBox checkboxx;

        public ViewHolderForList(View view) {
            saldo = view.findViewById(R.id.saldo);
            facrura = view.findViewById(R.id.facrura);
            verid = view.findViewById(R.id.verid);
            plate = view.findViewById(R.id.plate);
            checkboxx = view.findViewById(R.id.checkboxx);
        }
    }

    @Override
    public void onBackPressed() {
        Utils.saveData(context, "TOTAL", String.valueOf(0));
        Intent intent = new Intent(VerDetalleActivity.this, FormActivity.class);
        startActivity(intent);
        finish();
    }

}