package lab4;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public final class AsyncFuncUtils {

    private AsyncFuncUtils() {
    }

    private static Integer calculateAverageValue(List<Integer> list) {
        AtomicInteger sum = new AtomicInteger();
        list.stream().parallel()
                .forEach(num -> {
                    int oldValue;
                    int newValue;
                    do {
                        oldValue = sum.get();
                        newValue = oldValue + num;
                    } while(!sum.compareAndSet(oldValue, newValue));
                });
        int average = sum.get() / list.size();
        System.out.println("Average: " + average);
        return average;
    }

    public static CompletableFuture<List<Integer>> removeElementsLessThenMiddleValue(List<Integer> list) {
        Integer average = calculateAverageValue(list);
        return CompletableFuture.supplyAsync(() -> list)
                .thenApplyAsync(l -> l.stream()
                        .sorted()
                        .filter(num -> num >= average)
                        .collect(Collectors.toList()));
    }

    public static CompletableFuture<List<Integer>> removeElementsMoreThenMiddleValue(List<Integer> list) {
        Integer average = calculateAverageValue(list);
        return CompletableFuture.supplyAsync(() -> list)
                .thenApplyAsync(l -> l.stream()
                        .sorted()
                        .filter(num -> num <= average)
                        .collect(Collectors.toList()));
    }

    public static CompletableFuture<Set<Integer>> mergeFuturesAndGetResult(CompletableFuture<List<Integer>> firstFuture, CompletableFuture<List<Integer>> secondFuture) {
        return firstFuture.thenCombine(secondFuture, (first, second) -> {
            CopyOnWriteArraySet<Integer> resultList = new CopyOnWriteArraySet<>();
            System.out.println("First: " + first);
            System.out.println("Second: " + second);
            first.stream().parallel().forEach(num -> {
                int binarySearchResult = Collections.binarySearch(second, num);
                if (binarySearchResult > 0) {
                    resultList.add(num);
                }
            });
            return resultList;
        });
    }

}
