package com.example.codedbmastersfx.modelo;

public class SeguroBasicoModelo extends SeguroModelo {
    //Atributos
    private String tipo;
    private double precio;

    //Constructor
    public SeguroBasicoModelo() {
        super(0, "Básico", 50);
        tipo = "Básico";
        precio = 50;
    }

    public String getTipo() {
        return tipo;
    }

    public double getPrecio() {
        return precio;
    }
    //El precio del seguro no se especifica, por lo que por el momento será 50 euros.
}
