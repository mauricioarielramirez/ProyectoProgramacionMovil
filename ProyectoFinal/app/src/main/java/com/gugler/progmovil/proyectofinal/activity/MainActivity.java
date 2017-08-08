package com.gugler.progmovil.proyectofinal.activity;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.dao.CuentaDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import progmovil.gugler.com.pf.R;

public class MainActivity extends AppCompatActivity {
    private CuentaDAO cuentaDao;
    private ServicioCuentas sCuentas;
    public static StringBuffer CADENA_SQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CADENA_SQL = new StringBuffer();
        //leerScript();
//        cuentaDao = new CuentaDAO(this,cadena.toString());
        sCuentas = new ServicioCuentas();

        //Mirar aca http://elbauldeandroid.blogspot.com.ar/2013/10/actionbar-android-en-construccion.html

        ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
        actionBar.setTitle("Main activity");
        actionBar.setSubtitle("Bienvenido");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.ic_back); //ícono de la izquierda

    }



    public void onBtnGuardarClick(View view){
        Cuenta cuenta = new Cuenta();
        cuenta.setDenominacion(((TextView)findViewById(R.id.txtDenominacion)).getText().toString());
        cuenta.setDescripcion(((TextView)findViewById(R.id.txtDescripcion)).getText().toString());
        Float saldo =  Float.parseFloat (((TextView)findViewById(R.id.txtSaldo)).getText().toString());
        cuenta.setSaldo(saldo);
        Boolean resultado = false;
        try {
            resultado = cuentaDao.agregar(cuenta);
        }catch (ValidacionException ex){
            Toast toast = Toast.makeText(this, ex.getMensaje(), Toast.LENGTH_SHORT);
            toast.show();
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Falló al intentar agregar la cuenta.", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (resultado == true) {
            Toast toast = Toast.makeText(this, "Se agregó la cuenta correctamente.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
