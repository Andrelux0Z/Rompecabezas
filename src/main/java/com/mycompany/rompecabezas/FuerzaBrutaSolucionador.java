package com.mycompany.rompecabezas;

import java.util.List;

// Solucionador que utiliza fuerza bruta probando todas las permutaciones de piezas
public class FuerzaBrutaSolucionador extends Solucionador {

    private long intentos;
    private long podas;

    @Override
    public boolean resolverInterno(Tablero tablero) {
        lineasEjecutadas++;
        intentos = 0;
        asignaciones++;

        lineasEjecutadas++;
        podas = 0;
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
        System.out.println("Resolviendo con fuerza bruta (backtracking)...");
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
        System.out.println();
        lineasEjecutadas++;
        System.out.println("=== ESTADÍSTICAS DE BACKTRACKING ===");
        lineasEjecutadas++;
        System.out.println("Alternativas evaluadas: " + intentos);
        lineasEjecutadas++;
        System.out.println("Podas realizadas:       " + podas);

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
        for (int i = 2; i <= n; i++) {
            comparaciones++;
            lineasEjecutadas++;
            resultado *= i;
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
        for (int i = 0; i < piezasDisponibles.size(); i++) {
            comparaciones++;

            lineasEjecutadas++;
            Pieza pieza = piezasDisponibles.remove(i);
            asignaciones++;

            lineasEjecutadas++;
            tablero.colocarPieza(fila, columna, pieza);

            // Verificar compatibilidad antes de continuar (poda)
            lineasEjecutadas++;
            comparaciones++;
            if (esCompatible(tablero, fila, columna)) {
                lineasEjecutadas++;
                intentos++;
                asignaciones++;

                if (probarPermutaciones(tablero, indice + 1)) {
                    return true;
                }
            } else {
                // Poda: la pieza no es compatible, no seguimos por esta rama
                lineasEjecutadas++;
                podas++;
                asignaciones++;
            }

            // Quitar pieza y devolverla
            lineasEjecutadas++;
            tablero.quitarPieza(fila, columna);

            lineasEjecutadas++;
            piezasDisponibles.add(i, pieza);
        }
        comparaciones++;

        lineasEjecutadas++;
        return false;
    }

}
