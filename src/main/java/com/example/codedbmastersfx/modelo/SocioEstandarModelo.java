package com.example.codedbmastersfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.ManyToOne;

@DiscriminatorValue("Estándar")
public class SocioEstandarModelo extends SociosModelo {
    //Atributos
    @Column(name = "nif", length = 20)
    private String nif;
    @ManyToOne
    @Column(name = "seguro")
    private SeguroModelo seguro;

    //Constructor
    public SocioEstandarModelo(String nombre, String nif, SeguroModelo seguro, int n_socio, String tipo) {
        super(n_socio, nombre, tipo, nif);
        this.nif = nif;
        this.seguro = seguro;
    }

    //Getters y Setters

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public SeguroModelo getSeguro() {
        return seguro;
    }

    public void setSeguro(SeguroModelo seguro) {
        this.seguro = seguro;
    }
}
