package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import progmovil.gugler.com.pf.R;

public class TransaccionActivity extends BaseActivity {

    public final String TRANSACCION_NO_EDITABLE = "SoloConfirmarTransaccion";
    public final String TRANSACCION_EDITABLE = "EditarTransaccion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        prepararStringSql();
        configurarInterface("");
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            case TRANSACCION_NO_EDITABLE:
            /*
            BLOQUEAR SPINNERS Y EDITTEXT
            CAMBIAR TITULOS
            */
            break;
            case TRANSACCION_EDITABLE:
            /*
            HABILITAR SPINNERS Y EDITTEXT
            CAMBIAR TITULOS
            */
            break;
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Transacción");
                actionBar.setSubtitle("");
        }
    }
}
