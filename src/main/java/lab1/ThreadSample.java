package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import utils.ArrayUtils;

public class ThreadSample {

    static double calculateNorm(List<Double> array) {
        double result = 0.0;
        for(double num : array) {
            if(result < Math.abs(num)) {
                result = num;
            }
        }
        return result;
    }

    static void calculateParallel(int number, List<Double> randomArray) throws InterruptedException {
        ThreadCalc[] threads = new ThreadCalc[number];
        int elementsPerThread = randomArray.size() / threads.length;
        int j = 0;
        for(int i = 0; i < threads.length; i++) {
            if (j + elementsPerThread >= randomArray.size()) {
                threads[i] = new ThreadCalc(randomArray, j, (randomArray.size() - 1));
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
        List<Double> resultList = new ArrayList<>();
        for(int i = 0; i < threads.length; i++) {
            resultList.add(threads[i].getResult());
        }
        double result = calculateNorm(resultList);
        System.out.printf("Result with %s threads: %s time : %sms.%n", threads.length, result, (System.currentTimeMillis() - timeBefore));
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.print("Input array size: ");
        Scanner scanner = new Scanner(System.in);
        List<Double> randomArray = ArrayUtils.getRandomArrayDouble(scanner.nextInt());

        System.out.println();
        long timeBefore = System.currentTimeMillis();
        double resultSingle = calculateNorm(randomArray);
        System.out.printf("Result with single thread: %s time : %sms.%n\n", resultSingle, (System.currentTimeMillis() - timeBefore));

        for(int i = 1; i <= 50; i++) {
            calculateParallel(i, randomArray);
        }
    }
}
