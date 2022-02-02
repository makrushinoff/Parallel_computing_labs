package lab1;

import java.util.Arrays;
import java.util.Scanner;

import utils.ArrayUtils;

public class ThreadSample {

    static void calculateSingleThread(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            int rowSum = 0;
            for(int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j] != matrix[i][i]) {
                    rowSum += matrix[i][j];
                }
            }
            matrix[i][i] = rowSum;
        }
    }

    static int[] getMainDiagonal(int[][] matrix) {
        int[] diagonal = new int[matrix.length];
        for(int i = 0; i < matrix.length; i++) {
            diagonal[i] = matrix[i][i];
        }
        return diagonal;
    }

    static void calculateParallel(int number, int[][] matrix) throws InterruptedException {
        ThreadCalc[] threads = new ThreadCalc[number];
        int elementsPerThread = matrix.length / threads.length;
        int j = 0;
        for(int i = 0; i < threads.length; i++) {
            if (j + elementsPerThread >= matrix.length) {
                threads[i] = new ThreadCalc(matrix, j, (matrix.length - 1));
            } else {
                threads[i] = new ThreadCalc(matrix, j, (j + elementsPerThread) - 1);
            }
            j += elementsPerThread;
        }
        long timeBefore = System.currentTimeMillis();
        for (ThreadCalc thread : threads) {
            thread.start();
        }
        for (ThreadCalc thread : threads) {
            thread.join();
        }
        System.out.printf("Result with %s threads: \n%s\nTime : %sms.%n", threads.length,
                Arrays.toString(getMainDiagonal(matrix)), (System.currentTimeMillis() - timeBefore));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Input matrix dimension: ");
        Scanner scanner = new Scanner(System.in);
        final int matrixDimension = scanner.nextInt();
        int[][] matrix = new int[matrixDimension][matrixDimension];
        for(int i = 0; i < matrixDimension; i++) {
            matrix[i] = ArrayUtils.getRandomArrayIntegerPrimitive(matrixDimension);
        }
        int[][] matrix2 = Arrays.stream(matrix)
                .map(int[]::clone)
                .toArray(int[][]::new);
        long timeBefore = System.currentTimeMillis();
        calculateSingleThread(matrix);
        System.out.printf("Result with single thread: \n%s \nTime : %sms.%n\n", Arrays.toString(getMainDiagonal(matrix)), (System.currentTimeMillis() - timeBefore));
        for(int i = 1; i <= 50; i++) {
            calculateParallel(i, matrix2);
        }
    }
}
