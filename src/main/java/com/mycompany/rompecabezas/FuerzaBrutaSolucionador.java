package com.mycompany.rompecabezas;

import java.util.List;

// Solucionador que utiliza fuerza bruta probando todas las permutaciones de piezas
public class FuerzaBrutaSolucionador extends Solucionador {

    private long intentos;

    @Override
    public boolean resolverInterno(Tablero tablero) {
        lineasEjecutadas++;
        intentos = 0;
        asignaciones++;

        lineasEjecutadas++;
        int tamaño = tablero.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        int totalPiezas = tamaño * tamaño;
        asignaciones++;

        lineasEjecutadas++;
        System.out.println();
        lineasEjecutadas++;
        System.out.println("Resolviendo con fuerza bruta...");
        lineasEjecutadas++;
        System.out.println("Total de piezas: " + totalPiezas);
        lineasEjecutadas++;
        System.out.println("Permutaciones posibles: " + totalPiezas + "! = " + factorial(totalPiezas));
        lineasEjecutadas++;
        System.out.println();

        lineasEjecutadas++;
        boolean resuelto = probarPermutaciones(tablero, 0);
        asignaciones++;

        lineasEjecutadas++;
        System.out.println("Intentos realizados: " + intentos);

        lineasEjecutadas++;
        return resuelto;
    }

    // Calcula el factorial (para mostrar información)
    private String factorial(int n) {
        lineasEjecutadas++;
        comparaciones++;
        if (n > 20) {
            lineasEjecutadas++;
            return "número muy grande";
        }

        lineasEjecutadas++;
        long resultado = 1;
        asignaciones++;

        lineasEjecutadas++;
        asignaciones++;
        for (int i = 2; i <= n; i++) {
            comparaciones++;
            lineasEjecutadas++;
            resultado *= i;
            asignaciones++;
            lineasEjecutadas++;
            asignaciones++;
        }
        comparaciones++;

        lineasEjecutadas++;
        return String.valueOf(resultado);
    }

    // Prueba todas las permutaciones de piezas
    private boolean probarPermutaciones(Tablero tablero, int indice) {
        lineasEjecutadas++;
        int tamaño = tablero.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        int totalPiezas = tamaño * tamaño;
        asignaciones++;

        lineasEjecutadas++;
        List<Pieza> piezasDisponibles = tablero.getPiezasDisponibles();
        asignaciones++;

        // Caso base: todas las posiciones han sido llenadas
        lineasEjecutadas++;
        comparaciones++;
        if (indice == totalPiezas) {
            lineasEjecutadas++;
            intentos++;
            asignaciones++;
            lineasEjecutadas++;
            return tablero.estaResuelto();
        }

        // Calcular posición en el tablero
        lineasEjecutadas++;
        int fila = indice / tamaño;
        asignaciones++;

        lineasEjecutadas++;
        int columna = indice % tamaño;
        asignaciones++;

        // Probar cada pieza disponible en esta posición
        lineasEjecutadas++;
        asignaciones++;
        for (int i = 0; i < piezasDisponibles.size(); i++) {
            comparaciones++;

            lineasEjecutadas++;
            Pieza pieza = piezasDisponibles.remove(i);
            asignaciones++;

            lineasEjecutadas++;
            tablero.colocarPieza(fila, columna, pieza);

            // Verificar compatibilidad antes de continuar (poda)
            lineasEjecutadas++;
            if (esCompatible(tablero, fila, columna)) {
                lineasEjecutadas++;
                comparaciones++;
                if (probarPermutaciones(tablero, indice + 1)) {
                    lineasEjecutadas++;
                    return true;
                }
            }

            // Quitar pieza y devolverla
            lineasEjecutadas++;
            tablero.quitarPieza(fila, columna);

            lineasEjecutadas++;
            piezasDisponibles.add(i, pieza);

            lineasEjecutadas++;
            asignaciones++;
        }
        comparaciones++;

        lineasEjecutadas++;
        return false;
    }

    // Verifica si la pieza es compatible con sus vecinos ya colocados
    private boolean esCompatible(Tablero tablero, int fila, int columna) {
        lineasEjecutadas++;
        Pieza actual = tablero.getPieza(fila, columna);
        asignaciones++;

        // Verificar con pieza de arriba
        lineasEjecutadas++;
        comparaciones++;
        if (fila > 0) {
            lineasEjecutadas++;
            Pieza arriba = tablero.getPieza(fila - 1, columna);
            asignaciones++;

            lineasEjecutadas++;
            comparaciones++;
            if (actual.arriba != arriba.abajo) {
                lineasEjecutadas++;
                return false;
            }
        }

        // Verificar con pieza de la izquierda
        lineasEjecutadas++;
        comparaciones++;
        if (columna > 0) {
            lineasEjecutadas++;
            Pieza izquierda = tablero.getPieza(fila, columna - 1);
            asignaciones++;

            lineasEjecutadas++;
            comparaciones++;
            if (actual.izquierda != izquierda.derecha) {
                lineasEjecutadas++;
                return false;
            }
        }

        lineasEjecutadas++;
        return true;
    }
}
