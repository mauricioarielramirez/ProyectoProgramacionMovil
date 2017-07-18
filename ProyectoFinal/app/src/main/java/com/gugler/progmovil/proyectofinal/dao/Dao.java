package com.gugler.progmovil.proyectofinal.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ericd on 6/6/2017.
 */

public class Dao extends SQLiteOpenHelper {

    private String sql;

    public Dao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, String cadena) {
        super(context, name, factory, version);
        sql = cadena;
    }

    public Dao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String cadenaAuxiliar = sql;
        Integer posicionFinal;
        posicionFinal = cadenaAuxiliar.indexOf(";");
        while (posicionFinal!= -1){
            db.execSQL(cadenaAuxiliar.substring(0,posicionFinal));
            cadenaAuxiliar = cadenaAuxiliar.substring(posicionFinal+1);
            posicionFinal = cadenaAuxiliar.indexOf(";");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Integer ultimoId(String campoId, String tabla,SQLiteDatabase db){
        Integer id = 0;
        StringBuilder consulta = new StringBuilder();

        consulta.append("SELECT MAX");
        consulta.append(campoId+" FROM");
        consulta.append(tabla);
        Cursor cursor = db.rawQuery(consulta.toString(),null);
        if (cursor.moveToFirst()){
            if (cursor.getCount() == 1){
                do{
                    id = cursor.getInt(0);
                }while (cursor.moveToNext());
            }
        }
        return id;
    }
}
