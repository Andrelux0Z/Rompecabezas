package com.mycompany.rompecabezas;

public class Pieza {
    int arriba;
    int izquierda;
    int derecha;
    int abajo;

    public Pieza(int arriba, int izquierda, int derecha, int abajo) {
        this.arriba = arriba;
        this.izquierda = izquierda;
        this.derecha = derecha;
        this.abajo = abajo;
    }

    // Crear una copia de la pieza
    public Pieza copiar() {
        return new Pieza(arriba, izquierda, derecha, abajo);
    }

    @Override
    public String toString() {
        return String.format("[%d,%d,%d,%d]", arriba, izquierda, derecha, abajo);
    }
}
