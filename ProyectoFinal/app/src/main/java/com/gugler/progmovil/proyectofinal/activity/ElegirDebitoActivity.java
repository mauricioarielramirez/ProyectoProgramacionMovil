package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import com.gugler.progmovil.proyectofinal.adaptador.CuentaAdapter;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ElegirDebitoActivity extends BaseActivity {

//    private String CADENA_SQL;
//    private Inicializador inicializador;

    private ArrayList<Cuenta> listaCuentas;
    private CuentaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_debito);
        prepararStringSql();
        configurarInterface("");
        inicializarListView();
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Configurar Cuenta");
                actionBar.setSubtitle("Nueva cuenta");
        }
    }

    /**
     * Instancia el adaptador de cuenta e interactúa con el componente listview
     */
    private void inicializarListView(){
        listaCuentas = new ArrayList<Cuenta>();
        adapter = new CuentaAdapter(this,listaCuentas);
        ListView lstCuentas = (ListView)findViewById(R.id.lstCuentas);
        try{
            lstCuentas.setAdapter(adapter);
        }catch(Exception ex){
            throw  ex;
        }
        llenarListView();
        adapter.notifyDataSetChanged();
        lstCuentas.addHeaderView(findViewById(R.id.txvHeaderListView),null,false);
    }

    /**
     * Realiza la llamada para obtener las cuentas hacia el servicio
     */
    private void llenarListView(){
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,CADENA_SQL);
        ArrayList<Cuenta> cuentas;
        cuentas = sCuentas.listarTodo();
        listaCuentas.addAll(cuentas);
    }

}
