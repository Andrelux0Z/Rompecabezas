package com.mycompany.rompecabezas;

// Clase base para todos los algoritmos que resuelven el rompecabezas
public abstract class Solucionador {

    // Contadores de métricas
    protected long asignaciones = 0;
    protected long comparaciones = 0;
    protected long lineasEjecutadas = 0;
    protected long memoriaUsada = 0;

    // Resuelve el rompecabezas midiendo métricas
    public void resolver(Tablero tablero) {
        // Reiniciar contadores
        asignaciones = 0;
        comparaciones = 0;
        lineasEjecutadas = 0;
        memoriaUsada = 0;

        // Variable para almacenar el resultado del algoritmo
        final boolean[] resultado = new boolean[1];

        // Medir tiempo con nanosegundos para mayor precisión
        long tiempoInicio = System.nanoTime();

        // Medir memoria consumida durante la ejecución del algoritmo
        memoriaUsada = MemoryTracker.measure(() -> {
            resultado[0] = resolverInterno(tablero);
        });

        long tiempoFin = System.nanoTime();

        // Obtener el resultado de la ejecución
        boolean resuelto = resultado[0];

        // Calcular tiempo
        double tiempoMs = (tiempoFin - tiempoInicio) / 1_000_000.0;

        // Mostrar resultado
        if (resuelto) {
            System.out.println("¡Solución encontrada!");
            tablero.mostrar();
        } else {
            System.out.println("No se encontró solución.");
        }

        // Mostrar métricas
        System.out.println();
        System.out.println("=== MÉTRICAS DE RENDIMIENTO ===");
        System.out.println("Asignaciones:                    " + asignaciones);
        System.out.println("Comparaciones:                   " + comparaciones);
        System.out.println("Líneas ejecutadas:               " + lineasEjecutadas);
        System.out.printf("Tiempo de ejecución:             %.3f ms%n", tiempoMs);
        System.out.println("Memoria adicional consumida:     " + memoriaUsada + " bytes (" + MemoryTracker.formatBytes(memoriaUsada) + ")");
    }

    // Método que cada algoritmo debe implementar con su lógica específica
    protected abstract boolean resolverInterno(Tablero tablero);

    // Verifica si la pieza en (fila, columna) es compatible con sus vecinos ya
    // colocados
    protected boolean esCompatible(Tablero tablero, int fila, int columna) {
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
