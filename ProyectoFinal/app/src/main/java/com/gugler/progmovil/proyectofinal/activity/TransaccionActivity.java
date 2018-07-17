package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
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
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.modelo.Movimiento;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;
import com.gugler.progmovil.proyectofinal.servicio.ServicioMovimientos;
import com.gugler.progmovil.proyectofinal.servicio.ServicioTransacciones;

import java.util.ArrayList;
import java.util.Calendar;

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

        Button btnGuardar = (Button)findViewById(R.id.btnToolbarGuardar);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicioMovimientos servicioMovimientos = new ServicioMovimientos();
                servicioMovimientos.crearBase(getApplicationContext(),CADENA_SQL);

                Movimiento movimiento = new Movimiento();

                //Si es transacción genérica tomo el valor de los campos modificables
                if (idTransaccion == 0) {
                    EditText txtImporte = (EditText) findViewById(R.id.txtImporte);
                    EditText txtTransaccion = (EditText) findViewById(R.id.txtTransaccion);
                    nombreTransaccion = txtTransaccion.getText().toString();
                    importe = Float.parseFloat(txtImporte.getText().toString());
                }

                movimiento.setCuentaAsociada(denominacionCuenta);
                movimiento.setTipo(tipoTransaccion);
                movimiento.setFechaHora(Calendar.getInstance().getTime());
                movimiento.setMonto(importe);
                movimiento.setTransaccion(nombreTransaccion);

                try {
                    if (movimiento.getMonto() > 0) {
                        servicioMovimientos.agregarMovimiento(movimiento,getApplicationContext(),CADENA_SQL);
                        Toast tExito = Toast.makeText(getApplicationContext(), "Movimiento realizado exitosamente", Toast.LENGTH_SHORT);
                        tExito.show();
                    } else {
                        Toast tMensaje = Toast.makeText(getApplicationContext(), "No se guardará una transacción de $0", Toast.LENGTH_SHORT);
                        tMensaje.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast tError = Toast.makeText(getApplicationContext(), "Error al realizar movimiento: " + e.getMessage(), Toast.LENGTH_LONG);
                    tError.show();
                } finally {
                    Intent intento = new Intent(getApplicationContext(), NormalActivity.class);
                    startActivity(intento);
                }
            }
        });

        Button btnToolbarCancelar = (Button) findViewById(R.id.btnToolbarCancelar);
        btnToolbarCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    onBackPressed();
                } catch (Exception ex) {

                }
            }
        });
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
                txtTipoTransaccion.setFocusable(false);
                txtDenominacionCuenta.setEnabled(false);
                txtDenominacionCuenta.setFocusable(false);
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
                if (this.importe!=null){
                    txtImporte.setText(this.importe.toString());
                }else {
                    txtImporte.setText("0");
                }

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
