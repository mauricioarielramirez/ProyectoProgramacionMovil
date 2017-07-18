package com.gugler.progmovil.proyectofinal.modelo;

/**
 * Created by ericd on 6/6/2017.
 */

public class Cuenta {
    private Long id;
    private String denominacion;
    private String descripcion;
    private Float saldo;

    public Cuenta() {
    }

    public Cuenta(Long id, String denominacion, String descripcion, Float saldo) {
        this.id = id;
        this.denominacion = denominacion;
        this.descripcion = descripcion;
        this.saldo = saldo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }

    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getSaldo() {
        return saldo;
    }

    public void setSaldo(Float saldo) {
        this.saldo = saldo;
    }
}
