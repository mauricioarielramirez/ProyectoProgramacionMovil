package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.dao.MovimientoDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;
import com.gugler.progmovil.proyectofinal.servicio.ServicioMovimientos;
import com.gugler.progmovil.proyectofinal.watcher.CuentaWatcher;

import progmovil.gugler.com.pf.R;

public class ConfigurarCuentaActivity extends BaseActivity{

    private String tipoTransaccion;
    private String denominacionCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_cuenta);

        prepararStringSql();
        leerBundle();
        configurarInterface(tipoTransaccion);

        //Seteo de los watcher
        Button btnToolbarGuardar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnToolbarGuardar.setEnabled(false);
        EditText txtDenominacionCuenta = (EditText) findViewById(R.id.txtDenominacion);
        EditText txtDescripcion = (EditText) findViewById(R.id.txtDescripcion);
        EditText txtSaldo = (EditText) findViewById(R.id.txtSaldo);

        txtDenominacionCuenta.addTextChangedListener(new CuentaWatcher(txtDenominacionCuenta,txtDescripcion,txtSaldo, btnToolbarGuardar));
        txtDescripcion.addTextChangedListener(new CuentaWatcher(txtDenominacionCuenta,txtDescripcion,txtSaldo, btnToolbarGuardar));
        txtSaldo.addTextChangedListener(new CuentaWatcher(txtDenominacionCuenta,txtDescripcion,txtSaldo, btnToolbarGuardar));

        Button btnGuardarToolbar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnGuardarToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServicioCuentas sCuentas = new ServicioCuentas();
                sCuentas.crearBase(getApplicationContext(), ConfigurarCuentaActivity.super.CADENA_SQL);

                String denominacion = ((TextView) findViewById(R.id.txtDenominacion)).getText().toString();
                String descripcion = ((TextView) findViewById(R.id.txtDescripcion)).getText().toString();
                Float saldo = 0F;
                if ( !((TextView) findViewById(R.id.txtSaldo)).getText().toString().equals("")) {
                    saldo = Float.parseFloat(((TextView) findViewById(R.id.txtSaldo)).getText().toString());
                }

                Cuenta cuentaAgregar = new Cuenta(denominacion, descripcion, saldo);

                if (tipoTransaccion.equals("N") ) { //NUEVO
                    try {
                        Boolean res = sCuentas.agregarCuenta(cuentaAgregar);
                        if (res) {
                            Toast t = Toast.makeText(getApplicationContext(), "Cuenta agregada exitosamente", Toast.LENGTH_SHORT);
                            t.show();
                            onBackPressed();
                        }
                    } catch (ValidacionException vEx) {
                        Toast t = Toast.makeText(getApplicationContext(), vEx.getMensaje(), Toast.LENGTH_LONG);
                        t.show();
                    } catch (Exception ex) {
                        Toast t = Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG);
                        t.show();
                    }
                }

                if (tipoTransaccion.equals("M") ) { //MODIFICACION
                    try {
                        Boolean res = sCuentas.modificarCuenta(cuentaAgregar,denominacionCuenta);
                        if (res) {
                            Toast t = Toast.makeText(getApplicationContext(), "Cuenta modificada exitosamente", Toast.LENGTH_SHORT);
                            ServicioMovimientos servicioMovimientos = new ServicioMovimientos();
                            servicioMovimientos.crearBase(getApplicationContext(),CADENA_SQL);
                            servicioMovimientos.modificarHistoriaDeMovimiento(denominacionCuenta,cuentaAgregar.getDenominacion(), MovimientoDAO.CUENTA);
                            t.show();
                            onBackPressed();
                        }
                    } catch (ValidacionException vEx) {
                        Toast t = Toast.makeText(getApplicationContext(), vEx.getMensaje(), Toast.LENGTH_LONG);
                        t.show();
                    } catch (Exception ex) {
                        Toast t = Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG);
                        t.show();
                    }
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
        ActionBar actionBar = getSupportActionBar();
        switch (modo) {
            case "N": //Nuevo
                //ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Configurar Cuenta");
                actionBar.setSubtitle("Nueva cuenta");
                break;
            case "M": //Modificación
                //ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Configurar Cuenta");
                actionBar.setSubtitle("Modificar cuenta");
                cargarCuentaExistente();
                break;
            default:
                //ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(":/");
                actionBar.setSubtitle(":(");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepararStringSql();
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help, menu); //En menu.xml se definen
        return true;
    }

    public void leerBundle() {
        Bundle recurso = getIntent().getExtras();
        this.tipoTransaccion = recurso.getString("tipoTransaccion");
        this.denominacionCuenta = recurso.getString("denominacionCuenta");
    }

    public void cargarCuentaExistente() {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(getApplicationContext(), ConfigurarCuentaActivity.super.CADENA_SQL);
        EditText txtDenominacionCuenta = (EditText) findViewById(R.id.txtDenominacion);
        EditText txtDescripcionCuenta = (EditText) findViewById(R.id.txtDescripcion);
        EditText txtSaldo = (EditText) findViewById(R.id.txtSaldo);

        Cuenta cuenta = sCuentas.obtenerCuentaPorDenominacion(denominacionCuenta);
        txtDenominacionCuenta.setText(cuenta.getDenominacion());
        txtDescripcionCuenta.setText(cuenta.getDescripcion());
        txtSaldo.setText(cuenta.getSaldo().toString());
    }

}
