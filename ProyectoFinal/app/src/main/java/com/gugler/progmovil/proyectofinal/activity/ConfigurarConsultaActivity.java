package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import progmovil.gugler.com.pf.R;

public class ConfigurarConsultaActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configurar_consulta);

        EditText txtFechaDesde = (EditText) findViewById(R.id.txtFechaDesde);
        txtFechaDesde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
//                DatePickerDialog datePicker = new DatePickerDialog(, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        String date = "Date"+String.valueOf(year) +"-"+String.valueOf(monthOfYear)
//                                +"-"+String.valueOf(dayOfMonth);
//                        tfDescription.setText(date);
//                        tfDate.setText(date);
//                    }
//                }, yy, mm, dd);


            }
        });
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
}
