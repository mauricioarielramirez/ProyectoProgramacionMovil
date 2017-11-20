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

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof Transaccion){
//            return ((Transaccion)obj).getTipo().equals(this.getTipo()) &&
//                    ((Transaccion)obj).getNombre().equals(this.getNombre()) &&
//                    ((Transaccion)obj).getMonto().equals(this.getMonto());
//        }
//        return false;
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Transaccion that = (Transaccion) o;

        if (!id.equals(that.id)) return false;
        if (!nombre.equals(that.nombre)) return false;
        if (!tipo.equals(that.tipo)) return false;
        if (!monto.equals(that.monto)) return false;
        return favorito.equals(that.favorito);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nombre.hashCode();
        result = 31 * result + tipo.hashCode();
        result = 31 * result + monto.hashCode();
        result = 31 * result + favorito.hashCode();
        return result;
    }
}
