package com.gugler.progmovil.proyectofinal.modelo.dto;

/**
 * Created by Ariel on 10/3/2018.
 */

public class ResumenComparativoDTO {
    private String concepto;
    private String periodo1;
    private String periodo2;
    private String diferencia;

    public ResumenComparativoDTO() {
    }

    public ResumenComparativoDTO(String concepto, String periodo1, String periodo2, String diferencia) {
        this.concepto = concepto;
        this.periodo1 = periodo1;
        this.periodo2 = periodo2;
        this.diferencia = diferencia;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
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

    public String getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(String diferencia) {
        this.diferencia = diferencia;
    }
}
