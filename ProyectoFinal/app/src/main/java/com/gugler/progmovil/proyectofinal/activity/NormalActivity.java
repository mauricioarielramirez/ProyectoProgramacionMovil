package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.ListAdapter;
import com.gugler.progmovil.proyectofinal.modelo.dto.ListaItem;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class NormalActivity extends BaseActivity {
    private ArrayList<String> listaCtas;
    private ArrayList<Object> listaOperaciones;
    private ListAdapter adapterOperaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        prepararStringSql();
        configurarInterface("");
        inicializarListViewOperaciones();

        ListView lst = (ListView) findViewById(R.id.lstOperaciones);
        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txvId = (TextView) view.findViewById(R.id.txvId);
                TextView txvDescripcion = (TextView) view.findViewById(R.id.txvDescripcion);

                Toast.makeText(getApplicationContext(), txvId.getText().toString() +" "+txvDescripcion.getText(), Toast.LENGTH_SHORT).show();

                switch (txvDescripcion.getText().toString()){
                    case "Débito":
                        Intent intento = new Intent(getApplicationContext(),ElegirDebitoActivity.class);
                        startActivity(intento);
                        break;
                    case "Crédito":
                        break;
                    case "Consultas":
                        break;
                    case "Administrar":
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Seguimiento de efectivo");
                actionBar.setSubtitle("Elegir acción");
        }
    }


    public void llenarFavoritos() {

    }

    private void inicializarListViewOperaciones(){
        listaOperaciones = new ArrayList<Object>();
        ListView lstOperaciones = (ListView)findViewById(R.id.lstOperaciones);
        llenarListView();
        try{
            adapterOperaciones = new ListAdapter(this,listaOperaciones);
            lstOperaciones.setAdapter(adapterOperaciones);
        }catch(Exception ex){
            throw  ex;
        }
        adapterOperaciones.notifyDataSetChanged();
    }

    private void llenarListView(){
        listaOperaciones.add(new ListaItem(1,"Débito"));
        listaOperaciones.add(new ListaItem(2,"Crédito"));
        listaOperaciones.add(new ListaItem(3,"Consultas"));
        listaOperaciones.add(new ListaItem(4,"Administrar"));

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        moveTaskToBack(true);
    }

    private Boolean existenCuentas() {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,CADENA_SQL);
        if (sCuentas.listarTodo().isEmpty()){
            return false;
        }else{
            return true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepararStringSql();
        if (existenCuentas() == false) {
            Intent intento = new Intent(this, InicioActivity.class);
            startActivity(intento);
        }
    }
}
