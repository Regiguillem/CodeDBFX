package com.example.codedbmastersfx.modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "federaciones")
public class FederacionesModelo {
    @Id
    @Column(name = "codigo")
    private String codigo;

    @Column(name = "nombre")
    private String nombre;

    public FederacionesModelo() {}

//Constructor
    public FederacionesModelo(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

//Getters y Setters

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}