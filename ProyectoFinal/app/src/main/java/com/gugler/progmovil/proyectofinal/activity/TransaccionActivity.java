package com.gugler.progmovil.proyectofinal.activity;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Space;

import com.gugler.progmovil.proyectofinal.modelo.Transaccion;
import com.gugler.progmovil.proyectofinal.servicio.ServicioTransacciones;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class TransaccionActivity extends BaseActivity {

    public final String TRANSACCION_NO_EDITABLE = "SoloConfirmarTransaccion";
    public final String TRANSACCION_EDITABLE = "EditarTransaccion";
    private ArrayList<String> listaTipoTransaccion;
    private String denominacionCuenta;
    private String nombreTransaccion;
    private Long idTransaccion;
    private String tipoTransaccion;
    private Float  importe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        prepararStringSql();
        leerBundle();
        if (nombreTransaccion.equals("")) {
            configurarInterface(TRANSACCION_EDITABLE);
        } else {
            configurarInterface(TRANSACCION_NO_EDITABLE);
        }
    }

    private void configurarInterface(String modo) {
        // Capturar botones del toolbar
        Button btnGuardar = (Button)findViewById(R.id.btnToolbarGuardar);
        Button btnCancelar = (Button)findViewById(R.id.btnToolbarCancelar);
        LinearLayout layoutGuardar = (LinearLayout) findViewById(R.id.lytLeftToolbar);
        LinearLayout layoutCancelar = (LinearLayout) findViewById(R.id.lytRightToolbar);
        Space spcLeft = (Space) findViewById(R.id.spcLeft);
        Space spcRight = (Space) findViewById(R.id.spcRight);

        //CAPTURAR COMPONENTES
        EditText txtTransaccion = (EditText) findViewById(R.id.txtTransaccion);
        EditText txtImporte = (EditText) findViewById(R.id.txtImporte);
        EditText txtTipoTransaccion = (EditText) findViewById(R.id.txtTipoTransaccion);
        EditText txtDenominacionCuenta = (EditText) findViewById(R.id.txtCuentaAsociada);

        //Modificar la toolbar
        ActionBar actionBar = getSupportActionBar();
        switch (modo) {
            case TRANSACCION_NO_EDITABLE:
                //ESTABLECER VALORES EN COMPONENTES
                txtTransaccion.setText(this.nombreTransaccion);
                txtTipoTransaccion.setText(this.tipoTransaccion.trim().equals("D") ? "Débito":"Crédito");
                txtImporte.setText("$ " + this.importe);
                txtDenominacionCuenta.setText(this.denominacionCuenta);

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
                txtTipoTransaccion.setEnabled(false);
                txtDenominacionCuenta.setEnabled(false);
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
                txtTransaccion.setText("Transacción genérica");
                txtDenominacionCuenta.setText(this.denominacionCuenta);
                txtTipoTransaccion.setText(this.tipoTransaccion.trim().equals("D") ? "Débito":"Crédito");

                txtDenominacionCuenta.setEnabled(false);
                txtTipoTransaccion.setEnabled(false);

                txtImporte.setEnabled(true);
                txtImporte.setFocusable(true);

                actionBar.setTitle("Transacción");
                actionBar.setSubtitle("Editar transacción");
                break;
        }
    }

    /**
     * Obtiene los datos que llegan desde la activity que lo invoca
     */
    private void leerBundle() {
        Bundle recurso = getIntent().getExtras();
        this.denominacionCuenta = recurso.getString("denominacionCuenta");
        this.idTransaccion = recurso.getLong("idTransaccion");
        this.tipoTransaccion = recurso.getString("tipoTransaccion");
        this.nombreTransaccion = "";
        if (this.idTransaccion !=0){
            obtenerTransaccion(this.idTransaccion);
        }
    }


    private void obtenerTransaccion(Long idTransaccion) {
        ServicioTransacciones sTransacciones = new ServicioTransacciones();
        Transaccion transaccion = new Transaccion();

        sTransacciones.crearBase(getApplicationContext(),CADENA_SQL);

        transaccion = sTransacciones.obtenerTransaccionPorId(idTransaccion);
        this.importe = transaccion.getMonto();
        this.tipoTransaccion = transaccion.getTipo();
        this.nombreTransaccion = transaccion.getNombre();
    }
}
