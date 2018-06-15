package com.gugler.progmovil.proyectofinal.activity;

import android.content.Intent;
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
import com.gugler.progmovil.proyectofinal.watcher.TransaccionWatcher;

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

    private String nombreTransaccion;
    private Long idTransaccion;
    private String tipoTransaccion;
    private Float montoTransaccion;
    private Boolean favoritoTransaccion;

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
        leerBundle();
        configurarInterface("");
        inicializarSpinnerTipoTransaccion();
        if (this.nombreTransaccion != null) {
            cargarCampos();
        }

        //Seteo de watcher
        Button btnToolbarGuardar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnToolbarGuardar.setEnabled(false);
        EditText txtNombreTransaccion = (EditText) findViewById(R.id.txtNombre);
        EditText txtSaldo = (EditText) findViewById(R.id.txtSaldo);

        txtNombreTransaccion.addTextChangedListener(new TransaccionWatcher(txtNombreTransaccion,txtSaldo,btnToolbarGuardar));
        txtSaldo.addTextChangedListener(new TransaccionWatcher(txtNombreTransaccion,txtSaldo,btnToolbarGuardar));

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
                    if (sTransacciones.agregarTransaccion(getApplicationContext(),ConfigurarTransaccionActivity.super.CADENA_SQL,transaccion, denominacionCuenta)){
                        Toast toastEx = Toast.makeText(getApplicationContext(), "Transacción agregada exitosamente", Toast.LENGTH_SHORT);
                        toastEx.show();
                        Intent intento = new Intent(getApplicationContext(), NormalActivity.class);
                        startActivity(intento);
                    }else{
                        Toast toastEx = Toast.makeText(getApplicationContext(), "El nombre de transacción ya existe, escriba otro.", Toast.LENGTH_LONG);
                        toastEx.show();
                        TextView textView = (TextView)findViewById(R.id.txtNombre);
                        textView.requestFocus();
                    }

                    // onBackPressed();

                } catch (Exception e) {
                    Toast toastEx = Toast.makeText(getApplicationContext(), ValidacionException.PROBLEMAS_ALTA_TRANSACCION, Toast.LENGTH_SHORT);
                    toastEx.show();
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

//        Spinner spn = (Spinner) findViewById(R.id.spnCtasAsociadas);
        //CuentaAdapter adapter = new CuentaAdapter
        inicializarSpinner();
    }

    private void cargarCampos() {
        EditText txtNombreTransaccion = (EditText) findViewById(R.id.txtNombre);
        EditText txtMontoTransaccion = (EditText) findViewById(R.id.txtSaldo);
        txtNombreTransaccion.setText(this.nombreTransaccion);
        // txtMontoTransaccion.setText(0);

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

    private void leerBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            this.nombreTransaccion = bundle.getString("nombreTransaccion");
            this.idTransaccion = bundle.getLong("idTransaccion");
            this.tipoTransaccion = bundle.getString("tipoTransaccion");
            this.montoTransaccion = bundle.getFloat("montoTransaccion");
            this.favoritoTransaccion = bundle.getBoolean("favoritoTransaccion");
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
