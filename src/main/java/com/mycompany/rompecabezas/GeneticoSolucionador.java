package com.mycompany.rompecabezas;

import java.util.List;
import java.util.Random;
import java.util.Collections;

// Solucionador que utiliza un algoritmo genético (solo 10 generaciones)
public class GeneticoSolucionador extends Solucionador {

    // Modos de ejecución
    public static final int MODO_NORMAL = 1;
    public static final int MODO_ILUSTRATIVO = 2;

    private boolean ilustrativo = false;

    public void setModo(int modo) {
        this.ilustrativo = (modo == MODO_ILUSTRATIVO);
    }

    @Override
    protected boolean resolverInterno(Tablero tablero) {
        lineasEjecutadas++;
        System.out.println();
        lineasEjecutadas++;
        System.out.println("Resolviendo con algoritmo genético...");
        lineasEjecutadas++;
        System.out.println();

        lineasEjecutadas++;
        int tamaño = tablero.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        int cantidadHijos = obtenerCantidadHijos(tamaño);
        asignaciones++;

        // Crear población inicial
        lineasEjecutadas++;
        List<Tablero> poblacion = crearPoblacionInicial(tablero);
        asignaciones++;

        lineasEjecutadas++;
        boolean solucionEncontrada = false;
        asignaciones++;

        // Bucle de 10 generaciones
        lineasEjecutadas++;
        for (int generacion = 1; generacion <= 10; generacion++) {
            comparaciones++;
            lineasEjecutadas++;

            if (ilustrativo) {
                System.out.println();
                System.out.println("========== GENERACIÓN " + generacion + " ==========");
            }

            // Generar hijos mediante cruce
            lineasEjecutadas++;
            List<Tablero> hijos;

            lineasEjecutadas++;
            comparaciones++;
            if (tamaño == 3) {
                lineasEjecutadas++;
                if (ilustrativo) {
                    hijos = cruce3x3Ilustrativo(poblacion);
                } else {
                    hijos = cruce3x3(poblacion);
                }
                asignaciones++;
            } else {
                lineasEjecutadas++;
                if (ilustrativo) {
                    hijos = cruceGeneralIlustrativo(poblacion, cantidadHijos);
                } else {
                    hijos = cruceGeneral(poblacion, cantidadHijos);
                }
                asignaciones++;
            }

            // Combinar población actual + hijos
            lineasEjecutadas++;
            List<Tablero> combinados = new java.util.ArrayList<>();
            asignaciones++;

            lineasEjecutadas++;
            combinados.addAll(poblacion);

            lineasEjecutadas++;
            combinados.addAll(hijos);

            // Seleccionar los mejores
            lineasEjecutadas++;
            poblacion = seleccionarMejores(combinados, cantidadHijos);
            asignaciones++;

            lineasEjecutadas++;
            int mejorFitness = calcularFitness(poblacion.get(0));
            asignaciones++;

            lineasEjecutadas++;
            System.out.println("Generación " + generacion + " - Mejor fitness: " + mejorFitness);

            // Verificar si encontramos solución
            lineasEjecutadas++;
            comparaciones++;
            if (mejorFitness == 0) {
                lineasEjecutadas++;
                solucionEncontrada = true;
                asignaciones++;
                break;
            }
        }
        comparaciones++;

        // Mostrar los 3 mejores individuos al final
        lineasEjecutadas++;
        System.out.println();
        lineasEjecutadas++;
        System.out.println("=== TOP 3 MEJORES INDIVIDUOS ===");

        lineasEjecutadas++;
        int mostrar = Math.min(3, poblacion.size());
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < mostrar; i++) {
            comparaciones++;

            lineasEjecutadas++;
            Tablero mejor = poblacion.get(i);
            asignaciones++;

            lineasEjecutadas++;
            int fitness = calcularFitness(mejor);
            asignaciones++;

            lineasEjecutadas++;
            System.out.println();
            lineasEjecutadas++;
            System.out.println("Individuo #" + (i + 1) + " - Fitness: " + fitness);
            lineasEjecutadas++;
            mejor.mostrar();
        }
        comparaciones++;

        lineasEjecutadas++;
        System.out.println();

