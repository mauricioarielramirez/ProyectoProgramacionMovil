package com.gugler.progmovil.proyectofinal.watcher;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ericd on 1/6/2018.
 */

public class DateButtonWatcher implements TextWatcher {
    private Button btn1;
    private Button btn2;
    private String changed;
    private Context context;

    /**
     * @param btn1 Button de fecha inicial
     * @param btn2 Button de fecha final
     */
    public DateButtonWatcher(Button btn1, Button btn2, String changed, Context context) {
        super();
        this.btn1 = btn1;
        this.btn2 = btn2;
        this.changed = changed;
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaBtn1 = null;
        Date fechaBtn2 = null;
        try {
            fechaBtn1 = sdf.parse(btn1.getText().toString());
            fechaBtn2 = sdf.parse(btn2.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        switch (changed) {
            case "btn1":
                if (fechaBtn1.after(fechaBtn2)) {
                    // Si fechaBtn 1 es MAYOR a fechaBtn2
                    btn2.setText(btn1.getText().toString());
                }
                break;
            case "btn2":
                if (fechaBtn2.before(fechaBtn1)) {
                    // Si fechaBtn2 es MENOR a fechaBtn2
                    Toast tError = Toast.makeText(this.context, "La fecha HASTA no puede ser menor a la fecha DESDE", Toast.LENGTH_LONG);
                    tError.show();
                    btn2.setText(btn1.getText().toString());
                }
                break;
            default:
                break;
        }
    }
}
