package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import progmovil.gugler.com.pf.R;

public class ConfigurarCuenta extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);
        prepararStringSql();
        configurarInterface("");
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Configurar Cuenta");
                actionBar.setSubtitle("Nueva cuenta");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepararStringSql();
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu); //En menu.xml se definen
        return true;
    }

    public void onBtnGuardarClick(View view) {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,super.CADENA_SQL);

        String denominacion = ((TextView) findViewById(R.id.txtDenominacion)).getText().toString();
        String descripcion = ((TextView) findViewById(R.id.txtDescripcion)).getText().toString();
        Float saldo = Float.parseFloat(((TextView) findViewById(R.id.txtSaldo)).getText().toString());

        Cuenta cuentaAgregar = new Cuenta(null, denominacion, descripcion, saldo);
        try {
            Boolean res = sCuentas.agregarCuenta(cuentaAgregar);
            if (res) {
                Toast t = Toast.makeText(this, "Cuenta agregada exitosamente", Toast.LENGTH_SHORT);
                t.show();
            }
        } catch (ValidacionException vEx) {
            Toast t = Toast.makeText(this, vEx.getMensaje(), Toast.LENGTH_LONG);
            t.show();
        } catch (Exception ex) {
            Toast t = Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG);
            t.show();
        }
    }

}
