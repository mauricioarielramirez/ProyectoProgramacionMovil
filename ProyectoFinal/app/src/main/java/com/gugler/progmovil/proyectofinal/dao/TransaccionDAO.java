package com.gugler.progmovil.proyectofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Cuenta;
import com.gugler.progmovil.proyectofinal.modelo.Transaccion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ariel on 22/8/2017.
 */

public class TransaccionDAO {

    private static final String TR_ID = "tr_id";
    private static final String TR_NOMBRE = "tr_nombre";
    private static final String TR_TIPO = "tr_tipo";
    private static final String TR_MONTO = "tr_monto";
    private static final String TR_FAVORITO = "tr_favorito";

    private Dao baseDeDatos;
    private SQLiteDatabase db;

    public void crearBase(Context context, String cadena) {
        baseDeDatos = new Dao(context,"db_proyecto",null,1,cadena);
        db = baseDeDatos.getWritableDatabase();
    }

    /**
     * Permite dar de alta una cuenta
     * @param transaccion
     * @return Devuelve True si la operación se realiza con éxito
     * @throws Exception
     */
    public Long agregar(Transaccion transaccion) throws ValidacionException, Exception {
        try {
            ContentValues registro = new ContentValues();
            registro.put(TR_ID, (obtenerUltimoId() + 1));
            registro.put(TR_NOMBRE, transaccion.getNombre());
            registro.put(TR_TIPO, transaccion.getTipo());
            registro.put(TR_MONTO, transaccion.getMonto());
            registro.put(TR_FAVORITO, transaccion.getFavorito());
            long res = db.insert("db_transaccion",null,registro);

            return (res == -1 ? -1 : obtenerUltimoId());
        } catch (Exception ex) {
            throw  ex;
        }
    }

    public Boolean existeTransaccion (String nombreTransaccion) {
        StringBuilder cadenaQuery = new StringBuilder();
        Long dato = 0L;
        cadenaQuery.append("select ifnull((select 1 as dato from db_transaccion where upper(tr_nombre) = upper('");
        cadenaQuery.append(nombreTransaccion.toUpperCase());
        cadenaQuery.append("')),0) as valor");
        Cursor cursor = db.rawQuery(cadenaQuery.toString(),null);

        if (cursor.moveToFirst()){
            do{
                dato = cursor.getLong(0);
            }while(cursor.moveToNext());
            cursor.close();
        }

        return (dato==0?false:true);
    }

