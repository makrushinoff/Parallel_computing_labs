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
        CompletableFuture<List<Integer>> firstFuture = AsyncFuncUtils.removeElementsLessThenMiddleValue(list1);
        CompletableFuture<List<Integer>> secondFuture = AsyncFuncUtils.removeElementsMoreThenMiddleValue(list2);
        Set<Integer> result = AsyncFuncUtils.mergeFuturesAndGetResult(firstFuture, secondFuture).get();
        System.out.println(result);
    }

}
