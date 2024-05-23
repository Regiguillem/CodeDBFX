package com.example.codedbmastersfx.modelo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "excursiones")
public class ExcursionesModelo {

    @Id
    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "descripcion", length = 100)
    private String descripcion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "n_dias")
    private int n_dias;

    @Column(name = "precio")
    private double precio;

    public ExcursionesModelo() {}

    public ExcursionesModelo(String codigo, String descripcion, LocalDate fecha, int n_dias, double precio) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.n_dias = n_dias;
        this.precio = precio;
    }

    //getters y setters
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getN_dias() {
        return n_dias;
    }

    public void setN_dias(int n_dias) {
        this.n_dias = n_dias;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "ExcursionesModelo{" +
                "codigo='" + codigo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha=" + fecha +
                ", n_dias=" + n_dias +
                ", precio=" + precio +
                '}';
    }
}
