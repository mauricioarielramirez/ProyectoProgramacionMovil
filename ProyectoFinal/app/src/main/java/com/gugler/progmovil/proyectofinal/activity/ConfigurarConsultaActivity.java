package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.gugler.progmovil.proyectofinal.fragment.DatePickerFragment;
import com.gugler.progmovil.proyectofinal.fragment.OpcionesConsultaDialog;

import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ConfigurarConsultaActivity extends BaseActivity {

    private ArrayList<Integer> mSelectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_consulta);
        mSelectedItems = new ArrayList<>();

//        EditText txtFechaDesde = (EditText) findViewById(R.id.txtFechaDesde);
//        txtFechaDesde.setOnClickListener(onClickFecha(txtFechaDesde));
    }

    private void configurarInterface(String modo) {
        ActionBar actionBar;
        switch (modo) {
            case "C": // NO VA A ENTRAR ACA
                actionBar = getSupportActionBar();
                actionBar.setTitle("Consulta");
                actionBar.setSubtitle("Configuración");

                break;
            default:
                actionBar = getSupportActionBar();
                actionBar.setTitle(":(");
                actionBar.setSubtitle(":/");
        }
    }

    public void onClickFecha(View view)
    {
        Bundle r = new Bundle();
        DatePickerFragment picker = new DatePickerFragment();
        r.putInt("campoFecha", view.getId());
        picker.setArguments(r);
        picker.show(getFragmentManager(), "fechaNacimientoPicker");
    }

    public void onConsultaOpciones(View view)
    {
        Bundle r = new Bundle();
        OpcionesConsultaDialog dialog = new OpcionesConsultaDialog();
        r.putSerializable("listaOpciones", mSelectedItems);
        dialog.setArguments(r);
        dialog.show(getFragmentManager(), "opcionesConsultaDialog");
    }


}
