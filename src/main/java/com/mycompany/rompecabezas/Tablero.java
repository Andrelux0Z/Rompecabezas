package com.mycompany.rompecabezas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Tablero {
    Pieza[][] piezas;
    List<Pieza> piezasDisponibles; // Lista de piezas sin colocar
    int tamaño;

    public Tablero(int tamaño, int maxValor) {
        this.tamaño = tamaño;
        this.piezas = new Pieza[tamaño][tamaño];
        this.piezasDisponibles = new ArrayList<>();
        generarPiezas(maxValor);
    }

    private void generarPiezas(int maxValor) {
        Random random = new Random();
        List<Pieza> todasLasPiezas = new ArrayList<>();

        // Generar tablero resuelto temporalmente para crear piezas compatibles
        Pieza[][] tableroTemporal = new Pieza[tamaño][tamaño];

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                int arriba, izquierda, derecha, abajo;

                // Si hay pieza arriba, tomar valor de abajo
                if (i > 0) {
                    arriba = tableroTemporal[i - 1][j].abajo;
                } else {
                    arriba = random.nextInt(maxValor + 1);
                }

                // Si hay pieza a la izquierda, tomar valor de derecha
                if (j > 0) {
                    izquierda = tableroTemporal[i][j - 1].derecha;
                } else {
                    izquierda = random.nextInt(maxValor + 1);
                }

                // Derecha y abajo son aleatorios
                derecha = random.nextInt(maxValor + 1);
                abajo = random.nextInt(maxValor + 1);

                tableroTemporal[i][j] = new Pieza(arriba, izquierda, derecha, abajo);
                todasLasPiezas.add(tableroTemporal[i][j]);
            }
        }

        // Mezclar las piezas aleatoriamente
        Collections.shuffle(todasLasPiezas, random);

        // Guardar las piezas mezcladas en la lista de disponibles
        piezasDisponibles.addAll(todasLasPiezas);

        // Limpiar el tablero (las piezas están en la lista, no colocadas)
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                piezas[i][j] = null;
            }
        }
    }

    // Muestra el tablero resuelto
    public void mostrar() {
        mostrarGrilla("=== TABLERO RESUELTO ===", piezas);
    }

    // Muestra las piezas disponibles
    public void mostrarPiezasDisponibles() {
        // Convertir lista a matriz para reutilizar el método de visualización
        Pieza[][] grilla = new Pieza[tamaño][tamaño];
        int indice = 0;
        for (int i = 0; i < tamaño && indice < piezasDisponibles.size(); i++) {
            for (int j = 0; j < tamaño && indice < piezasDisponibles.size(); j++) {
                grilla[i][j] = piezasDisponibles.get(indice++);
            }
        }
        mostrarGrilla("=== PUZZLE MEZCLADO (" + piezasDisponibles.size() + " piezas) ===", grilla);
    }

    // Método compartido para mostrar una grilla de piezas
    private void mostrarGrilla(String titulo, Pieza[][] grilla) {
        System.out.println();
        System.out.println(titulo);
        System.out.println();

        for (int i = 0; i < tamaño; i++) {
            // Línea superior de cada fila de piezas
            for (int j = 0; j < tamaño; j++) {
                Pieza p = grilla[i][j];
                if (p != null) {
                    System.out.print(String.format("\\%s%d /", espacio(p.arriba), p.arriba));
                } else {
                    System.out.print("\\   /");
                }
                if (j < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea central de cada fila de piezas
            for (int j = 0; j < tamaño; j++) {
                Pieza p = grilla[i][j];
                if (p != null) {
                    System.out.print(
                            String.format("%d%s|%s%d", p.izquierda, espacio(p.izquierda), espacio(p.derecha),
                                    p.derecha));
                } else {
                    System.out.print("  |  ");
                }
                if (j < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea inferior de cada fila de piezas
            for (int j = 0; j < tamaño; j++) {
                Pieza p = grilla[i][j];
                if (p != null) {
                    System.out.print(String.format("/%s%d \\", espacio(p.abajo), p.abajo));
                } else {
                    System.out.print("/   \\");
                }
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

    // Verifica si el tablero está resuelto (todos los bordes adyacentes coinciden).
    public boolean estaResuelto() {
        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                Pieza actual = piezas[i][j];

                // Verificar con pieza de abajo
                if (i < tamaño - 1) {
                    Pieza abajo = piezas[i + 1][j];
                    if (actual.abajo != abajo.arriba) {
                        return false;
                    }
                }

                // Verificar con pieza de la derecha
                if (j < tamaño - 1) {
                    Pieza derecha = piezas[i][j + 1];
                    if (actual.derecha != derecha.izquierda) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // Coloca una pieza en una posición del tablero
    public void colocarPieza(int fila, int columna, Pieza pieza) {
        piezas[fila][columna] = pieza;
    }

    // Quita una pieza de una posición del tablero
    public Pieza quitarPieza(int fila, int columna) {
        Pieza pieza = piezas[fila][columna];
        piezas[fila][columna] = null;
        return pieza;
    }

    // Obtiene la lista de piezas disponibles
    public List<Pieza> getPiezasDisponibles() {
        return piezasDisponibles;
    }

    // Obtiene la pieza en la posición (fila, columna).
    public Pieza getPieza(int fila, int columna) {
        return piezas[fila][columna];
    }

    // Obtiene el tamaño del tablero.
    public int getTamaño() {
        return tamaño;
    }

    // Crea una copia del tablero
    public Tablero copiar() {
        Tablero copia = new Tablero();
        copia.tamaño = this.tamaño;
        copia.piezas = new Pieza[tamaño][tamaño];
        copia.piezasDisponibles = new ArrayList<>(this.piezasDisponibles.size());

        for (int i = 0; i < tamaño; i++) {
            for (int j = 0; j < tamaño; j++) {
                if (this.piezas[i][j] != null) {
                    copia.piezas[i][j] = this.piezas[i][j].copiar();
                }
            }
        }

        for (Pieza p : this.piezasDisponibles) {
            copia.piezasDisponibles.add(p.copiar());
        }

        return copia;
    }

    // Constructor privado para copiar
    private Tablero() {
        this.piezasDisponibles = new ArrayList<>();
    }
}
