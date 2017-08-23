package com.gugler.progmovil.proyectofinal.servicio;

import android.content.Context;

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

    public Boolean agregarCuenta(Transaccion transaccion) throws ValidacionException, Exception {
        transaccionDao.agregar(transaccion);
        return true;
    }

    public ArrayList<Transaccion> listarTodo(){
        ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
        transacciones = transaccionDao.listarTodo();
        return transacciones;
    }
}
