/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rompecabezas;

/**
 *
 * @author andre
 */
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

    // Rotar la pieza 90 grados en sentido horario
    public void rotarHorario() {
        int temp = arriba;
        arriba = izquierda;
        izquierda = abajo;
        abajo = derecha;
        derecha = temp;
    }

    // Rotar la pieza 90 grados en sentido antihorario
    public void rotarAntihorario() {
        int temp = arriba;
        arriba = derecha;
        derecha = abajo;
        abajo = izquierda;
        izquierda = temp;
    }
}
