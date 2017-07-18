package com.gugler.progmovil.proyectofinal.modelo;

import java.util.ArrayList;

/**
 * Created by ericd on 6/6/2017.
 */

public class Reporte {
    private Long id;
    private String nombre;
    private String descripcion;
    private ArrayList<Movimiento> listaMovimiento;

    public Reporte() {
    }

    public Reporte(Long id, String nombre, String descripcion, ArrayList<Movimiento> listaMovimiento) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.listaMovimiento = listaMovimiento;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Movimiento> getListaMovimiento() {
        return listaMovimiento;
    }

    public void setListaMovimiento(ArrayList<Movimiento> listaMovimiento) {
        this.listaMovimiento = listaMovimiento;
    }
}
