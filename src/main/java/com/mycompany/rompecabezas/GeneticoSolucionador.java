package com.mycompany.rompecabezas;

import java.util.List;
import java.util.Collections;

// Solucionador que utiliza un algoritmo genético
public class GeneticoSolucionador extends Solucionador {

    @Override
    protected boolean resolverInterno(Tablero tablero) {
        System.out.println();
        System.out.println("Resolviendo con algoritmo genético...");
        System.out.println();

        List<Tablero> poblacion = new java.util.ArrayList<>();
        int Tamaño = tablero.getTamaño();
        int totalPiezas = Tamaño * Tamaño;
        // Crear población inicial
        for (int i = 0; i < totalPiezas; i++) {
            Tablero individuo = tablero.copiar();
            List<Pieza> piezas = individuo.getPiezasDisponibles();
            Collections.shuffle(piezas);
            // Colocar las piezas de nuevo en el tablero
            int indice = 0;
            for (int fila = 0; fila < Tamaño; fila++) {
                for (int columna = 0; columna < Tamaño; columna++) {
                    individuo.colocarPieza(fila, columna, piezas.get(indice));
                    indice++;
                }
            }
            poblacion.add(individuo);
        }
        // Evaluar la población

        return false;
    }
}
