package progmovil.gugler.com.proyectofinal.exception;

/**
 * Created by Ariel on 7/7/2017.
 */

public class ValidacionException extends Exception {
    public static final String EXISTE_EN_BASE = "El elemento que se intenta agregar ya existe";

    private String mensaje;
    private String stackTraceOriginal;

    public ValidacionException() {
        super();
    }

    public ValidacionException(String mensaje) {
        super();
        this.mensaje = mensaje;

    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getStackTraceOriginal() {
        return stackTraceOriginal;
    }

    public void setStackTraceOriginal(String stackTraceOriginal) {
        this.stackTraceOriginal = stackTraceOriginal;
    }

}
