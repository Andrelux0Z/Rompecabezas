package com.mycompany.rompecabezas;

import java.util.ArrayList;
import java.util.List;

// Solucionador que utiliza avance rápido con heurística golosa y retroceso
public class AvanceRapidoSolucionador extends Solucionador {

    @Override
    protected boolean resolverInterno(Tablero tablero) {
        lineasEjecutadas++;
        System.out.println();
        lineasEjecutadas++;
        System.out.println("Resolviendo con avance rápido (Goloso + Retroceso)...");
        lineasEjecutadas++;
        System.out.println();

        lineasEjecutadas++;
        return resolverRecursivo(tablero);
    }

    // Solucionador recursivo con backtracking
    private boolean resolverRecursivo(Tablero tablero) {
        lineasEjecutadas++;

        // Caso base: tablero completo
        lineasEjecutadas++;
        comparaciones++;
        if (tablero.getPiezasDisponibles().isEmpty()) {
            lineasEjecutadas++;
            return true;
        }

        // Seleccionar celda con menos candidatos (VMR)
        lineasEjecutadas++;
        CeldaConCandidatos mejorCelda = seleccionarCeldaVMR(tablero);
        asignaciones++;

        lineasEjecutadas++;
        comparaciones++;
        if (mejorCelda == null || mejorCelda.candidatos.isEmpty()) {
            lineasEjecutadas++;
            return false;
        }

        // Probar cada candidato
        lineasEjecutadas++;
        for (Pieza candidato : mejorCelda.candidatos) {
            lineasEjecutadas++;

            lineasEjecutadas++;
            tablero.colocarPieza(mejorCelda.fila, mejorCelda.columna, candidato);
            asignaciones++;

            lineasEjecutadas++;
            tablero.getPiezasDisponibles().remove(candidato);
            asignaciones++;

            lineasEjecutadas++;
            comparaciones++;
            if (resolverRecursivo(tablero)) {
                lineasEjecutadas++;
                return true;
            }

            // Retroceso
            lineasEjecutadas++;
            tablero.quitarPieza(mejorCelda.fila, mejorCelda.columna);
            asignaciones++;

            lineasEjecutadas++;
            tablero.getPiezasDisponibles().add(candidato);
            asignaciones++;
        }

        lineasEjecutadas++;
        return false;
    }

    // Selecciona la celda con menor cantidad de candidatos válidos
    private CeldaConCandidatos seleccionarCeldaVMR(Tablero tablero) {
        lineasEjecutadas++;

        CeldaConCandidatos mejorCelda = null;
        asignaciones++;

        int menorCantidad = Integer.MAX_VALUE;
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < tablero.getTamaño(); i++) {
            lineasEjecutadas++;

            for (int j = 0; j < tablero.getTamaño(); j++) {
                lineasEjecutadas++;

                lineasEjecutadas++;
                comparaciones++;
                if (tablero.getPieza(i, j) == null) {
                    lineasEjecutadas++;

                    List<Pieza> candidatos = generarCandidatos(tablero, i, j);
                    asignaciones++;

                    lineasEjecutadas++;
                    comparaciones++;
                    if (candidatos.size() < menorCantidad) {
                        lineasEjecutadas++;
                        menorCantidad = candidatos.size();
                        asignaciones++;

                        lineasEjecutadas++;
                        mejorCelda = new CeldaConCandidatos(i, j, candidatos);
                        asignaciones++;

                        lineasEjecutadas++;
                        comparaciones++;
                        if (menorCantidad == 0) {
                            lineasEjecutadas++;
                            return mejorCelda;
                        }
                    }
                }
            }
        }

        lineasEjecutadas++;
        return mejorCelda;
    }

    // Genera lista de piezas compatibles para una celda
    private List<Pieza> generarCandidatos(Tablero tablero, int fila, int columna) {
        lineasEjecutadas++;

        List<Pieza> candidatos = new ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        for (Pieza pieza : tablero.getPiezasDisponibles()) {
            lineasEjecutadas++;

            lineasEjecutadas++;
            comparaciones++;
            if (esCompatibleConVecinos(tablero, pieza, fila, columna)) {
                lineasEjecutadas++;
                candidatos.add(pieza);
                asignaciones++;
            }
        }

        lineasEjecutadas++;
        return candidatos;
    }

    // Verifica si una pieza es compatible con los vecinos ya colocados
    private boolean esCompatibleConVecinos(Tablero tablero, Pieza pieza, int fila, int columna) {
        lineasEjecutadas++;

        // Verificar con pieza de arriba
        lineasEjecutadas++;
        comparaciones++;
        if (fila > 0) {
            lineasEjecutadas++;
            Pieza piezaArriba = tablero.getPieza(fila - 1, columna);
            asignaciones++;

            lineasEjecutadas++;
            comparaciones++;
            if (piezaArriba != null) {
                lineasEjecutadas++;
                comparaciones++;
                if (pieza.arriba != piezaArriba.abajo) {
                    lineasEjecutadas++;
                    return false;
                }
            }
        }

        // Verificar con pieza de la izquierda
        lineasEjecutadas++;
        comparaciones++;
        if (columna > 0) {
            lineasEjecutadas++;
            Pieza piezaIzquierda = tablero.getPieza(fila, columna - 1);
            asignaciones++;

            lineasEjecutadas++;
            comparaciones++;
            if (piezaIzquierda != null) {
                lineasEjecutadas++;
                comparaciones++;
                if (pieza.izquierda != piezaIzquierda.derecha) {
                    lineasEjecutadas++;
                    return false;
                }
            }
        }

        lineasEjecutadas++;
        return true;
    }

    // Clase auxiliar para guardar celda con sus candidatos
    private static class CeldaConCandidatos {
        int fila;
        int columna;
        List<Pieza> candidatos;

        CeldaConCandidatos(int fila, int columna, List<Pieza> candidatos) {
            this.fila = fila;
            this.columna = columna;
            this.candidatos = candidatos;
        }
    }
}
