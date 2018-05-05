package com.gugler.progmovil.proyectofinal.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.gugler.progmovil.proyectofinal.fragment.DatePickerFragment;
import com.gugler.progmovil.proyectofinal.fragment.OpcionesConsultaDialog;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ConfigurarConsultaActivity extends BaseActivity {

    private ArrayList<Integer> mSelectedItems;
    private String denominacionCuenta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_consulta);
        mSelectedItems = new ArrayList<>();
        mSelectedItems.add(0);
        mSelectedItems.add(1);
        leerBundle();

        Button btnOpciones = (Button) findViewById(R.id.btnOpcionesConsulta);
        Button btnConfirmarConsulta = (Button) findViewById(R.id.btnConfirmarConsulta);

        //denominacionCuenta = bundle.getString("denominacionCuenta");

        btnOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ConfigurarConsultaActivity.this);
                alert.setTitle("Opciones");
                boolean[] checked = new boolean[]{false, false, false};

                for (int i : mSelectedItems) {
                    checked[i] = true;
                }

                alert.setMultiChoiceItems(R.array.OpcionesConsulta, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if (isChecked) {
                            mSelectedItems.add(which);
                        } else if (mSelectedItems.contains(which)) {
                            mSelectedItems.remove(Integer.valueOf(which));
                        }
                    }
                });
                alert.setPositiveButton("OK", null);
                alert.show();
            }

        });

        btnConfirmarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Capturar los datos de los campos
                RadioButton rdbRangoDeFechas = (RadioButton) findViewById(R.id.rdbRangoDeFechas);
                RadioButton rdbCompararPeriodos = (RadioButton) findViewById(R.id.rdbCompararPeriodos);

                Button btnFechaDesde = (Button) findViewById(R.id.btnFechaDesde);
                Button btnFechaHasta = (Button) findViewById(R.id.btnFechaHasta);
                Button btnPeriodo1Desde = (Button) findViewById(R.id.btnPeriodo1Desde);
                Button btnPeriodo1Hasta = (Button) findViewById(R.id.btnPeriodo1Hasta);
                Button btnPeriodo2Desde = (Button) findViewById(R.id.btnPeriodo2Desde);
                Button btnPeriodo2Hasta = (Button) findViewById(R.id.btnPeriodo2Hasta);

                Bundle bundle = new Bundle();
                bundle.putString("denominacionCuenta",denominacionCuenta);

                if (rdbRangoDeFechas.isChecked()) { //Consulta normal
                    bundle.putChar("tipoConsulta",'N');
                    bundle.putString("fechaInicialPeriodo1",btnFechaDesde.getText().toString());
                    bundle.putString("fechaFinalPeriodo1",btnFechaHasta.getText().toString());
                    bundle.putString("fechaInicialPeriodo2", "");
                    bundle.putString("fechaFinalPeriodo2", "");
                    bundle.putString("mostrarDebito","S");
                    bundle.putString("mostrarCredito","S");

                    Intent intent = new Intent(getApplicationContext(),ResultadoConsultaActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                if (rdbCompararPeriodos.isChecked()) { //Consulta comparativa
                    bundle.putChar("tipoConsulta",'C');
                    bundle.putString("fechaInicialPeriodo1",btnPeriodo1Desde.getText().toString());
                    bundle.putString("fechaFinalPeriodo1",btnPeriodo1Hasta.getText().toString());
                    bundle.putString("fechaInicialPeriodo2",btnPeriodo2Desde.getText().toString());
                    bundle.putString("fechaFinalPeriodo2",btnPeriodo2Hasta.getText().toString());
                    bundle.putString("mostrarDebito","S");
                    bundle.putString("mostrarCredito","S");

                    Intent intent = new Intent(getApplicationContext(),ResultadoConsultaActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });

        checkRadioButton1();

        checkRadioButton2();

        final RadioButton rdbFechas = (RadioButton) findViewById(R.id.rdbRangoDeFechas);
        rdbFechas.setChecked(true);
        rdbFechas.performClick();

        configurarInterface("C");
    }

    private void checkRadioButton2() {
        final RadioButton rdbPeriodos = (RadioButton) findViewById(R.id.rdbCompararPeriodos);
        rdbPeriodos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbPeriodos.isChecked()) {
                    Button btn1 = (Button) findViewById(R.id.btnPeriodo1Desde);
                    btn1.setEnabled(true);
                    Button btn2 = (Button) findViewById(R.id.btnPeriodo1Hasta);
                    btn2.setEnabled(true);
                    Button btn3 = (Button) findViewById(R.id.btnPeriodo2Desde);
                    btn3.setEnabled(true);
                    Button btn4 = (Button) findViewById(R.id.btnPeriodo2Hasta);
                    btn4.setEnabled(true);

                    Button btnDesde = (Button) findViewById(R.id.btnFechaDesde);
                    btnDesde.setEnabled(false);
                    Button btnHasta = (Button) findViewById(R.id.btnFechaHasta);
                    btnHasta.setEnabled(false);
                }
            }
        });
    }

    private void checkRadioButton1() {
        final RadioButton rdbFechas = (RadioButton) findViewById(R.id.rdbRangoDeFechas);
        rdbFechas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rdbFechas.isChecked()) {
                    Button btn1 = (Button) findViewById(R.id.btnPeriodo1Desde);
                    btn1.setEnabled(false);
                    Button btn2 = (Button) findViewById(R.id.btnPeriodo1Hasta);
                    btn2.setEnabled(false);
                    Button btn3 = (Button) findViewById(R.id.btnPeriodo2Desde);
                    btn3.setEnabled(false);
                    Button btn4 = (Button) findViewById(R.id.btnPeriodo2Hasta);
                    btn4.setEnabled(false);

                    Button btnDesde = (Button) findViewById(R.id.btnFechaDesde);
                    btnDesde.setEnabled(true);
                    Button btnHasta = (Button) findViewById(R.id.btnFechaHasta);
                    btnHasta.setEnabled(true);
                }
            }
        });
    }

    private void configurarInterface(String modo) {
        ActionBar actionBar;
        switch (modo) {
            case "C":
                actionBar = getSupportActionBar();
                actionBar.setTitle("Consulta");
                actionBar.setSubtitle("Configuración");

                checkRadioButton1();

                break;
            default:
                actionBar = getSupportActionBar();
                actionBar.setTitle(":(");
                actionBar.setSubtitle(":/");
        }
    }

    public void onClickFecha(View view) {
        Bundle r = new Bundle();
        DatePickerFragment picker = new DatePickerFragment();
        r.putInt("campoFecha", view.getId());
        picker.setArguments(r);
        picker.show(getFragmentManager(), "fechaNacimientoPicker");
    }

    public void onConsultaOpciones(View view) {
        Bundle r = new Bundle();
        OpcionesConsultaDialog dialog = new OpcionesConsultaDialog();
        r.putSerializable("listaOpciones", mSelectedItems);
        dialog.setArguments(r);
        dialog.show(getFragmentManager(), "opcionesConsultaDialog");
    }

    public void leerBundle() {
        Bundle recurso = getIntent().getExtras();
        this.denominacionCuenta = recurso.getString("denominacionCuenta");
    }

}