        lineasEjecutadas++;
        comparaciones++;
        return solucionEncontrada;
    }

    // ==================== SELECCIÓN ====================

    private List<Tablero> seleccionarMejores(List<Tablero> individuos, int cantidad) {
        lineasEjecutadas++;
        individuos.sort((a, b) -> {
            comparaciones++;
            return calcularFitness(a) - calcularFitness(b);
        });

        lineasEjecutadas++;
        List<Tablero> mejores = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < cantidad && i < individuos.size(); i++) {
            comparaciones += 2;
            lineasEjecutadas++;
            mejores.add(individuos.get(i));
        }
        comparaciones += 2;

        lineasEjecutadas++;
        return mejores;
    }

    private Tablero seleccionarPorTorneo(List<Tablero> poblacion, Random random) {
        lineasEjecutadas++;
        int cantidadTorneo = (int) Math.ceil(poblacion.size() * 0.3);
        asignaciones++;

        lineasEjecutadas++;
        List<Tablero> participantes = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        List<Integer> indices = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < poblacion.size(); i++) {
            comparaciones++;
            lineasEjecutadas++;
            indices.add(i);
        }
        comparaciones++;

        lineasEjecutadas++;
        Collections.shuffle(indices, random);

        lineasEjecutadas++;
        for (int i = 0; i < cantidadTorneo; i++) {
            comparaciones++;
            lineasEjecutadas++;
            participantes.add(poblacion.get(indices.get(i)));
        }
        comparaciones++;

        lineasEjecutadas++;
        Tablero mejor = participantes.get(0);
        asignaciones++;

        lineasEjecutadas++;
        int mejorFitness = calcularFitness(mejor);
        asignaciones++;

        lineasEjecutadas++;
        for (Tablero t : participantes) {
            lineasEjecutadas++;
            int f = calcularFitness(t);
            asignaciones++;

            lineasEjecutadas++;
            comparaciones++;
            if (f < mejorFitness) {
                lineasEjecutadas++;
                mejorFitness = f;
                asignaciones++;

                lineasEjecutadas++;
                mejor = t;
                asignaciones++;
            }
        }

        lineasEjecutadas++;
        return mejor;
    }

    // ==================== POBLACIÓN ====================

    private List<Tablero> crearPoblacionInicial(Tablero tableroBase) {
        lineasEjecutadas++;
        List<Tablero> poblacion = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        int tamaño = tableroBase.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        int tamañoPoblacion = obtenerTamañoPoblacion(tamaño);
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < tamañoPoblacion; i++) {
            comparaciones++;

            lineasEjecutadas++;
            Tablero individuo = tableroBase.copiar();
            asignaciones++;

            lineasEjecutadas++;
            List<Pieza> piezas = individuo.getPiezasDisponibles();
            asignaciones++;

            lineasEjecutadas++;
            Collections.shuffle(piezas);

            lineasEjecutadas++;
            int indice = 0;
            asignaciones++;

            lineasEjecutadas++;
            for (int fila = 0; fila < tamaño; fila++) {
                comparaciones++;

                lineasEjecutadas++;
                for (int columna = 0; columna < tamaño; columna++) {
                    comparaciones++;

                    lineasEjecutadas++;
                    individuo.colocarPieza(fila, columna, piezas.get(indice));

                    lineasEjecutadas++;
                    indice++;
                    asignaciones++;
                }
                comparaciones++;
            }
            comparaciones++;

            lineasEjecutadas++;
            piezas.clear();

            lineasEjecutadas++;
            poblacion.add(individuo);
        }
        comparaciones++;

        lineasEjecutadas++;
        return poblacion;
    }

    // ==================== CONFIGURACIÓN ====================

    private int obtenerTamañoPoblacion(int tamaño) {
        switch (tamaño) {
            case 3:
                return 3;
            case 5:
                return 5;
            case 10:
                return 10;
            case 15:
                return 15;
            case 30:
                return 30;
            default:
                return 30;
        }
    }

    private int obtenerCantidadHijos(int tamaño) {
        switch (tamaño) {
            case 3:
                return 6;
            case 5:
                return 10;
            case 10:
                return 20;
            case 15:
                return 30;
            case 30:
                return 60;
            default:
                return 60;
        }
    }

    // ==================== FITNESS ====================
    // Nota: calcularFitness NO cuenta asignaciones/comparaciones/líneas
    // porque es una función auxiliar de evaluación, no parte del algoritmo
    // principal

    private int calcularFitness(Tablero individuo) {
        int tamaño = individuo.getTamaño();
        int fitness = 0;

        for (int fila = 0; fila < tamaño; fila++) {
            for (int columna = 0; columna < tamaño; columna++) {
                if (!esCompatibleSinContar(individuo, fila, columna)) {
                    fitness++;
                }
            }
        }

        return fitness;
    }

    // Versión de esCompatible que NO cuenta métricas (para calcularFitness)
    private boolean esCompatibleSinContar(Tablero tablero, int fila, int columna) {
        Pieza pieza = tablero.getPieza(fila, columna);
        if (pieza == null)
            return true;

        int tamaño = tablero.getTamaño();

        // Verificar con pieza de arriba
        if (fila > 0) {
            Pieza arriba = tablero.getPieza(fila - 1, columna);
            if (arriba != null && pieza.arriba + arriba.abajo != 0) {
                return false;
            }
        }

        // Verificar con pieza de la izquierda
        if (columna > 0) {
            Pieza izquierdaPieza = tablero.getPieza(fila, columna - 1);
            if (izquierdaPieza != null && pieza.izquierda + izquierdaPieza.derecha != 0) {
                return false;
            }
        }

        // Verificar con pieza de abajo
        if (fila < tamaño - 1) {
            Pieza abajo = tablero.getPieza(fila + 1, columna);
            if (abajo != null && pieza.abajo + abajo.arriba != 0) {
                return false;
            }
        }

        // Verificar con pieza de la derecha
        if (columna < tamaño - 1) {
            Pieza derechaPieza = tablero.getPieza(fila, columna + 1);
            if (derechaPieza != null && pieza.derecha + derechaPieza.izquierda != 0) {
                return false;
            }
        }

        return true;
    }

    // Muestra un tablero en formato compacto para el modo ilustrativo
    private void mostrarTableroInline(Tablero tablero) {
        int tamaño = tablero.getTamaño();

        for (int fila = 0; fila < tamaño; fila++) {
            // Línea superior de cada fila de piezas
            for (int col = 0; col < tamaño; col++) {
                Pieza p = tablero.getPieza(fila, col);
                if (p != null) {
                    System.out.print(String.format("\\%s%d /", espacio(p.arriba), p.arriba));
                } else {
                    System.out.print("\\   /");
                }
                if (col < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea central de cada fila de piezas
            for (int col = 0; col < tamaño; col++) {
                Pieza p = tablero.getPieza(fila, col);
                if (p != null) {
                    System.out.print(String.format("%d%s|%s%d", p.izquierda, espacio(p.izquierda), espacio(p.derecha),
                            p.derecha));
                } else {
                    System.out.print("  |  ");
                }
                if (col < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea inferior de cada fila de piezas
            for (int col = 0; col < tamaño; col++) {
                Pieza p = tablero.getPieza(fila, col);
                if (p != null) {
                    System.out.print(String.format("/%s%d \\", espacio(p.abajo), p.abajo));
                } else {
                    System.out.print("/   \\");
                }
                if (col < tamaño - 1)
                    System.out.print("  ");
            }
            System.out.println();

            // Línea separadora entre filas
            if (fila < tamaño - 1) {
                System.out.println();
            }
        }
    }

    private String espacio(int valor) {
        return valor < 10 ? " " : "";
    }

    // ==================== CRUCE Y MUTACIÓN ====================

    private List<Tablero> cruce3x3(List<Tablero> poblacion) {
        lineasEjecutadas++;
        List<Tablero> hijos = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        Random random = new Random();
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < poblacion.size(); i++) {
            comparaciones++;

            lineasEjecutadas++;
            for (int j = 0; j < poblacion.size(); j++) {
                comparaciones++;

                lineasEjecutadas++;
                comparaciones++;
                if (i != j) {
                    lineasEjecutadas++;
                    Tablero hijo = cruzar(poblacion.get(i), poblacion.get(j), random);
                    asignaciones++;

                    lineasEjecutadas++;
                    mutar(hijo, random);

                    lineasEjecutadas++;
                    hijos.add(hijo);
                }
            }
            comparaciones++;
        }
        comparaciones++;

        lineasEjecutadas++;
        return hijos;
    }

    private List<Tablero> cruceGeneral(List<Tablero> poblacion, int cantidadHijos) {
        lineasEjecutadas++;
        List<Tablero> hijos = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        Random random = new Random();
        asignaciones++;

        lineasEjecutadas++;
        comparaciones++;
        while (hijos.size() < cantidadHijos) {
            lineasEjecutadas++;
            Tablero padre1 = seleccionarPorTorneo(poblacion, random);
            asignaciones++;

            lineasEjecutadas++;
            Tablero padre2 = seleccionarPorTorneo(poblacion, random);
            asignaciones++;

            lineasEjecutadas++;
            Tablero hijo = cruzar(padre1, padre2, random);
            asignaciones++;

            lineasEjecutadas++;
            mutar(hijo, random);

            lineasEjecutadas++;
            hijos.add(hijo);

            lineasEjecutadas++;
            comparaciones++;
        }

        lineasEjecutadas++;
        return hijos;
    }

    private Tablero cruzar(Tablero padre1, Tablero padre2, Random random) {
        lineasEjecutadas++;
        int tamaño = padre1.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        int totalPiezas = tamaño * tamaño;
        asignaciones++;

        lineasEjecutadas++;
        List<Pieza> piezas1 = obtenerPiezasComoLista(padre1);
        asignaciones++;

        lineasEjecutadas++;
        List<Pieza> piezas2 = obtenerPiezasComoLista(padre2);
        asignaciones++;

        lineasEjecutadas++;
        int puntoCruce = random.nextInt(totalPiezas);
        asignaciones++;

        lineasEjecutadas++;
        List<Pieza> piezasHijo = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        for (int i = 0; i < puntoCruce; i++) {
            comparaciones++;
            lineasEjecutadas++;
            piezasHijo.add(piezas1.get(i).copiar());
        }
        comparaciones++;

        lineasEjecutadas++;
        for (int i = puntoCruce; i < totalPiezas; i++) {
            comparaciones++;
            lineasEjecutadas++;
            piezasHijo.add(piezas2.get(i).copiar());
        }
        comparaciones++;

        lineasEjecutadas++;
        Tablero hijo = padre1.copiar();
        asignaciones++;

        lineasEjecutadas++;
        int indice = 0;
        asignaciones++;

        lineasEjecutadas++;
        for (int fila = 0; fila < tamaño; fila++) {
            comparaciones++;

            lineasEjecutadas++;
            for (int col = 0; col < tamaño; col++) {
                comparaciones++;

                lineasEjecutadas++;
                hijo.colocarPieza(fila, col, piezasHijo.get(indice));

                lineasEjecutadas++;
                indice++;
                asignaciones++;
            }
            comparaciones++;
        }
        comparaciones++;

        lineasEjecutadas++;
        hijo.getPiezasDisponibles().clear();

        lineasEjecutadas++;
        return hijo;
    }

    private List<Pieza> obtenerPiezasComoLista(Tablero tablero) {
        lineasEjecutadas++;
        List<Pieza> piezas = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        int tamaño = tablero.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        for (int fila = 0; fila < tamaño; fila++) {
            comparaciones++;

            lineasEjecutadas++;
            for (int col = 0; col < tamaño; col++) {
                comparaciones++;
                lineasEjecutadas++;
                piezas.add(tablero.getPieza(fila, col));
            }
            comparaciones++;
        }
        comparaciones++;

        lineasEjecutadas++;
        return piezas;
    }

    private void mutar(Tablero individuo, Random random) {
        lineasEjecutadas++;
        int tamaño = individuo.getTamaño();
        asignaciones++;

        lineasEjecutadas++;
        int totalPiezas = tamaño * tamaño;
        asignaciones++;

        lineasEjecutadas++;
        int fitnessAntes = calcularFitness(individuo);
        asignaciones++;

        lineasEjecutadas++;
        int pos1 = random.nextInt(totalPiezas);
        asignaciones++;

        lineasEjecutadas++;
        int pos2 = random.nextInt(totalPiezas);
        asignaciones++;

        lineasEjecutadas++;
        comparaciones++;
        while (pos2 == pos1) {
            lineasEjecutadas++;
            pos2 = random.nextInt(totalPiezas);
            asignaciones++;
            lineasEjecutadas++;
            comparaciones++;
        }

        lineasEjecutadas++;
        int fila1 = pos1 / tamaño;
        asignaciones++;

        lineasEjecutadas++;
        int col1 = pos1 % tamaño;
        asignaciones++;

        lineasEjecutadas++;
        int fila2 = pos2 / tamaño;
        asignaciones++;

        lineasEjecutadas++;
        int col2 = pos2 % tamaño;
        asignaciones++;

        lineasEjecutadas++;
        Pieza temp = individuo.getPieza(fila1, col1);
        asignaciones++;

        lineasEjecutadas++;
        individuo.colocarPieza(fila1, col1, individuo.getPieza(fila2, col2));

        lineasEjecutadas++;
        individuo.colocarPieza(fila2, col2, temp);

        lineasEjecutadas++;
        int fitnessDespues = calcularFitness(individuo);
        asignaciones++;

        lineasEjecutadas++;
        comparaciones++;
        if (fitnessDespues >= fitnessAntes) {
            lineasEjecutadas++;
            temp = individuo.getPieza(fila1, col1);
            asignaciones++;

            lineasEjecutadas++;
            individuo.colocarPieza(fila1, col1, individuo.getPieza(fila2, col2));

            lineasEjecutadas++;
            individuo.colocarPieza(fila2, col2, temp);
        }
    }

    // ==================== MÉTODOS ILUSTRATIVOS ====================

    private List<Tablero> cruce3x3Ilustrativo(List<Tablero> poblacion) {
        lineasEjecutadas++;
        List<Tablero> hijos = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        Random random = new Random();
        asignaciones++;

        int contadorCruce = 0;

        lineasEjecutadas++;
        for (int i = 0; i < poblacion.size(); i++) {
            comparaciones++;

            lineasEjecutadas++;
            for (int j = 0; j < poblacion.size(); j++) {
                comparaciones++;

                lineasEjecutadas++;
                comparaciones++;
                if (i != j) {
                    contadorCruce++;
                    Tablero padre1 = poblacion.get(i);
                    Tablero padre2 = poblacion.get(j);
                    int fitnessPadre1 = calcularFitness(padre1);
                    int fitnessPadre2 = calcularFitness(padre2);

                    lineasEjecutadas++;
                    Tablero hijo = cruzar(padre1, padre2, random);
                    asignaciones++;

                    int fitnessHijoAntes = calcularFitness(hijo);
                    int fitnessHijoDespues = fitnessHijoAntes;

                    // Intentar mutar
                    int tamañoHijo = hijo.getTamaño();
                    int totalPiezas = tamañoHijo * tamañoHijo;
                    int pos1 = random.nextInt(totalPiezas);
                    int pos2 = random.nextInt(totalPiezas);
                    while (pos2 == pos1)
                        pos2 = random.nextInt(totalPiezas);

                    int fila1 = pos1 / tamañoHijo, col1 = pos1 % tamañoHijo;
                    int fila2 = pos2 / tamañoHijo, col2 = pos2 % tamañoHijo;

                    Pieza temp = hijo.getPieza(fila1, col1);
                    hijo.colocarPieza(fila1, col1, hijo.getPieza(fila2, col2));
                    hijo.colocarPieza(fila2, col2, temp);

                    fitnessHijoDespues = calcularFitness(hijo);
                    boolean muto = fitnessHijoDespues < fitnessHijoAntes;

                    if (!muto) {
                        // Revertir
                        temp = hijo.getPieza(fila1, col1);
                        hijo.colocarPieza(fila1, col1, hijo.getPieza(fila2, col2));
                        hijo.colocarPieza(fila2, col2, temp);
                        fitnessHijoDespues = fitnessHijoAntes;
                    }

                    System.out.println();
                    System.out.println("============================================");
                    System.out.println("               CRUCE #" + contadorCruce);
                    System.out.println("============================================");

                    System.out.println();
                    System.out.println("PADRE " + (i + 1) + " (fitness=" + fitnessPadre1 + "):");
                    mostrarTableroInline(padre1);

                    System.out.println();
                    System.out.println("PADRE " + (j + 1) + " (fitness=" + fitnessPadre2 + "):");
                    mostrarTableroInline(padre2);

                    System.out.println();
                    System.out.println("Hijo fitness antes de mutar: " + fitnessHijoAntes);
                    if (muto) {
                        System.out.println(
                                "Mutación APLICADA: fitness " + fitnessHijoAntes + " -> " + fitnessHijoDespues);
                    } else {
                        System.out.println("Mutación REVERTIDA (no mejoró)");
                    }

                    System.out.println();
                    System.out.println("HIJO (fitness=" + fitnessHijoDespues + "):");
                    mostrarTableroInline(hijo);

                    lineasEjecutadas++;
                    hijos.add(hijo);
                }
            }
            comparaciones++;
        }
        comparaciones++;

        lineasEjecutadas++;
        return hijos;
    }

    private List<Tablero> cruceGeneralIlustrativo(List<Tablero> poblacion, int cantidadHijos) {
        lineasEjecutadas++;
        List<Tablero> hijos = new java.util.ArrayList<>();
        asignaciones++;

        lineasEjecutadas++;
        Random random = new Random();
        asignaciones++;

        int contadorCruce = 0;

        lineasEjecutadas++;
        comparaciones++;
        while (hijos.size() < cantidadHijos) {
            contadorCruce++;

            lineasEjecutadas++;
            Tablero padre1 = seleccionarPorTorneo(poblacion, random);
            asignaciones++;

            lineasEjecutadas++;
            Tablero padre2 = seleccionarPorTorneo(poblacion, random);
            asignaciones++;

            int fitnessPadre1 = calcularFitness(padre1);
            int fitnessPadre2 = calcularFitness(padre2);

            lineasEjecutadas++;
            Tablero hijo = cruzar(padre1, padre2, random);
            asignaciones++;

            int fitnessHijoAntes = calcularFitness(hijo);
            int fitnessHijoDespues = fitnessHijoAntes;

            // Intentar mutar
            int tamañoHijo = hijo.getTamaño();
            int totalPiezas = tamañoHijo * tamañoHijo;
            int pos1 = random.nextInt(totalPiezas);
            int pos2 = random.nextInt(totalPiezas);
            while (pos2 == pos1)
                pos2 = random.nextInt(totalPiezas);

            int fila1 = pos1 / tamañoHijo, col1 = pos1 % tamañoHijo;
            int fila2 = pos2 / tamañoHijo, col2 = pos2 % tamañoHijo;

            Pieza temp = hijo.getPieza(fila1, col1);
            hijo.colocarPieza(fila1, col1, hijo.getPieza(fila2, col2));
            hijo.colocarPieza(fila2, col2, temp);

            fitnessHijoDespues = calcularFitness(hijo);
            boolean muto = fitnessHijoDespues < fitnessHijoAntes;

            if (!muto) {
                // Revertir
                temp = hijo.getPieza(fila1, col1);
                hijo.colocarPieza(fila1, col1, hijo.getPieza(fila2, col2));
                hijo.colocarPieza(fila2, col2, temp);
                fitnessHijoDespues = fitnessHijoAntes;
            }

            System.out.println();
            System.out.println("============================================");
            System.out.println("               CRUCE #" + contadorCruce);
            System.out.println("============================================");

            System.out.println();
            System.out.println("PADRE A (fitness=" + fitnessPadre1 + "):");
            mostrarTableroInline(padre1);

            System.out.println();
            System.out.println("PADRE B (fitness=" + fitnessPadre2 + "):");
            mostrarTableroInline(padre2);

            System.out.println();
            System.out.println("Hijo fitness antes de mutar: " + fitnessHijoAntes);
            if (muto) {
                System.out.println("Mutación APLICADA: fitness " + fitnessHijoAntes + " -> " + fitnessHijoDespues);
            } else {
                System.out.println("Mutación REVERTIDA (no mejoró)");
            }

            System.out.println();
            System.out.println("HIJO (fitness=" + fitnessHijoDespues + "):");
            mostrarTableroInline(hijo);

            lineasEjecutadas++;
            hijos.add(hijo);

            lineasEjecutadas++;
            comparaciones++;
        }

        lineasEjecutadas++;
        return hijos;
    }
}
