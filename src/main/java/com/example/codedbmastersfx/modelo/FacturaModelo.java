package com.example.codedbmastersfx.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;


//Atributos


@Entity
@Table(name = "facturas")
public class FacturaModelo {

    private static int facturaID = 1;
    @Id
    @Column(name = "id_factura")
    private int id;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "importe")
    private double importe;

    @ManyToOne
    @JoinColumn(name = "id_socio")
    private SociosModelo socio;

    public FacturaModelo () {}

    public FacturaModelo(LocalDate fecha, double importe, SociosModelo socio) {
        this.id = facturaID++;
        this.fecha = fecha;
        this.importe = importe;
        this.socio = socio;
    }
    //Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public SociosModelo getSocio() {
        return socio;
    }

    public void setSocio(SociosModelo socio) {
        this.socio = socio;
    }
}