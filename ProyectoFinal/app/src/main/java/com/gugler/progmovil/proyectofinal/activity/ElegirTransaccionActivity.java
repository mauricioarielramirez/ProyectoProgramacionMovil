package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import com.gugler.progmovil.proyectofinal.adaptador.TransaccionAdapter;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;
import com.gugler.progmovil.proyectofinal.servicio.ServicioTransacciones;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ElegirTransaccionActivity extends BaseActivity {

    private ArrayList<Object> listaTransacciones;
    private TransaccionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_transaccion);
        prepararStringSql();
        configurarInterface("");
        inicializarListView();
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Elegir transacción");
                actionBar.setSubtitle("");
        }
    }

    /**
     * Instancia el adaptador de transaccion e interactúa con el componente listview
     */
    private void inicializarListView(){
        listaTransacciones = new ArrayList<Object>();
        ListView lstTransacciones = (ListView)findViewById(R.id.lstTransacciones);
        obtenerTransacciones();
        try {
            adapter = new TransaccionAdapter(this,listaTransacciones);
            lstTransacciones.setAdapter(adapter);
        } catch(Exception ex) {
            throw  ex;
        }
        // adapter.notifyDataSetChanged();
    }

    /**
     * Realiza la llamada para obtener las cuentas hacia el servicio
     */
    private void llenarListView(String nombreCuenta){
        ServicioTransacciones sTransacciones = new ServicioTransacciones();
        sTransacciones.crearBase(this,CADENA_SQL);
        ArrayList<Transaccion> transacciones;
        transacciones = sTransacciones.listarPorCuenta(this,CADENA_SQL,nombreCuenta);
        listaTransacciones.add(" Transacciones");
        listaTransacciones.addAll(transacciones);
    }

    private void obtenerTransacciones(){
        Bundle recurso = getIntent().getExtras();
        String nombreCuenta = recurso.getString("nombreCuenta");
        llenarListView(nombreCuenta);
    }

}
