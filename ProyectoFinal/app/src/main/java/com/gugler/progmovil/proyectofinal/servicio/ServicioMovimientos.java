package com.gugler.progmovil.proyectofinal.servicio;

import android.content.Context;

import com.gugler.progmovil.proyectofinal.dao.MovimientoDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.modelo.Movimiento;

import java.util.ArrayList;

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
        movimientos = movimientoDao.listarTodo();
        return movimientos;
    };

    public void actualizarCuenta(Float monto, String tipo, String denominacionCuenta, String cadena, Context contexto)  throws ValidacionException, Exception{
        ServicioCuentas servicioCuentas = new ServicioCuentas();
        servicioCuentas.crearBase(contexto,cadena);
        Cuenta cuenta = new Cuenta();
        cuenta = servicioCuentas.obtenerCuentaPorDenominacion(denominacionCuenta);
        saldo = (tipo.trim().equals("D") ? cuenta.getSaldo()-monto : cuenta.getSaldo() + monto ); //guardo el saldo resultante
        cuenta.setSaldo(saldo);
        servicioCuentas.modificarCuenta(cuenta);
    };


}
