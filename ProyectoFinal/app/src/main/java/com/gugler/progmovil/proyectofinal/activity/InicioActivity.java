package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;

import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import progmovil.gugler.com.pf.R;

public class InicioActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        prepararStringSql();
        if (existenCuentas() == true) {
            Intent intento = new Intent(this, NormalActivity.class);
            startActivity(intento);
        }
        configurarInterface("");
    }

    /**
     *
     * @param modo
     */
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
                        Bundle recurso = new Bundle();
                        recurso.putString("tipoTransaccion","N");
                        Intent intentoConfigCuenta = new Intent(view.getContext(), ConfigurarCuentaActivity.class);
                        intentoConfigCuenta.putExtras(recurso);
                        startActivity(intentoConfigCuenta);
                    }
                });
        }
    }

    /**
     * Comprueba si existen cuentas haciendo la llamada al servicio
     * @return true si existen, false si no
     */
    private Boolean existenCuentas() {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,CADENA_SQL);
        if (sCuentas.listarTodo().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu); //En menu.xml se definen
        return true;
    }

    /**
     * Si se ejecuta OnResume es porque se ejecutó una activity desde aquí. En este caso llama a inicializarActivity para determinar si se redirecciona o no.
     */
    @Override
    protected void onResume() {
        super.onResume();
        prepararStringSql();
        if (existenCuentas() == true) {
            Intent intento = new Intent(this, NormalActivity.class);
            startActivity(intento);
        }
    }
}
