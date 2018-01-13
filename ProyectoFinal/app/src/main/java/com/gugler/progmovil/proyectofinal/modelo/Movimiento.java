package com.gugler.progmovil.proyectofinal.modelo;

import java.util.Date;

/**
 * Created by ericd on 6/6/2017.
 */

public class Movimiento {
    private Long id;
    private String cuentaAsociada;
    private String transaccion;
    private Float monto;
    private String tipo;
    private Float saldoActual; /*Saldo de la cuenta al momento del impacto del movimiento*/
    private Date fechaHora;

    public Movimiento() {
    }

    public Movimiento(Long id, String cuentaAsociada, String transaccion, Float monto, String tipo, Float saldoActual, Date fechaHora) {
        this.id = id;
        this.cuentaAsociada = cuentaAsociada;
        this.transaccion = transaccion;
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

    public String getCuentaAsociada() {
        return cuentaAsociada;
    }

    public void setCuentaAsociada(String cuentaAsociada) {
        this.cuentaAsociada = cuentaAsociada;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
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