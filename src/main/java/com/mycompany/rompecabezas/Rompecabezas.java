/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.rompecabezas;

import java.util.Scanner;

/**
 *
 * @author andre
 */
public class Rompecabezas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tamañoTablero = pedirTamanoTablero(scanner);
        int maxValorPieza = pedirRangoValores(scanner);

        System.out.println();
        System.out.println("Configuración seleccionada:");
        System.out.println("- Tamaño del tablero: " + tamañoTablero + "x" + tamañoTablero);
        System.out.println("- Valores en las piezas: 0 a " + maxValorPieza);

        // Crear y mostrar el tablero
        Tablero tablero = new Tablero(tamañoTablero, maxValorPieza);
        tablero.mostrar();

        scanner.close();
    }

    private static int pedirTamanoTablero(Scanner scanner) {
        int opcion;
        int[] opcionesTamano = { 3, 5, 10, 15, 30, 60, 100 };

        while (true) {
            System.out.println("Seleccione el tamaño del tablero:");
            System.out.println("1. 3x3");
            System.out.println("2. 5x5");
            System.out.println("3. 10x10");
            System.out.println("4. 15x15");
            System.out.println("5. 30x30");
            System.out.println("6. 60x60");
            System.out.println("7. 100x100");
            System.out.print("Opción (1-7): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion >= 1 && opcion <= 7) {
                    return opcionesTamano[opcion - 1];
                }
            } else {
                scanner.next(); // limpiar entrada no numérica
            }

            System.out.println("Opción no válida. Intente de nuevo.\n");
        }
    }

    private static int pedirRangoValores(Scanner scanner) {
        int opcion;

        while (true) {
            System.out.println();
            System.out.println("Seleccione el rango de valores para las piezas:");
            System.out.println("1. Valores de 0 a 9");
            System.out.println("2. Valores de 0 a 15");
            System.out.print("Opción (1-2): ");

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion == 1) {
                    return 9;
                } else if (opcion == 2) {
                    return 15;
                }
            } else {
                scanner.next(); // limpiar entrada no numérica
            }

            System.out.println("Opción no válida. Intente de nuevo.\n");
        }
    }
}
