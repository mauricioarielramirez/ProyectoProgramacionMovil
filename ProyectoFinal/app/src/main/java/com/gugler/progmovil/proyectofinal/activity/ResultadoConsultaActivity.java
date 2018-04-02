package com.gugler.progmovil.proyectofinal.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.widget.ListView;

import com.gugler.progmovil.proyectofinal.adaptador.CabeceraConsultaAdapter;
import com.gugler.progmovil.proyectofinal.adaptador.CabeceraResumenComparativoAdapter;
import com.gugler.progmovil.proyectofinal.modelo.dto.CabeceraConsultaDTO;
import com.gugler.progmovil.proyectofinal.servicio.ServicioMovimientos;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import progmovil.gugler.com.pf.R;

public class ResultadoConsultaActivity extends BaseActivity {

    private Character tipoConsulta;
    private String fechaInicialPeriodo1;
    private String fechaFinalPeriodo1;
    private String fechaInicialPeriodo2;
    private String fechaFinalPeriodo2;
    private String denominacionCuenta;
    private Character mostrarDebito;
    private Character mostrarCredito;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_consulta);
        /*
        Deberia recibir un buncle con el tipo de consulta, fecha y cuenta
        Llenar los listview correspondientes
        */
        prepararStringSql();
        leerBundle();
        configurarInterface(this.tipoConsulta);
    }

    public void leerBundle() {
        Bundle recurso = getIntent().getExtras();
        this.tipoConsulta = recurso.getChar("tipoConsulta");
        this.fechaInicialPeriodo1 = recurso.getString("fechaInicialPeriodo1");
        this.fechaFinalPeriodo1 = recurso.getString("fechaFinalPeriodo1");
        this.fechaInicialPeriodo2 = recurso.getString("fechaInicialPeriodo2");
        this.fechaFinalPeriodo2 = recurso.getString("fechaFinalPeriodo2");
        this.denominacionCuenta = recurso.getString("denominacionCuenta");
        this.mostrarDebito = recurso.getChar("mostrarDebito");
        this.mostrarCredito = recurso.getChar("mostrarCredito");
    }

    private void configurarInterface(Character modo) {
        ActionBar actionBar = getSupportActionBar();
        switch (modo) {
            case 'N': //Normal
                actionBar.setTitle("Consulta");
                actionBar.setSubtitle("Movimientos por periodo");
                break;
            case 'C': //Comparativo
                actionBar.setTitle("Consulta");
                actionBar.setSubtitle("Comparación de periodos");
                llenarCabeceraComparativo();
                llenarResumenComparativo();
                //llenarMovimientosComparativo();
                break;
            default:
                //ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(":/");
                actionBar.setSubtitle(":(");
        }
    }

    private void llenarCabeceraComparativo() {
        ArrayList<Object> listaCabeceraConsultaDTO = new ArrayList<Object>();
        listaCabeceraConsultaDTO.add(new CabeceraConsultaDTO(" Periodo 1", " Del " +fechaInicialPeriodo1 +" al "+fechaFinalPeriodo1));
        listaCabeceraConsultaDTO.add(new CabeceraConsultaDTO(" Periodo 2", " Del " +fechaInicialPeriodo2 +" al "+fechaFinalPeriodo2));
        //capturar el listview
        ListView lstCabecera = (ListView) findViewById(R.id.lstConsultaResumen);
        CabeceraConsultaAdapter adapter;
        try{
            adapter = new CabeceraConsultaAdapter(getApplicationContext(),listaCabeceraConsultaDTO);
            lstCabecera.setAdapter(adapter);
        }catch(Exception ex){
            throw  ex;
        }
        adapter.notifyDataSetChanged();
    }

    private void llenarResumenComparativo() {
        ServicioMovimientos servicioMovimientos = new ServicioMovimientos();
        servicioMovimientos.crearBase(this,CADENA_SQL);
        ArrayList<Object> listaResumenComparativoDTO = new ArrayList<Object>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date periodo1Desde = null, periodo1Hasta = null, periodo2Desde = null, periodo2Hasta = null;
        try {
            periodo1Desde = dateFormat.parse(this.fechaInicialPeriodo1);
            periodo1Hasta = dateFormat.parse(this.fechaFinalPeriodo1);
            periodo2Desde = dateFormat.parse(this.fechaInicialPeriodo2);
            periodo2Hasta = dateFormat.parse(this.fechaFinalPeriodo2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        listaResumenComparativoDTO.addAll(servicioMovimientos.devolverResumenComparativo(periodo1Desde,periodo1Hasta,periodo2Desde,periodo2Hasta,this.denominacionCuenta));
        ListView lstConsultaPeriodoComparador = (ListView) findViewById(R.id.lstConsultaPeriodoComparador);
        CabeceraResumenComparativoAdapter adapter;
        try{
            adapter = new CabeceraResumenComparativoAdapter(this,listaResumenComparativoDTO);
            lstConsultaPeriodoComparador.setAdapter(adapter);
        }catch(Exception ex){
            throw  ex;
        }
        adapter.notifyDataSetChanged();
    }

    private void llenarMovimientosComparativo() {

    }




}

