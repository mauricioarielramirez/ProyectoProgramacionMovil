package progmovil.gugler.com.proyectofinal.servicio;

import android.content.Context;

import progmovil.gugler.com.proyectofinal.dao.CuentaDAO;
import progmovil.gugler.com.proyectofinal.exception.ValidacionException;
import progmovil.gugler.com.proyectofinal.modelo.Cuenta;

/**
 * Created by ericd on 13/6/2017.
 */

public class ServicioCuentas extends Servicio {
    private CuentaDAO cuentaDao;

    public ServicioCuentas(Context contexto, String cadena){
        if (cuentaDao == null){
            cuentaDao = new CuentaDAO(contexto,cadena);
        }
    };

    public Boolean agregarCuenta(Cuenta cuenta) throws ValidacionException, Exception {
        cuentaDao.agregar(cuenta);
        return true;
    }

}
