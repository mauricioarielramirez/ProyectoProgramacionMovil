package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.CuentaAdapter;
import com.gugler.progmovil.proyectofinal.adaptador.ListAdapter;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;
import com.gugler.progmovil.proyectofinal.servicio.ServicioCuentas;
import com.gugler.progmovil.proyectofinal.servicio.ServicioTransacciones;

import java.util.ArrayList;
import java.util.List;

import progmovil.gugler.com.pf.R;

public class ConfigurarTransaccionActivity extends BaseActivity {

    private TextView mTextMessage;
    private List<String> listaCuentasAsociadas;
    private CuentaAdapter adapter;
    private ServicioTransacciones sTransacciones;

    private ArrayList<String> listaTipoTransaccion;
    private ListAdapter adapterOperaciones;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_guardar:
                    //mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.action_cancelar:
                    //mTextMessage.setText(R.string.title_dashboard);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_transaccion);

        prepararStringSql();
        configurarInterface("");
        inicializarSpinnerTipoTransaccion();

        Button btnGuardarToolbar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnGuardarToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast toast = Toast.makeText(getApplicationContext(), "Btn guardar click", Toast.LENGTH_SHORT);
                toast.show();*/
                Transaccion transaccion = new Transaccion();
                transaccion.setId(null);
                transaccion.setNombre( ((EditText)findViewById(R.id.txtNombre)).getText().toString() );
                transaccion.setMonto( Float.parseFloat(((EditText)findViewById(R.id.txtSaldo)).getText().toString()) );
                transaccion.setFavorito( ((CheckBox)findViewById(R.id.chkFavortio)).isChecked() );

                String tipoTransaccion = ((Spinner) findViewById(R.id.spnTipoTransaccion)).getSelectedItem().toString();
                String denominacionCuenta = ((Spinner) findViewById(R.id.spnCtasAsociadas)).getSelectedItem().toString();

                switch (tipoTransaccion) {
                    case "Débito":
                        transaccion.setTipo("D");
                        break;
                    case "Crédito":
                        transaccion.setTipo("C");
                        break;
                    default:
                        transaccion.setTipo("D");
                        break;
                }

                sTransacciones = new ServicioTransacciones();
                sTransacciones.crearBase(getApplicationContext(),ConfigurarTransaccionActivity.super.CADENA_SQL);
                try {
                    sTransacciones.agregarTransaccion(getApplicationContext(),ConfigurarTransaccionActivity.super.CADENA_SQL,transaccion, denominacionCuenta);
                    Toast toastEx = Toast.makeText(getApplicationContext(), "Transacción agregada exitosamente", Toast.LENGTH_SHORT);
                    toastEx.show();
                    onBackPressed();
                } catch (Exception e) {
                    Toast toastEx = Toast.makeText(getApplicationContext(), ValidacionException.PROBLEMAS_ALTA_TRANSACCION, Toast.LENGTH_SHORT);
                    toastEx.show();
                }
            }
        });

//        Spinner spn = (Spinner) findViewById(R.id.spnCtasAsociadas);
        //CuentaAdapter adapter = new CuentaAdapter
        inicializarSpinner();
    }

    private void configurarInterface(String modo) {
        switch (modo) {
            default:
                ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle("Configurar transacción");
                actionBar.setSubtitle("");
        }
    }

    /*Botón de ayuda*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    private void inicializarSpinner(){
        listaCuentasAsociadas = new ArrayList<String>();
        Spinner spn = (Spinner) findViewById(R.id.spnCtasAsociadas);
        llenarSpinner();

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaCuentasAsociadas);
            spn.setAdapter(adapter);
        } catch(Exception ex) {
            throw ex;
        }
//        adapter.notifyDataSetChanged();
    }

    private void llenarSpinner() {
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(this, CADENA_SQL);
        ArrayList<Cuenta> cuentas;
        cuentas = sCuentas.listarTodo();
        for (Cuenta c: cuentas){
            listaCuentasAsociadas.add(c.toString());
        }
    }


    /**
     * Inicializa spinner de tipo de transacciones
     */
    private void inicializarSpinnerTipoTransaccion(){
        listaTipoTransaccion = new ArrayList<String>();
        listaTipoTransaccion.add("Débito");
        listaTipoTransaccion.add("Crédito");
        Spinner spn = (Spinner) findViewById(R.id.spnTipoTransaccion);

        try {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, listaTipoTransaccion);
            spn.setAdapter(adapter);
        } catch(Exception ex) {
            throw ex;
        }
    }
}
