package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 31/3/2018.
 */

public class CabeceraConsultaDTO {
    private String textoConcepto;
    private String textoValor;

    public CabeceraConsultaDTO() { };

    public CabeceraConsultaDTO(String textoConcepto, String textoValor) {
        this.textoConcepto = textoConcepto;
        this.textoValor = textoValor;
    }

    public String getTextoConcepto() {
        return textoConcepto;
    }

    public void setTextoConcepto(String textoConcepto) {
        this.textoConcepto = textoConcepto;
    }

    public String getTextoValor() {
        return textoValor;
    }

    public void setTextoValor(String textoValor) {
        this.textoValor = textoValor;
    }
}
