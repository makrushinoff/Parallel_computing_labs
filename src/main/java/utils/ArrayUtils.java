package utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public final class ArrayUtils {

    private static final Random random = new Random();

    private ArrayUtils() {}

    public static List<Double> getRandomArrayDouble(int arraySize) {
        List<Double> array = new ArrayList<>();
        for(int i = 0; i < arraySize; i++) {
            array.add(random.nextDouble() * 100);
        }
        return array;
    }

    public static Integer[] getRandomArrayInteger(int arraySize) {
        Integer[] array = new Integer[arraySize];
        for(int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt() * 100;
        }
        return array;
    }

    public static Integer[] getLittleRandomArrayInteger(int arraySize) {
        Integer[] array = new Integer[arraySize];
        for(int i = 0; i < arraySize; i++) {
            array[i] = (int) (Math.random() * 100);
        }
        return array;
    }

    public static long[] getRandomArrayLong(int arraySize) {
        long[] array = new long[arraySize];
        for(int i = 0; i < arraySize; i++) {
            array[i] = random.nextLong() * 100;
        }
        return array;
    }

    public static void printArray(long[] array) {
        StringJoiner stringJoiner = new StringJoiner(", ", "Array[", "]");
        for(long num : array) {
            stringJoiner.add(String.valueOf(num));
        }
        System.out.println(stringJoiner);
    }
}
