package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.CuentaAdapter;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ElegirDebitoActivity extends BaseActivity {

//    private String CADENA_SQL;
//    private Inicializador inicializador;

    private ArrayList<Object> listaCuentas;
    private CuentaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_debito);
        prepararStringSql();
        configurarInterface("");
        inicializarListView();

        ListView listView = (ListView)findViewById(R.id.lstCuentas);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView txvNombreCuenta = (TextView) view.findViewById(R.id.txvDenominacionCuenta);
                    TextView txvSaldo = (TextView) view.findViewById(R.id.txvSaldo);

                    Toast.makeText(getApplicationContext(), txvNombreCuenta.getText(), Toast.LENGTH_LONG).show();
                }
        });
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Elegir débito");
                actionBar.setSubtitle("Seleccione una cuenta");
        }
    }

    /**
     * Instancia el adaptador de cuenta e interactúa con el componente listview
     */
    private void inicializarListView(){
        listaCuentas = new ArrayList<Object>();
        ListView lstCuentas = (ListView)findViewById(R.id.lstCuentas);
        llenarListView();
        try{
            adapter = new CuentaAdapter(this,listaCuentas);
            lstCuentas.setAdapter(adapter);
        }catch(Exception ex){
            throw  ex;
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * Realiza la llamada para obtener las cuentas hacia el servicio
     */
    private void llenarListView(){
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,CADENA_SQL);
        ArrayList<Cuenta> cuentas;
        cuentas = sCuentas.listarTodo();
        listaCuentas.add(" Cuentas");
        listaCuentas.addAll(cuentas);
    }

}
