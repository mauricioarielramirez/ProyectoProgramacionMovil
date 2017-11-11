package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.adaptador.ListAdapter;
import com.gugler.progmovil.proyectofinal.modelo.dto.ListaItem;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class AdministracionActivity extends BaseActivity {

    private ArrayList<Object> listaAdminCrear;
    private ArrayList<Object> listaAdminModificar;
    private ListAdapter adapterAdminCrear;
    private ListAdapter adapterAdminModificar;

    private final String NUEVA_CUENTA = "1";
    private final String NUEVA_TRANSACCION = "2";
    private final String MODIFICAR_CUENTA = "3";
    private final String MODIFICAR_TRANSACCION = "4";
    private final String MODIFICAR_MOVIMIENTO = "5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administracion);
        prepararStringSql();
        configurarInterface("");
        inicializarListViews();

        ListView lstAdministrarNuevo = (ListView) findViewById(R.id.lstAdminNuevo);
        lstAdministrarNuevo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtIdOpcion = (TextView) view.findViewById(R.id.txvId);
                Intent intento;
                switch (txtIdOpcion.getText().toString().trim()){
                    case NUEVA_CUENTA:
                        intento = new Intent(getApplicationContext(),ConfigurarCuentaActivity.class);
                        startActivity(intento);
                        break;
                    case NUEVA_TRANSACCION:
                        intento = new Intent(getApplicationContext(),ConfigurarTransaccionActivity.class);
                        startActivity(intento);
                        break;
                    case MODIFICAR_CUENTA:
                        break;
                    case MODIFICAR_TRANSACCION:
                        break;
                    case MODIFICAR_MOVIMIENTO:
                        break;
                }

            }
        });
    }

    public void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Administrar");
                actionBar.setSubtitle("");
        }
    }

    private void inicializarListViews(){
        listaAdminCrear = new ArrayList<Object>();
        listaAdminModificar = new ArrayList<Object>();
        ListView lstAdminNuevo = (ListView)findViewById(R.id.lstAdminNuevo);
        ListView lstAdminModificar = (ListView)findViewById(R.id.lstAdminModificar);
        llenarListViews();
        try {
            adapterAdminCrear = new ListAdapter(this, listaAdminCrear);
            lstAdminNuevo.setAdapter(adapterAdminCrear);
            adapterAdminModificar = new ListAdapter(this, listaAdminModificar);
            lstAdminModificar.setAdapter(adapterAdminModificar);
        } catch(Exception ex) {
            throw  ex;
        }
        adapterAdminCrear.notifyDataSetChanged();
        adapterAdminModificar.notifyDataSetChanged();
    }

    private void llenarListViews(){
        listaAdminCrear.add(new ListaItem(1," Cuenta"));
        listaAdminCrear.add(new ListaItem(2," Transacción"));
        listaAdminModificar.add(new ListaItem(3," Cuenta"));
        listaAdminModificar.add(new ListaItem(4," Transacción"));
        listaAdminModificar.add(new ListaItem(5," Movimiento"));
    }

}
