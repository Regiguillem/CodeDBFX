package com.example.codedbmastersfx.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "socios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class SociosModelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "n_socio")
    private int n_socio;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "cuota_mensual")
    private double cuota_mensual;

    @Column(name = "tipo_socio")
    private String tipo_socio;

    @Column(name = "nif", length = 20)
    private String nif;

    @ManyToOne
    @JoinColumn(name = "federacion_codigo") // Cambio realizado
    private FederacionesModelo federacion;

    @ManyToOne
    @JoinColumn(name = "seguro")
    private SeguroModelo seguro;

    public SociosModelo() {}

    // Constructor
    public SociosModelo(int n_socio, String nombre, String tipo_socio, String nif) {
        this.n_socio = n_socio;
        this.nombre = nombre;
        this.cuota_mensual = 50; // Valor predeterminado
        this.seguro = seguro;
        this.tipo_socio = tipo_socio;
        this.nif = nif;
    }

    //Getters y Setters

    public int getN_socio() {
        return n_socio;
    }

    public void setN_socio(int n_socio) {
        this.n_socio = n_socio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCuotaMensual() {
        return cuota_mensual;
    }

    public void setTipo_socio(String tipo_socio) {
    }
    public String getTipo_socio() { return tipo_socio;
    }
    public void setNif(String nif) {
    }

    public String getNif() {return nif;
    }
}
