package progmovil.gugler.com.proyectofinal.modelo;

import java.util.Date;

/**
 * Created by ericd on 6/6/2017.
 */

public class Criterio {
    private Long id;
    private Date fechaInicio;
    private Date fechaFin;
    private Date horaInicio;
    private Date horaFin;
    private Character tipoTransaccion;
    private Transaccion transaccionMultiplicador;
    private Integer minimoMultiplicador;
    private String periodicidad;
    private Float saldoMinimo;
    private Float saldoMaximo;
    private Cuenta cuenta;

    public Criterio() {
    }

    public Criterio(Long id, Date fechaInicio, Date fechaFin, Date horaInicio, Date horaFin, Character tipoTransaccion, Transaccion transaccionMultiplicador, Integer minimoMultiplicador, String periodicidad, Float saldoMinimo, Float saldoMaximo, Cuenta cuenta) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoTransaccion = tipoTransaccion;
        this.transaccionMultiplicador = transaccionMultiplicador;
        this.minimoMultiplicador = minimoMultiplicador;
        this.periodicidad = periodicidad;
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

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public Character getTipoTransaccion() {
        return tipoTransaccion;
    }

    public void setTipoTransaccion(Character tipoTransaccion) {
        this.tipoTransaccion = tipoTransaccion;
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

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
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
