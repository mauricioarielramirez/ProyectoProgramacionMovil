package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 20/4/2018.
 */

public class MovimientosPorPeriodoDTO {
    private Integer periodo;
    private String fecha;
    private String transaccion;
    private String tipo;
    private String monto;
    private String saldo;

    public MovimientosPorPeriodoDTO() {
    }

    public MovimientosPorPeriodoDTO(Integer periodo, String fecha, String transaccion, String tipo, String monto, String saldo) {
        this.periodo = periodo;
        this.fecha = fecha;
        this.transaccion = transaccion;
        this.tipo = tipo;
        this.monto = monto;
        this.saldo = saldo;
    }

    public Integer getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = periodo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(String transaccion) {
        this.transaccion = transaccion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovimientosPorPeriodoDTO that = (MovimientosPorPeriodoDTO) o;

        if (!fecha.equals(that.fecha)) return false;
        if (!transaccion.equals(that.transaccion)) return false;
        if (!tipo.equals(that.tipo)) return false;
        if (!monto.equals(that.monto)) return false;
        return saldo.equals(that.saldo);

    }

    @Override
    public int hashCode() {
        int result = fecha.hashCode();
        result = 31 * result + transaccion.hashCode();
        result = 31 * result + tipo.hashCode();
        result = 31 * result + monto.hashCode();
        result = 31 * result + saldo.hashCode();
        return result;
    }
}