package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.CuentaAdapter;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

public class ConfigurarTransaccionActivity extends BaseActivity {

    private TextView mTextMessage;
    private List<String> listaCuentasAsociadas;
    private CuentaAdapter adapter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_guardar:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.action_cancelar:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_transaccion);

        prepararStringSql();
        configurarInterface("");

        Button btnGuardarToolbar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnGuardarToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getApplicationContext(), "Btn guardar click", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

//        Spinner spn = (Spinner) findViewById(R.id.spnCtasAsociadas);
        //CuentaAdapter adapter = new CuentaAdapter
        inicializarSpinner();
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Configurar transacción");
                actionBar.setSubtitle("");
        }
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    private void inicializarSpinner(){
        listaCuentasAsociadas = new ArrayList<String>();
        Spinner spn = (Spinner) findViewById(R.id.spnCtasAsociadas);
        llenarSpinner();

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaCuentasAsociadas);
            spn.setAdapter(adapter);
        } catch(Exception ex) {
            throw ex;
        }
//        adapter.notifyDataSetChanged();
    }

    private void llenarSpinner() {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this, CADENA_SQL);
        ArrayList<Cuenta> cuentas;
        cuentas = sCuentas.listarTodo();
        for (Cuenta c: cuentas){
            listaCuentasAsociadas.add(c.toString());
        }
    }
}
