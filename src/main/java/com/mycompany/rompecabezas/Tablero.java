/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rompecabezas;

import java.util.Random;

/**
 *
 * @author andre
 */
public class Tablero {
    Pieza[][] piezas;
    int tamaño;

    public Tablero(int tamaño, int maxValor) {
        this.tamaño = tamaño;
        this.piezas = new Pieza[tamaño][tamaño];
        generarPiezas(maxValor);
    }

    private void generarPiezas(int maxValor) {
        Random random = new Random();

        // Generar tablero resuelto
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                int arriba, izquierda, derecha, abajo;

                // Si hay pieza arriba, tomar valor de abajo
                if (i > 0) {
                    arriba = piezas[i - 1][j].abajo;
                } else {
                    arriba = random.nextInt(maxValor + 1);
                }

                // Si hay pieza a la izquierda, tomar valor de derecha
                if (j > 0) {
                    izquierda = piezas[i][j - 1].derecha;
                } else {
                    izquierda = random.nextInt(maxValor + 1);
                }

                // Derecha y abajo son aleatorios
                derecha = random.nextInt(maxValor + 1);
                abajo = random.nextInt(maxValor + 1);

                piezas[i][j] = new Pieza(arriba, izquierda, derecha, abajo);
            }
        }

        // Rotar aleatoriamente cada pieza
        rotarPiezasAleatoriamente(random);
    }

    private void rotarPiezasAleatoriamente(Random random) {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                // Rotar cada pieza un número aleatorio de veces (0-3)
                int rotaciones = random.nextInt(4);
                for (int r = 0; r < rotaciones; r++) {
                    piezas[i][j].rotarHorario();
                }
            }
        }
    }

    public void mostrar() {
        System.out.println();
        System.out.println("=== TABLERO DEL ROMPECABEZAS ===");
        System.out.println();

        for (int i = 0; i < tamaño; i++) {
            // Línea superior de cada fila de piezas
            for (int j = 0; j < tamaño; j++) {
                Pieza p = piezas[i][j];
                System.out.print(String.format("\\%s%d /", espacio(p.arriba), p.arriba));
                if (j < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea central de cada fila de piezas
            for (int j = 0; j < tamaño; j++) {
                Pieza p = piezas[i][j];
                System.out.print(
                        String.format("%d%s|%s%d", p.izquierda, espacio(p.izquierda), espacio(p.derecha), p.derecha));
                if (j < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea inferior de cada fila de piezas
            for (int j = 0; j < tamaño; j++) {
                Pieza p = piezas[i][j];
                System.out.print(String.format("/%s%d \\", espacio(p.abajo), p.abajo));
                if (j < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea separadora entre filas
            if (i < tamaño - 1) {
                System.out.println();
            }
        }
        System.out.println();
    }

    private String espacio(int valor) {
        if (valor < 10) {
            return " ";
        } else {
            return "";
        }
    }
}
