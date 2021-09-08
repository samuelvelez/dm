package com.bhargav.tool.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

        totalll.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getValor()));

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
                    .lsFacturasXCobrar.get(i).getValor()== 0) {
                viewHolderForList.valor.setText("");
            } else {
                viewHolderForList.valor.setText(String.valueOf(detallePlanificacionsData
                        .get(position)
                        .lsDivisiones.get(division_position)
                        .lsFacturasXCobrar.get(i).getValor()));
            }

            if (detallePlanificacionsData
                    .get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i).getIsChecked() == 0) {
                viewHolderForList.checkboxx.setChecked(false);
            } else {
                viewHolderForList.checkboxx.setChecked(true);

            }

            viewHolderForList.valor.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (String.valueOf(viewHolderForList.valor.getText()).equals("")) {
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i)
                                .setValor(0);
                    } else {
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i)
                                .setValor(Integer.parseInt(String.valueOf(viewHolderForList.valor.getText())));
                    }
                    abcd();
                }
            });

            viewHolderForList.checkboxx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i).setIsChecked(1);
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones
                                .get(division_position)
                                .setCantidad(detallePlanificacionsData
                                        .get(position)
                                        .lsDivisiones
                                        .get(division_position)
                                        .getCantidad() + 1);
                        abcd();
                    } else {
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones.get(division_position)
                                .lsFacturasXCobrar.get(i).setIsChecked(0);
                        detallePlanificacionsData
                                .get(position)
                                .lsDivisiones
                                .get(division_position)
                                .setCantidad(detallePlanificacionsData
                                        .get(position)
                                        .lsDivisiones
                                        .get(division_position)
                                        .getCantidad() - 1);
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

        detallePlanificacionsData.get(position).lsDivisiones.get(division_position).setValor(0);

        for (int i = 0;
             detallePlanificacionsData
                     .get(position)
                     .lsDivisiones.get(division_position)
                     .lsFacturasXCobrar.size() > i; i++) {

            if (detallePlanificacionsData
                    .get(position)
                    .lsDivisiones.get(division_position)
                    .lsFacturasXCobrar.get(i).getIsChecked() == 1) {

                String.valueOf(detallePlanificacionsData
                        .get(position)
                        .lsDivisiones.get(division_position)
                        .lsFacturasXCobrar.get(i)
                        .getValor());
                int total = detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getValor();
                total = total + Integer.parseInt(String.valueOf(detallePlanificacionsData
                        .get(position)
                        .lsDivisiones.get(division_position)
                        .lsFacturasXCobrar.get(i)
                        .getValor()));
                detallePlanificacionsData.get(position).lsDivisiones.get(division_position).setValor(total);
            }

        }

        totalll.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getValor()));
    }

    private class ViewHolderForList {
        TextView verid, facrura, saldo;
        LinearLayout plate;
        CheckBox checkboxx;
        EditText valor;

        public ViewHolderForList(View view) {
            saldo = view.findViewById(R.id.saldo);
            facrura = view.findViewById(R.id.facrura);
            verid = view.findViewById(R.id.verid);
            plate = view.findViewById(R.id.plate);
            checkboxx = view.findViewById(R.id.checkboxx);
            valor = view.findViewById(R.id.valor);

        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(VerDetalleActivity.this, FormActivity.class);
        startActivity(intent);
        finish();
    }

}