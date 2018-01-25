package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.CuentaAdapter;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;
import com.gugler.progmovil.proyectofinal.servicio.ServicioTransacciones;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ElegirCuentaActivity extends BaseActivity {

    private ArrayList<Object> listaCuentas;
    private CuentaAdapter adapter;
    private String tipoTransaccion;
    private String idTransaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_cuenta);
        prepararStringSql();
        leerBundle();
        configurarInterface(tipoTransaccion);
        inicializarListView();

        ListView listView = (ListView)findViewById(R.id.lstCuentas);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Listener para el evento onItemClic (navegación hacia la transacción de la cuenta)
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView txvNombreCuenta = (TextView) view.findViewById(R.id.txvDenominacionCuenta);
                TextView txvSaldo = (TextView) view.findViewById(R.id.txvSaldo);
                Intent intento;
                Bundle recurso;
                // Toast.makeText(getApplicationContext(), txvNombreCuenta.getText(), Toast.LENGTH_LONG).show();
                if (idTransaccion==null) {
                    intento = new Intent(getApplicationContext(),FabElegirTransaccionActivity.class);
                    recurso = new Bundle();
                    recurso.putString("nombreCuenta", txvNombreCuenta.getText().toString());
                    recurso.putString("tipoTransaccion",tipoTransaccion);

                    intento.putExtras(recurso);

                    startActivity(intento);
                } else {
                    intento = new Intent(getApplicationContext(), TransaccionActivity.class);

                    recurso = new Bundle();
                    recurso.putString("denominacionCuenta", txvNombreCuenta.getText().toString());
                    recurso.putString("tipoTransaccion",tipoTransaccion);
                    recurso.putLong("idTransaccion", Long.parseLong(idTransaccion));

                    intento.putExtras(recurso);

                    startActivity(intento);
                }

            }
        });
    }

    private void configurarInterface(String modo) {
        ActionBar actionBar;
        switch (modo) {
            case "D":
                actionBar = getSupportActionBar();
                actionBar.setTitle("Elegir débito");
                actionBar.setSubtitle("Seleccione una cuenta");
                break;
            case "C":
                actionBar = getSupportActionBar();
                actionBar.setTitle("Elegir Crédito");
                actionBar.setSubtitle("Seleccione una cuenta");
                break;
            default:
                actionBar = getSupportActionBar();
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
     * Contiene la lógica necesaria para evaluar si es llamado desde favoritos (se carga idTransaccion) o por operatoria normal(se carga sólo idTransaccion)
     */
    private void llenarListView(){
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this,CADENA_SQL);
        ArrayList<Cuenta> cuentas;
        try {
            if (idTransaccion == null) {
                cuentas = sCuentas.listarTodo();
            } else {
                ServicioTransacciones sTransacciones = new ServicioTransacciones();
                sTransacciones.crearBase(getApplicationContext(),CADENA_SQL);
                cuentas = sTransacciones.obtenerCuentasPorTransaccion(getApplicationContext(), CADENA_SQL, Long.parseLong(idTransaccion));
            }
            listaCuentas.add(" Cuentas");
            listaCuentas.addAll(cuentas);
        }catch (Exception ex){
            Toast toast = Toast.makeText(getApplicationContext(), ValidacionException.PROBLEMAS_LEER_CUENTA,Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void leerBundle() {
        Bundle recurso = getIntent().getExtras();
        this.tipoTransaccion = recurso.getString("tipoTransaccion");
        // Si es llamado de favoritos, se carga este valor
        this.idTransaccion = recurso.getString("idTransaccion");
    }


//    @Override
//    protected void onResume() {
//        super.onResume();
//        prepararStringSql();
//        inicializarListView();
//    }
}
