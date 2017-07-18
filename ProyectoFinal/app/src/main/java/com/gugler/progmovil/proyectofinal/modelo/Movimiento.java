package com.gugler.progmovil.proyectofinal.modelo;

import java.util.Date;

/**
 * Created by ericd on 6/6/2017.
 */

public class Movimiento {
    private Long id;
    private Cuenta cuentaAsociada;
    private Float monto;
    private String tipo;
    private Float saldoActual;
    private Date fechaHora;

    public Movimiento() {
    }

    public Movimiento(Long id, Cuenta cuentaAsociada, Float monto, String tipo, Float saldoActual, Date fechaHora) {
        this.id = id;
        this.cuentaAsociada = cuentaAsociada;
        this.monto = monto;
        this.tipo = tipo;
        this.saldoActual = saldoActual;
        this.fechaHora = fechaHora;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cuenta getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(Cuenta cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Float getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Float saldoActual) {
        this.saldoActual = saldoActual;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}
