package com.gugler.progmovil.proyectofinal.modelo;

import java.util.ArrayList;

/**
 * Created by ericd on 6/6/2017.
 */

public class Cuenta {
    //private Long id;
    private String denominacion;
    private String descripcion;
    private Float saldo;
    private ArrayList<Transaccion> transaccion;

    public Cuenta() {
    }

    public Cuenta(/*Long id,*/ String denominacion, String descripcion, Float saldo) {
        //this.id = id;
        this.denominacion = denominacion;
        this.descripcion = descripcion;
        this.saldo = saldo;
        transaccion = new ArrayList<Transaccion>();
    }

    public Cuenta(/*Long id,*/ String denominacion, String descripcion, Float saldo, ArrayList<Transaccion> transaccion) {
        //this.id = id;
        this.denominacion = denominacion;
        this.descripcion = descripcion;
        this.saldo = saldo;
        this.transaccion = transaccion;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }

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

    public ArrayList<Transaccion> getTransaccion() {
        return transaccion;
    }

    public void setTransaccion(ArrayList<Transaccion> transaccion) {
        this.transaccion = transaccion;
    }

    @Override
    public String toString() {
        return this.getDenominacion().concat(" ");
    }
}
