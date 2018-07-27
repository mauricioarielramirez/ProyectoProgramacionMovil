package com.gugler.progmovil.proyectofinal.servicio;

import android.content.Context;

import com.gugler.progmovil.proyectofinal.dao.MovimientoDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.modelo.Movimiento;
import com.gugler.progmovil.proyectofinal.modelo.dto.MovimientosPorPeriodoDTO;
import com.gugler.progmovil.proyectofinal.modelo.dto.ResumenComparativoDTO;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Created by Ariel on 13/1/2018.
 */

public class ServicioMovimientos extends Servicio{
    private MovimientoDAO movimientoDao;
    private Float saldo;

    public ServicioMovimientos() {
        if (movimientoDao == null){
            movimientoDao = new MovimientoDAO();
        }
    };

    public void crearBase(Context contexto, String cadena) {
        movimientoDao.crearBase(contexto, cadena);
    };

    public Boolean agregarMovimiento(Movimiento movimiento,Context contexto, String cadena) throws ValidacionException, Exception {
        actualizarCuenta(movimiento.getMonto(),movimiento.getTipo(),movimiento.getCuentaAsociada(),cadena,contexto);
        movimiento.setSaldoActual(saldo);
        movimientoDao.agregar(movimiento);
        return true;
    };

    public ArrayList<Movimiento> listarTodo() {
        ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
        try {
            movimientos = movimientoDao.listarTodo();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return movimientos;
    };

    public void actualizarCuenta(Float monto, String tipo, String denominacionCuenta, String cadena, Context contexto)  throws ValidacionException, Exception{
        ServicioCuentas servicioCuentas = new ServicioCuentas();
        servicioCuentas.crearBase(contexto,cadena);
        Cuenta cuenta = new Cuenta();
        cuenta = servicioCuentas.obtenerCuentaPorDenominacion(denominacionCuenta);
        saldo = (tipo.trim().equals("D") ? cuenta.getSaldo()-monto : cuenta.getSaldo() + monto ); //guardo el saldo resultante
        cuenta.setSaldo(saldo);
        servicioCuentas.modificarCuenta(cuenta,denominacionCuenta);
    };

    public ArrayList<MovimientosPorPeriodoDTO> devolverPeriodoMovimientos(String denominacionCuenta, Date fechaDesde, Date fechaHasta) {
        ArrayList<Movimiento> listaMovimientos = new ArrayList<Movimiento>();
        listaMovimientos = movimientoDao.listarTodoConFecha(denominacionCuenta, fechaDesde, fechaHasta);
        // ArrayList<Movimiento> movimientosPeriodo1 = movimientoDao.listarTodoConFecha(denominacionCuenta, fechaDesdePeriodo1, fechaHastaPeriodo1);
        ArrayList<MovimientosPorPeriodoDTO> movimientosPeriodo = new ArrayList<MovimientosPorPeriodoDTO>();

        for (Movimiento m: listaMovimientos) {
            String fechaString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(m.getFechaHora());
            movimientosPeriodo.add(new MovimientosPorPeriodoDTO(m.getId(), 1,fechaString,m.getTransaccion(),m.getTipo(), "$ "+(new DecimalFormat("#0.00").format(m.getMonto())).toString() ,"$ "+(new DecimalFormat("#0.00").format(m.getSaldoActual())).toString()));

        }

        return movimientosPeriodo;
    }

    public void modificarHistoriaDeMovimiento(String valorViejo, String valorNuevo, Integer tipo) throws Exception {
        movimientoDao.modificarHistoriaDeMovimiento(valorViejo,valorNuevo,tipo);
    }

    public ArrayList<ResumenComparativoDTO> devolverResumenComparativo(Date fechaDesdePeriodo1, Date fechaHastaPeriodo1, Date fechaDesdePeriodo2, Date fechaHastaPeriodo2, String denominacionCuenta) {
        // ArrayList<ResumenComparativoColDTO> listaResumenComparativo = movimientoDao.devolverRsumenComparativo(fechaDesdePeriodo1, fechaHastaPeriodo1, fechaDesdePeriodo2, fechaHastaPeriodo2);
        ArrayList<Movimiento> movimientosPeriodo1 = movimientoDao.listarTodoConFecha(denominacionCuenta, fechaDesdePeriodo1, fechaHastaPeriodo1);
        ArrayList<Movimiento> movimientosPeriodo2 = movimientoDao.listarTodoConFecha(denominacionCuenta, fechaDesdePeriodo2, fechaHastaPeriodo2);

        ArrayList<ResumenComparativoDTO> listaResumen = new ArrayList<ResumenComparativoDTO>();
        ResumenComparativoDTO dtoCantidadDebitos = new ResumenComparativoDTO();
        ResumenComparativoDTO dtoCantidadCreditos = new ResumenComparativoDTO();
        ResumenComparativoDTO dtoDebito = new ResumenComparativoDTO();
        ResumenComparativoDTO dtoCredito = new ResumenComparativoDTO();
        ResumenComparativoDTO dtoSaldoInicial = new ResumenComparativoDTO();
        ResumenComparativoDTO dtoSaldoFinal = new ResumenComparativoDTO();

        dtoCantidadDebitos.setConcepto("Cantidad de débitos");
        dtoCantidadCreditos.setConcepto("Cantidad de créditos");
        dtoDebito.setConcepto("Débito");
        dtoCredito.setConcepto("Crédito");
        dtoSaldoInicial.setConcepto("Saldo inicial");
        dtoSaldoFinal.setConcepto("Saldo final");

        Integer debitosPeriodo1 = 0;
        Integer creditosPeriodo1 = 0;
        Integer debitosPeriodo2 = 0;
        Integer creditosPeriodo2 = 0;
        Float   debitoPeriodo1 = 0F;
        Float   creditoPeriodo1 = 0F;
        Float   debitoPeriodo2 = 0F;
        Float   creditoPeriodo2 = 0F;
        for (Movimiento mv: movimientosPeriodo1) {
            if (mv.getTipo().equals("D")) {
                debitoPeriodo1 = mv.getMonto() + debitoPeriodo1;
                debitosPeriodo1 = debitosPeriodo1 + 1;
            } else {
                creditoPeriodo1 = mv.getMonto() + creditoPeriodo1;
                creditosPeriodo1 = creditosPeriodo1 + 1;
            }
        }
        for (Movimiento mv: movimientosPeriodo2) {
            if (mv.getTipo().equals("D")) {
                debitoPeriodo2 = mv.getMonto() + debitoPeriodo2;
                debitosPeriodo2 = debitosPeriodo2 + 1;
            } else {
                creditoPeriodo2 = mv.getMonto() + creditoPeriodo2;
                creditosPeriodo2 = creditosPeriodo2 + 1;
            }
        }


        dtoSaldoInicial.setPeriodo1( String.valueOf((movimientosPeriodo1.size()>0 ? movimientosPeriodo1.get(0).getSaldoActual():0)) );
        dtoSaldoInicial.setPeriodo2( String.valueOf((movimientosPeriodo2.size()>0 ? movimientosPeriodo2.get(0).getSaldoActual():0)) );

        dtoSaldoInicial.setDiferencia(new DecimalFormat("#0.00").format( Math.abs((movimientosPeriodo2.size()>0 ? movimientosPeriodo2.get(0).getSaldoActual() :0)-((movimientosPeriodo1.size()>0 ? movimientosPeriodo1.get(0).getSaldoActual() :0)))));
        //(?:true:false)
        dtoSaldoFinal.setPeriodo1(new DecimalFormat("#0.00").format((movimientosPeriodo1.size()>0 ? movimientosPeriodo1.get(movimientosPeriodo1.size()-1).getSaldoActual():0)));

        dtoSaldoFinal.setPeriodo2(new DecimalFormat("#0.00").format((movimientosPeriodo2.size()>0 ? movimientosPeriodo2.get(movimientosPeriodo2.size()-1).getSaldoActual():0)));

        dtoSaldoFinal.setDiferencia(new DecimalFormat("#0.00").format(Math.abs((movimientosPeriodo2.size()>0 ? (movimientosPeriodo2.get(movimientosPeriodo2.size()-1).getSaldoActual()):0)) - ((movimientosPeriodo1.size()>0 ? (movimientosPeriodo1.get(movimientosPeriodo1.size()-1).getSaldoActual()):0))));

        dtoCantidadDebitos.setPeriodo1(debitosPeriodo1.toString());
        dtoCantidadDebitos.setPeriodo2(debitosPeriodo2.toString());

        dtoCantidadCreditos.setPeriodo1(creditosPeriodo1.toString());
        dtoCantidadCreditos.setPeriodo2(creditosPeriodo2.toString());

        dtoCredito.setPeriodo1((new DecimalFormat("#0.00").format(creditoPeriodo1)));
        //dtoCredito.setPeriodo1(creditoPeriodo1.toString());

        dtoCredito.setPeriodo2(new DecimalFormat("#0.00").format(creditoPeriodo2));

        //new DecimalFormat("#0.00").format( Math.abs(creditoPeriodo2-creditoPeriodo1)
        dtoCredito.setDiferencia(new DecimalFormat("#0.00").format( Math.abs(creditoPeriodo2-creditoPeriodo1)));

        dtoDebito.setPeriodo1(new DecimalFormat("#0.00").format(debitoPeriodo1));

        dtoDebito.setPeriodo2(new DecimalFormat("#0.00").format(debitoPeriodo2));

        dtoDebito.setDiferencia(String.valueOf(Math.abs(debitoPeriodo2-debitoPeriodo1)));

        Integer diferenciaDebitos = Math.abs(debitosPeriodo2 - debitosPeriodo1);
        dtoCantidadDebitos.setDiferencia(diferenciaDebitos.toString());
        Integer diferenciaCreditos = Math.abs(creditosPeriodo2 - creditosPeriodo1);
        dtoCantidadCreditos.setDiferencia(diferenciaCreditos.toString());

        listaResumen.add(new ResumenComparativoDTO("Concepto", "Período 1", "Período 2", "Diferencia"));
        listaResumen.add(dtoDebito);
        listaResumen.add(dtoCantidadDebitos);
        listaResumen.add(dtoCredito);
        listaResumen.add(dtoCantidadCreditos);
        listaResumen.add(dtoSaldoInicial);
        listaResumen.add(dtoSaldoFinal);


        return listaResumen;
    }

    public ArrayList<MovimientosPorPeriodoDTO> devolverMovimientosPorPeriodos(Date fechaDesdePeriodo1, Date fechaHastaPeriodo1, Date fechaDesdePeriodo2, Date fechaHastaPeriodo2, String denominacionCuenta) throws Exception{
        ArrayList<Movimiento> movimientosPeriodo1 = movimientoDao.listarTodoConFecha(denominacionCuenta, fechaDesdePeriodo1, fechaHastaPeriodo1);
        ArrayList<Movimiento> movimientosPeriodo2 = movimientoDao.listarTodoConFecha(denominacionCuenta, fechaDesdePeriodo2, fechaHastaPeriodo2);
        ArrayList<MovimientosPorPeriodoDTO> movimientos = new ArrayList<MovimientosPorPeriodoDTO>();
        String test = "";

        for (Movimiento m:movimientosPeriodo1) {

            String fechaString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(m.getFechaHora());
            movimientos.add(new MovimientosPorPeriodoDTO(m.getId(), 1,fechaString,m.getTransaccion(),m.getTipo(), "$ "+(m.getMonto()).toString() ,"$ "+(m.getSaldoActual()).toString()));
        }

        for (Movimiento m:movimientosPeriodo2) {
            String fechaString = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(m.getFechaHora());
            movimientos.add(new MovimientosPorPeriodoDTO(m.getId(), 2,fechaString,m.getTransaccion(),m.getTipo(), "$ "+(m.getMonto()).toString() ,"$ "+(m.getSaldoActual()).toString()));
        }

        Collections.sort(movimientos);

        return movimientos;
    }

    public MovimientosPorPeriodoDTO devolverMovimientoDTO(Long idMovimiento) {
        Movimiento movimiento = movimientoDao.getMovimientoPorId(idMovimiento);
        String fechaMov = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(movimiento.getFechaHora());
        MovimientosPorPeriodoDTO movimientoDTO = new MovimientosPorPeriodoDTO(
                movimiento.getId(), 1, fechaMov, movimiento.getTransaccion(),
                movimiento.getTipo(), (new DecimalFormat("#0.00").format(movimiento.getMonto())).toString(), (new DecimalFormat("#0.00").format(movimiento.getSaldoActual())).toString());
        return movimientoDTO;
        //new DecimalFormat("#0.00").format(movimiento.getMonto())
        //new DecimalFormat("#0.00").format(movimiento.getSaldoActual())
    }

    public Boolean eliminarMovimientosDeCuenta(String denominacionCuenta) {
        return movimientoDao.eliminarMovimientosDeCuenta (denominacionCuenta);
    }
}
