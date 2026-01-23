package com.mycompany.rompecabezas;

import java.util.ArrayList;
import java.util.List;

/**
 * Solucionador que utiliza un algoritmo de avance rápido con heurística golosa y backtracking.
 * 
 * ESTRATEGIA:
 * 1. Selecciona la celda vacía con menor número de candidatos válidos (MRV - Minimum Remaining Values)
 * 2. Genera candidatos compatibles para esa celda
 * 3. Prueba cada candidato recursivamente
 * 4. Si ningún candidato funciona, hace backtracking
 * 
 * VENTAJAS:
 * - La heurística MRV reduce dramáticamente el espacio de búsqueda
 * - Al elegir celdas con pocas opciones primero, detectamos fallos más temprano
 * - Más eficiente que fuerza bruta porque usa información del estado actual
 */
public class AvanceRapidoSolucionador extends Solucionador {

    @Override
    protected boolean resolverInterno(Tablero tablero) {
        System.out.println();
        System.out.println("Resolviendo con avance rápido (Greedy + Backtracking)...");
        System.out.println();

        // Llamar al solucionador recursivo
        lineasEjecutadas++;
        return resolverRecursivo(tablero);
    }

    /**
     * FASE 4: Solucionador recursivo con backtracking
     * 
     * Este método implementa el algoritmo principal de backtracking:
     * 1. Si el tablero está completo, hemos encontrado la solución
     * 2. Usa MRV para seleccionar la mejor celda a llenar
     * 3. Genera candidatos compatibles para esa celda
     * 4. Prueba cada candidato recursivamente
     * 5. Si un candidato falla, lo quita (backtracking) y prueba el siguiente
     * 
     * @param tablero El tablero actual en proceso de resolución
     * @return true si se encuentra una solución, false si no hay solución posible
     */
    private boolean resolverRecursivo(Tablero tablero) {
        lineasEjecutadas++;
        
        // Caso base: si no quedan piezas disponibles, el tablero está completo
        lineasEjecutadas++;
        comparaciones++;
        if (tablero.getPiezasDisponibles().isEmpty()) {
            lineasEjecutadas++;
            return true; // ¡Solución encontrada!
        }

        // FASE 3: Seleccionar la celda con menor número de candidatos válidos (heurística MRV)
        lineasEjecutadas++;
        CeldaConCandidatos mejorCelda = seleccionarCeldaMRV(tablero);
        asignaciones++;

        // Si alguna celda no tiene candidatos válidos, este camino no tiene solución
        lineasEjecutadas++;
        comparaciones++;
        if (mejorCelda == null || mejorCelda.candidatos.isEmpty()) {
            lineasEjecutadas++;
            return false; // Backtracking: este camino no funciona
        }

        // Intentar cada candidato para la celda seleccionada
        lineasEjecutadas++;
        for (Pieza candidato : mejorCelda.candidatos) {
            lineasEjecutadas++;
            
            // Colocar la pieza candidata en el tablero
            lineasEjecutadas++;
            tablero.colocarPieza(mejorCelda.fila, mejorCelda.columna, candidato);
            asignaciones++;
            
            // Remover la pieza de las disponibles
            lineasEjecutadas++;
            tablero.getPiezasDisponibles().remove(candidato);
            asignaciones++;

            // Llamada recursiva: intentar resolver el resto del tablero
            lineasEjecutadas++;
            comparaciones++;
            if (resolverRecursivo(tablero)) {
                lineasEjecutadas++;
                return true; // ¡Éxito! Propagar la solución hacia arriba
            }

            // BACKTRACKING: este candidato no funcionó, deshacer cambios
            lineasEjecutadas++;
            tablero.quitarPieza(mejorCelda.fila, mejorCelda.columna);
            asignaciones++;
            
            lineasEjecutadas++;
            tablero.getPiezasDisponibles().add(candidato);
            asignaciones++;
        }

        // Ningún candidato funcionó, hacer backtracking al nivel anterior
        lineasEjecutadas++;
        return false;
    }

