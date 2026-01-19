package com.mycompany.rompecabezas;

// Clase base para todos los algoritmos que resuelven el rompecabezas
public abstract class Solucionador {

    // Contadores de métricas
    protected long asignaciones = 0;
    protected long comparaciones = 0;
    protected long lineasEjecutadas = 0;

    // Resuelve el rompecabezas midiendo métricas
    public void resolver(Tablero tablero) {
        // Reiniciar contadores
        asignaciones = 0;
        comparaciones = 0;
        lineasEjecutadas = 0;

        // Medir tiempo con nanosegundos para mayor precisión
        long tiempoInicio = System.nanoTime();

        boolean resuelto = resolverInterno(tablero);

        long tiempoFin = System.nanoTime();

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
        System.out.println("Asignaciones:        " + asignaciones);
        System.out.println("Comparaciones:       " + comparaciones);
        System.out.println("Líneas ejecutadas:   " + lineasEjecutadas);
        System.out.printf("Tiempo de ejecución: %.3f ms%n", tiempoMs);
    }

    // Método que cada algoritmo debe implementar con su lógica específica
    protected abstract boolean resolverInterno(Tablero tablero);
}
