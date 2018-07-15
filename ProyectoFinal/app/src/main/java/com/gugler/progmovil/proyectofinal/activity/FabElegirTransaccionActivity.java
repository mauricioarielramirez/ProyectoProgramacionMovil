package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Space;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.adaptador.TransaccionAdapter;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;
import com.gugler.progmovil.proyectofinal.servicio.ServicioTransacciones;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class FabElegirTransaccionActivity extends BaseActivity {

    private ArrayList<Object> listaTransacciones;
    private TransaccionAdapter adapter;
    private String denominacionCuenta;
    private String tipoTransaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab_elegir_transaccion);
        prepararStringSql();
        leerBundle();
        configurarInterface(denominacionCuenta);
        inicializarListView();
        redimensionarListView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("denominacionCuenta",denominacionCuenta);
                bundle.putLong("idTransaccion",0);
                bundle.putString("tipoTransaccion",tipoTransaccion);

                // definir comportamiento para debito unico
                Intent intento = new Intent(getApplicationContext(), TransaccionActivity.class);
                intento.putExtras(bundle);
                startActivity(intento);
            }
        });

        ListView lstTransacciones = (ListView) findViewById(R.id.lstTransacciones);
        lstTransacciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                TextView txvNombreTransaccion = (TextView) view.findViewById(R.id.txvNombreTransaccion);
                TextView txvIdtr = (TextView) view.findViewById(R.id.txvIdtr);
                Transaccion transaccionElegida = new Transaccion();

                //Recupero la transacción
                for (Object t : listaTransacciones) {
                    if ( ((Transaccion)t).getId().equals(Long.parseLong(txvIdtr.getText().toString())) ){
                        transaccionElegida = (Transaccion)t;
                    }
                }
                bundle.putString("denominacionCuenta",denominacionCuenta);
                bundle.putLong("idTransaccion",Long.parseLong(txvIdtr.getText().toString()));
                bundle.putString("nombreTransaccion",txvNombreTransaccion.getText().toString());
                bundle.putString("tipoTransaccion",tipoTransaccion);
                bundle.putBoolean("favoritoTransaccion", transaccionElegida.getFavorito());
                bundle.putFloat("montoTransaccion",transaccionElegida.getMonto());

                Intent intento;
                if (denominacionCuenta.equals("")) {
                    intento = new Intent(getApplicationContext(), ConfigurarTransaccionActivity.class);
                } else {
                    intento = new Intent(getApplicationContext(), TransaccionActivity.class);
                }

                intento.putExtras(bundle);
                startActivity(intento);
            }
        });
    }

    private void configurarInterface(String modo) {
        ActionBar actionBar = getSupportActionBar();
        switch (modo) {
            case "": //Modo de edición (no hay cuentas, vino de AdministracionActivity > (ModificarTransacción))
                actionBar.setTitle("Modificar transacción");
                actionBar.setSubtitle("");
                FloatingActionButton btnFab = (FloatingActionButton) findViewById(R.id.fab);
                btnFab.setVisibility(View.GONE);
                break;
            default: //Entra si viene alguna cuenta (Vino de ElegirCuentaActivity)
                actionBar.setTitle("Elegir transacción");
                actionBar.setSubtitle("");
        }
    }

    private void leerBundle(){
        Bundle recurso = getIntent().getExtras();
        String nombreCuenta = recurso.getString("nombreCuenta");
        tipoTransaccion = recurso.getString("tipoTransaccion");
        denominacionCuenta = (nombreCuenta==null?"":nombreCuenta);
    }


    /**
     * Instancia el adaptador de transaccion e interactúa con el componente listview
     */
    private void inicializarListView(){
        listaTransacciones = new ArrayList<Object>();
        ListView lstTransacciones = (ListView)findViewById(R.id.lstTransacciones);
        //obtenerTransacciones();
        llenarListView(denominacionCuenta);
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
     * Se remueven las transacciones que no correspondan al tipo de transacción solicitado (Débito/Crédito)
     */
    private void llenarListView(String nombreCuenta){
        ServicioTransacciones sTransacciones = new ServicioTransacciones();
        sTransacciones.crearBase(this,CADENA_SQL);
        ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
        ArrayList<Transaccion> transaccionesFiltradas = new ArrayList<Transaccion>();
        try {
            if(nombreCuenta.equals("")) {
                transaccionesFiltradas = sTransacciones.listarTodo();
            }else {
                transacciones = sTransacciones.listarPorCuenta(this,CADENA_SQL,nombreCuenta);
                transaccionesFiltradas.addAll(transacciones);
                for(Transaccion tr: transacciones){
                    if (!tr.getTipo().equals(tipoTransaccion)) {
                        transaccionesFiltradas.remove(tr);
                    }
                }
            }


        } catch (ValidacionException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Error en la aplicación");
            alert.setMessage(e.getMensaje());
            alert.setPositiveButton("Ok", null);
            alert.show();
        }
        //listaTransacciones.add(" Transacciones");
        listaTransacciones.addAll(transaccionesFiltradas);
    }

    /**
     * Leer el bundle con el nombre de cuenta
     */
    private void obtenerTransacciones(){
        /*Bundle recurso = getIntent().getExtras();
        String nombreCuenta = recurso.getString("nombreCuenta");
        tipoTransaccion = recurso.getString("tipoTransaccion");
        denominacionCuenta = nombreCuenta;*/
        llenarListView(denominacionCuenta);
    }

    private void redimensionarListView() {
        final ListView lstTransacciones = (ListView)findViewById(R.id.lstTransacciones);
        lstTransacciones.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private Integer alto;
            private Integer altoMaximo;
            private ListView lstTransacciones1;
            private Integer items;

            /**
             * Esta lógica ayuda a disparar rutinas luego de que se haya cargado efectivamente el activity en pantalla
             * De no hacerlo, no es posible manipular los objetos visuales directamente desde OnCreate
             */
            @Override
            public void onGlobalLayout() {
                lstTransacciones1 = (ListView)findViewById(R.id.lstTransacciones);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    lstTransacciones1.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                alto = lstTransacciones1.getHeight();
                modificarAlto();
            }

            /**
             * Modifica el alto del listview de favoritos, teniendo como máximo tres elementos para mostrar (permite scroll)
             */
            public void modificarAlto() {
                try{
                    lstTransacciones1 = (ListView)findViewById(R.id.lstTransacciones);
                    ViewGroup.LayoutParams lstParams = lstTransacciones1.getLayoutParams();
                    items = lstTransacciones1.getCount();
                    TextView txvMensajeInformativo = (TextView) findViewById(R.id.txvMensajeInformativo);
                    Space spcMensajeInformativo = (Space) findViewById(R.id.spcMensajeInformativo);
                    if (items>0) { // Para no tener en cuenta la cabecera
                        spcMensajeInformativo.setVisibility(View.GONE);
                        txvMensajeInformativo.setText("");
                        txvMensajeInformativo.setVisibility(ViewGroup.GONE);
                    }else {
                        lstParams.height = 0;
                        ViewGroup.LayoutParams txvParams = txvMensajeInformativo.getLayoutParams();
                        txvMensajeInformativo.setText(" No existen transacciones definidas para esta cuenta");
                        txvParams.height = 200;
                        txvParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        txvMensajeInformativo.setVisibility(ViewGroup.VISIBLE);
                    }
                }catch (Exception ex){
                    return;
                }

            }
        });
    }

}
