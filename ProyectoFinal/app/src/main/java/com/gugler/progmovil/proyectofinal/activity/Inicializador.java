package com.gugler.progmovil.proyectofinal.activity;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Ariel on 25/7/2017.
 */

public class Inicializador {

    public StringBuffer CADENA_SQL;

    public Inicializador(InputStream stream) throws Exception {
        leerScript(stream);
    }

    /**
     * Lee la cadena del archivo de base de datos.
     * @param stream
     * @throws Exception
     */
    private void leerScript(InputStream stream) throws Exception {
        CADENA_SQL = new StringBuffer();
        try {
            //InputStream inputStream = stream;//getResources().openRawResource(R.raw.script);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            for (String linea; (linea=reader.readLine())!=null;){
                CADENA_SQL.append(linea);
            }
        } catch (Exception ex) {
            throw new ValidacionException(ValidacionException.PROBLEMAS_ARCHIVO);
        }
    }

    /**
     * Devuelve el valor de la cadena
     * @return
     */
    public String devolverCadena(){
        return CADENA_SQL.toString();
    }

}
