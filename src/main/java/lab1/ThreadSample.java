package lab1;

import java.util.Random;
import java.util.Scanner;

public class ThreadSample {

    static double[] getRandomArray(int arraySize) {
        double[] array = new double[arraySize];
        Random random = new Random();
        for(int i = 0; i < arraySize; i++) {
            array[i] = random.nextDouble() * 100;
        }
        return array;
    }

    static double calculateNorm(double[] array) {
        double result = 0.0;
        for(double num : array) {
            result += Math.pow(Math.abs(num), 2);
        }
        return Math.sqrt(result);
    }

    static void calc(int number, double[] randomArray) throws InterruptedException {
        ThreadCalc[] threads = new ThreadCalc[number];
        int elementsPerThread = randomArray.length / threads.length;
        int j = 0;
        for(int i = 0; i < threads.length; i++) {
            if (j + elementsPerThread >= randomArray.length) {
                threads[i] = new ThreadCalc(randomArray, j, (randomArray.length - 1));
            } else {
                threads[i] = new ThreadCalc(randomArray, j, (j + elementsPerThread) - 1);
            }
            j += elementsPerThread;
        }
        long timeBefore = System.currentTimeMillis();
        for (int i = 0; i < threads.length; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
        double result = 0.0;
        for(int i = 0; i < threads.length; i++) {
            result += threads[i].getResult();
        }
        result = Math.sqrt(result);
        System.out.printf("Result with %s threads: %s time : %sms.%n", threads.length, result, (System.currentTimeMillis() - timeBefore));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Input array size: ");
        Scanner scanner = new Scanner(System.in);
        double[] randomArray = getRandomArray(scanner.nextInt());

        System.out.println();
        long timeBefore = System.currentTimeMillis();
        double resultSingle = calculateNorm(randomArray);
        System.out.printf("Result with single thread: %s time : %sms.%n\n", resultSingle, (System.currentTimeMillis() - timeBefore));

        for(int i = 1; i <= 50; i++) {
            calc(i, randomArray);
        }
    }
}
