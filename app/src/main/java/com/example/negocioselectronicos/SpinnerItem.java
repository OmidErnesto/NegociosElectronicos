package com.example.negocioselectronicos;

public class SpinnerItem {
    private int codigo;
    private String nombre;
    private String queryName;

    public SpinnerItem(int codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public SpinnerItem(int codigo, String nombre, String queryName) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.queryName = queryName;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getQueryName() {
        return queryName;
    }

    // Este método determina lo que se muestra en el Spinner
    @Override
    public String toString() {
        return nombre;
    }
}
