package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;

import progmovil.gugler.com.pf.R;

public class MenuPrincipalActivity extends AppCompatActivity {
    private String CADENA_SQL;
    private Inicializador inicializador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }

    private void prepararStringSql() {
        if (CADENA_SQL == null) {
            try {
                inicializador = new Inicializador(getResources().openRawResource(R.raw.script));
                CADENA_SQL = inicializador.devolverCadena();
            } catch (ValidacionException e) {
                Toast tostada = Toast.makeText(this, e.getMensaje(), Toast.LENGTH_SHORT);
                tostada.show();
            } catch (Exception e) {
                Toast tostada = Toast.makeText(this, "Ocurrió un problema desconocido en la aplicación", Toast.LENGTH_SHORT);
                tostada.show();
            }
        }
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
                actionBar.setTitle("Seguimiento del efectivo");
                actionBar.setSubtitle("Bienvenido");

                FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intentoConfigCuenta = new Intent(view.getContext(), ConfigurarCuenta.class);
                        startActivity(intentoConfigCuenta);
                    }
                });
        }
    }
}
