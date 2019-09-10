package nqueens;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author _
 */
public class Optimized {

    public static void main(String[] args) {
        System.out.println("El problema de las N reinas");
        System.out.println("Inserte cantidad de reinas:");
        int n = new Scanner(System.in).nextInt();
        if (n < 4) {
            System.out.println("No hay solucion para aquellos tableros menores a 4 (excepto 1x1 por obvias razones)\nIntente con 4 en adelante");
        } else {
            int[] randoms = getRandomVector(n);
            for (int num : randoms) {
                System.out.print(" " + num);
            }
            System.out.println("");
            System.out.println("Tablero original:");
            printBoard(getBoard(randoms));
            System.out.println("");
            System.out.println("H: " + getH(randoms));
            int[] solution = getSolution(randoms);
            System.out.println("\n");
            System.out.println("Tablero con menor cantidad de colisiones posibles: ");
            printBoard(getBoard(solution));
            System.out.println("H: " + getH(solution));
        }
    }

    /**
     * Devuelve un vector con una permutacion de n reinas
     *
     * @param queens la cantidad de reinas en el tablero
     * @return las posiciones aleatorias
     */
    public static int[] getRandomVector(int queens) {
        int[] randoms;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < queens; i++) {
            list.add(i);
        }
        java.util.Collections.shuffle(list);
        randoms = list.stream()
                .mapToInt(Integer::intValue)
                .toArray();
        return randoms;
    }

    /**
     * Devuelve una matriz de n*n a partir de un vector con longitud n
     *
     * @param vector el vector descriptivo
     * @return el tablero en matriz
     */
    public static int[][] getBoard(int[] vector) {
        int[][] board = new int[vector.length][vector.length];
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                if (vector[i] == j) {
                    board[i][j] = 1;
                } else {
                    board[i][j] = 0;
                }
            }
        }
        return board;
    }

    /**
     * Imprime el tablero dada una matriz
     *
     * @param board la matriz a imprimir
     */
    public static void printBoard(int[][] board) {
        for (int[] row : board) {
            for (int num : row) {
                System.out.print(" " + num);
            }
            System.out.println(" ");
        }
    }

    /**
     * Cantidad de colisiones entre reinas
     *
     * @param vector las posiciones de las reinas
     * @return h las colisiones
     */
    public static int getH(int[] vector) {
        int h = 0;
        for (int i = 0; i < vector.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                if (i != j) {
                    if (i - vector[i] == j - vector[j]
                            || i + vector[i] == j + vector[j]
                            || vector[i] - vector[j] == i - j
                            || vector[i] - vector[j] == j - i) {
                        h++;
                    }
                }
            }
        }
        return h;
    }

    /**
     * Devuelve la mejor solucion posible dado un vector random
     *
     * @param original
     * @return la mejor solución posible
     */
    private static int[] getSolution(int[] original) {
        int iteraciones = 0;
        int[] solved = new int[original.length];
        int[] actual = original;
        boolean climb = true;
        while (climb) {
            if (getH(actual) > 0 && iteraciones < 50) {
                int[] anterior = actual;
                actual = getRandomVector(actual.length);
                if (getH(anterior) < getH(actual)) {
                    actual = anterior;
                }
                iteraciones++;
            } else {
                solved = actual;
                climb = false;
            }
        }
        System.out.println("Iteraciones: " + iteraciones);
        return solved;
    }

}
