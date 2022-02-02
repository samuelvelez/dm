package com.bhargav.tool.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.util.Base64;
import java.io.ByteArrayOutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhargav.tool.ModelClass.ABC;
import com.bhargav.tool.ModelClass.ClientesUtils;
import com.bhargav.tool.ModelClass.ClientesUtilsTemp;
import com.bhargav.tool.ModelClass.ClientesUtilsTempFinal;
import com.bhargav.tool.ModelClass.Divisiones;
import com.bhargav.tool.ModelClass.FacturasXCobrar;
import com.bhargav.tool.ModelClass.RestClient;
import com.bhargav.tool.Printer.MainActivity;
import com.bhargav.tool.R;
import com.bhargav.tool.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import android.provider.MediaStore;

public class FormActivity extends AppCompatActivity {

    EditText txt_cliente;
    Context context = FormActivity.this;
    Spinner spinner_division;
    ArrayList<String> divisionesData = new ArrayList<>();
    Button btn_print;
    Button btn_image;
    ArrayList<ClientesUtils> detallePlanificacionsData = new ArrayList<>();
    ClientesUtils tempArr;
    int position;
    int division_position;
    String division_name;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static final String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    int forma_pogo_po = 0;
    int forma_pogo_posiiii = 0;
    LinearLayout plate;
    RecyclerView recyclerView, recyclerViewNew;
    ListAdapter listAdapter;
    ListAdapterForNewList listAdapterForNewList;
    LinearLayoutManager llm, llmNew;
    ImageView btn_add;
    private Uri fileUri;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        findView();

        position = Integer.parseInt(Utils.getData(context, "CUST_NO"));

        verifystoragepermissions(this);

        Gson gson = new Gson();
        detallePlanificacionsData = gson.fromJson(Utils.getData(context, "SaveDetallePlanifi"),
                new TypeToken<ArrayList<ClientesUtils>>() {
                }.getType());


        division_position = detallePlanificacionsData.get(position).getDivisionPosition();
        division_name = detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getNombreDivision();

        allSetText();
        callIsclianta();

        recyclerViewNew = findViewById(R.id.recycle_view_new);
        recyclerViewNew.setNestedScrollingEnabled(false);

        listAdapterForNewList = new ListAdapterForNewList(this, division_name, divisionesData.size());
        llmNew = new LinearLayoutManager(this);

        recyclerViewNew.setAdapter(listAdapterForNewList);
        recyclerViewNew.setLayoutManager(llmNew);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setNestedScrollingEnabled(false);

        listAdapter = new ListAdapter(this, division_name, divisionesData.size(), btn_add, listAdapterForNewList, recyclerViewNew, plate);
        llm = new LinearLayoutManager(this);

        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(llm);

