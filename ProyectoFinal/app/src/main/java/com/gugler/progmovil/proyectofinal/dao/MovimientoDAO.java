package com.gugler.progmovil.proyectofinal.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gugler.progmovil.proyectofinal.exception.ValidacionException;
import com.gugler.progmovil.proyectofinal.modelo.Movimiento;
import com.gugler.progmovil.proyectofinal.modelo.dto.CabeceraResumenDTO;
import com.gugler.progmovil.proyectofinal.modelo.dto.ResumenComparativoDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
            String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(movimiento.getFechaHora());
            registro.put(MV_FECHA_HORA,fecha);

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

    public ArrayList<Movimiento> listarTodo() throws ParseException {
        ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
        Cursor cursor = db.rawQuery("SELECT "+MV_ID+", "+MV_DENOMINACION_CUENTA+", "+MV_NOMBRE_TRANSACCION+", "+MV_MONTO+", "+MV_TIPO+", "+MV_FECHA_HORA+", "+MV_SALDO_ACTUAL+ " FROM db_movimiento",null);
        if (cursor.moveToFirst()){
            do{
                //Long id, String cuentaAsociada, String transaccion, Float monto, String tipo, Float saldoActual, Date fechaHora
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = dateFormat.parse(cursor.getString(5));
                movimientos.add(new Movimiento(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getFloat(6), fecha));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movimientos;
    }

    public ArrayList<Movimiento> listarPorCuenta(String denominacionCuenta) throws ParseException {
        ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
        Cursor cursor = db.rawQuery("SELECT "+MV_ID+", "+MV_DENOMINACION_CUENTA+", "+MV_NOMBRE_TRANSACCION+", "+MV_MONTO+", "+MV_TIPO+", "+MV_FECHA_HORA+", "+MV_SALDO_ACTUAL+ " FROM db_movimiento where mv_denominacion_cuenta = "+denominacionCuenta ,null);
        if (cursor.moveToFirst()){
            do{
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date fecha = dateFormat.parse(cursor.getString(5));
                movimientos.add(new Movimiento(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getFloat(6), fecha));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movimientos;
    }


    /**
     * Devuelve el contenido de la cabecera llenando los primeros 4 campos para conuslta normal, o los últimos dos para comparacion de periodos
     * @param fechaDesdePeriodo1
     * @param fechaHastaPeriodo1
     * @param fechaDesdePeriodo2
     * @param fechaHastaPeriodo2
     * @return
     */
    public CabeceraResumenDTO devolverCabecera(Date fechaDesdePeriodo1, Date fechaHastaPeriodo1, Date fechaDesdePeriodo2, Date fechaHastaPeriodo2) {
        CabeceraResumenDTO cabeceraResumenDTO = new CabeceraResumenDTO();
        String fechaDesdePeriodo1String = new SimpleDateFormat("dd/MM/yyyy").format(fechaDesdePeriodo1);
        String fechaHastaPeriodo1String = new SimpleDateFormat("dd/MM/yyyy").format(fechaHastaPeriodo1);

        // SI FECHAS DE PERIODO 2 VIENEN VACIAS (MODO DE CONSULTA ORDINARIO)
        if (fechaDesdePeriodo2.toString().equals("") && fechaHastaPeriodo2.toString().equals("")){
            /*Este es el caso de la cabecera ordinaria*/
            Cursor cursor = db.rawQuery("Select ifnull(case when mv_tipo = 'C' then sum(mv_monto) end,0) AS \"Total de créditos\","
                    +"ifnull(case when mv_tipo = 'D' then sum(mv_monto) end,0) AS \"Total de débitos\","+
                    "(select mv_saldo_actual from db_movimiento where mv_fecha_hora >= '"+fechaDesdePeriodo1.toString()+
                    "' and mv_fecha_hora <= '"+fechaHastaPeriodo1.toString()+
                    "' order by mv_fecha_hora Desc LIMIT 1) as \"Saldo Actual\""+
                    "FROM db_movimiento where mv_fecha_hora >= '"+fechaDesdePeriodo1.toString() +
                    "' and mv_fecha_hora <= '"+fechaHastaPeriodo1.toString()+"'",null); //lleva null a final
            if (cursor.moveToFirst()){
                do{
                    //movimientos.add(new Movimiento(cursor.getLong(0),cursor.getString(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4),cursor.getFloat(6), Date.valueOf(cursor.getString(5))));
                    cabeceraResumenDTO.setPeriodo("Del "+fechaDesdePeriodo1String+" al " + fechaHastaPeriodo1String);
                    cabeceraResumenDTO.setTotalCreditos("$ " + String.valueOf(cursor.getDouble(0)));
                    cabeceraResumenDTO.setTotalDebito("$ " + String.valueOf(cursor.getDouble(1)));
                    cabeceraResumenDTO.setSaldoActual("$ " + String.valueOf(cursor.getDouble(2)));

                }while(cursor.moveToNext());
            }
            cursor.close();
        //SI FECHAS DEL PERIODO DOS VIENEN CARGADAS, ENTONCES CARGAR LAS CABECERAS CON INICIO-FIN DE LOS DOS PERIODOS
        } else {
            //Solo se necesita mostrar los dos periodos desde - hasta
            String fechaDesdePeriodo2String = new SimpleDateFormat("dd/MM/yyyy").format(fechaDesdePeriodo2);
            String fechaHastaPeriodo2String = new SimpleDateFormat("dd/MM/yyyy").format(fechaHastaPeriodo2);
            cabeceraResumenDTO.setPeriodo1("Del "+fechaDesdePeriodo1String+" al " + fechaHastaPeriodo1String);
            cabeceraResumenDTO.setPeriodo2("Del "+fechaDesdePeriodo2String+" al " + fechaHastaPeriodo2String);

        }
        return cabeceraResumenDTO;
    }


    /*
    *   private String concepto;
    private String periodo1;
    private String periodo2;
    private String diferencia;
    * */
    /**
     * Devuelve los datos para el cuadro de resumen comparativo para las consultas con dos periodos
     * @param fechaDesdePeriodo1
     * @param fechaHastaPeriodo1
     * @param fechaDesdePeriodo2
     * @param fechaHastaPeriodo2
     * @return
     */
    public ArrayList<ResumenComparativoDTO> devolverResumenComparativo(Date fechaDesdePeriodo1, Date fechaHastaPeriodo1, Date fechaDesdePeriodo2, Date fechaHastaPeriodo2) {
       /* Cursor cursor = db.rawQuery("select * from db_movimiento where mv_fecha_hora between '" + fechaDesdePeriodo1.toString() + "' and '" + fechaHastaPeriodo1.toString() + "' " +
                "union all select * from db_movimiento where mv_fecha_hora between '" + fechaDesdePeriodo2.toString() + "' and '" + fechaHastaPeriodo2.toString() + "';", null);
        ArrayList<ResumenComparativoDTO> listaResumenDto = new ArrayList<ResumenComparativoDTO>();
        if (cursor.moveToFirst()) {
            do {

            } while (cursor.moveToNext());
        }
        cursor.close();*/
       //Para periodo 1 cargar la lista movimientosPeriodo1

        return null;
    }

    /**
     * Permite listar los movimientos por rango de fechas
     * @param denominacionCuenta
     * @param fechaDesde
     * @param fechaHasta
     * @return
     */
    public ArrayList<Movimiento> listarTodoConFecha(String denominacionCuenta, Date fechaDesde, Date fechaHasta) {
        ArrayList<Movimiento> movimientos = new ArrayList<Movimiento>();
        Date fechaDesdeLocal;
        Date fechaHastaLocal;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fechaDesdeLocal = fechaDesde;
        fechaHastaLocal = fechaHasta;

        Cursor cursor = db.rawQuery("SELECT mv_id, mv_monto, mv_tipo, mv_saldo_actual, mv_fecha_hora, mv_denominacion_cuenta, mv_nombre_transaccion from db_movimiento where substr(mv_fecha_hora,1,10) between substr('" + formatter.format(fechaDesdeLocal) +"',1,10) and substr('"+ formatter.format(fechaHastaLocal)+"',1,10) and mv_denominacion_cuenta = '"+denominacionCuenta.toString()+"' order by mv_fecha_hora asc" ,null);
        Date fecha = new Date();
        if (cursor.moveToFirst()) {
            do {
                try {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    fecha = dateFormat.parse(cursor.getString(4));

                    //fecha = new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(4));
                } catch (ParseException e) {
                    fecha = Calendar.getInstance().getTime(); // O lanzar hacia arriba
                }
                movimientos.add(new Movimiento(cursor.getLong(0),cursor.getString(5),cursor.getString(6),cursor.getFloat(1),cursor.getString(2),cursor.getFloat(3),fecha));
            } while (cursor.moveToNext());
        }
        return movimientos;
    }

}
