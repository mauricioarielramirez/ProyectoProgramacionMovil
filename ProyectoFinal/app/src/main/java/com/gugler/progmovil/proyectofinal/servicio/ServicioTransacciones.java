package com.gugler.progmovil.proyectofinal.servicio;

import android.content.Context;

import com.gugler.progmovil.proyectofinal.dao.CuentaDAO;
import com.gugler.progmovil.proyectofinal.dao.TransaccionDAO;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;
import com.gugler.progmovil.proyectofinal.modelo.dto.FavoritoItem;

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
        if (!transaccionDao.existeTransaccion(transaccion.getNombre())){
            Long ultimoIdTransaccion = transaccionDao.agregar(transaccion);
            // 2)
            ServicioCuentas sCuentas = new ServicioCuentas();
            sCuentas.crearBase(contexto,cadena);
            sCuentas.asociarTransaccion(denominacionCuenta,ultimoIdTransaccion);
            return true;
        }
        return false;
    }




    public ArrayList<Transaccion> listarTodo(){
        ArrayList<Transaccion> transacciones;
        transacciones = transaccionDao.listarTodo();
        return transacciones;
    }

    public ArrayList<Transaccion> listarPorCuenta(Context contexto, String cadena, String denominacion) throws ValidacionException {
        CuentaDAO cuentaDAO = new CuentaDAO();
        cuentaDAO.crearBase(contexto,cadena);
        return cuentaDAO.obtenerTransacciones(contexto,cadena,denominacion);
    }

    public Transaccion obtenerTransaccionPorId(Long id) {
        return  transaccionDao.obtenerPorId(id);
    }

    /**
     * Obtiene las transacciones con marca de favoritos
     * @return
     * @throws ValidacionException
     */
    public ArrayList<FavoritoItem> obtenerFavoritos() throws ValidacionException{
        //Obtener las transacciones y filtrarlas
        ArrayList<Transaccion> transacciones =  listarTodo();
        ArrayList<FavoritoItem> favoritos = new ArrayList<FavoritoItem>();
        ArrayList<Transaccion> transaccionesFiltradas = new ArrayList<Transaccion>();

        transaccionesFiltradas.addAll(transacciones);
        for (Transaccion tr: transacciones){
            if((tr.getFavorito()).equals(false)){
                transaccionesFiltradas.remove(tr);
            }
        }

        //Aramar listado de DTO
        for (Transaccion tr: transaccionesFiltradas){
            FavoritoItem fav = new FavoritoItem();
            fav.setIdTransaccion(tr.getId().toString());
            fav.setNombreTransaccion(tr.getNombre());
            fav.setTipo(tr.getTipo());

            // Integer cantidadCuentas = obtenerCuentasPorTransaccion()
            fav.setCantidadCuentasAsociadas("(ninguna cuenta asociada)");
            favoritos.add(fav);
        }
        return favoritos;
    }

    /**
     * Obtiene las cuentas vinculadas a una transacción
     * @param contexto
     * @param cadena
     * @param idTransaccion
     * @return
     * @throws ValidacionException
     */
    public ArrayList<Cuenta> obtenerCuentasPorTransaccion(Context contexto, String cadena, Long idTransaccion) throws ValidacionException{
        return transaccionDao.obtenerCuentas(contexto, cadena, idTransaccion);
    }

    /**
     * Modifica todos los datos de una transacción
     * @param contexto
     * @param cadena
     * @param denominacionCuenta
     * @param transaccion
     * @return
     * @throws Exception
     */
    public Boolean modificarTransaccion (Context contexto, String cadena, String denominacionCuenta, Transaccion transaccion) throws Exception {

        //Necesito cambiar la vinculación también, lo hago llamando a los métodos correspondientes también
        ServicioCuentas sCuentas = new ServicioCuentas();
        sCuentas.crearBase(contexto,cadena);
        //Desvinculacion existente
        sCuentas.desvincularTransaccion(denominacionCuenta,transaccion.getId(),'S'); //Elimina el registro de vinculación con la transacción y la cuenta
        //Asociación nueva
        sCuentas.asociarTransaccion(denominacionCuenta,transaccion.getId());

        return transaccionDao.modificar(transaccion);
    }

    public Boolean eliminarTransaccion (Context contexto, String cadena, Long idTransaccion) {
        return transaccionDao.eliminarTransaccion(contexto,cadena,idTransaccion);
    }

}
