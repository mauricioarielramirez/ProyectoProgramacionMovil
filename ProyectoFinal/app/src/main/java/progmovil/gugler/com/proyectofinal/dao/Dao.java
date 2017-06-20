package progmovil.gugler.com.proyectofinal.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import progmovil.gugler.com.proyectofinal.R;

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
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
