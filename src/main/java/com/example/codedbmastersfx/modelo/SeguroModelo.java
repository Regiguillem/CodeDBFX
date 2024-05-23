package com.example.codedbmastersfx.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "seguros")
public class SeguroModelo {
    @Id
    @Column(name = "id_seguro")
    private int id;
    @Column(name = "tipo_seguro")
    private String tipo;
    @Column(name = "precio")
    private double precio;

    public SeguroModelo() {}

    public SeguroModelo(int idSeguro, String tipoSeguro, double precio) {
        this.id= idSeguro;
        this.tipo= tipoSeguro;
        this.precio= precio;

    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    public int getId() {return id;}
}
