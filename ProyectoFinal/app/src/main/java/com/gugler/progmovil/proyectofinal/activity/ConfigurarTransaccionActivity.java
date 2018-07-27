package com.gugler.progmovil.proyectofinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    private String cuentaTransaccion;

    private Boolean eliminarTranscaccion; // atributo para manejar el resultado de la elección del dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_transaccion);

        prepararStringSql();
        leerBundle();
        configurarInterface("");
        inicializarSpinnerTipoTransaccion(); // Este es el de tipo Débito/Crédito
        inicializarSpinner(); //Este es el de cuentas
        Button btnToolbarGuardar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnToolbarGuardar.setEnabled(true);
        Button btnEliminarTransaccion = (Button) findViewById(R.id.btnEliminarTransaccion);
        eliminarTranscaccion = false;
        btnEliminarTransaccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ConfigurarTransaccionActivity.this);
                alert.setTitle("Confirmar operación");
                alert.setMessage("Ha elegido eliminar esta transacción. ¿Desea continuar?");
                alert.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarTranscaccion = true;
                        if (sTransacciones.eliminarTransaccion(getApplicationContext(),CADENA_SQL,idTransaccion)){
                            Toast toast = Toast.makeText(getApplicationContext(),"Transacción eliminada exitosamente",Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(getApplicationContext(),NormalActivity.class);
                            startActivity(intent);
                        }else{
                            Toast toast = Toast.makeText(getApplicationContext(),"No se pudo eliminar la transacción",Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
                alert.setNegativeButton("No",null);
                alert.show();
            }
        });

        if (this.nombreTransaccion != null) {
            try{
                cargarCampos();
                btnEliminarTransaccion.setVisibility(View.VISIBLE);
            } catch (Exception ex) {
                // Mostrar toast de error
                Toast toast = Toast.makeText(this.getApplicationContext(),"Se ha producido un error general",Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            btnEliminarTransaccion.setVisibility(View.GONE);
            btnToolbarGuardar.setEnabled(false);
        }

        //Seteo de watcher
        EditText txtNombreTransaccion = (EditText) findViewById(R.id.txtNombre);
        EditText txtSaldo = (EditText) findViewById(R.id.txtSaldo);

        txtNombreTransaccion.addTextChangedListener(new TransaccionWatcher(txtNombreTransaccion,txtSaldo,btnToolbarGuardar));
        txtSaldo.addTextChangedListener(new TransaccionWatcher(txtNombreTransaccion,txtSaldo,btnToolbarGuardar));

        Button btnGuardarToolbar = (Button) findViewById(R.id.btnToolbarGuardar);
        btnGuardarToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Transaccion transaccion = new Transaccion();
                if (idTransaccion==null) {
                    transaccion.setId(null);
                } else {
                    transaccion.setId(idTransaccion);
                };
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
                    if(idTransaccion != null && idTransaccion > 0) { //Se trata de una modificación
                        if (sTransacciones.modificarTransaccion(getApplicationContext(),CADENA_SQL,denominacionCuenta,transaccion)) {
                            Toast toastEx = Toast.makeText(getApplicationContext(), "Transacción Modificada exitosamente", Toast.LENGTH_SHORT);
                            toastEx.show();
                            Intent intento = new Intent(getApplicationContext(), NormalActivity.class);
                            startActivity(intento);
                        }
                    } else {
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
                    }

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
    }

    // Esto servia si levantabas un menú contextual
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

    private void cargarCampos() throws  Exception{
        if (idTransaccion!=null || idTransaccion > 0) {
            //Recupero los datos de transaccion completos
            Transaccion transaccion = new Transaccion();
            String denominacionCuenta = "";
            sTransacciones = new ServicioTransacciones(); //No estaba inicializado al principio del activity, lo hago aca directamente
            sTransacciones.crearBase(this.getApplicationContext(),CADENA_SQL);
            transaccion = sTransacciones.obtenerTransaccionPorId(idTransaccion);
            try {
                ArrayList<Cuenta> cuentas = sTransacciones.obtenerCuentasPorTransaccion(this.getApplicationContext(),CADENA_SQL,idTransaccion);
                denominacionCuenta = cuentas.get(0).getDenominacion();
            } catch (Exception ex) {
                throw ex;
            }
            //Seteo los valores en los atributos
            this.nombreTransaccion = transaccion.getNombre();
            this.tipoTransaccion = transaccion.getTipo();
            this.favoritoTransaccion  = transaccion.getFavorito();
            this.montoTransaccion = transaccion.getMonto();
            this.cuentaTransaccion = denominacionCuenta;

            //Recupero los elementos a trabajar
            EditText txtNombreTransaccion = (EditText) findViewById(R.id.txtNombre);
            Spinner spnTipoTransaccion = (Spinner) findViewById(R.id.spnTipoTransaccion);
            EditText txtMontoTransaccion = (EditText) findViewById(R.id.txtSaldo);
            CheckBox chkFavortio = (CheckBox) findViewById(R.id.chkFavortio);
            Spinner spnCtasAsociadas = (Spinner) findViewById(R.id.spnCtasAsociadas);

            //Seteo los elementos del activity
            txtNombreTransaccion.setText(this.nombreTransaccion);
            if (this.tipoTransaccion.equals("D")) {
                spnTipoTransaccion.setSelection(0); // Débito
            }else {
                spnTipoTransaccion.setSelection(1); // Crédito
            }

            txtMontoTransaccion.setText(this.montoTransaccion.toString());

            if (this.favoritoTransaccion == true) {
                chkFavortio.setChecked(true);
            } else {
                chkFavortio.setChecked(false);
            }
            //Busco la posición de la cuenta en el spinner
            Integer counterIndexer = 0;
            for (String e : listaCuentasAsociadas) {
                if(e.trim().equals(denominacionCuenta.trim())){
                    break;
                }else{
                    counterIndexer++;
                }
            }
            spnCtasAsociadas.setSelection(counterIndexer); //Seteo la posición correpondiente
        }

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

    //Llena el Spinner de cuentas (siempre carga todas, tanto para editar como nuevas)
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
