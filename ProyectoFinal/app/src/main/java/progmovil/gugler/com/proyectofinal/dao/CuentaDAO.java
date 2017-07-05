package progmovil.gugler.com.proyectofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

import progmovil.gugler.com.proyectofinal.modelo.Cuenta;

/**
 * Created by ericd on 13/6/2017.
 */

public class CuentaDAO {

    private Dao baseDeDatos;
    private SQLiteDatabase db;

    public CuentaDAO(Context context, String cadena) {
        baseDeDatos = new Dao(context,"db_proyecto",null,1,cadena);
        db = baseDeDatos.getWritableDatabase();
    }

    public Boolean agregar(Cuenta cuenta) throws Exception {
        try {
            ContentValues registro = new ContentValues();
            registro.put("ct_id", 1);  //VALOR QUEMADO, REVENTARÁ A LA SEGUNDA INSERCIÓN
            registro.put("ct_denominacion", cuenta.getDenominacion());
            registro.put("ct_descripcion", cuenta.getDescripcion());
            registro.put("ct_saldo", cuenta.getSaldo());
            long res = db.insert("db_cuenta",null,registro);

            return (res == -1 ? false : true);

        }catch (Exception ex){
            throw  ex;
            //return false;
        }
    }

    public boolean borrar(int id){
        // SQLiteDatabase db = baseDeDatos.getWritableDatabase();
        db.delete("db_cuenta", "ct_id = " + id, null);
        return true;
    }

}
