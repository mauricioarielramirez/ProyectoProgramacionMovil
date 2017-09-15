package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 14/9/2017.
 */

public class ListaItem {
    private Integer id;
    private String descripcion;

    public ListaItem(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
