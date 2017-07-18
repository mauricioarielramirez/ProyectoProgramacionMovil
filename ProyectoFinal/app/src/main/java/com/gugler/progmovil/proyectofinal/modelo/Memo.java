package com.gugler.progmovil.proyectofinal.modelo;

/**
 * Created by ericd on 6/6/2017.
 */

public class Memo {
    private Long id;
    private String rutaAcceso;
    private String texto;
    private String tipo;

    public Memo() {
    }

    public Memo(Long id, String rutaAcceso, String texto, String tipo) {
        this.id = id;
        this.rutaAcceso = rutaAcceso;
        this.texto = texto;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRutaAcceso() {
        return rutaAcceso;
    }

    public void setRutaAcceso(String rutaAcceso) {
        this.rutaAcceso = rutaAcceso;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
