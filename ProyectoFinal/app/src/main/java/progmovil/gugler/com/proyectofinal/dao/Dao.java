package progmovil.gugler.com.proyectofinal.dao;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import progmovil.gugler.com.proyectofinal.R;

/**
 * Created by ericd on 6/6/2017.
 */

public abstract class Dao extends SQLiteOpenHelper {

    //private String sqlCreate = "create table ";

    public Dao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public Dao(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Context context;
        //File script = new File("");
        //context.getResources().openRawResource(R.
        //FileOutputStream script = new FileOutputStream(R.);

        //script.
    }
}
