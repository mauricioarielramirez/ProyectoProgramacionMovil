package com.gugler.progmovil.proyectofinal.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.Spinner;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class TransaccionActivity extends BaseActivity {

    public final String TRANSACCION_NO_EDITABLE = "SoloConfirmarTransaccion";
    public final String TRANSACCION_EDITABLE = "EditarTransaccion";
    private ArrayList<String> listaTipoTransaccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        prepararStringSql();
        configurarInterface(TRANSACCION_NO_EDITABLE);
        inicializarSpinnerTipoTransaccion();
    }

    private void configurarInterface(String modo) {
        Spinner spnTipo = (Spinner)findViewById(R.id.spnTipoTransaccion);
        Spinner spnCuenta = (Spinner)findViewById(R.id.spnCuentaAsociada);
        EditText txtImporte = (EditText) findViewById(R.id.txtImporte);
        // Capturar botones del toolbar
        Button btnGuardar = (Button)findViewById(R.id.btnToolbarGuardar);
        Button btnCancelar = (Button)findViewById(R.id.btnToolbarCancelar);
        LinearLayout layoutGuardar = (LinearLayout) findViewById(R.id.lytLeftToolbar);
        LinearLayout layoutCancelar = (LinearLayout) findViewById(R.id.lytRightToolbar);
        Space spcLeft = (Space) findViewById(R.id.spcLeft);
        Space spcRight = (Space) findViewById(R.id.spcRight);
        EditText txtTransaccion = (EditText) findViewById(R.id.txtTransaccion);

        //Modificar la toolbar
        ActionBar actionBar = getSupportActionBar();
        switch (modo) {
            case TRANSACCION_NO_EDITABLE:
                cargarDatosTransaccion();

               //MODIFICAR EL TOOLBAR
                btnGuardar.setText("Confirmar");
                Resources resource = getResources();
                Drawable drawable = resource.getDrawable(R.drawable.ic_confirm);
                btnGuardar.setCompoundDrawablesWithIntrinsicBounds(null,drawable,null,null);
                layoutCancelar.setVisibility(View.GONE);
                ViewGroup.LayoutParams paramsSpaceLeft = spcLeft.getLayoutParams();
                paramsSpaceLeft.width = 120;
                ViewGroup.LayoutParams paramsSpaceRight = spcRight.getLayoutParams();
                paramsSpaceRight.width = 120;

                /*DESHABILITAR COMPONENTES Y NO PERMITIR FOCUS*/
                spnTipo.setEnabled(false);
                spnCuenta.setEnabled(false);
                txtImporte.setEnabled(false);
                txtImporte.setFocusable(false);
                txtTransaccion.setEnabled(false);
                txtTransaccion.setFocusable(false);


                actionBar.setTitle("Transacción");
                actionBar.setSubtitle("Confirmar transacción");
                /*
                BLOQUEAR SPINNERS Y EDITTEXT
                CAMBIAR TITULOS
                */
                break;
            case TRANSACCION_EDITABLE:

                /*
                HABILITAR SPINNERS Y EDITTEXT
                CAMBIAR TITULOS
                */
                actionBar.setTitle("Transacción");
                actionBar.setSubtitle("Editar transacción");
                break;
        }
    }


    private void cargarDatosTransaccion(){

    }

    /**
     * TEMPORALMENTE PARA PROBAR EL SPINNER DE CUENTAS
     */
    private void inicializarSpinnerTipoTransaccion(){
        listaTipoTransaccion = new ArrayList<String>();
        listaTipoTransaccion.add("Carga de crédito teléfono Claro de Eric Daniel Pennachini Corporativo");
        listaTipoTransaccion.add("Compra diaria de comida");
        listaTipoTransaccion.add("Depósito efectivo mensual");
        listaTipoTransaccion.add("Carga de tarjebus");
        listaTipoTransaccion.add("Pasaje diario SUBE");
        Spinner spn1 = (Spinner) findViewById(R.id.spnTipoTransaccion);
        Spinner spn2 = (Spinner) findViewById(R.id.spnCuentaAsociada);

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listaTipoTransaccion);

            spn1.setAdapter(adapter);
            spn2.setAdapter(adapter);
        } catch(Exception ex) {
            throw ex;
        }
    }
}
