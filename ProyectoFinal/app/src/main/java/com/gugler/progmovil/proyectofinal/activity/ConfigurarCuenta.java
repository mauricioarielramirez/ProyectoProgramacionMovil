package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.widget.LinearLayout;

import progmovil.gugler.com.pf.R;

public class ConfigurarCuenta extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
        actionBar.setTitle("Configurar Cuenta");
        actionBar.setSubtitle("Nueva cuenta");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        linearLayout.setLeft(12);
        linearLayout.setRight(width-12);
        linearLayout.setTop(24);
        linearLayout.setBottom(height-24);
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu); //En menu.xml se definen
        return true;
    }


}
