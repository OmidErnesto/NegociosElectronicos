package com.example.negocioselectronicos;

public class SpinnerItem {
    private int codigo;
    private String nombre;

    public SpinnerItem(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    // Este método determina lo que se muestra en el Spinner
    @Override
    public String toString() {
        return nombre;
    }
}
