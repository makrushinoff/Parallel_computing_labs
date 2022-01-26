package lab4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public final class AsyncFuncUtils {

    private AsyncFuncUtils() {
    }

    public static Integer calculateMaxValue(List<Integer> list) {
        return list.stream().parallel().max(Integer::compareTo).get();
    }


    public static CompletableFuture<Set<Integer>> mergeFuturesAndGetResult(CompletableFuture<List<Integer>> firstFuture,
                                                                            CompletableFuture<List<Integer>> secondFuture,
                                                                            CompletableFuture<List<Integer>> thirdFuture) {
        return secondFuture.thenCombine(thirdFuture, (second, third) -> {
            List<Integer> firstArray = new ArrayList<>();
            try {
                firstArray = firstFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            CopyOnWriteArraySet<Integer> resultList = new CopyOnWriteArraySet<>();
            System.out.println("First: " + firstArray);
            System.out.println("Second: " + second);
            System.out.println("Third: " + third);

            List<Integer> finalFirstArray = firstArray;
            second.stream().parallel().forEach(num -> {
                if (Collections.binarySearch(third, num) >= 0 && Collections.binarySearch(finalFirstArray, num) < 0) {
                    resultList.add(num);
                }
            });
            return resultList;
        });
    }

    public static CompletableFuture<List<Integer>> multiplyNumberBy2(List<Integer> numbers) {
        return CompletableFuture.supplyAsync(() -> numbers)
                .thenApplyAsync(list -> list.stream()
                        .sorted()
                        .map(num -> num * 2)
                        .collect(Collectors.toList()));
    }

    public static CompletableFuture<List<Integer>> filterOddNumbers(List<Integer> numbers) {
        return CompletableFuture.supplyAsync(() -> numbers)
                .thenApplyAsync(list -> list.stream()
                        .sorted()
                        .filter(num -> num % 2 == 0)
                        .collect(Collectors.toList()));
    }

    public static CompletableFuture<List<Integer>> filterNumbersInRelationToMaxValue(List<Integer> numbers) {
        Integer maxValue = calculateMaxValue(numbers);
        return CompletableFuture.supplyAsync(() -> numbers)
                .thenApplyAsync(list -> list.stream()
                        .sorted()
                        .filter(num -> (num <= maxValue * 0.6 && num >= maxValue * 0.6))
                        .collect(Collectors.toList()));
    }

}
