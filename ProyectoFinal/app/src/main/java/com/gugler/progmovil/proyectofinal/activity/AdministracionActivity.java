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
                Bundle recurso = new Bundle();
                switch (txtIdOpcion.getText().toString().trim()){
                    case NUEVA_CUENTA:
                        //Bundle recurso = new Bundle();
                        recurso.putString("tipoTransaccion", "N");
                        intento = new Intent(getApplicationContext(),ConfigurarCuentaActivity.class);
                        intento.putExtras(recurso);
                        startActivity(intento);
                        break;
                    case NUEVA_TRANSACCION:
                        intento = new Intent(getApplicationContext(),ConfigurarTransaccionActivity.class);
                        startActivity(intento);
                        break;
                }

            }
        });
        ListView lstAdministrarModificar = (ListView) findViewById(R.id.lstAdminModificar);
        lstAdministrarModificar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txtIdOpcion = (TextView) view.findViewById(R.id.txvId);
                Intent intento;
                Bundle recurso = new Bundle();
                switch (txtIdOpcion.getText().toString().trim()){
                    case MODIFICAR_CUENTA:
                        recurso.putString("tipoTransaccion", "S"); //S: SETTING, N: NEW
                        intento = new Intent(getApplicationContext(),ElegirCuentaActivity.class);
                        intento.putExtras(recurso);
                        startActivity(intento);
                        break;
                    case MODIFICAR_TRANSACCION:
                        //Muestro todas las transacciones para modificar, pués podría cambiar la cuenta asociada
                        recurso.putString("tipoTransaccion","S"); //S: SETTING, N: NEW
                        intento = new Intent(getApplicationContext(),FabElegirTransaccionActivity.class);
                        intento.putExtras(recurso);
                        startActivity(intento);
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
        listaAdminCrear.add(new ListaItem(1," Cuenta",ListaItem.OPERACIONES_ADMINISTRAR_NUEVA_CUENTA));
        listaAdminCrear.add(new ListaItem(2," Transacción",ListaItem.OPERACIONES_ADMINISTRAR_NUEVA_TRANSACCION));
        listaAdminModificar.add(new ListaItem(3," Cuenta",ListaItem.OPERACIONES_ADMINISTRAR_MODIFICAR_CUENTA));
        listaAdminModificar.add(new ListaItem(4," Transacción",ListaItem.OPERACIONES_ADMINISTRAR_MODIFICAR_TRANSACCION));
        //listaAdminModificar.add(new ListaItem(5," Movimiento",ListaItem.OPERACIONES_ADMINISTRAR_MODIFICAR_MOVIMIENTO));
    }

}
