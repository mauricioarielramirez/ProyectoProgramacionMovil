package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 10/3/2018.
 */

public class CabeceraResumenDTO {
    private String periodo;
    private String totalCreditos;
    private String totalDebito;
    private String saldoActual;
    /*Para la cabecera en modo comparador*/
    private String periodo1;
    private String periodo2;

    public CabeceraResumenDTO() {}

    public CabeceraResumenDTO(String periodo, String totalCreditos, String totalDebito, String saldoActual, String periodo1, String periodo2) {
        this.periodo = periodo;
        this.totalCreditos = totalCreditos;
        this.totalDebito = totalDebito;
        this.saldoActual = saldoActual;
        this.periodo1 = periodo1;
        this.periodo2 = periodo2;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getTotalCreditos() {
        return totalCreditos;
    }

    public void setTotalCreditos(String totalCreditos) {
        this.totalCreditos = totalCreditos;
    }

    public String getTotalDebito() {
        return totalDebito;
    }

    public void setTotalDebito(String totalDebito) {
        this.totalDebito = totalDebito;
    }

    public String getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(String saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getPeriodo1() {
        return periodo1;
    }

    public void setPeriodo1(String periodo1) {
        this.periodo1 = periodo1;
    }

    public String getPeriodo2() {
        return periodo2;
    }

    public void setPeriodo2(String periodo2) {
        this.periodo2 = periodo2;
    }
}
