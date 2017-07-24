package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.gugler.progmovil.proyectofinal.adaptador.CuentaAdapter;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ElegirDebitoActivity extends AppCompatActivity {

    private ArrayList<Cuenta> listaCuentas;
    private CuentaAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_debito);
        inicializarListView();
    }

    /**
     * Instancia el adaptador de cuenta e interactúa con el componente listview
     */
    private void inicializarListView(){
        listaCuentas = new ArrayList<Cuenta>();
        adapter = new CuentaAdapter(this,listaCuentas);
        ListView lstCuentas = (ListView)findViewById(R.id.lstCuentas);
        lstCuentas.setAdapter(adapter);
        llenarListView();
        adapter.notifyDataSetChanged();
    }

    /**
     * Realiza la llamada para obtener las cuentas hacia el servicio
     */
    private void llenarListView(){
        ServicioCuentas sCuentas = new ServicioCuentas(this,InicioActivity.CADENA_SQL.toString());
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        cuentas = sCuentas.listarTodo();
        listaCuentas.addAll(cuentas);
    }

}
