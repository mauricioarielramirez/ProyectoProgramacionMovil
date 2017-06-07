package progmovil.gugler.com.proyectofinal.modelo;

/**
 * Created by ericd on 6/6/2017.
 */

public class Recordatorio {
    private Long id;
    private String texto;
    private String accion;
    private Transaccion transaccionMultiplicador;
    private Integer minimoMultiplicador;
    private Float saldoMinimo;
    private Float saldoMaximo;
    private Cuenta cuenta;

    public Recordatorio() {
    }

    public Recordatorio(Long id, String texto, String accion, Transaccion transaccionMultiplicador, Integer minimoMultiplicador, Float saldoMinimo, Float saldoMaximo, Cuenta cuenta) {
        this.id = id;
        this.texto = texto;
        this.accion = accion;
        this.transaccionMultiplicador = transaccionMultiplicador;
        this.minimoMultiplicador = minimoMultiplicador;
        this.saldoMinimo = saldoMinimo;
        this.saldoMaximo = saldoMaximo;
        this.cuenta = cuenta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public Transaccion getTransaccionMultiplicador() {
        return transaccionMultiplicador;
    }

    public void setTransaccionMultiplicador(Transaccion transaccionMultiplicador) {
        this.transaccionMultiplicador = transaccionMultiplicador;
    }

    public Integer getMinimoMultiplicador() {
        return minimoMultiplicador;
    }

    public void setMinimoMultiplicador(Integer minimoMultiplicador) {
        this.minimoMultiplicador = minimoMultiplicador;
    }

    public Float getSaldoMinimo() {
        return saldoMinimo;
    }

    public void setSaldoMinimo(Float saldoMinimo) {
        this.saldoMinimo = saldoMinimo;
    }

    public Float getSaldoMaximo() {
        return saldoMaximo;
    }

    public void setSaldoMaximo(Float saldoMaximo) {
        this.saldoMaximo = saldoMaximo;
    }

    public Cuenta getCuenta() {
        return cuenta;
    }

    public void setCuenta(Cuenta cuenta) {
        this.cuenta = cuenta;
    }
}
