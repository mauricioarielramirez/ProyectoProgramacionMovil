package com.gugler.progmovil.proyectofinal.modelo;

/**
 * Created by ericd on 6/6/2017.
 */

public class Transaccion {
    private Long id;
    private String nombre;
    private String tipo;
    private String monto;

    public Transaccion() {
    }

    public Transaccion(Long id, String nombre, String tipo, String monto) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.monto = monto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
}
