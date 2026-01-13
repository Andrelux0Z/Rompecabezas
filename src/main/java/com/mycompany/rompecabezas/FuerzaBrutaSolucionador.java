package com.mycompany.rompecabezas;

// Solucionador que utiliza un algoritmo de fuerza bruta
// Prueba todas las combinaciones posibles de rotaciones hasta encontrar la solución
public class FuerzaBrutaSolucionador extends Solucionador {

    private long intentos;

    @Override
    public boolean resolverInterno(Tablero tablero) {
        intentos = 0;
        int tamaño = tablero.getTamaño();
        int totalPiezas = tamaño * tamaño;

        System.out.println();
        System.out.println("Resolviendo con fuerza bruta...");
        System.out.println("Total de piezas: " + totalPiezas);
        System.out.println("Combinaciones posibles: 4^" + totalPiezas + " = " + (long) Math.pow(4, totalPiezas));
        System.out.println();

        boolean resuelto = probarCombinaciones(tablero, 0);

        System.out.println("Intentos realizados: " + intentos);

        return resuelto;
    }

    // Prueba todas las combinaciones de rotaciones
    private boolean probarCombinaciones(Tablero tablero, int indice) {
        int tamaño = tablero.getTamaño();
        int totalPiezas = tamaño * tamaño;

        // Caso base: todas las piezas han sido procesadas
        if (indice == totalPiezas) {
            intentos++;
            return tablero.estaResuelto();
        }

        // Calcular fila y columna a partir del índice
        int fila = indice / tamaño;
        int columna = indice % tamaño;
        Pieza pieza = tablero.getPieza(fila, columna);

        // Probar las 4 rotaciones posibles
        for (int rotacion = 0; rotacion < 4; rotacion++) {
            // Verificar si la pieza actual es compatible con sus vecinos ya colocados
            if (esCompatible(tablero, fila, columna)) {
                // Continuar con la siguiente pieza
                if (probarCombinaciones(tablero, indice + 1)) {
                    return true;
                }
            }
            // Rotar para probar la siguiente orientación
            pieza.rotarHorario();
        }

        return false;
    }

    // Verifica si la pieza en (fila, columna) es compatible con sus vecinos ya
    // colocados
    private boolean esCompatible(Tablero tablero, int fila, int columna) {
        Pieza actual = tablero.getPieza(fila, columna);

        // Verificar con pieza de arriba (si existe)
        if (fila > 0) {
            Pieza arriba = tablero.getPieza(fila - 1, columna);
            if (actual.arriba != arriba.abajo) {
                return false;
            }
        }

        // Verificar con pieza de la izquierda (si existe)
        if (columna > 0) {
            Pieza izquierda = tablero.getPieza(fila, columna - 1);
            if (actual.izquierda != izquierda.derecha) {
                return false;
            }
        }

        return true;
    }
}
