package com.gugler.progmovil.proyectofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;

import java.util.ArrayList;

/**
 * Created by ericd on 13/6/2017.
 */

public class CuentaDAO {

    private static final String CT_DENOMINACION = "ct_denominacion";
    private static final String CT_DESCRIPCION = "ct_descripcion";
    private static final String CT_SALDO = "ct_saldo";

    private Dao baseDeDatos;
    private SQLiteDatabase db;

    private Context contextoAux; // Atributo auxiliar
    private String cadenaSql; // Atributo auxiliar

    public CuentaDAO(/*Context context, String cadena*/) {
        //baseDeDatos = new Dao(context,"db_proyecto",null,1,cadena);
        //db = baseDeDatos.getWritableDatabase();
    }

    public void crearBase(Context context, String cadena) {
        baseDeDatos = new Dao(context,"db_proyecto",null,1,cadena);
        db = baseDeDatos.getWritableDatabase();
        contextoAux = context;
        cadenaSql = cadena;

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
                throw new ValidacionException(ValidacionException.EXISTE_EN_BASE);
            }

            ContentValues registro = new ContentValues();
            registro.put(CT_DENOMINACION, cuenta.getDenominacion().trim()); //Agregar trim
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
            cursor.close();
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
     * @param denominacion: Nombre viejo de la cuenta
     * @return Devuelve true si la modificación se llevó con éxito, sino false.
     * @throws ValidacionException Devolución en caso de que no exista el elemento a modificar.
     * @throws Exception
     */
    public boolean modificar(Cuenta cuentaModificada, String denominacion) throws ValidacionException,Exception{
        Cuenta cuentaOriginal = obtenerPorDenominacion(denominacion); //Comprobar si existe elemento
        if (cuentaOriginal == null) {
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
                    long res = db.update("db_cuenta",registro,"ct_denominacion = ?",valores);
                    return (res == -1 ? false : true);
                }catch (Exception ex){
                    throw  ex;
                }
            }else{
                return false; //No hay nada que modificar
            }
        }
    }

    /**
     * Devuelve un listado con todas las cuentas (sin sus transacciones)
     * @return Listado completo de cuentas
     */
    public ArrayList<Cuenta> listarTodo(){
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        Cursor cursor = db.rawQuery("SELECT "+CT_DENOMINACION+", "+CT_DESCRIPCION+", "+CT_SALDO+" FROM db_cuenta",null);
        if (cursor.moveToFirst()){
            do{
                try {
                    Float monto = cursor.getFloat(2);

                    cuentas.add(new Cuenta(cursor.getString(0),cursor.getString(1),monto));
                } catch (Exception ex ){
                    System.out.println(ex.getStackTrace().toString());
                }

            }while(cursor.moveToNext());
        }
        cursor.close();
        return cuentas;
    }

    /**
     * Obtiene las transacciones de una cuenta
     * @param denominacion Nombre de la cuenta de la que se quiere obtener las transacciones
     * @return Listado de transacciones de la cuenta
     */
    public ArrayList<Transaccion> obtenerTransacciones(Context contexto, String cadena, String denominacion) throws ValidacionException {
        ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
        TransaccionDAO transaccionDAO = new TransaccionDAO();
        transaccionDAO.crearBase(contexto,cadena);

        String[] campos = new String[]{"cutr_ct_denominacion", "cutr_tr_id"}; //Campos a devolver
        String[] filtro = new String[]{denominacion};                         //Filtro

        try{
            Cursor cursor = db.query("db_cuenta_transaccion",campos,"cutr_ct_denominacion=?",filtro,null,null,null);
            if (cursor.moveToFirst()) {
                do {
                    transacciones.add(transaccionDAO.obtenerPorId(cursor.getLong(1)));
                } while(cursor.moveToNext());
                cursor.close();
            }
            return transacciones;
        }catch (Exception ex){
            throw new ValidacionException(ValidacionException.PROBLEMAS_LEER_TRANSACCION);
        }
    }

    public Boolean agregarCuentaTransaccion(String denominacionCuenta, Long idTransaccion) {
        ContentValues registro = new ContentValues();
        registro.put("cutr_ct_denominacion", denominacionCuenta.trim());
        registro.put("cutr_tr_id", idTransaccion);
        long res = db.insert("db_cuenta_transaccion",null,registro);

        return (res == -1 ? false : true);
    }

    /**
     *
     * @param denominacionCuenta
     * @param idTransaccion
     * @param desvincularTodo Marca para indicar si se desaea borrar todas las relaciones de la transacción dada o una determinada combinación de transacción-denominacionCuenta
     * @return
     */
    public Boolean eliminarCuentaTransaccion (String denominacionCuenta, Long idTransaccion, Character desvincularTodo) {
        try {
            long res = -1;
            if (desvincularTodo.equals('S')) {
                res = db.delete("db_cuenta_transaccion", "cutr_tr_id" + "=?",new String[]{idTransaccion.toString()});
            } else {
                res = db.delete("db_cuenta_transaccion", "cutr_ct_denominacion" + "=? AND " + "cutr_tr_id" + "=?",new String[]{denominacionCuenta,idTransaccion.toString()});
            }

            return (res == -1 ? false : true);
        } catch (Exception ex) {
            return false;
        }
    }

    public Boolean eliminarCuenta (String denominacionCuenta) {
        Integer res = -1;
        try{
            //Elimino de la relación cuenta-transaccion
            res = db.delete("db_cuenta_transaccion", "cutr_ct_denominacion" + "=?",new String[]{denominacionCuenta.trim()}); //Desvinculación de relación cuenta-transaccion
            //Elimino de cuentas
            res = db.delete("db_cuenta", "ct_denominacion" + "=?",new String[]{denominacionCuenta.trim()}); //Eliminación de la tabla de cuentas
            //Elimino de la tabla de transacciones
            res = db.delete("db_transaccion","cutr_ct_denominacion =?",new String[]{denominacionCuenta.trim()});
            return true;
        }catch (Exception ex) {
            return false;
        }
    }
}


