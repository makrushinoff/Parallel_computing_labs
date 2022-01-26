package lab4;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import utils.ArrayUtils;

public class AsyncSample {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<Integer> list1 = Arrays.asList(ArrayUtils.getLittleRandomArrayInteger(100));
        List<Integer> list2 = Arrays.asList(ArrayUtils.getLittleRandomArrayInteger(100));
        List<Integer> list3 = Arrays.asList(ArrayUtils.getLittleRandomArrayInteger(100));
        final CompletableFuture<List<Integer>> completableFuture1 = AsyncFuncUtils.multiplyNumberBy2(list1);
        final CompletableFuture<List<Integer>> completableFuture2 = AsyncFuncUtils.filterOddNumbers(list2);
        final CompletableFuture<List<Integer>> completableFuture3 = AsyncFuncUtils.filterNumbersInRelationToMaxValue(list3);
        Set<Integer> result = AsyncFuncUtils.mergeFuturesAndGetResult(completableFuture1, completableFuture2, completableFuture3).get();
        System.out.println(result);
    }

}
