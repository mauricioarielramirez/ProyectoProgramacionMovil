package com.gugler.progmovil.proyectofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;

/**
 * Created by ericd on 13/6/2017.
 */

public class CuentaDAO {

    private static final String CT_DENOMINACION = "ct_denominacion";
    private static final String CT_DESCRIPCION = "ct_descripcion";
    private static final String CT_SALDO = "ct_saldo";

    private Dao baseDeDatos;
    private SQLiteDatabase db;

    public CuentaDAO(Context context, String cadena) {
        baseDeDatos = new Dao(context,"db_proyecto",null,1,cadena);
        db = baseDeDatos.getWritableDatabase();
    }

    /**
     * Permite dar de alta una cuenta
     * @param cuenta
     * @return Devuelve True si la operación se realiza con éxito
     * @throws Exception
     */
    public Boolean agregar(Cuenta cuenta) throws ValidacionException, Exception {
        try {
            Cuenta cuentaRec = obtenerPorDenominacion(cuenta.getDenominacion());
            if (cuentaRec.getDenominacion() != null){
                ValidacionException ex = new ValidacionException(ValidacionException.EXISTE_EN_BASE);
                throw ex;
            }

            ContentValues registro = new ContentValues();
            registro.put(CT_DENOMINACION, cuenta.getDenominacion());
            registro.put(CT_DESCRIPCION, cuenta.getDescripcion());
            registro.put(CT_SALDO, cuenta.getSaldo());
            long res = db.insert("db_cuenta",null,registro);

            return (res == -1 ? false : true);

        }catch (Exception ex){
            throw  ex;
        }
    }

    /**
     * Devuelve una cuenta buscada por denominación
     * @param denominacion
     * @return
     */
    public Cuenta obtenerPorDenominacion(String denominacion){
        Cuenta cuenta = new Cuenta();
        String[] campos = new String[]{CT_DENOMINACION,CT_DESCRIPCION,CT_SALDO}; //Campos a devolver
        String[] filtro = new String[]{denominacion.toUpperCase()};   //Filtro
        Cursor cursor = db.query("db_cuenta",campos,"upper("+CT_DENOMINACION+")" + "=?",filtro,null,null,null);
        if (cursor.moveToFirst()){
            do{
                if (cursor.getCount() == 1){
                    cuenta.setDenominacion(cursor.getString(0));
                    cuenta.setDescripcion(cursor.getString(1));
                    cuenta.setSaldo(cursor.getFloat(2));
                }else{
                    break;
                }
            }while(cursor.moveToNext());
        }

        return cuenta;
    }

    /**
     * Elimina una cuenta por denominación
     * @param denominacion
     * @return
     */
    public boolean borrar(String denominacion){
        db.delete("db_cuenta", CT_DENOMINACION +"=?"+ denominacion, null);
        return true;
    }

    /**
     * Intenta modificar un objeto cuenta.
     * @param cuentaModificada
     * @param denominacion
     * @return Devuelve true si la modificación se llevó con éxito, sino false.
     * @throws ValidacionException Devolución en caso de que no exista el elemento a modificar.
     * @throws Exception
     */
    public boolean modificar(Cuenta cuentaModificada, String denominacion) throws ValidacionException,Exception{
        Cuenta cuentaOriginal = obtenerPorDenominacion(denominacion); //Comprobar si existe elemento
        if (cuentaOriginal == null){
            ValidacionException ex = new ValidacionException(ValidacionException.NO_EXISTE_EN_BASE);
            throw  ex;
        }else{
            if (!cuentaModificada.equals(cuentaOriginal)){ //Hubo modificaciones
                try{
                    String[] valores = {denominacion};
                    ContentValues registro = new ContentValues();
                    registro.put(CT_DENOMINACION, cuentaModificada.getDenominacion());
                    registro.put(CT_DESCRIPCION, cuentaModificada.getDescripcion());
                    registro.put(CT_SALDO, cuentaModificada.getSaldo());
                    long res = db.update("db_cuenta",registro,"ct_denominacion =?",valores);
                    return (res == -1 ? false : true);
                }catch (Exception ex){
                    throw  ex;
                }
            }else{
                return false; //No hay nada que modificar
            }
        }
    }
}
