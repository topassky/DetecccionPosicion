package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.utilities.Utilities;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button ubicame;
    private Button btn_start;
    private Button btn_stop;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;
    private String latitudeg;
    private String longitude;
    public static ArrayList latitudInicial = new ArrayList();
    public static ArrayList logitudInicial = new ArrayList();
    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String coordinates = "\n" +intent.getExtras().get("coordinates");

                    String[] parts = coordinates.split(",");
                    String logitude = parts[0];
                    String latitude = parts[1];
                    textView.append("\n"+logitude+"\n");
                    textView.append(latitude);
                    latitudInicial.add(latitude);
                    logitudInicial.add(logitude);

                    //Toast.makeText(getApplicationContext(),"Id Registro"+latitudInicial.get(0),Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getApplicationContext(),"Id Registro"+logitudInicial.get(0),Toast.LENGTH_SHORT).show();
                    //longitude = Double.parseDouble(logitude);
                    //latitudeg = Double.parseDouble(latitude);

                    //longitude = logitude;
                    //latitudeg = latitude;
                    //regitrarUsuarios();
                    UsersRegister(logitude,latitude);

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }
    //coneccion  a la base de datos
    private void UsersRegister(String longitude2, String latitudeg2 ) {
        String name = "'felipe'";
        Date actual= Calendar.getInstance().getTime();
        actual.getTime();
        ConecionSQLiteHelper conn = new ConecionSQLiteHelper(this, "db_user4",null,6);
        SQLiteDatabase db = conn.getWritableDatabase();
        String insert = "INSERT INTO "+Utilities.TableUser+
                " ("+Utilities.Name+","+Utilities.Latitude+","+Utilities.Longitude+","+
                Utilities.Date+")"+" VALUES "+" ("+name+","+longitude2+","+latitudeg2+","+
                "'"+actual.toString()+"')";
        db.execSQL(insert);
        db.close();

    }

    //escucha la latitud y longitud en broadcast
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ConecionSQLiteHelper conn = new ConecionSQLiteHelper(this, "db_name",null,1);

        //button definition
        ubicame = (Button)findViewById(R.id.button);
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_stop = (Button)findViewById(R.id.btn_stop);
        textView = (TextView) findViewById(R.id.textView);


        if(!runtime_permissions())
            enable_buttons();
    }
// registo en base de datos con sin lenguaje SQL
    private void regitrarUsuarios(){
        ConecionSQLiteHelper conn = new ConecionSQLiteHelper(this, "db_name4",null,2);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Utilities.Id,"3456");
        values.put(Utilities.Name,"Felipe");
        values.put(Utilities.Latitude, latitudeg);
        values.put(Utilities.Longitude, longitude);
        values.put(Utilities.Date, "hola");

        long isResultante = db.insert(Utilities.TableUser, Utilities.Id, values);
        Toast.makeText(getApplicationContext(),"Id Registro"+isResultante,Toast.LENGTH_SHORT).show();
        db.close();
    }

    //control de bonotes
    private void enable_buttons() {
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }
        });

        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),GPS_Service.class);
                stopService(i);

            }
        });

        ubicame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });

    }
    //permisos de conexion a la red
    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                enable_buttons();
            } else {
                runtime_permissions();
            }
        }
    }
}
