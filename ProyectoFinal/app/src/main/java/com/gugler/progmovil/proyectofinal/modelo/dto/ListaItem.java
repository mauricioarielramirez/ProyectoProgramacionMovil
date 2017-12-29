package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 14/9/2017.
 */

public class ListaItem {
    public static final int SIN_ICONO = 0;
    public static final int OPERACIONES_DEBITO = 1;
    public static final int OPERACIONES_CREDITO = 2;
    public static final int OPERACIONES_CONSULTAS = 3;
    public static final int OPERACIONES_ADMINISTRAR = 4;
    public static final int OPERACIONES_ADMINISTRAR_NUEVA_CUENTA = 5;
    public static final int OPERACIONES_ADMINISTRAR_NUEVA_TRANSACCION = 6;
    public static final int OPERACIONES_ADMINISTRAR_MODIFICAR_CUENTA = 7;
    public static final int OPERACIONES_ADMINISTRAR_MODIFICAR_TRANSACCION = 8;
    public static final int OPERACIONES_ADMINISTRAR_MODIFICAR_MOVIMIENTO = 9;


    private Integer id;
    private String descripcion;
    private Integer tipoItem;

    public ListaItem(Integer id, String descripcion, Integer tipoItem) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipoItem = tipoItem;
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

    public Integer getTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(Integer tipoItem) {
        this.tipoItem = tipoItem;
    }

}
