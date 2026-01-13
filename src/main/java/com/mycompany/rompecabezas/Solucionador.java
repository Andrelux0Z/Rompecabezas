package com.mycompany.rompecabezas;

// Clase base para todos los algoritmos que resuelven el rompecabezas
public abstract class Solucionador {

    // Resuelve el rompecabezas midiendo el tiempo de ejecución
    public void resolver(Tablero tablero) {
        long tiempoInicio = System.currentTimeMillis();

        boolean resuelto = resolverInterno(tablero);

        long tiempoFin = System.currentTimeMillis();
        long tiempoTotal = tiempoFin - tiempoInicio;

        if (resuelto) {
            System.out.println("¡Solución encontrada!");
            tablero.mostrar();
        } else {
            System.out.println("No se encontró solución.");
        }

        System.out.println("Tiempo: " + tiempoTotal + " ms");
    }

    // Método que cada algoritmo debe implementar con su lógica específica
    protected abstract boolean resolverInterno(Tablero tablero);
}
