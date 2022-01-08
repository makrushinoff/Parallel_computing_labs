package lab3;

import java.util.Arrays;
import java.util.List;

import lab3.utils.NotBlockingFunctionsUtils;
import utils.ArrayUtils;

public class NonBlockingSample {

    public static void main(String[] args) throws InterruptedException {
        long[] numbers = ArrayUtils.getRandomArrayLong(100);
//        long[] numbers = {1L, 2L, 3L, 4L, 5L, 6L};
        System.out.println("Xor sum: " + NotBlockingFunctionsUtils.xorArray(ArrayUtils.getRandomArrayInteger(10)));
        System.out.println("Max element: " + NotBlockingFunctionsUtils.maxElement(numbers));
        System.out.println("Min element: " + NotBlockingFunctionsUtils.minElement(numbers));
        System.out.println("Mode: " + NotBlockingFunctionsUtils.mode(numbers));
        System.out.println("Median: " + NotBlockingFunctionsUtils.median(numbers));
        System.out.println("Scalar product: " + NotBlockingFunctionsUtils.scalarProduct(Arrays.asList(ArrayUtils.getRandomArrayInteger(100000)), Arrays.asList(ArrayUtils.getRandomArrayInteger(100000))));
    }

}
