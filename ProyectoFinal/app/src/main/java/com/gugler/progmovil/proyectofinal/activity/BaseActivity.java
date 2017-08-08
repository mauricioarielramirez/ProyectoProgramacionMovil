package com.gugler.progmovil.proyectofinal.activity;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;

import progmovil.gugler.com.pf.R;

/**
 * Created by Ariel on 7/8/2017.
 */

public class BaseActivity extends AppCompatActivity {

    protected String CADENA_SQL;
    protected Inicializador inicializador;

    protected void prepararStringSql() {
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


}