    /**
     * FASE 3: Selección de celda con heurística MRV (Minimum Remaining Values)
     * 
     * Esta heurística golosa (greedy) selecciona la celda vacía que tiene el menor
     * número de piezas candidatas válidas. Esto es efectivo porque:
     * - Reduce el factor de ramificación del árbol de búsqueda
     * - Detecta callejones sin salida más temprano (si una celda tiene 0 candidatos)
     * - Concentra el esfuerzo en las decisiones más restringidas primero
     * 
     * @param tablero El tablero actual
     * @return La celda con menos candidatos válidos, o null si no hay celdas vacías
     */
    private CeldaConCandidatos seleccionarCeldaMRV(Tablero tablero) {
        lineasEjecutadas++;
        
        CeldaConCandidatos mejorCelda = null;
        asignaciones++;
        
        int menorCantidadCandidatos = Integer.MAX_VALUE;
        asignaciones++;

        // Recorrer todas las celdas del tablero
        lineasEjecutadas++;
        for (int i = 0; i < tablero.getTamaño(); i++) {
            lineasEjecutadas++;
            
            for (int j = 0; j < tablero.getTamaño(); j++) {
                lineasEjecutadas++;
                
                // Solo considerar celdas vacías
                lineasEjecutadas++;
                comparaciones++;
                if (tablero.getPieza(i, j) == null) {
                    lineasEjecutadas++;
                    
                    // FASE 2: Generar candidatos válidos para esta celda
                    List<Pieza> candidatos = generarCandidatos(tablero, i, j);
                    asignaciones++;
                    
                    // Actualizar si esta celda tiene menos candidatos (heurística MRV)
                    lineasEjecutadas++;
                    comparaciones++;
                    if (candidatos.size() < menorCantidadCandidatos) {
                        lineasEjecutadas++;
                        menorCantidadCandidatos = candidatos.size();
                        asignaciones++;
                        
                        lineasEjecutadas++;
                        mejorCelda = new CeldaConCandidatos(i, j, candidatos);
                        asignaciones++;
                        
                        // Optimización: si encontramos una celda sin candidatos, 
                        // retornarla inmediatamente (fallo detectado temprano)
                        lineasEjecutadas++;
                        comparaciones++;
                        if (menorCantidadCandidatos == 0) {
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

    /**
     * FASE 2: Generación de candidatos compatibles
     * 
     * Genera una lista de todas las piezas disponibles que son compatibles
     * con las restricciones de la celda (fila, columna).
     * 
     * Una pieza es compatible si sus bordes coinciden con los bordes de las
     * piezas vecinas ya colocadas (arriba e izquierda).
     * 
     * @param tablero El tablero actual
     * @param fila La fila de la celda a evaluar
     * @param columna La columna de la celda a evaluar
     * @return Lista de piezas compatibles para esta celda
     */
    private List<Pieza> generarCandidatos(Tablero tablero, int fila, int columna) {
        lineasEjecutadas++;
        
        List<Pieza> candidatos = new ArrayList<>();
        asignaciones++;

        // Probar cada pieza disponible
        lineasEjecutadas++;
        for (Pieza pieza : tablero.getPiezasDisponibles()) {
            lineasEjecutadas++;
            
            // FASE 1: Verificar compatibilidad con vecinos ya colocados
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

    /**
     * FASE 1: Verificación de compatibilidad
     * 
     * Verifica si una pieza específica es compatible con los vecinos ya colocados
     * en la posición (fila, columna).
     * 
     * Restricciones verificadas:
     * - Si hay pieza arriba: el borde superior de la pieza candidata debe coincidir
     *   con el borde inferior de la pieza de arriba
     * - Si hay pieza a la izquierda: el borde izquierdo de la pieza candidata debe
     *   coincidir con el borde derecho de la pieza de la izquierda
     * 
     * @param tablero El tablero actual
     * @param pieza La pieza candidata a verificar
     * @param fila La fila donde se quiere colocar la pieza
     * @param columna La columna donde se quiere colocar la pieza
     * @return true si la pieza es compatible, false en caso contrario
     */
    private boolean esCompatibleConVecinos(Tablero tablero, Pieza pieza, int fila, int columna) {
        lineasEjecutadas++;
        
        // Verificar compatibilidad con la pieza de arriba
        lineasEjecutadas++;
        comparaciones++;
        if (fila > 0) {
            lineasEjecutadas++;
            Pieza piezaArriba = tablero.getPieza(fila - 1, columna);
            asignaciones++;
            
            // Si hay una pieza arriba, verificar que los bordes coincidan
            lineasEjecutadas++;
            comparaciones++;
            if (piezaArriba != null) {
                lineasEjecutadas++;
                comparaciones++;
                if (pieza.arriba != piezaArriba.abajo) {
                    lineasEjecutadas++;
                    return false; // No compatible
                }
            }
        }

        // Verificar compatibilidad con la pieza de la izquierda
        lineasEjecutadas++;
        comparaciones++;
        if (columna > 0) {
            lineasEjecutadas++;
            Pieza piezaIzquierda = tablero.getPieza(fila, columna - 1);
            asignaciones++;
            
            // Si hay una pieza a la izquierda, verificar que los bordes coincidan
            lineasEjecutadas++;
            comparaciones++;
            if (piezaIzquierda != null) {
                lineasEjecutadas++;
                comparaciones++;
                if (pieza.izquierda != piezaIzquierda.derecha) {
                    lineasEjecutadas++;
                    return false; // No compatible
                }
            }
        }

        // La pieza es compatible con todos los vecinos existentes
        lineasEjecutadas++;
        return true;
    }

    /**
     * Clase auxiliar para almacenar información sobre una celda y sus candidatos.
     * Se usa en la heurística MRV para rastrear qué celda tiene menos opciones.
     */
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
