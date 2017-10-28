package com.gugler.progmovil.proyectofinal.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.gugler.progmovil.proyectofinal.adaptador.TransaccionAdapter;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
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
        redimensionarListView();
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
        ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
        try {
            transacciones = sTransacciones.listarPorCuenta(this,CADENA_SQL,nombreCuenta);
        } catch (ValidacionException e) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Error en la aplicación");
            alert.setMessage(e.getMensaje());
            alert.setPositiveButton("Ok", null);
            alert.show();
        }
        //listaTransacciones.add(" Transacciones");
        listaTransacciones.addAll(transacciones);
    }

    private void obtenerTransacciones(){
        Bundle recurso = getIntent().getExtras();
        String nombreCuenta = recurso.getString("nombreCuenta");
        llenarListView(nombreCuenta);
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
                        LinearLayout lytDebitoUnico = (LinearLayout)findViewById(R.id.lytDebitoUnico);
                        RelativeLayout lytRelativePadre = (RelativeLayout) findViewById(R.id.lytRelativePadre);
                        Space spcDivisor = (Space) findViewById(R.id.spcDivisor);
                        //lstParams.height = lytRelativePadre.getHeight() - lytDebitoUnico.getHeight()-spcDivisor.getHeight();
                        altoMaximo  = lytRelativePadre.getHeight() - lytDebitoUnico.getHeight()-spcDivisor.getHeight();
                        spcMensajeInformativo.setVisibility(View.GONE);
                        txvMensajeInformativo.setText("");
                        txvMensajeInformativo.setVisibility(ViewGroup.GONE);
                        if (lstTransacciones1.getHeight() > altoMaximo) {
                            lstParams.height = altoMaximo;
                        } else {
                            lstParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                        }
                    }else {
                        lstParams.height = 0;
                        txvMensajeInformativo.setVisibility(ViewGroup.VISIBLE);
                        txvMensajeInformativo.setText(" No existen transacciones definidas para esta cuenta");
                    }
                }catch (Exception ex){
                    return;
                }

            }
        });
    }
}