    private Long obtenerUltimoId(){
        Cursor cursor = db.rawQuery("SELECT MAX(tr_id) from db_transaccion",null);
        Long id = 0L;
        if (cursor.moveToFirst()){
            do{
                id = cursor.getLong(0);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return id;
    }

    /**
     * Devuelve una cuenta buscada por denominación
     * @param nombre
     * @return
     */
    public List<Transaccion> obtenerPorNombre(String nombre){
        List<Transaccion> transaccion = new ArrayList<Transaccion>();
        Transaccion tAux = new Transaccion();
        String[] campos = new String[]{TR_ID ,TR_NOMBRE, TR_TIPO, TR_MONTO, TR_FAVORITO}; //Campos a devolver
        String[] filtro = new String[]{nombre.toUpperCase()};   //Filtro
        Cursor cursor = db.query("db_transaccion",campos,"upper("+TR_NOMBRE+")" + "=?",filtro,null,null,null);
        if (cursor.moveToFirst()){
            do{
                tAux.setId(cursor.getLong(0));
                tAux.setNombre(cursor.getString(1));
                tAux.setTipo(cursor.getString(2));
                tAux.setMonto(cursor.getFloat(3));
                tAux.setFavorito(Boolean.parseBoolean(cursor.getString(4)));
                transaccion.add(tAux);
            }while(cursor.moveToNext());
            cursor.close();
        }
        return transaccion;
    }

    /**
     * Obtiene una transacción por su ID
     * @param id
     * @return
     */
    public Transaccion obtenerPorId(Long id){
        Transaccion transaccion = new Transaccion();
        String[] campos = new String[]{TR_ID ,TR_NOMBRE, TR_TIPO, TR_MONTO, TR_FAVORITO}; //Campos a devolver
        String[] filtro = new String[]{id.toString()};
        Cursor cursor = db.query("db_transaccion",campos,TR_ID+"=?",filtro,null,null,null);
        if (cursor.moveToFirst()) {
            do {
                transaccion.setId(cursor.getLong(0));
                transaccion.setNombre(cursor.getString(1));
                transaccion.setTipo(cursor.getString(2));
                transaccion.setMonto(cursor.getFloat(3));
                transaccion.setFavorito( (cursor.getInt(4))==0?false:true );
            } while (cursor.moveToNext());
            cursor.close();
        }
        return transaccion;
    }

    /**
     * Elimina una transacción por id
     * @param id
     * @return
     */
    public boolean borrar(String id){
        db.delete("db_transaccion", TR_ID +"=?"+ id, null);
        return true;
    }

    /**
     * Intenta modificar un objeto transacción.
     * @param transaccion
     * @return Devuelve true si la modificación se llevó con éxito, sino false.
     * @throws ValidacionException Devolución en caso de que no exista el elemento a modificar.
     * @throws Exception
     */
    public boolean modificar(Transaccion transaccion) throws ValidacionException,Exception {

        try {
            String[] valores = {transaccion.getId().toString()};
            ContentValues registro = new ContentValues();
            registro.put(TR_ID, transaccion.getId());
            registro.put(TR_NOMBRE, transaccion.getNombre());
            registro.put(TR_TIPO, transaccion.getTipo());
            registro.put(TR_MONTO, transaccion.getMonto());
            registro.put(TR_FAVORITO, transaccion.getFavorito());
            long res = db.update("db_transaccion",registro,"tr_id =?",valores);
            return (res == -1 ? false : true);
        } catch (Exception ex){
            throw  ex;
        }
    }

    /**
     * Devuelve todas las transacciones existentes
     * @return
     */
    public ArrayList<Transaccion> listarTodo(){
        ArrayList<Transaccion> transacciones = new ArrayList<Transaccion>();
        Cursor cursor = db.rawQuery("SELECT "+TR_ID+", "+TR_NOMBRE+", "+TR_TIPO+", "+TR_MONTO+", "+TR_FAVORITO+" FROM db_transaccion",null);
        if (cursor.moveToFirst()){
            do{
                transacciones.add(new Transaccion(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3), (cursor.getInt(4))==0?false:true ));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return transacciones;
    }

    /**
     * Devuelve las cuentas relacionadas con la transaccion
     * @param contexto
     * @param cadena
     * @param idTransaccion
     * @return
     * @throws ValidacionException
     */
    public ArrayList<Cuenta> obtenerCuentas(Context contexto, String cadena, Long idTransaccion) throws ValidacionException {
        ArrayList<Cuenta> cuentas = new ArrayList<Cuenta>();
        CuentaDAO cuentaDAO = new CuentaDAO();
        cuentaDAO.crearBase(contexto,cadena);

        String[] campos = new String[]{"cutr_ct_denominacion", "cutr_tr_id"}; //Campos a devolver
        String[] filtro = new String[]{idTransaccion.toString()};                         //Filtro

        try{
            Cursor cursor = db.rawQuery("SELECT DISTINCT cutr_ct_denominacion FROM db_cuenta_transaccion WHERE cutr_tr_id ="+idTransaccion.toString(),null);
            if (cursor.moveToFirst()) {
                do {
                    cuentas.add(cuentaDAO.obtenerPorDenominacion(cursor.getString(0)));
                } while(cursor.moveToNext());
                cursor.close();
            }
            return cuentas;
        }catch (Exception ex){
            throw new ValidacionException(ValidacionException.PROBLEMAS_LEER_TRANSACCION);
        }
    }

    public Boolean eliminarTransaccion (Context context, String cadena, Long idTransaccion) {
        Integer res = -1;
        try{
            res = db.delete("db_transaccion", "tr_id" + "=?",new String[]{idTransaccion.toString()});
            return true;
        } catch (Exception ex) {
            return false;
        }
    }
}
