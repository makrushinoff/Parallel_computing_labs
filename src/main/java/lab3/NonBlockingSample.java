package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import utils.ArrayUtils;

public class NonBlockingSample {

    public static final int DEFAULT_ARRAY_SIZE = 90000;

    public static long sumMultipleOfTenNonBlocking(int[] array) {
        AtomicLong sum = new AtomicLong();
        Arrays.stream(array).parallel()
                .forEach(num -> {
                    if(num % 10 == 0) {
                        long oldValue;
                        long newValue;
                        do {
                            oldValue = sum.get();
                            newValue = num + oldValue;
                        } while(!sum.compareAndSet(oldValue, newValue));
                    }
                });
        return sum.get();
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Use default array size(90000) or else[Y/n]: ");
        String ans = scanner.next();
        int arraySize;
        if(ans.contains("Y")) {
            arraySize = DEFAULT_ARRAY_SIZE;
        } else {
            System.out.print("Input array size: ");
            arraySize = scanner.nextInt();
        }
        int[] numbers = ArrayUtils.getRandomArrayIntegerPrimitive(arraySize);
        long currentTime = System.currentTimeMillis();
        long sumNonBlocking = sumMultipleOfTenNonBlocking(numbers);
        System.out.println("Sum non blocking: " + sumNonBlocking);
        long resultTime = System.currentTimeMillis() - currentTime;
        System.out.println("Time for execution: " + resultTime);

        System.out.print("\nInput number of threads: ");
        final int numberOfThreads = scanner.nextInt();
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        List<ThreadCalc> threads = new ArrayList<>();
        int elementsPerThread = DEFAULT_ARRAY_SIZE / numberOfThreads;
        int j = 0;
        for(int i = 0; i < numberOfThreads; i++) {
            if (j + elementsPerThread >= DEFAULT_ARRAY_SIZE) {
                threads.add(new ThreadCalc(numbers, j, (DEFAULT_ARRAY_SIZE - 1)));
            } else {
                threads.add(new ThreadCalc(numbers, j, (j + elementsPerThread) - 1));
            }
            j += elementsPerThread;
        }
        currentTime = System.currentTimeMillis();
        List<Future<Long>> futures = executorService.invokeAll(threads);
        executorService.shutdown();
        boolean waitingIsSuccessful = executorService.awaitTermination(10, TimeUnit.SECONDS);
        resultTime = System.currentTimeMillis() - currentTime;
        if(!waitingIsSuccessful) {
            throw new RuntimeException("Timeout of waiting for tasks execution");
        }

        List<Long> results = new ArrayList<>();
        for(Future<Long> future : futures) {
            results.add(future.get());
        }
        long sumMultiThreads = results.stream().mapToLong(num -> num).sum();
        System.out.println("MultiThread result: " + sumMultiThreads);
        System.out.println("Time for execution: " + resultTime);
        long sum = 0;
        currentTime = System.currentTimeMillis();
        for(long num : numbers) {
            if(num % 10 == 0) {
                sum += num;
            }
        }
        resultTime = System.currentTimeMillis() - currentTime;
        System.out.println("\nSingle thread result : " + sum);
        System.out.println("Time for execution: " + resultTime);
    }

}
