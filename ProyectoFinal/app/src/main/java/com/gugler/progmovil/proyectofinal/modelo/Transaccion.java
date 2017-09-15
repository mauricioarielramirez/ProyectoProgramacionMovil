package com.gugler.progmovil.proyectofinal.modelo;

/**
 * Created by ericd on 6/6/2017.
 */

public class Transaccion {
    private Long id;
    private String nombre;
    private String tipo;
    private Float monto;
    private Boolean favorito;

    public Transaccion() {
    }

    public Transaccion(Long id, String nombre, String tipo, Float monto, Boolean favorito) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.monto = monto;
        this.favorito = favorito;
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

    public Float getMonto() {
        return monto;
    }

    public void setMonto(Float monto) {
        this.monto = monto;
    }

    public Boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(Boolean favorito) {
        this.favorito = favorito;
    }
}
