package com.bhargav.tool.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bhargav.tool.ModelClass.ABC;
import com.bhargav.tool.ModelClass.Clientes;
import com.bhargav.tool.ModelClass.ClientesUtils;
import com.bhargav.tool.ModelClass.DetallePlanificacions;
import com.bhargav.tool.ModelClass.RestClient;
import com.bhargav.tool.Printer.MainActivity;
import com.bhargav.tool.R;
import com.bhargav.tool.Utils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText txt_cliente, ed_total, et_documento;
    Context context = FormActivity.this;
    Spinner spinner_forma_pago, spinner_division, spinner_cuenta;
    ArrayList<String> nombreFormaPago = new ArrayList<String>();
    ArrayList<String> divisionesData = new ArrayList<String>();
    ArrayList<String> comNumerosCuentasData = new ArrayList<String>();
    ImageView cheque_date_img, entrega_date_img;
    EditText cheque_date, entrega_date;
    Button btn_ver_detalle, btn_print;
    ArrayList<ClientesUtils> detallePlanificacionsData = new ArrayList<>();
    int position;
    int division_position;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        position = Integer.parseInt(Utils.getData(context, "CUST_NO"));

        verifystoragepermissions(this);

        findView();

        Gson gson = new Gson();
        detallePlanificacionsData = gson.fromJson(Utils.getData(context, "SaveDetallePlanifi"),
                new TypeToken<ArrayList<ClientesUtils>>() {
                }.getType());

        division_position = detallePlanificacionsData.get(position).getDivisionPosition();

        secondApiCall();
        callIsclianta();
        ComNumerosCuentas();
        allSetText();

        btn_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_documento.getText().toString().equals("")) {
                    et_documento.setError("Documento Empty");
                } else if (String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getBillTotal()).equals("0")) {
                    ed_total.setError("Total Empty");
                } else if (cheque_date.getText().toString().equals("")) {
                    cheque_date.setError("Fecha Cheque Empty");
                } else if (entrega_date.getText().toString().equals("")) {
                    entrega_date.setError("Fetcha Entrega Empty");
                } else {
                    screenshot(getWindow().getDecorView().getRootView(), "result");
                }
            }
        });
    }

    private void ComNumerosCuentas() {
        for (int j = 0; detallePlanificacionsData.get(position).lsComNumerosCuentas.size() > j; j++) {
            comNumerosCuentasData.add(detallePlanificacionsData.get(position).lsComNumerosCuentas.get(j).getNumeroCuenta());
        }


        ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, comNumerosCuentasData);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cuenta.setAdapter(ad);

        spinner_cuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int positionSpin, long id) {
                detallePlanificacionsData.get(position)
                        .lsDivisiones.get(division_position)
                        .setCuentaPosition(positionSpin);
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }

        });
        spinner_cuenta.setSelection(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getCuentaPosition());


    }


    private void callIsclianta() {
        for (int j = 0; detallePlanificacionsData.get(position).lsDivisiones.size() > j; j++) {
            divisionesData.add(detallePlanificacionsData.get(position).lsDivisiones.get(j).getNombreDivision());
        }


        ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, divisionesData);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_division.setAdapter(ad);

        spinner_division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int positionSpinnnn, long id) {
                detallePlanificacionsData.get(position).setDivisionPosition(positionSpinnnn);

                if (positionSpinnnn != division_position) {
                    Intent intent = new Intent(context, FormActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        spinner_division.setSelection(detallePlanificacionsData.get(position).getDivisionPosition());


    }

    @Override
    protected void onPause() {
        super.onPause();

        Gson gson2 = new Gson();
        String json = gson2.toJson(detallePlanificacionsData);
        Utils.saveData(context, "SaveDetallePlanifi", json);
    }


    private void allSetText() {

        txt_cliente.setEnabled(false);
        txt_cliente.setText(detallePlanificacionsData.get(position).getNombres());

        cheque_date.setEnabled(false);
        cheque_date.setText(detallePlanificacionsData.get(position)
                .lsDivisiones.get(division_position).getFechaCheque());

        entrega_date.setEnabled(false);
        entrega_date.setText(detallePlanificacionsData.get(position)
                .lsDivisiones.get(division_position).getFechaEntrega());

        ed_total.setEnabled(false);
        ed_total.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getBillTotal()));

        String document_no = String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getDocumentNumber());
        if (document_no.equals("0")) {
            et_documento.setText("");
        } else {
            et_documento.setText(document_no);
        }

        cheque_date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datepicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar fromCal = Calendar.getInstance();
                        fromCal.setTimeInMillis(0);
                        fromCal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = dateFormat.format(fromCal.getTime());
                        cheque_date.setText(dateString);
                        detallePlanificacionsData.get(position)
                                .lsDivisiones.get(division_position).setFechaCheque(dateString);

                    }
                }, year, month, day);
                datepicker.show();

            }
        });

        btn_ver_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_documento.getText().toString().equals("")) {
                    et_documento.setText("");
                } else {
                    detallePlanificacionsData.get(position).lsDivisiones.get(division_position)
                            .setDocumentNumber(Integer.parseInt(et_documento.getText().toString()));
                }

                detallePlanificacionsData.get(position).lsDivisiones.get(division_position)
                        .setBillTotal(Integer.parseInt(ed_total.getText().toString()));

                Intent intent = new Intent(context, VerDetalleActivity.class);
                startActivity(intent);
                finish();
            }
        });

        entrega_date_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                DatePickerDialog datepicker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar fromCal = Calendar.getInstance();
                        fromCal.setTimeInMillis(0);
                        fromCal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String dateString = dateFormat.format(fromCal.getTime());
                        entrega_date.setText(dateString);
                        detallePlanificacionsData.get(position)
                                .lsDivisiones.get(division_position).setFechaEntrega(dateString);
                    }
                }, year, month, day);
                datepicker.show();

            }
        });
    }

    protected File screenshot(View view, String filename) {
        Date date = new Date();

        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            // Initialising the directory of storage
            String dirpath = Environment.getExternalStorageDirectory() + "";
            File file = new File(dirpath);
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }

            // File name
            String path = dirpath + "/" + filename + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            File imageurl = new File(path);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();

            Utils.saveData(FormActivity.this, "PRINT_URL", String.valueOf(imageurl));

            uploadData();

            return imageurl;

        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void uploadData() {
        SimpleDateFormat sdf = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        detallePlanificacionsData.get(position)
                .lsDivisiones.get(division_position).setInvoiceNoForUpdate(currentTime);

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf2.format(new Date());
        detallePlanificacionsData.get(position)
                .lsDivisiones.get(division_position).setDateForUpdate(currentDate);

        if (et_documento.getText().toString().equals("")) {
            et_documento.setText("");
        } else {
            detallePlanificacionsData.get(position).lsDivisiones.get(division_position)
                    .setDocumentNumber(Integer.parseInt(et_documento.getText().toString()));
        }

        detallePlanificacionsData.get(position).lsDivisiones.get(division_position)
                .setBillTotal(Integer.parseInt(ed_total.getText().toString()));

        if (Utils.isOnline(context)) {
            apiCallForUpdateData();
        } else {
            ArrayList<ClientesUtils> detallePlanificacionsDataTemp = new ArrayList<>();
            ArrayList<ClientesUtils> detallePlanificacionsDataTempUpload = new ArrayList<>();

            if (Utils.getData(context, "UploadDetallePlanifi") != null) {
                Gson gson = new Gson();
                detallePlanificacionsDataTempUpload = gson.fromJson(Utils.getData(context, "UploadDetallePlanifi"),
                        new TypeToken<ArrayList<ClientesUtils>>() {
                        }.getType());
            }

            for (int i = 0; detallePlanificacionsData.size() > i; i++) {
                if (position != i) {
                    detallePlanificacionsDataTemp.add(detallePlanificacionsData.get(i));
                } else {
                    detallePlanificacionsDataTempUpload.add(detallePlanificacionsData.get(i));

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(detallePlanificacionsDataTempUpload);
                    Utils.saveData(context, "UploadDetallePlanifi", json3);
                }
            }

            detallePlanificacionsData = detallePlanificacionsDataTemp;
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void apiCallForUpdateData() {

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

                        Intent intent = new Intent(context, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, datas.getError(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, "Something Went Wrong...", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ABC> call, Throwable t) {
                Toast.makeText(context, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void findView() {
        btn_print = findViewById(R.id.btn_print);
        cheque_date_img = findViewById(R.id.cheque_date_img);
        txt_cliente = findViewById(R.id.txt_cliente);
        spinner_forma_pago = findViewById(R.id.spinner_forma_pago);
        spinner_division = findViewById(R.id.spinner_division);
        spinner_cuenta = findViewById(R.id.spinner_cuenta);
        cheque_date = findViewById(R.id.cheque_date);
        entrega_date = findViewById(R.id.entrega_date);
        entrega_date_img = findViewById(R.id.entrega_date_img);
        btn_ver_detalle = findViewById(R.id.btn_ver_detalle);
        ed_total = findViewById(R.id.ed_total);
        et_documento = findViewById(R.id.et_documento);
    }

    private void secondApiCall() {

        Gson gson = new Gson();
        nombreFormaPago = gson.fromJson(Utils.getData(context, "FormaPogo"),
                new TypeToken<ArrayList<String>>() {
                }.getType());

        spinner_forma_pago.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) context);

        ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, nombreFormaPago);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_forma_pago.setAdapter(ad);

        spinner_forma_pago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int positionSpin, long id) {
                detallePlanificacionsData.get(position).lsDivisiones.get(division_position).setFormaPogoPosition(positionSpin);
            }

            public void onNothingSelected(AdapterView<?> arg0) {// do nothing
            }
        });
        spinner_forma_pago.setSelection(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getFormaPogoPosition());

    }


    @Override
    public void onBackPressed() {
        if (et_documento.getText().toString().equals("")) {
            et_documento.setText("");
        } else {
            detallePlanificacionsData.get(position).lsDivisiones.get(division_position)
                    .setDocumentNumber(Integer.parseInt(et_documento.getText().toString()));
        }

        detallePlanificacionsData.get(position).lsDivisiones.get(division_position)
                .setBillTotal(Integer.parseInt(ed_total.getText().toString()));
        Intent intent = new Intent(FormActivity.this, PlanningActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int positionSpinner, long id) {
//        Toast.makeText(getApplicationContext(), nombreFormaPago[positionSpinner], Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static void verifystoragepermissions(Activity activity) {

        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGe);
        }
    }
}