package com.bhargav.tool.Printer;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.text.method.LinkMovementMethod;
import android.util.Base64;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bhargav.tool.Activity.FormActivity;
import com.bhargav.tool.Activity.PlanningActivity;
import com.bhargav.tool.Activity.VerDetalleActivity;
import com.bhargav.tool.R;
import com.bhargav.tool.Utils;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.comm.TcpConnection;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.PrinterStatus;
import com.zebra.sdk.printer.SGD;
import com.zebra.sdk.printer.ZebraPrinter;
import com.zebra.sdk.printer.ZebraPrinterFactory;
import com.zebra.sdk.printer.ZebraPrinterLanguageUnknownException;
import com.zebra.sdk.printer.ZebraPrinterLinkOs;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MainActivity extends ConnectionScreen {

    private ProgressDialog myDialog;
    private UIHelper helper = new UIHelper(this);
    private Connection connection;
    private AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * This is an abstract method from connection screen where we are implementing the calls.
     */
    @Override
    public void performTest() {
        new Thread(new Runnable() {
            public void run() {
                Looper.prepare();
                doPerformTest();
                Looper.loop();
                Looper.myLooper().quit();
            }
        }).start();

    }

    /**
     * This method is used to create a new alert dialog to sign and print and implements best practices to check the status of the printer.
     */
    public void doPerformTest() {
        if (isBluetoothSelected() == false) {
            try {
                int port = Integer.parseInt(getTcpPortNumber());
                connection = new TcpConnection(getTcpAddress(), port);
            } catch (NumberFormatException e) {
                helper.showErrorDialogOnGuiThread("Port number is invalid");
                return;
            }
        } else {
            connection = new BluetoothConnection(getMacAddressFieldText());
        }
        try {
            connection.open();
            final ZebraPrinter printer = ZebraPrinterFactory.getInstance(connection);
            ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);
            PrinterStatus printerStatus = (linkOsPrinter != null) ? linkOsPrinter.getCurrentStatus() : printer.getCurrentStatus();
            getPrinterStatus();
            if (printerStatus.isReadyToPrint) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        Toast.makeText(MainActivity.this, "Printer Ready", Toast.LENGTH_LONG).show();

                    }
                });

                Utils.saveData(MainActivity.this,"PRINT_IS","READY");

                try {
                    connection.open();
                    byte [] encodeByte=Base64.decode(Utils.getData(MainActivity.this,"PRINT_URL"), Base64.DEFAULT);
                    InputStream inputStream  = new ByteArrayInputStream(encodeByte);
                    Bitmap signatureBitmap  = BitmapFactory.decodeStream(inputStream);
                //    Bitmap signatureBitmap = Bitmap.createScaledBitmap(bitmap);
                    printer.printImage(new ZebraImageAndroid(signatureBitmap), 0, 0, signatureBitmap.getWidth(), signatureBitmap.getHeight(), false);
                    connection.close();

                } catch (ConnectionException e) {
                    helper.showErrorDialogOnGuiThread(e.getMessage());
                }


            } else if (printerStatus.isHeadOpen) {
                helper.showErrorMessage("Error: Head Open \nPlease Close Printer Head to Print");
            } else if (printerStatus.isPaused) {
                helper.showErrorMessage("Error: Printer Paused");
            } else if (printerStatus.isPaperOut) {
                helper.showErrorMessage("Error: Media Out \nPlease Load Media to Print");
            } else {
                helper.showErrorMessage("Error: Please check the Connection of the Printer");
            }

            connection.close();
            getAndSaveSettings();
        } catch (ConnectionException e) {
            helper.showErrorDialogOnGuiThread(e.getMessage());
        } catch (ZebraPrinterLanguageUnknownException e) {
            helper.showErrorDialogOnGuiThread(e.getMessage());
        } finally {
            helper.dismissLoadingDialog();
        }

    }



    /**
     * This method implements the best practices to check the language of the printer and set the language of the printer to ZPL.
     *
     * @throws ConnectionException
     */
    private void getPrinterStatus() throws ConnectionException{


        final String printerLanguage = SGD.GET("device.languages", connection); //This command is used to get the language of the printer.

        final String displayPrinterLanguage = "Printer Language is " + printerLanguage;

        SGD.SET("device.languages", "zpl", connection); //This command set the language of the printer to ZPL

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Toast.makeText(MainActivity.this, displayPrinterLanguage + "\n" + "Language set to ZPL", Toast.LENGTH_LONG).show();

            }
        });

    }

    /**
     * This method saves the entered address of the printer.
     */

    private void getAndSaveSettings() {
        SettingsHelper.saveBluetoothAddress(MainActivity.this, getMacAddressFieldText());
        SettingsHelper.saveIp(MainActivity.this, getTcpAddress());
        SettingsHelper.savePort(MainActivity.this, getTcpPortNumber());
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, PlanningActivity.class);
        startActivity(intent);
        finish();
    }


}
