package com.gugler.progmovil.proyectofinal.servicio;

import android.content.Context;

import com.gugler.progmovil.proyectofinal.dao.CuentaDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;

import java.util.ArrayList;

/**
 * Created by ericd on 13/6/2017.
 */

public class ServicioCuentas extends Servicio {
    private CuentaDAO cuentaDao;

    public ServicioCuentas(Context contexto, String cadena){
        if (cuentaDao == null){
            cuentaDao = new CuentaDAO(contexto,cadena);
        }
    };

    public Boolean agregarCuenta(Cuenta cuenta) throws ValidacionException, Exception {
        cuentaDao.agregar(cuenta);
        return true;
    }

    public ArrayList<Cuenta> listarTodo(){
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        cuentas = cuentaDao.listarTodo();
        return cuentas;
    }

}
