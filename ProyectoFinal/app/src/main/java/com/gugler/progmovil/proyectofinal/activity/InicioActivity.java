package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import progmovil.gugler.com.pf.R;

public class InicioActivity extends AppCompatActivity {
    public static StringBuffer CADENA_SQL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        leerScript();
        inicializarActivity();
        configurarVisualActivity(null);
    }

    /**
     * Determina Titulo, subtítulo, llama a la carga de menues, y toda acción requerida que sea propio del aspacto visual
     * @param modo Bandera para indicar la modificación de algún comportamiento en el aspecto visual (utilizar constantes)
     */
    private void configurarVisualActivity(String modo){
        ActionBar actionBar = getSupportActionBar(); // Permite personalizar el action bar
        actionBar.setTitle("Seguimiento del efectivo");
        actionBar.setSubtitle("Bienvenido");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentoConfigCuenta = new Intent(view.getContext(), ConfigurarCuenta.class);
                startActivity(intentoConfigCuenta);
            }
        });
    }

    /**
     * Determina que activity presentar, dependiendo si no existen cuentas (primer ingreso) o sí las hay (siguientes ingresos)
     */
    private void inicializarActivity() {
        if (existenCuentas()){
            Intent intentoElegirDebito = new Intent(this, ElegirDebitoActivity.class);
            startActivity(intentoElegirDebito);
        }
    }

    /**
     * Comprueba si existen cuentas haciendo la llamada al servicio
     * @return true si existen, false si no
     */
    private Boolean existenCuentas() {
        ServicioCuentas sCuentas = new ServicioCuentas(this,this.CADENA_SQL.toString());
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
     * Carga las sentencias SQL necesarias para la creación de la BD
     */
    private void leerScript() {
        CADENA_SQL = new StringBuffer();
        try {
            InputStream inputStream = getResources().openRawResource(R.raw.script);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            for (String linea; (linea=reader.readLine())!=null;){
                CADENA_SQL.append(linea);
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(this, "Problemas al intentar leer el archivo.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * Si se ejecuta OnResume es porque se ejecutó una activity desde aquí. En este caso llama a inicializarActivity para determinar si se redirecciona o no.
     */
    @Override
    protected void onResume() {
        super.onResume();
        inicializarActivity();
    }
}
