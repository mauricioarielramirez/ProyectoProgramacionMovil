package com.gugler.progmovil.proyectofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Movimiento;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Ariel on 13/1/2018.
 */

public class MovimientoDAO {

    public static final Integer CUENTA = 0;
    public static final Integer TRANSACCION = 1;

    private static final String MV_ID = "mv_id";
    private static final String MV_DENOMINACION_CUENTA = "mv_denominacion_cuenta";
    private static final String MV_NOMBRE_TRANSACCION = "mv_nombre_transaccion";
    private static final String MV_SALDO_ACTUAL = "mv_saldo_actual";
    private static final String MV_TIPO = "mv_tipo";
    private static final String MV_MONTO = "mv_monto";
    private static final String MV_FECHA_HORA = "mv_fecha_hora";


    private Dao baseDeDatos;
    private SQLiteDatabase db;

    private Context contextoAux; // Atributo auxiliar
    private String cadenaSql; // Atributo auxiliar

    public MovimientoDAO() {
    }

    public void crearBase(Context context, String cadena) {
        baseDeDatos = new Dao(context,"db_proyecto",null,1,cadena);
        db = baseDeDatos.getWritableDatabase();
        contextoAux = context;
        cadenaSql = cadena;
    }

    public Boolean agregar(Movimiento movimiento) throws ValidacionException, Exception {
        try {

            ContentValues registro = new ContentValues();
            registro.put(MV_ID, obtenerUltimoId() + 1);
            registro.put(MV_DENOMINACION_CUENTA,movimiento.getCuentaAsociada().trim());
            registro.put(MV_NOMBRE_TRANSACCION,movimiento.getTransaccion().trim());
            registro.put(MV_TIPO,movimiento.getTipo().trim());
            registro.put(MV_MONTO,movimiento.getMonto());
            registro.put(MV_SALDO_ACTUAL,movimiento.getSaldoActual());
            registro.put(MV_FECHA_HORA,movimiento.getFechaHora().toString());

            long res = db.insert("db_movimiento",null,registro);

            return (res == -1 ? false : true);

        }catch (Exception ex){
            throw  ex;
        }
    }

    private Long obtenerUltimoId(){
        Cursor cursor = db.rawQuery("SELECT MAX(mv_id) from db_movimiento",null);
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
     * Modifica un movimiento en particular, accedido por una consulta
     * @param movimiento
     * @return
     * @throws ValidacionException
     * @throws Exception
     */
    public Boolean modificar (Movimiento movimiento) throws ValidacionException, Exception{

        return false;
    }

    /**
     * Modifica en forma masiva la historia de movimiento
     * @param valorViejo
     * @param valorNuevo
     * @param tipo 0 para denominaciónCuenta, 1 para nombreTransaccion
     * @return
     * @throws ValidacionException
     * @throws Exception
     */
    public Boolean modificarHistoriaDeMovimiento (String valorViejo, String valorNuevo, Integer tipo) throws ValidacionException, Exception {
        String nombreCampo;
        if (!valorViejo.equals(valorNuevo)) {
            nombreCampo = (tipo==CUENTA ? "mv_denominacion_cuenta" : "mv_nombre_transaccion");
            ContentValues registroActualizar = new ContentValues();
            registroActualizar.put(nombreCampo, valorNuevo);
            db.update("db_movimiento", registroActualizar, nombreCampo+"=?", new String[] {valorViejo});
        }
        return false;
    }

    public Boolean borrar(Long id) throws ValidacionException, Exception{
        db.delete("db_movimiento", MV_ID +"=?"+ id, null);
        return true;
    }

    public ArrayList<Movimiento> listarTodo() {
        ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
        Cursor cursor = db.rawQuery("SELECT "+MV_ID+", "+MV_DENOMINACION_CUENTA+", "+MV_NOMBRE_TRANSACCION+", "+MV_MONTO+", "+MV_TIPO+", "+MV_FECHA_HORA+", "+MV_SALDO_ACTUAL+ " FROM db_movimiento",null);
        if (cursor.moveToFirst()){
            do{
                //Long id, String cuentaAsociada, String transaccion, Float monto, String tipo, Float saldoActual, Date fechaHora
                movimientos.add(new Movimiento(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getFloat(6), Date.valueOf(cursor.getString(5))));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movimientos;
    }

    public ArrayList<Movimiento> listarPorCuenta(String denominacionCuenta) {
        ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
        Cursor cursor = db.rawQuery("SELECT "+MV_ID+", "+MV_DENOMINACION_CUENTA+", "+MV_NOMBRE_TRANSACCION+", "+MV_MONTO+", "+MV_TIPO+", "+MV_FECHA_HORA+", "+MV_SALDO_ACTUAL+ " FROM db_movimiento where mv_denominacion_cuenta = "+denominacionCuenta ,null);
        if (cursor.moveToFirst()){
            do{
                movimientos.add(new Movimiento(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getFloat(6), Date.valueOf(cursor.getString(5))));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movimientos;
    }

}
