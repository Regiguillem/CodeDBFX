package com.example.codedbmastersfx.modelo;

import jakarta.persistence.*;

@DiscriminatorValue("Federado")
public class SociosFederadosModelo extends SociosModelo {
    @Column(name = "nif", length = 20)
    private String nif;
    @ManyToOne
    @Column (name = "federacion_codigo", length = 20)
    private FederacionesModelo federacion;
    @Column(name = "descuento_cuota")
    private double descuento_cuota;

    @Column(name = "descuento_exc")
    private double descuento_exc;


    //Constructor
    public SociosFederadosModelo(int n_socio, String nombre, String tipo_socio, String nif, FederacionesModelo federacion) {
        super(n_socio, nombre, tipo_socio, nif); // Llama al constructor de la superclase con los parámetros requeridos
        this.federacion = federacion; // Asigna la federación proporcionada
        this.descuento_cuota = 0.05; // Valor predeterminado
        this.descuento_exc = 0.1; // Valor predeterminado
    }


    //Getters y Setters

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public FederacionesModelo getFederacion() {
        return federacion;
    }

    public void setFederacion(FederacionesModelo federacion) {
        this.federacion = federacion;
    }

    public double getDescuento_cuota() {
        return descuento_cuota;
    }

    public void setDescuento_cuota(double descuento_cuota) {
        this.descuento_cuota = descuento_cuota;
    }

    public double getDescuento_exc() {
        return descuento_exc;
    }

    public void setDescuento_exc(double descuento_exc) {
        this.descuento_exc = descuento_exc;
    }
}
