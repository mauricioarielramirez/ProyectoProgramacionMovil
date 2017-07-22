package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import progmovil.gugler.com.pf.R;

public class InicioActivity extends AppCompatActivity {
    private ServicioCuentas sCuentas;
    public static StringBuffer CADENA_SQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        leerScript();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentoConfigCuenta = new Intent(view.getContext(), ConfigurarCuenta.class);
                startActivity(intentoConfigCuenta);
            }
        });

        ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
        actionBar.setTitle("Seguimiento del efectivo");
        actionBar.setSubtitle("Bienvenido");
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu); //En menu.xml se definen
        return true;
    }

    private void leerScript() {
        CADENA_SQL = new StringBuffer();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.script);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (String linea; (linea=reader.readLine())!=null;){
                CADENA_SQL.append(linea);
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Problemas al intentar leer el archivo.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
