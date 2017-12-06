package com.gugler.progmovil.proyectofinal.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ariel on 6/12/2017.
 */

public class CuentaWatcher implements TextWatcher {
    private TextView component;
    private Button btn;

    public CuentaWatcher(TextView component, Button btn) {
        super();
        this.component = component;
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
        String message = "";
        if ( component.getText().toString().length() > 0 ){
            //message = "Complete este campo";
            // habilitar boton
            btn.setEnabled(true);
        } else {
            // deshabilitar botón
            btn.setEnabled(false);
        }

        if ( message.equals("") == true )
        {
            component.setError(null);
        }
        else
        {
            component.setError(message);
        }
    }
}
