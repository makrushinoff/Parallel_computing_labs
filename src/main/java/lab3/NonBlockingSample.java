package lab3;

import java.util.Arrays;
import java.util.List;

import lab3.utils.NotBlockingFunctionsUtils;
import utils.ArrayUtils;

public class NonBlockingSample {

    private static final int DEFAULT_ARRAY_SIZE = 90000;

    public static void main(String[] args) throws InterruptedException {
        long[] numbers = ArrayUtils.getRandomArrayLong(DEFAULT_ARRAY_SIZE);
        System.out.println("Xor sum: " + NotBlockingFunctionsUtils.xorArray(ArrayUtils.getRandomArrayInteger(DEFAULT_ARRAY_SIZE)));
        System.out.println("Max element: " + NotBlockingFunctionsUtils.maxElement(numbers));
        System.out.println("Min element: " + NotBlockingFunctionsUtils.minElement(numbers));
        System.out.println("Mode: " + NotBlockingFunctionsUtils.mode(numbers));
        System.out.println("Median: " + NotBlockingFunctionsUtils.median(numbers));
        System.out.println("Scalar product: " + NotBlockingFunctionsUtils.scalarProduct(Arrays.asList(ArrayUtils.getRandomArrayInteger(DEFAULT_ARRAY_SIZE)),
                                                    Arrays.asList(ArrayUtils.getRandomArrayInteger(DEFAULT_ARRAY_SIZE))));
    }

}
