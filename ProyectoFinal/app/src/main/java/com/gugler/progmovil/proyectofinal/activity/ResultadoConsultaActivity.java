package com.gugler.progmovil.proyectofinal.activity;

import android.app.AlertDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.gugler.progmovil.proyectofinal.adaptador.CabeceraResumenComparativoAdapter;
import com.gugler.progmovil.proyectofinal.adaptador.MovimientoPorPeriodoAdapter;
import com.gugler.progmovil.proyectofinal.modelo.dto.MovimientosPorPeriodoDTO;
import com.gugler.progmovil.proyectofinal.servicio.ServicioMovimientos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
        Resources res = getResources();

        TabHost tabHost = (TabHost) findViewById(R.id.tabHostPestanias);
        tabHost.setup();

        TabHost.TabSpec spec=tabHost.newTabSpec("mitab1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("RESUMEN", null);
        tabHost.addTab(spec);

        spec=tabHost.newTabSpec("mitab2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Períodos", null);
        tabHost.addTab(spec);
        spec=tabHost.newTabSpec("mitab3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Movimientos", null);
        tabHost.addTab(spec);

        tabHost.setCurrentTab(0);
        //Se va a contruir el alert para mostrar el detalle del resumen
        ListView lstMovimientos = (ListView) findViewById(R.id.lstConsultaMovimientosDetalles);
        lstMovimientos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ResultadoConsultaActivity.this);
                alert.setTitle("Detalles de movimiento");
                alert.setNeutralButton("Cerrar",null);

                ServicioMovimientos sMovimientos = new ServicioMovimientos();
                sMovimientos.crearBase(getApplicationContext(), CADENA_SQL);
                TextView txvIdMovimiento = (TextView) view.findViewById(R.id.txvIdMovimiento);

                MovimientosPorPeriodoDTO movimientoDTO = sMovimientos.devolverMovimientoDTO(Long.parseLong(txvIdMovimiento.getText().toString()));

                String mensaje =
                        "\n" + "◘ Transacción: " + movimientoDTO.getTransaccion() +
                        "\n" + "◘ Importe: $ " + movimientoDTO.getMonto() +
                        "\n" + "◘ Saldo de la cuenta: $ " + movimientoDTO.getSaldo() +
                        "\n" + "◘ Fecha: " + movimientoDTO.getFecha() +
                        "\n" + "◘ Tipo: " + (movimientoDTO.getTipo().equals("D") ? "Débito" : "Crédito");
                alert.setMessage(mensaje);
                alert.setIcon(R.drawable.ic_info_black_24dp);
                alert.show();
            }
        });

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
        // Seteo de headers
       /* View vIncludeSuperior = findViewById(R.id.includeCabeceraSuperior);
        TextView txvTituloHeaderSuperior = (TextView) vIncludeSuperior.findViewById(R.id.txvTituloHeader);
        txvTituloHeaderSuperior.setText("Resumen");
        View vIncludeInferior = findViewById(R.id.includeCabeceraInferior);
        TextView txvTituloHeaderInferior = (TextView) vIncludeInferior.findViewById(R.id.txvTituloHeader);
        txvTituloHeaderInferior.setText("Movimientos");*/

        switch (modo) {
            case 'N': //Normal
                actionBar.setTitle("Consulta");
                actionBar.setSubtitle("Movimientos por periodo");
                TabHost tabHost = (TabHost) findViewById(R.id.tabHostPestanias);
                tabHost.getTabWidget().getChildAt(1).setVisibility(View.GONE);
                llenarCabeceraNormal();
                llenarMovimientosNormal();
                break;
            case 'C': //Comparativo
                actionBar.setTitle("Consulta");
                actionBar.setSubtitle("Comparación de periodos");
                llenarCabeceraComparativo();
                llenarResumenComparativo();
                llenarMovimientosComparativo();
                break;
            default:
                //ActionBar actionBar = getSupportActionBar();
                actionBar.setTitle(":/");
                actionBar.setSubtitle(":(");
        }
    }

    private void llenarCabeceraNormal() {
        ServicioMovimientos sMovimientos = new ServicioMovimientos();
        sMovimientos.crearBase(this, CADENA_SQL);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDesde = null, fechaHasta = null;
        try {
            fechaDesde = dateFormat.parse(this.fechaInicialPeriodo1);
            fechaHasta = dateFormat.parse(this.fechaFinalPeriodo1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ArrayList<MovimientosPorPeriodoDTO> listaMovimientosDTO = sMovimientos.devolverPeriodoMovimientos(this.denominacionCuenta, fechaDesde, fechaHasta);

        TextView txvElemento1I = (TextView) findViewById(R.id.txvElemento1I);
        txvElemento1I.setText("Periodo");
        TextView txvElemento1D = (TextView) findViewById(R.id.txvElemento1D);
        txvElemento1D.setText("Del " + this.fechaInicialPeriodo1 + " al " + this.fechaFinalPeriodo1);

        Integer cantidadCreditos = 0, cantidadDebitos = 0;
        for (MovimientosPorPeriodoDTO mov: listaMovimientosDTO) {
            if (mov.getTipo().equals("C")) {
                cantidadCreditos++;
            } else {
                cantidadDebitos++;
            }
        }
        TextView txvElemento2I = (TextView) findViewById(R.id.txvElemento2I);
        txvElemento2I.setText("Total de créditos");
        TextView txvElemento2D = (TextView) findViewById(R.id.txvElemento2D);
        txvElemento2D.setText(cantidadCreditos.toString());
        TextView txvElemento3I = (TextView) findViewById(R.id.txvElemento3I);
        txvElemento3I.setText("Total de débitos");
        TextView txvElemento3D = (TextView) findViewById(R.id.txvElemento3D);
        txvElemento3D.setText(cantidadDebitos.toString());
        TextView txvElemento4I = (TextView) findViewById(R.id.txvElemento4I);
        txvElemento4I.setText("Total");
        TextView txvElemento4D = (TextView) findViewById(R.id.txvElemento4D);
        txvElemento4D.setText(String.valueOf(listaMovimientosDTO.size()));
        TextView txvElemento5I = (TextView) findViewById(R.id.txvElemento5I);
        txvElemento5I.setText("Saldo actual");
        TextView txvElemento5D = (TextView) findViewById(R.id.txvElemento5D);
        int cantidadMovimientos = listaMovimientosDTO.size();
        if (cantidadMovimientos == 0) {
            txvElemento5D.setText(" - ");
        } else {
            txvElemento5D.setText(String.valueOf( (listaMovimientosDTO.get(0).getSaldo() )));
        }

    }

    private void llenarMovimientosNormal() {
        ServicioMovimientos servicioMovimientos = new ServicioMovimientos();
        servicioMovimientos.crearBase(this,CADENA_SQL);
        ArrayList<Object> movimientoPorPeriodoDTO = new ArrayList<Object>();

        ListView lstMovimientoPeriodo = (ListView) findViewById(R.id.lstConsultaMovimientosDetalles);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date periodo1Desde = null, periodo1Hasta = null;
        try {
            periodo1Desde = dateFormat.parse(this.fechaInicialPeriodo1);
            periodo1Hasta = dateFormat.parse(this.fechaFinalPeriodo1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        MovimientoPorPeriodoAdapter adapter = null;
        try {
            movimientoPorPeriodoDTO.addAll(servicioMovimientos.devolverPeriodoMovimientos(this.denominacionCuenta,periodo1Desde,periodo1Hasta));
            adapter = new MovimientoPorPeriodoAdapter(this,movimientoPorPeriodoDTO);
            lstMovimientoPeriodo.setAdapter(adapter);
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(),"Error al intentar mostrar",Toast.LENGTH_SHORT);
            toast.show();
        }
        adapter.notifyDataSetChanged();

        // Bloquea la pestaña de movimientos
        if (movimientoPorPeriodoDTO.size() == 0) {
            TabHost tabHost = (TabHost) findViewById(R.id.tabHostPestanias);
            tabHost.getTabWidget().getChildAt(2).setEnabled(false);
            tabHost.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.disabledTab));
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); // Titulo de la pestaña
            tv.setTextColor(Color.parseColor("#737373"));
        }

    }


    private void llenarCabeceraComparativo() {
        TextView txvElemento3I = (TextView) findViewById(R.id.txvElemento3I);
        txvElemento3I.setVisibility(View.GONE);
        TextView txvElemento3D = (TextView) findViewById(R.id.txvElemento3D);
        txvElemento3D.setVisibility(View.GONE);
        TextView txvElemento4I = (TextView) findViewById(R.id.txvElemento4I);
        txvElemento4I.setVisibility(View.GONE);
        TextView txvElemento4D = (TextView) findViewById(R.id.txvElemento4D);
        txvElemento4D.setVisibility(View.GONE);
        TextView txvElemento5I = (TextView) findViewById(R.id.txvElemento5I);
        txvElemento5I.setVisibility(View.GONE);
        TextView txvElemento5D = (TextView) findViewById(R.id.txvElemento5D);
        txvElemento5D.setVisibility(View.GONE);

        TextView txvElemento1I = (TextView) findViewById(R.id.txvElemento1I);
        txvElemento1I.setText("Periodo 1");
        TextView txvElemento1D = (TextView) findViewById(R.id.txvElemento1D);
        txvElemento1D.setText("Del " + fechaInicialPeriodo1 + " al " + fechaFinalPeriodo1);
        TextView txvElemento2I = (TextView) findViewById(R.id.txvElemento2I);
        txvElemento2I.setText("Periodo 2");
        TextView txvElemento2D = (TextView) findViewById(R.id.txvElemento2D);
        txvElemento2D.setText("Del " + fechaInicialPeriodo2 + " al " + fechaFinalPeriodo2);
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
        }catch(Exception ex) {
            throw  ex;
        }
        adapter.notifyDataSetChanged();
    }

    private void llenarMovimientosComparativo() {
        ServicioMovimientos servicioMovimientos = new ServicioMovimientos();
        servicioMovimientos.crearBase(this,CADENA_SQL);
        ArrayList<Object> movimientoPorPeriodoDTO = new ArrayList<Object>();

        ListView lstMovimientoPeriodo = (ListView) findViewById(R.id.lstConsultaMovimientosDetalles);

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

        MovimientoPorPeriodoAdapter adapter = null;
        try {
            movimientoPorPeriodoDTO.addAll(servicioMovimientos.devolverMovimientosPorPeriodos(periodo1Desde,periodo1Hasta,periodo2Desde,periodo2Hasta,this.denominacionCuenta));
            adapter = new MovimientoPorPeriodoAdapter(this,movimientoPorPeriodoDTO);
            lstMovimientoPeriodo.setAdapter(adapter);
        } catch (Exception ex) {
            Toast toast = Toast.makeText(getApplicationContext(),"Error al intentar mostrar",Toast.LENGTH_SHORT);
            toast.show();
        }
        adapter.notifyDataSetChanged();

        // Bloquea la pestaña de movimientos
        if (movimientoPorPeriodoDTO.size() == 0) {
            TabHost tabHost = (TabHost) findViewById(R.id.tabHostPestanias);
            tabHost.getTabWidget().getChildAt(2).setEnabled(false);
            tabHost.getTabWidget().getChildAt(2).setBackgroundColor(getResources().getColor(R.color.disabledTab));
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(2).findViewById(android.R.id.title); // Titulo de la pestaña
            tv.setTextColor(Color.parseColor("#737373"));
        }
    }




}

