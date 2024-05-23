package com.example.codedbmastersfx.modelo;

import jakarta.persistence.*;

@DiscriminatorValue("Infantil")
public class SocioInfantilModelo extends SociosModelo {

    @JoinColumn(name = "n_padre_madre")
    private SociosModelo n_socioPadreMadre;

    @Column(name = "descuento_cuota")
    private double descuento_cuota;

    //Constructor
    public SocioInfantilModelo(int id, String nombre, String tipo, String nif, SociosModelo n_socioPadreMadre) {
        super(id, nombre, tipo, nif); // Llama al constructor de la superclase con los parámetros requeridos
        this.n_socioPadreMadre = n_socioPadreMadre; // Asigna el socio padre/madre proporcionado
        this.descuento_cuota = 0.5; // Valor predeterminado
    }


    //Getters y Setters

    public SociosModelo getN_socioPadreMadre() {
        return n_socioPadreMadre;
    }

    public void setN_socioPadreMadre(SociosModelo n_socioPadreMadre) {
        this.n_socioPadreMadre = n_socioPadreMadre;
    }

    public double getDescuento_cuota() {
        return descuento_cuota;
    }

    public void setDescuento_cuota(double descuento_cuota) {
        this.descuento_cuota = descuento_cuota;
    }
}
