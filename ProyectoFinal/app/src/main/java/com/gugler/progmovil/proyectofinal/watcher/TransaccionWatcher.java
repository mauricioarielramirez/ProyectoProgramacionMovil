package com.gugler.progmovil.proyectofinal.watcher;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Ariel on 6/12/2017.
 */

public class TransaccionWatcher implements TextWatcher {
    private TextView componentNombre;
    private TextView componentMonto;
    private Button btn;

    public TransaccionWatcher(TextView componentNombre, TextView componentMonto, Button btn) {
        super();
        this.componentNombre = componentNombre;
        this.componentMonto = componentMonto;
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
        if (componentNombre.getText().toString().length() == 0 || componentMonto.getText().toString().length() == 0 || (Float.parseFloat(componentMonto.getText().toString())==0F) ) {
            btn.setEnabled(false);
        }else{
            btn.setEnabled(true);
        }
    }
}
