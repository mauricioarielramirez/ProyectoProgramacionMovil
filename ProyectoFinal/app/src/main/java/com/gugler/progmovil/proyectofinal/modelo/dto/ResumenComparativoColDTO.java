package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 13/3/2018.
 */

public class ResumenComparativoColDTO {
    private String debito;
    private String cantidadDebitos;
    private String credito;
    private String cantidadCreditos;
    private String saldoInicial;
    private String saldoFinal;

    public ResumenComparativoColDTO() {
    }

    public ResumenComparativoColDTO(String debito, String cantidadDebitos, String credito, String cantidadCreditos, String saldoInicial, String saldoFinal) {
        this.debito = debito;
        this.cantidadDebitos = cantidadDebitos;
        this.credito = credito;
        this.cantidadCreditos = cantidadCreditos;
        this.saldoInicial = saldoInicial;
        this.saldoFinal = saldoFinal;
    }

    public String getDebito() {
        return debito;
    }

    public void setDebito(String debito) {
        this.debito = debito;
    }

    public String getCantidadDebitos() {
        return cantidadDebitos;
    }

    public void setCantidadDebitos(String cantidadDebitos) {
        this.cantidadDebitos = cantidadDebitos;
    }

    public String getCredito() {
        return credito;
    }

    public void setCredito(String credito) {
        this.credito = credito;
    }

    public String getCantidadCreditos() {
        return cantidadCreditos;
    }

    public void setCantidadCreditos(String cantidadCreditos) {
        this.cantidadCreditos = cantidadCreditos;
    }

    public String getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(String saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public String getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(String saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
}
