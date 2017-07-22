package com.gugler.progmovil.proyectofinal.activity;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

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

    public void onBtnGuardarClick(View view) {
        ServicioCuentas sCuentas = new ServicioCuentas(this, InicioActivity.CADENA_SQL.toString());

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