        btn_image.setOnClickListener(v -> camera());
        btn_print.setOnClickListener(v -> apiJsonCreate());

    }

    private void camera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100, outputStream);
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                //imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

            String cadena =Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

                    cadena = cadena.replaceAll("\\s+","");
            Log.d("imagen", cadena);
            //}
            //agregar a arreglo de imagenes a enviar
            //imageView.setImageBitmap(imageBitmap);
        }
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void apiJsonCreate() {
        ClientesUtils temp = null;
        boolean should_pass = true;
        ClientesUtilsTemp temp_2;
        tempArr = null;
        tempArr = detallePlanificacionsData.get(position);
        ArrayList<FacturasXCobrar> facturasPago = new ArrayList<>();

        Gson gson2 = new Gson();
        String json = gson2.toJson(tempArr);

        Gson gson = new Gson();
        temp_2 = gson.fromJson(json,
                new TypeToken<ClientesUtilsTemp>() {
                }.getType());

        temp_2.setCodigoCliente(Integer.parseInt(tempArr.getCodigo()));
        temp_2.setCodigoFormaPago(Integer.parseInt(temp_2.lsDivisiones.get(0).getCodigoFormaPago()));
        Log.e("el codigo de pago es:" , temp_2.lsDivisiones.get(0).getCodigoFormaPago());

        temp_2.setSecuenciaReciboCobro(Integer.parseInt(Utils.getData(context, "INVOICENO")));
        temp_2.setSecuencialPersonal(Integer.parseInt(Utils.getData(context, "Personal")));

        SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf2.format(new Date());
        temp_2.setFechaReciboCobro(currentDate);

        if (Utils.getData(context, "INVOICENO") == null) {
            Utils.saveData(context, "INVOICENO", "1");
        }
        int new_invoice_no = Integer.parseInt(Utils.getData(context, "INVOICENO")) + 1;
        Utils.saveData(context, "INVOICENO", String.valueOf(new_invoice_no));
        temp_2.setInvoce_No(new_invoice_no);
        Utils.saveData(context, "INVOICENO", String.valueOf(Integer.parseInt(Utils.getData(context, "INVOICENO")) + 1));
        temp_2.setCodigoDivision((detallePlanificacionsData.get(position).lsDivisiones.get(division_position).getCodigoDivision()));
        Log.e("totoal", detallePlanificacionsData.get(position).lsDivisiones.size() + " total" + position + "." + division_position);

        for (int i = 0 ; i<= division_position; i++){
            Log.e("verefe-trans", i + ".-" +detallePlanificacionsData.get(position).lsDivisiones.get(i).isEsEfectivo() + " - " + detallePlanificacionsData.get(position).lsDivisiones.get(i).isEsTransferencia());
        }
        ClientesUtilsTemp temp_3 = temp_2;

        for (int i = temp_2.lsDivisiones.size() - 1; i >= 0; i--) {
            if (temp_2.lsDivisiones.get(i).getCodigoDivision()
                    != temp_2.getCodigoDivision()) {
                temp_3.lsDivisiones.remove(i);
            }
        }

        for (int i = 0; i < temp_3.lsDivisiones.size(); i++) {
            temp_3.setTotalAbonado(temp_3.getTotalAbonado() +
                    temp_3.lsDivisiones.get(i).getValor());
        }


        for (int i = 0; temp_3.lsDivisiones.size() > i; i++) {
            Double temp_valor = temp_3.lsDivisiones.get(i).getValor();
            temp_3.lsDivisiones.get(i).setValor(temp_3.lsDivisiones.get(i).getValorAplicado());
            temp_3.lsDivisiones.get(i).setValorAplicado(temp_valor);
            if(temp_2.lsDivisiones.get(0).getCodigoFormaPago().equals("1")){
                Log.e("abono", String.valueOf(temp_3.lsDivisiones.get(i).getValorAplicado()));
                 if(temp_3.lsDivisiones.get(i).getValorAplicado() <=0){
                    Toast.makeText(context, "El total abono es obligatorio", Toast.LENGTH_SHORT).show();
                    should_pass = should_pass && false;
                }

            }
            if(temp_2.lsDivisiones.get(0).getCodigoFormaPago().equals("2")){
                if(temp_3.lsDivisiones.get(i).getValorAplicado() <=0){
                    Toast.makeText(context, "El total abono es obligatorio cheque", Toast.LENGTH_SHORT).show();
                    should_pass = should_pass && false;
                }

            }
            if(temp_2.lsDivisiones.get(0).getCodigoFormaPago().equals("6")){
                if(temp_3.lsDivisiones.get(i).getValorAplicado() <=0){
                    Toast.makeText(context, "El total abono es obligatorio Deposito", Toast.LENGTH_SHORT).show();
                    should_pass = should_pass && false;
                }
                //Log.e("estransferencia", String.valueOf(temp_3.lsDivisiones.get(i).isTransferencia()) + " - " + position + " - "+ i);
                //Log.e("estransferencia1", String.valueOf(temp_3.lsDivisiones.size()) + " - " + position + " - "+ 1);
                if(temp_3.lsDivisiones.get(i).isTransferencia()){

                    if(temp_3.lsDivisiones.get(i).getNumeroTransaccion()>0){
                        Toast.makeText(context, "El numero de documento es obligatorio", Toast.LENGTH_SHORT).show();
                        should_pass = should_pass && false;
                    }
                }

            }
            if(temp_3.lsDivisiones.get(i).getValorAplicado() > temp_3.lsDivisiones.get(i).getValor() && temp_3.lsDivisiones.get(i).getCodigoFormaPago() == "2"){
                Toast.makeText(context, "El Valor aplicado es menor al valor que esta pagando", Toast.LENGTH_SHORT).show();
                should_pass = should_pass && false;
            }else{
                should_pass = should_pass && true;
            }
            ArrayList<ClientesUtils> detalleInicial = new ArrayList<>();
            detalleInicial = gson.fromJson(Utils.getData(context, "SaveDetallePlanifi"),
                    new TypeToken<ArrayList<ClientesUtils>>() {
                    }.getType());
            ArrayList<FacturasXCobrar> facturasInicial = detalleInicial.get(position).lsDivisiones.get(division_position).lsFacturasXCobrar;
            Boolean created = true;
            for (int j = temp_3.lsDivisiones.get(i).lsFacturasXCobrar.size() - 1; j >= 0; j--) {
                if(facturasPago.isEmpty()){
                    created = true;
                }else{
                    for (int f=0;f<facturasPago.size();f++){
                        if(facturasPago.get(f).getIsChecked()==1) {
                            if (facturasPago.get(f).getSecuenciaComprobante() == temp_3.lsDivisiones.get(i).lsFacturasXCobrar.get(j).getSecuenciaComprobante()) {
                                Double nuevo_total = facturasPago.get(f).getValor() + temp_3.lsDivisiones.get(i).lsFacturasXCobrar.get(j).getValor();
                                facturasPago.get(f).setValor(nuevo_total);
                                created = false;
                            }
                        }
                    }
                }
                if(created){
                    FacturasXCobrar nueva = new FacturasXCobrar();
                    nueva.setSecuenciaComprobante(temp_3.lsDivisiones.get(i).lsFacturasXCobrar.get(j).getSecuenciaComprobante());
                    nueva.setValor(temp_3.lsDivisiones.get(i).lsFacturasXCobrar.get(j).getValor());
                    facturasPago.add(nueva);
                }
                for(int m=0; m<facturasInicial.size();m++){
                    for(int n = 0; n<facturasPago.size();n++){
                        if(facturasInicial.get(m).getSecuenciaComprobante()==
                        facturasPago.get(n).getSecuenciaComprobante()){
                            if(facturasInicial.get(m).getTotalFactura()<
                                    facturasPago.get(n).getValor()){
                                should_pass= false;
                                Toast.makeText(context, "Los pagos realizados en la factura "+ facturasInicial.get(m).getFactura() + " no pueden ser mayor a "+ facturasInicial.get(m).getTotalFactura(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
                if (temp_3.lsDivisiones.get(i)
                        .lsFacturasXCobrar.get(j).getIsChecked() == 0) {
                    temp_3.lsDivisiones.get(i).lsFacturasXCobrar.get(j).getValor();

                    temp_3.lsDivisiones.get(i).lsFacturasXCobrar.remove(j);
                }
            }
        }

        Gson gson3 = new Gson();
        String json3 = gson3.toJson(temp_3);
        json3 = json3.replace("\"lsDivisiones\":", "\"lsPagoReciboCobros\":");
        json3 = json3.replace("\"secuencialPersonal\":", "\"secuenciaPersonal\":");
        json3 = json3.replace("\"lsFacturasXCobrar\":", "\"lsCobXSecComps\":");
        json3 = json3.replace("\"codigoDivision\":", "\"codigoDivisiones\":");

        Gson gsonFinal = new Gson();
        ClientesUtilsTempFinal tempFinal = gsonFinal.fromJson(json3,
                new TypeToken<ClientesUtilsTempFinal>() {
                }.getType());
        String jsonFinal = gsonFinal.toJson(tempFinal);

        Utils.saveData(context, "UploadTemp", jsonFinal);
        System.out.println("Call Api Body : " + jsonFinal);

        /*if(should_pass){
            //screenshot();
            uploadData();
            Intent intent = new Intent(context, PlanningActivity.class);
            startActivity(intent);
            finish();
            //revisar el mainactivity si ese no hacia que se borre de la lista!!!
        }*/

    }

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {
        ArrayList<T> newList = new ArrayList<T>();
        for (T element : list) {
            if (!newList.contains(element)) {
                newList.add(element);
            }
        }
        return newList;
    }

    private void callIsclianta() {

        for (int j = 0; detallePlanificacionsData.get(position).lsDivisiones.size() > j; j++) {
            divisionesData.add(detallePlanificacionsData.get(position).lsDivisiones.get(j).getNombreDivision());
        }

        divisionesData = removeDuplicates(divisionesData);

        ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, divisionesData);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_division.setAdapter(ad);

        spinner_division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView,
                                       View selectedItemView, int positionSpinnnn, long id) {
                detallePlanificacionsData.get(position).setDivisionPosition(positionSpinnnn);
                detallePlanificacionsData.get(position).setDivisionMaster(positionSpinnnn);

                if (positionSpinnnn != division_position) {
                    Intent intent = new Intent(context, FormActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });
        spinner_division.setSelection(detallePlanificacionsData.get(position).getDivisionMaster());


    }

    @Override
    protected void onPause() {
        super.onPause();

        Gson gson2 = new Gson();
        String json = gson2.toJson(detallePlanificacionsData);
        Utils.saveData(context, "SaveDetallePlanifi", json);
        Utils.saveData(context, "forma_pogo_po", "0");
    }


    private void allSetText() {

        txt_cliente.setEnabled(false);
        txt_cliente.setText(detallePlanificacionsData.get(position).getNombres());

    }

    public Bitmap takeScreenshot() {
        ScrollView iv = (ScrollView) findViewById(R.id.scrollView);
        Bitmap bitmap = Bitmap.createBitmap(
                iv.getChildAt(0).getWidth(),
                iv.getChildAt(0).getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        iv.getChildAt(0).draw(c);
        return bitmap;
    }

    protected File screenshot() {
        Date date = new Date();

        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            String dirpath = Environment.getExternalStorageDirectory() + "/pictures/";
            String path = dirpath + "/result-" + format + ".jpeg";

            Bitmap bitmap = takeScreenshot();

            File imageurl = new File(path);
            //File mFolder = new File(dirpath);
            /*File imageurl = new File(path);
            if (!imageurl.exists()) {
                imageurl.createNewFile();
            }
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(imageurl);
                bitmap.compress(Bitmap.CompressFormat.PNG,70, fos);
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
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

        if (Utils.isOnline(context)) {
            apiCallForUpdateData();
        } else {
            ArrayList<ClientesUtils> detallePlanificacionsDataTemp = new ArrayList<>();
            ArrayList<ClientesUtilsTempFinal> detallePlanificacionsDataTempUpload = new ArrayList<>();

            if (Utils.getData(context, "UploadDetallePlanifi") != null) {
                Gson gson = new Gson();
                detallePlanificacionsDataTempUpload = gson.fromJson(Utils.getData(context, "UploadDetallePlanifi"),
                        new TypeToken<ArrayList<ClientesUtilsTempFinal>>() {
                        }.getType());
            }

            for (int i = 0; detallePlanificacionsData.size() > i; i++) {
                if (position != i) {
                    detallePlanificacionsDataTemp.add(detallePlanificacionsData.get(i));
                } else {

                    Gson gson = new Gson();
                    ClientesUtilsTempFinal tempData = gson.fromJson(Utils.getData(context, "UploadTemp"),
                            new TypeToken<ClientesUtilsTempFinal>() {
                            }.getType());
                    Utils.saveData(context, "UploadTemp", "");

                    detallePlanificacionsDataTempUpload.add(tempData);

                    Gson gson3 = new Gson();
                    String json3 = gson3.toJson(detallePlanificacionsDataTempUpload);
                    Utils.saveData(context, "UploadDetallePlanifi", json3);
                }
            }

            detallePlanificacionsData = detallePlanificacionsDataTemp;
            Intent intent = new Intent(context, PlanningActivity.class);
            startActivity(intent);
            finish();

        }

    }

    private void apiCallForUpdateData() {

        JsonObject convertedObject = new JsonParser().parse(Utils.getData(context, "UploadTemp")).getAsJsonObject();

        Call<ABC> call = RestClient.post().registrarReciboCobroForUpload(convertedObject);

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

                        Intent intent = new Intent(context, PlanningActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(context, datas.getErrorSistemas(), Toast.LENGTH_SHORT).show();
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
        btn_image = findViewById(R.id.btn_image);
        txt_cliente = findViewById(R.id.txt_cliente);
        spinner_division = findViewById(R.id.spinner_division);
        btn_add = findViewById(R.id.btn_add);
        plate = findViewById(R.id.plate);
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(FormActivity.this, PlanningActivity.class);
        startActivity(intent);
        finish();
    }


    public static void verifystoragepermissions(Activity activity) {

        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGe);
        }
    }

    public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

        Context context;
        String division_name;
        ArrayList<String> nombreFormaPago = new ArrayList<String>();
        ArrayList<String> comNumerosCuentasData = new ArrayList<String>();
        int firt_ele;
        ImageView add;
        ListAdapterForNewList listAdapterForNewList;
        RecyclerView recyclerViewNew;
        LinearLayout plate;

        public ListAdapter(
                Context context,
                String division_name,
                int firt_ele,
                ImageView btn_add,
                ListAdapterForNewList listAdapterForNewList,
                RecyclerView recyclerViewNew,
                LinearLayout plate) {
            this.context = context;
            this.division_name = division_name;
            this.firt_ele = firt_ele;
            this.add = btn_add;
            this.listAdapterForNewList = listAdapterForNewList;
            this.recyclerViewNew = recyclerViewNew;
            this.plate = plate;
        }

        @Override
        public int getItemCount() {
            return detallePlanificacionsData.get(position).lsDivisiones.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.form_list, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

            if (i < firt_ele) {
                holder.remove.setVisibility(View.GONE);
                holder.rel_forma_pogo.setVisibility(View.VISIBLE);
            } else {
                holder.rel_forma_pogo.setVisibility(View.GONE);
                if(detallePlanificacionsData.get(position).lsDivisiones.get(i).getFormaPogoPosition()==2){
                    holder.check_forma_pago.setVisibility(View.VISIBLE);
                }else{
                    holder.check_forma_pago.setVisibility(View.GONE);
                }
            }



            if (!detallePlanificacionsData.get(position).lsDivisiones.get(i).getNombreDivision().equals(division_name)) {
                holder.rel_box.setVisibility(View.GONE);
            }else{
                if (forma_pogo_po == 0) {
                    forma_pogo_posiiii = i;
                }
                forma_pogo_po++;
            }

            holder.spinner_forma_pago.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (forma_pogo_po > 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("ERROR !!");
                        builder.setMessage("Eliminar todos los elementos primero");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                    return false;
                }
            });

            holder.cheque_date.setEnabled(false);
            holder.cheque_date.setText(detallePlanificacionsData.get(position)
                    .lsDivisiones.get(i).getFechaCheque());

            holder.entrega_date.setEnabled(false);
            holder.entrega_date.setText(detallePlanificacionsData.get(position)
                    .lsDivisiones.get(i).getFechaEntrega());

            holder.ed_total.setEnabled(false);
            holder.ed_total.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(i).getValor()));


            if(detallePlanificacionsData.get(position).lsDivisiones.get(i).getTotalAbono()>0){
                holder.totalaplicado.setText(String.valueOf((detallePlanificacionsData.get(position).lsDivisiones.get(i).getTotalAbono())));
            }else{
                holder.totalaplicado.setText("");

            }
            double tsa = detallePlanificacionsData.get(position).lsDivisiones.get(i).getTotalAbono() - detallePlanificacionsData.get(position).lsDivisiones.get(i).getValor();
            holder.totalsinaplicar.setText(String.valueOf(tsa));

            if(detallePlanificacionsData.get(position).lsDivisiones.get(i).getCodigoFormaPago()==6){
                Log.e("estransferencia" + i + ".."+ division_position + "//"+ position, String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).isEsTransferencia()));
                Log.e("esefectivo" + i + ".."+ division_position, String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(division_position).isEsEfectivo()));
                Log.d("ver codigo formapago", String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(i).getCodigoFormaPago()));
                holder.efectivo.setChecked(detallePlanificacionsData.get(position).lsDivisiones.get(i).isEsEfectivo());
                holder.transferencia.setChecked(detallePlanificacionsData.get(position).lsDivisiones.get(i).isEsTransferencia());

                holder.efectivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.e("cambio","clickeo efectivo" + position + " - "+ i);
                        if (holder.efectivo.isChecked()) {
                            Log.e("clickeo ef en posicion", String.valueOf(i));
                            holder.transferencia.setChecked(false);
                            holder.efectivo.setChecked(true);
                            holder.rel_documento.setVisibility(View.VISIBLE);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(true);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(false);

                        } else {
                            holder.efectivo.setChecked(false);
                            holder.rel_documento.setVisibility(View.GONE);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(false);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(true);
                        }
                    }
                });

                holder.transferencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.e("cambio","clickeo trnasferencia" + position + " - "+ i);
                        if (holder.transferencia.isChecked()) {
                            Log.e("click tra en posicion", String.valueOf(i));
                            holder.efectivo.setChecked(false);
                            holder.rel_documento.setVisibility(View.GONE);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(false);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(true);

                        } else {
                            holder.transferencia.setChecked(false);
                            holder.efectivo.setChecked(true);
                            holder.rel_documento.setVisibility(View.VISIBLE);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(true);
                            detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(false);
                        }
                    }
                });
            }


            holder.cheque_date_img.setOnClickListener(new View.OnClickListener() {
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
                            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                            String dateString = dateFormat.format(fromCal.getTime());
                            holder.cheque_date.setText(dateString);
                            detallePlanificacionsData.get(position)
                                    .lsDivisiones.get(i).setFechaCheque(dateString);
                            listAdapterForNewList.notifyDataSetChanged();
                        }
                    }, year, month, day);
                    datepicker.show();
                }
            });

            holder.btn_ver_detalle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (holder.et_documento.getText().toString().equals("")) {
                        holder.et_documento.setText("0");
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setNumeroTransaccion(0);
                    } else {
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setNumeroTransaccion(Integer.parseInt(holder.et_documento.getText().toString()));
                    }
                    if (holder.totalaplicado.getText().toString().equals("")) {
                        holder.totalaplicado.setText("0");
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setTotalAbono(0.0);
                    } else {
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setTotalAbono(Double.parseDouble(holder.totalaplicado.getText().toString()));
                        double tsa = detallePlanificacionsData.get(position).lsDivisiones.get(i).getTotalAbono() - detallePlanificacionsData.get(position).lsDivisiones.get(i).getValor();
                        holder.totalsinaplicar.setText(String.valueOf(tsa));

                    }


                    detallePlanificacionsData.get(position).lsDivisiones.get(i)
                            .setValor(Double.parseDouble(holder.ed_total.getText().toString()));

                    detallePlanificacionsData.get(position).setDivisionPosition(i);
                    listAdapterForNewList.notifyDataSetChanged();

                    if(holder.efectivo.isChecked()){
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(true);
                    }else{
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(false);
                    }

                    if(holder.transferencia.isChecked()){
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(true);
                    }else{
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(false);
                    }


                    Intent intent = new Intent(context, VerDetalleActivity.class);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            });

            String document_no = String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(i).getNumeroTransaccion());

            Log.i("numero transaccion", document_no);
            if (document_no.equals("0")) {
                holder.et_documento.setText("");
            } else {
                holder.et_documento.setText(document_no);
            }

            holder.et_documento.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (String.valueOf(holder.et_documento.getText()).equals("")) {
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setNumeroTransaccion(0);
                    } else {
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setNumeroTransaccion(Integer.parseInt(holder.et_documento.getText().toString()));
                    }

                    listAdapterForNewList.notifyDataSetChanged();
                }
            });

            holder.totalaplicado.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (holder.totalaplicado.getText().toString().equals("")) {
                        holder.totalaplicado.setText("0");
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setTotalAbono(0.0);
                    } else {
                        detallePlanificacionsData.get(position).lsDivisiones.get(i)
                                .setTotalAbono(Double.parseDouble(holder.totalaplicado.getText().toString()));
                        double tsa = detallePlanificacionsData.get(position).lsDivisiones.get(i).getTotalAbono() - detallePlanificacionsData.get(position).lsDivisiones.get(i).getValor();
                        holder.totalsinaplicar.setText(String.valueOf(tsa));
                        detallePlanificacionsData.get(position)
                                .lsDivisiones.get(i).setValorAplicado(Double.parseDouble(holder.totalaplicado.getText().toString()));
                        detallePlanificacionsData.get(position)
                                .lsDivisiones.get(i).setValorSinAplicar(tsa);
                        listAdapterForNewList.notifyDataSetChanged();

                    }


                    listAdapterForNewList.notifyDataSetChanged();
                }
            });
/*
            holder.efectivo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("cambio","clickeo efectivo" + position + " - "+ i);
                    if (holder.efectivo.isChecked()) {
                        holder.transferencia.setChecked(false);
                        holder.efectivo.setChecked(true);
                        holder.rel_documento.setVisibility(View.VISIBLE);
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(true);
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(false);

                    } else {
                        // your code to  no checked checkbox
                    }
                }
            });

            holder.transferencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.e("cambio","clickeo trnasferencia" + position + " - "+ i);
                    if (holder.transferencia.isChecked()) {
                        holder.efectivo.setChecked(false);
                        holder.rel_documento.setVisibility(View.GONE);
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsEfectivo(false);
                        detallePlanificacionsData.get(position).lsDivisiones.get(i).setEsTransferencia(true);

                    } else {
                        // your code to  no checked checkbox
                    }
                }
            });*/



            holder.entrega_date_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("click entrega", "click");
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
                            holder.entrega_date.setText(dateString);
                            detallePlanificacionsData.get(position)
                                    .lsDivisiones.get(i).setFechaEntrega(dateString);
                            listAdapterForNewList.notifyDataSetChanged();
                        }
                    }, year, month, day);
                    datepicker.show();
                }
            });

            Gson gson = new Gson();
            nombreFormaPago = gson.fromJson(Utils.getData(context, "FormaPogo"),
                    new TypeToken<ArrayList<String>>() {
                    }.getType());
//Log.d("nombreformapago", nombreFormaPago.get(i));
            ArrayAdapter ad = new ArrayAdapter(context, android.R.layout.simple_spinner_item, nombreFormaPago);
            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner_forma_pago.setAdapter(ad);


            holder.spinner_forma_pago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> parentView,
                                           View selectedItemView, int positionSpin, long id) {
                    Log.e("error", "entre");
                    detallePlanificacionsData.get(position).lsDivisiones.get(i).setFormaPogoPosition(positionSpin);
                    ArrayList<Float> codigoFormaPago = new ArrayList<Float>();
                    Gson gson = new Gson();
                    codigoFormaPago = gson.fromJson(Utils.getData(context, "codigoFormaPago"),
                            new TypeToken<ArrayList<Float>>() {
                            }.getType());

                    detallePlanificacionsData.get(position).lsDivisiones.get(i).setCodigoFormaPago(Math.round(codigoFormaPago.get(positionSpin)));
                    Log.e("tipo", nombreFormaPago.get(positionSpin));
                    if (nombreFormaPago.get(positionSpin).equals("EFECTIVO")) {
                        holder.rel_documento.setVisibility(View.GONE);
                        holder.rel_cuenta.setVisibility(View.GONE);
                        holder.rel_cheque_date.setVisibility(View.GONE);
                        holder.rel_entrega_date.setVisibility(View.GONE);
                        holder.totalsinaplicar_box.setVisibility(View.GONE);
                        holder.totalaplicado_box.setVisibility(View.GONE);
                        holder.check_forma_pago.setVisibility(View.GONE);
                        holder.efectivo.setChecked(false);
                        holder.transferencia.setChecked(false);
                        btn_add.setVisibility(View.GONE);
                        recyclerViewNew.setVisibility(View.GONE);
                        plate.setVisibility(View.GONE);
                    } else if(nombreFormaPago.get(positionSpin).equals("DEPOSITO")){
                        holder.rel_documento.setVisibility(View.VISIBLE);
                        holder.check_forma_pago.setVisibility(View.VISIBLE);
                        holder.rel_cuenta.setVisibility(View.VISIBLE);
                        holder.rel_cheque_date.setVisibility(View.GONE);
                        holder.rel_entrega_date.setVisibility(View.VISIBLE);
                        holder.totalsinaplicar_box.setVisibility(View.GONE);
                        holder.totalaplicado_box.setVisibility(View.VISIBLE);
                        holder.efectivo.setChecked(false);
                        holder.transferencia.setChecked(false);
                        btn_add.setVisibility(View.VISIBLE);
                        recyclerViewNew.setVisibility(View.VISIBLE);
                        plate.setVisibility(View.VISIBLE);
                    }else{
                        holder.check_forma_pago.setVisibility(View.GONE);
                        holder.rel_documento.setVisibility(View.VISIBLE);
                        holder.rel_cuenta.setVisibility(View.VISIBLE);
                        holder.rel_cheque_date.setVisibility(View.VISIBLE);
                        holder.rel_entrega_date.setVisibility(View.VISIBLE);
                        holder.totalsinaplicar_box.setVisibility(View.VISIBLE);
                        holder.totalaplicado_box.setVisibility(View.VISIBLE);
                        holder.efectivo.setChecked(true);
                        holder.transferencia.setChecked(false);
                        btn_add.setVisibility(View.VISIBLE);
                        recyclerViewNew.setVisibility(View.VISIBLE);
                        plate.setVisibility(View.VISIBLE);

                    }

                    listAdapterForNewList.notifyDataSetChanged();

                }

                public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                }
            });
            holder.spinner_forma_pago.setSelection(detallePlanificacionsData.get(position).lsDivisiones.get(i).getFormaPogoPosition());


            for (int j = 0; detallePlanificacionsData.get(position).lsComNumerosCuentas.size() > j; j++) {
                String nuevo = "";
                nuevo = String.valueOf(detallePlanificacionsData.get(position).lsComNumerosCuentas.get(j).getCodigoNumeroCuenta());
                nuevo = nuevo.concat(" - ");
                nuevo = nuevo.concat(String.valueOf(detallePlanificacionsData.get(position).lsComNumerosCuentas.get(j).getNumeroCuenta()));
                comNumerosCuentasData.add(nuevo);
            }

            ArrayAdapter ad2 = new ArrayAdapter(context, android.R.layout.simple_spinner_item, comNumerosCuentasData);
            ad2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner_cuenta.setAdapter(ad2);

            holder.spinner_cuenta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parentView,
                                           View selectedItemView, int positionSpin, long id) {
                    detallePlanificacionsData.get(position)
                            .lsDivisiones.get(i)
                            .setCuentaPosition(positionSpin);
                    detallePlanificacionsData.get(position)
                            .lsDivisiones.get(i)
                            .setCodigoNumeroCuenta(comNumerosCuentasData.get(positionSpin).split(" - ")[0]);
                    listAdapterForNewList.notifyDataSetChanged();
                }

                public void onNothingSelected(AdapterView<?> arg0) {// do nothing
                }

            });
            holder.spinner_cuenta.setSelection(detallePlanificacionsData.get(position).lsDivisiones.get(i).getCuentaPosition());

            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        detallePlanificacionsData.get(position).lsDivisiones.remove(i);
                        notifyDataSetChanged();
                        listAdapterForNewList.notifyDataSetChanged();
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            });

            add.setOnClickListener(v -> {

                try {
                    Divisiones temp = detallePlanificacionsData.get(position).lsDivisiones.get(division_position);

                    Gson gson2 = new Gson();
                    String json = gson2.toJson(temp);

                    Gson gson3 = new Gson();
                    Divisiones tempArrr = gson3.fromJson(json,
                            new TypeToken<Divisiones>() {
                            }.getType());
                    Divisiones facturas = tempArrr;

                    tempArrr.setNumeroTransaccion(0);
                    tempArrr.setCuentaPosition(0);
                    tempArrr.setFechaCheque(null);
                    tempArrr.setFechaEntrega(null);
                    tempArrr.setValor(0.0);
                    tempArrr.setCantidad(0);
                    tempArrr.setValorSinAplicar(0.0);
                    tempArrr.setTotalAbono(0.0);
                    tempArrr.setValorAplicado(0.0);
                    tempArrr.setEsTransferencia(false);
                    tempArrr.setEsEfectivo(false);

                    for (int j = 0; tempArrr.lsFacturasXCobrar.size() > j; j++) {
                        tempArrr.lsFacturasXCobrar.get(j).setValor(0.0);
                        tempArrr.lsFacturasXCobrar.get(j).setIsChecked(0);
                    }
                    detallePlanificacionsData.get(position)
                            .lsDivisiones.add(tempArrr);

                    Intent intent = new Intent(context, FormActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    finish();

                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            });
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView remove, cheque_date_img, entrega_date_img;
            EditText cheque_date, entrega_date, ed_total, et_documento;
            EditText totalaplicado, totalsinaplicar;
            Button btn_ver_detalle;
            RelativeLayout rel_documento, rel_cuenta, rel_cheque_date, rel_entrega_date, rel_box, rel_forma_pogo, check_forma_pago;
            Spinner spinner_forma_pago, spinner_cuenta;
            RelativeLayout totalaplicado_box, totalsinaplicar_box;
            CheckBox efectivo, transferencia;

            public ViewHolder(View itemView) {
                super(itemView);
                remove = itemView.findViewById(R.id.btn_remove);
                cheque_date = itemView.findViewById(R.id.cheque_date);
                entrega_date = itemView.findViewById(R.id.entrega_date);
                ed_total = itemView.findViewById(R.id.ed_total);
                cheque_date_img = itemView.findViewById(R.id.cheque_date_img);
                btn_ver_detalle = itemView.findViewById(R.id.btn_ver_detalle);
                et_documento = itemView.findViewById(R.id.et_documento);
                entrega_date_img = itemView.findViewById(R.id.entrega_date_img);
                rel_documento = itemView.findViewById(R.id.rel_documento);
                rel_cuenta = itemView.findViewById(R.id.rel_cuenta);
                rel_cheque_date = itemView.findViewById(R.id.rel_cheque_date);
                rel_entrega_date = itemView.findViewById(R.id.rel_entrega_date);
                spinner_forma_pago = itemView.findViewById(R.id.spinner_forma_pago);
                spinner_cuenta = itemView.findViewById(R.id.spinner_cuenta);
                rel_box = itemView.findViewById(R.id.rel_box);
                rel_forma_pogo = itemView.findViewById(R.id.rel_forma_pogo);
                check_forma_pago = itemView.findViewById(R.id.check_forma_pogo);
                //TOTALES
                totalaplicado_box = itemView.findViewById(R.id.rel8);
                totalsinaplicar_box = itemView.findViewById(R.id.totalnoaplicado);
                totalaplicado = itemView.findViewById(R.id.ed_totalaplicado);
                totalsinaplicar = itemView.findViewById(R.id.ed_totalsinaplicar);
                //checkboxs
                efectivo = itemView.findViewById(R.id.efectivo);
                transferencia = itemView.findViewById(R.id.transferencia);



            }
        }
    }

    public class ListAdapterForNewList extends RecyclerView.Adapter<ListAdapterForNewList.ViewHolder> {

        Context context;
        String division_name;
        int firt_ele;

        public ListAdapterForNewList(
                Context context,
                String division_name,
                int firt_ele) {
            this.context = context;
            this.division_name = division_name;
            this.firt_ele = firt_ele;
        }

        @Override
        public int getItemCount() {
            return detallePlanificacionsData.get(position).lsDivisiones.size();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.new_form_list, viewGroup, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int i) {

            if (!detallePlanificacionsData.get(position).lsDivisiones.get(i).getNombreDivision().equals(division_name)) {
                holder.plate.setVisibility(View.GONE);
            }

            holder.tv_cantidad.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(i).getCantidad()));
            String document_no = String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(i).getNumeroTransaccion());
            if (document_no.equals("0")) {
                holder.tv_documento.setText("");
            } else {
                holder.tv_documento.setText(document_no);
            }
            holder.tv_total.setText(String.valueOf(detallePlanificacionsData.get(position).lsDivisiones.get(i).getValor()));

        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv_cantidad, tv_documento, tv_total;
            LinearLayout plate;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_cantidad = itemView.findViewById(R.id.tv_cantidad);
                tv_documento = itemView.findViewById(R.id.tv_documento);
                tv_total = itemView.findViewById(R.id.tv_total);
                plate = itemView.findViewById(R.id.plate);

            }
        }


    }


}