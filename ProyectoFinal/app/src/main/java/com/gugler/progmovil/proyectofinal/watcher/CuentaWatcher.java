package com.gugler.progmovil.proyectofinal.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ariel on 6/12/2017.
 */

public class CuentaWatcher implements TextWatcher {
    private TextView component1;
    private TextView component2;
    private TextView component3;
    private Button btn;

    public CuentaWatcher(TextView component1,TextView component2,TextView component3, Button btn) {
        super();
        this.component1 = component1;
        this.component2 = component2;
        this.component3 = component3;
        this.btn = btn;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if ( component1.getText().toString().length() > 0 && component2.getText().toString().length() > 0 && component3.getText().toString().length() > 0){
            //message = "Complete este campo";
            // habilitar boton
            btn.setEnabled(true);
        } else {
            // deshabilitar botón
            btn.setEnabled(false);
        }
    }
}
