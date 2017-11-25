package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 18/11/2017.
 */

public class FavoritoItem {
    private String idTransaccion;
    private String nombreTransaccion;
    private String cantidadCuentasAsociadas;
    private String tipo;

    public FavoritoItem() {
    }

    public FavoritoItem(String nombreTransaccion, String cantidadCuentasAsociadas, String tipo) {
        this.nombreTransaccion = nombreTransaccion;
        this.cantidadCuentasAsociadas = cantidadCuentasAsociadas;
        this.tipo = tipo;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getNombreTransaccion() {
        return nombreTransaccion;
    }

    public String getCantidadCuentasAsociadas() {
        return cantidadCuentasAsociadas;
    }

    public String getTipo() {
        return tipo;
    }

    public void setNombreTransaccion(String nombreTransaccion) {
        this.nombreTransaccion = nombreTransaccion;
    }

    public void setCantidadCuentasAsociadas(String cantidadCuentasAsociadas) {
        this.cantidadCuentasAsociadas = cantidadCuentasAsociadas;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
