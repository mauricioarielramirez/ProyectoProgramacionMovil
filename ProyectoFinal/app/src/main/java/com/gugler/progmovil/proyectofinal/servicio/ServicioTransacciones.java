package com.gugler.progmovil.proyectofinal.servicio;

import android.content.Context;

import com.gugler.progmovil.proyectofinal.dao.CuentaDAO;
import com.gugler.progmovil.proyectofinal.dao.TransaccionDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;

import java.util.ArrayList;

/**
 * Created by Ariel on 22/8/2017.
 */

public class ServicioTransacciones extends Servicio {

    private TransaccionDAO transaccionDao;

    public ServicioTransacciones(){
        if (transaccionDao == null){
            transaccionDao = new TransaccionDAO();
        }
    };

    public void crearBase(Context contexto, String cadena){
        transaccionDao.crearBase(contexto, cadena);
    }

    public Boolean agregarTransaccion(Context contexto, String cadena,Transaccion transaccion, String denominacionCuenta) throws ValidacionException, Exception {
        /**
         * 1) Agregar la transacción con su DAO
         * 2) Agregar el registro de la relación 'cuenta_transaccion' a su correspondiente tabla
         */
        // 1)
        Long ultimoIdTransaccion = transaccionDao.agregar(transaccion);
        // 2)
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(contexto,cadena);
        sCuentas.asociarTransaccion(denominacionCuenta,ultimoIdTransaccion);
        return true;
    }

    public ArrayList<Transaccion> listarTodo(){
        ArrayList<Transaccion> transacciones;
        transacciones = transaccionDao.listarTodo();
        return transacciones;
    }

    public ArrayList<Transaccion> listarPorCuenta(String denominacion){
        CuentaDAO cuentaDAO = new CuentaDAO();
        return cuentaDAO.obtenerTransacciones(denominacion);
    }
}
