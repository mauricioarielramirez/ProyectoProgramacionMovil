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
import android.widget.Spinner;

import progmovil.gugler.com.pf.R;

public class TransaccionActivity extends BaseActivity {

    public final String TRANSACCION_NO_EDITABLE = "SoloConfirmarTransaccion";
    public final String TRANSACCION_EDITABLE = "EditarTransaccion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaccion);
        prepararStringSql();
        configurarInterface(TRANSACCION_NO_EDITABLE);
    }

    private void configurarInterface(String modo) {
        Spinner spnTransaccion = (Spinner)findViewById(R.id.spnTransaccion);
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

        //Modificar la toolbar
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

                spnTransaccion.setEnabled(false);
                spnTipo.setEnabled(false);
                spnCuenta.setEnabled(false);
                txtImporte.setEnabled(false);
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
            break;
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Transacción");
                actionBar.setSubtitle("");
        }
    }

    private void cargarDatosTransaccion(){

    }
}
